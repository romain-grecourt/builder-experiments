package com.acme.demo.api;


import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import io.helidon.common.LazyValue;

import com.acme.tls.NotReloadableKeyManager;
import com.acme.tls.ReloadableX509KeyManager;
import com.acme.tls.ReloadableX509TrustManager;
import com.acme.tls.NotReloadableTrustManager;
import com.acme.configurable.ConfiguredTypeBase;
import com.acme.tls.TlsClientAuth;
import com.acme.tls.TlsReloadableComponent;
import com.acme.tls.TrustAllManagerFactory;

/**
 * Tls.
 */
public class Tls extends ConfiguredTypeBase<TlsConfig> {

    /**
     * HTTPS endpoint identification algorithm, verifies certificate cn against host name.
     */
    public static final String ENDPOINT_IDENTIFICATION_HTTPS = "HTTPS";
    /**
     * Disable host name verification.
     */
    public static final String ENDPOINT_IDENTIFICATION_NONE = "NONE";

    // secure random cannot be stored in native image, it must
    // be initialized at runtime
    private static final LazyValue<SecureRandom> RANDOM = LazyValue.create(SecureRandom::new);

    private final SSLContext sslContext;
    private final SSLParameters sslParameters;
    private final SSLSocketFactory sslSocketFactory;
    private final SSLServerSocketFactory sslServerSocketFactory;
    private final List<TlsReloadableComponent> reloadableComponents;
    private final X509TrustManager originalTrustManager;
    private final X509KeyManager originalKeyManager;
    private final boolean enabled;

    Tls(TlsPrototype prototype) {
        super(prototype);
        TlsInitializer initializer = new TlsInitializer(prototype).init();
        sslContext = initializer.sslContext;
        sslParameters = initializer.sslParameters;
        sslSocketFactory = initializer.sslSocketFactory;
        sslServerSocketFactory = initializer.sslServerSocketFactory;
        reloadableComponents = initializer.reloadableComponents;
        originalTrustManager = initializer.originalTrustManager;
        originalKeyManager = initializer.originalKeyManager;
        enabled = prototype.config().enabled();
    }

    /**
     * SSL engine from this configuration.
     *
     * @return SSL Engine
     */
    public final SSLEngine newEngine() {
        SSLEngine sslEngine = sslContext.createSSLEngine();
        sslEngine.setSSLParameters(sslParameters);
        return sslEngine;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(sslContext) + hashCode(sslParameters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tls tls)) {
            return false;
        }

        return sslContext.equals(tls.sslContext) && equals(sslParameters, tls.sslParameters);
    }

    /**
     * Create a TLS socket for a server.
     *
     * @return a new server socket ready for TLS communication
     */
    public SSLServerSocket createServerSocket() {
        try {
            SSLServerSocket socket = (SSLServerSocket) sslServerSocketFactory.createServerSocket();
            socket.setSSLParameters(sslParameters);
            return socket;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Create a socket for the chosen protocol.
     *
     * @param alpnProtocol protocol to use
     * @return a new socket ready for TLS communication
     */
    public SSLSocket createSocket(String alpnProtocol) {
        try {
            SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket();
            sslParameters.setApplicationProtocols(new String[]{alpnProtocol});
            socket.setSSLParameters(sslParameters);
            return socket;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * SSL context based on the configured values.
     *
     * @return SSL context
     */
    public SSLContext sslContext() {
        return sslContext;
    }

    /**
     * SSL parameters.
     *
     * @return SSL parameters
     */
    public SSLParameters sslParameters() {
        return sslParameters;
    }

    public X509KeyManager originalKeyManager() {
        return originalKeyManager;
    }

    public X509TrustManager originalTrustManager() {
        return originalTrustManager;
    }

    /**
     * Reload reloadable TLS components with the new configuration.
     *
     * @param tls new TLS configuration
     */
    public void reload(Tls tls) {
        for (TlsReloadableComponent reloadableComponent : reloadableComponents) {
            reloadableComponent.reload(tls);
        }
    }

    /**
     * Whether this TLS configuration is enabled or not.
     *
     * @return whether TLS is enabled
     */
    public boolean enabled() {
        return enabled;
    }

    private static int hashCode(SSLParameters first) {
        int result = Objects.hash(first.getAlgorithmConstraints(),
                first.getEnableRetransmissions(),
                first.getEndpointIdentificationAlgorithm(),
                first.getMaximumPacketSize(),
                first.getNeedClientAuth(),
                first.getUseCipherSuitesOrder(),
                first.getWantClientAuth(),
                first.getServerNames(),
                first.getSNIMatchers());
        result = 31 * result + Arrays.hashCode(first.getApplicationProtocols());
        result = 31 * result + Arrays.hashCode(first.getCipherSuites());
        result = 31 * result + Arrays.hashCode(first.getProtocols());

        return result;
    }

    private static boolean equals(SSLParameters first, SSLParameters second) {
        return first.getAlgorithmConstraints().equals(second.getAlgorithmConstraints())
                && Arrays.equals(first.getApplicationProtocols(), second.getApplicationProtocols())
                && Arrays.equals(first.getCipherSuites(), second.getCipherSuites())
                && (first.getEnableRetransmissions() == second.getEnableRetransmissions())
                && Objects.equals(first.getEndpointIdentificationAlgorithm(), second.getEndpointIdentificationAlgorithm())
                && (first.getMaximumPacketSize() == second.getMaximumPacketSize())
                && (first.getNeedClientAuth() == second.getNeedClientAuth())
                && Arrays.equals(first.getProtocols(), second.getProtocols())
                && (first.getUseCipherSuitesOrder() == second.getUseCipherSuitesOrder())
                && (first.getWantClientAuth() == second.getWantClientAuth())
                && first.getServerNames().equals(second.getServerNames())
                && first.getSNIMatchers().equals(second.getSNIMatchers());
    }

    private static class TlsInitializer {

        private final TlsPrototype prototype;
        private SSLContext sslContext;
        private SSLParameters sslParameters;
        private SSLSocketFactory sslSocketFactory;
        private SSLServerSocketFactory sslServerSocketFactory;
        private List<TlsReloadableComponent> reloadableComponents;
        private X509TrustManager originalTrustManager;
        private X509KeyManager originalKeyManager;

        private TlsInitializer(TlsPrototype prototype) {
            this.prototype = prototype;
        }

        TlsInitializer init() {
            reloadableComponents = List.copyOf(prototype.reloadableComponents());
            sslParameters = sslParameters();
            try {
                sslContext = sslContext();
            } catch (GeneralSecurityException | IOException e) {
                throw new IllegalArgumentException("Failed to create SSL engine", e);
            }
            sslSocketFactory = sslContext.getSocketFactory();
            sslServerSocketFactory = sslContext.getServerSocketFactory();
            return this;
        }

        SSLParameters sslParameters() {
            SSLParameters sslParameters = prototype.sslParameters();
            if (sslParameters != null) {
                return sslParameters;
            }
            sslParameters = new SSLParameters();
            TlsConfig config = prototype.config();

            List<String> applicationProtocols = prototype.applicationProtocols();
            if (applicationProtocols != null) {
                sslParameters.setApplicationProtocols(applicationProtocols.toArray(new String[0]));
            }
            List<String> enabledProtocols = config.enabledProtocols();
            if (enabledProtocols != null) {
                sslParameters.setProtocols(enabledProtocols.toArray(new String[0]));
            }
            List<String> enabledCipherSuites = config.enabledCipherSuites();
            if (enabledCipherSuites != null) {
                sslParameters.setCipherSuites(enabledCipherSuites.toArray(new String[0]));
            }

            TlsClientAuth tlsClientAuth = config.tlsClientAuth();
            switch (tlsClientAuth) {
                case REQUIRED -> {
                    sslParameters.setNeedClientAuth(true);
                    sslParameters.setWantClientAuth(true);
                }
                case OPTIONAL -> sslParameters.setWantClientAuth(true);
            }

            String endpointIdentificationAlgorithm = prototype.endpointIdentificationAlgorithm();
            if (endpointIdentificationAlgorithm != null) {
                if (ENDPOINT_IDENTIFICATION_NONE.equals(endpointIdentificationAlgorithm)) {
                    sslParameters.setEndpointIdentificationAlgorithm("");
                } else {
                    sslParameters.setEndpointIdentificationAlgorithm(endpointIdentificationAlgorithm);
                }
            }
            return sslParameters;
        }

        SSLContext sslContext() throws GeneralSecurityException, IOException {
            SecureRandom secureRandom = secureRandom();

            KeyManagerFactory kmf = buildKmf(secureRandom);

            TrustManagerFactory tmf;
            if (prototype.trustAll()) {
                tmf = new TrustAllManagerFactory();
            } else {
                tmf = buildTmf();
            }

            SSLContext sslContext;
            String provider = prototype.provider();
            String protocol = prototype.protocol();
            if (provider == null) {
                sslContext = SSLContext.getInstance(protocol);
            } else {
                sslContext = SSLContext.getInstance(protocol, provider);
            }

            sslContext.init(kmf == null ? null : wrapX509KeyManagers(kmf.getKeyManagers()),
                    tmf == null ? null : wrapX509TrustManagers(tmf.getTrustManagers()),
                    secureRandom);

            SSLSessionContext serverSessionContext = sslContext.getServerSessionContext();
            if (serverSessionContext != null) {
                serverSessionContext.setSessionCacheSize(prototype.config().sessionCacheSize());
                serverSessionContext.setSessionTimeout((int) prototype.sessionTimeout().toSeconds());
            }

            return sslContext;
        }

        TrustManager[] wrapX509TrustManagers(TrustManager[] trustManagers) {
            TrustManager[] toReturn = new TrustManager[trustManagers.length];
            System.arraycopy(trustManagers, 0, toReturn, 0, toReturn.length);
            for (int i = 0; i < trustManagers.length; i++) {
                TrustManager trustManager = trustManagers[i];
                if (trustManager instanceof X509TrustManager x509TrustManager) {
                    originalTrustManager = x509TrustManager;
                    var wrappedTrustManager = new ReloadableX509TrustManager(x509TrustManager);
                    reloadableComponents.add(wrappedTrustManager);
                    toReturn[i] = wrappedTrustManager;
                    return toReturn;
                }
            }
            reloadableComponents.add(new NotReloadableTrustManager());
            return toReturn;
        }

        KeyManager[] wrapX509KeyManagers(KeyManager[] keyManagers) {
            KeyManager[] toReturn = new KeyManager[keyManagers.length];
            System.arraycopy(keyManagers, 0, toReturn, 0, toReturn.length);
            for (int i = 0; i < keyManagers.length; i++) {
                KeyManager keyManager = keyManagers[i];
                if (keyManager instanceof X509KeyManager x509KeyManager) {
                    originalKeyManager = x509KeyManager;
                    ReloadableX509KeyManager wrappedKeyManager = new ReloadableX509KeyManager(x509KeyManager);
                    reloadableComponents.add(wrappedKeyManager);
                    toReturn[i] = wrappedKeyManager;
                    return toReturn;
                }
            }
            reloadableComponents.add(new NotReloadableKeyManager());
            return toReturn;
        }

        SecureRandom secureRandom() {
            SecureRandom secureRandom = prototype.secureRandom();
            if (secureRandom != null) {
                return secureRandom;
            }
            String secureRandomProvider = prototype.secureRandomProvider();
            String secureRandomAlgorithm = prototype.secureRandomAlgorithm();
            if (secureRandomAlgorithm != null) {
                try {
                    if (secureRandomProvider == null) {
                        return SecureRandom.getInstance(secureRandomAlgorithm);
                    } else {
                        return SecureRandom.getInstance(secureRandomAlgorithm, secureRandomProvider);
                    }
                } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "Invalid configuration of secure random. Provider: %s, algorithm: %s",
                                    secureRandomProvider,
                                    secureRandomAlgorithm),
                            e);
                }
            }
            return RANDOM.get();
        }

        KeyManagerFactory buildKmf(SecureRandom secureRandom)
                throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {

            Keys keys = prototype.privateKey();
            if (keys == null) {
                return null;
            }

            PrivateKey key = keys.privateKey().orElse(null);
            if (key == null) {
                return null;
            }

            byte[] passwordBytes = new byte[64];
            secureRandom.nextBytes(passwordBytes);
            char[] password = Base64.getEncoder().encodeToString(passwordBytes).toCharArray();

            KeyStore ks = keystore();
            ks.setKeyEntry("key",
                    key,
                    password,
                    keys.certChain().toArray(new Certificate[0]));

            KeyManagerFactory kmf = kmf();
            kmf.init(ks, password);
            return kmf;
        }

        TrustManagerFactory buildTmf() throws KeyStoreException {
            Keys trustCertificates = prototype.trustCertificates();
            if (trustCertificates == null) {
                return null;
            }
            KeyStore ks = keystore();
            int i = 1;
            for (X509Certificate cert : trustCertificates.certs()) {
                ks.setCertificateEntry(String.valueOf(i), cert);
                i++;
            }
            TrustManagerFactory tmf = tmf();
            tmf.init(ks);
            return tmf;
        }

        KeyManagerFactory kmf() {
            String kmfAlgorithm = prototype.keyManagerFactoryAlgorithm();
            String kmfProvider = prototype.keyManagerFactoryProvider();
            try {
                return kmfProvider == null
                        ? KeyManagerFactory.getInstance(kmfAlgorithm)
                        : KeyManagerFactory.getInstance(kmfAlgorithm, kmfProvider);
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                throw new IllegalArgumentException("Invalid configuration of key manager factory. Provider: "
                        + kmfProvider
                        + ", algorithm: " + kmfAlgorithm, e);
            }
        }

        TrustManagerFactory tmf() {
            String tmfAlgorithm = prototype.trustManagerFactoryAlgorithm();
            String tmfProvider = prototype.trustManagerFactoryProvider();
            try {
                return tmfProvider == null
                        ? TrustManagerFactory.getInstance(tmfAlgorithm)
                        : TrustManagerFactory.getInstance(tmfAlgorithm, tmfProvider);
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                throw new IllegalArgumentException(
                        String.format(
                                "Invalid configuration of trust manager factory. Provider: %s, algorithm: %s",
                                tmfProvider,
                                tmfAlgorithm),
                        e);
            }
        }

        KeyStore keystore() {
            String internalKeystoreType = prototype.internalKeystoreType();
            String internalKeystoreProvider = prototype.internalKeystoreProvider();
            try {
                KeyStore keyStore;
                if (internalKeystoreProvider == null) {
                    keyStore = KeyStore.getInstance(internalKeystoreType);
                } else {
                    keyStore = KeyStore.getInstance(internalKeystoreType, internalKeystoreProvider);
                }
                keyStore.load(null, null);
                return keyStore;
            } catch (KeyStoreException
                     | NoSuchProviderException
                     | IOException
                     | NoSuchAlgorithmException
                     | CertificateException e) {
                throw new IllegalArgumentException(
                        String.format(
                                "Invalid configuration of internal keystore. Provider: %s, type: %s",
                                internalKeystoreProvider,
                                internalKeystoreType),
                        e);
            }
        }
    }
}
