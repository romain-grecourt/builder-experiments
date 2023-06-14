package com.acme.demo.api;

import com.acme.common.PkiUtil;
import com.acme.configurable.ConfigType;
import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ConfiguredPrototype;
import com.acme.configurable.ConfiguredTypeBase;

import java.io.IOException;
import java.io.InputStream;
import java.lang.System.Logger.Level;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * {@link KeyStore} based implementation of {@link KeysSupport}.
 */
public class KeyStoreKeys extends ConfiguredTypeBase<KeyStoreKeys.TypedConfig> implements KeysSupport {

    @Configured
    public interface TypedConfig extends ConfigType {

        @ConfiguredOption(key = "resource")
        Optional<Resource.TypedConfig> keystore();

        @ConfiguredOption(key = "key.passphrase")
        Optional<char[]> keyPassphrase();

        @ConfiguredOption(key = "type", value = "PKCS12")
        String keystoreType();

        @ConfiguredOption(key = "passphrase")
        Optional<char[]> keystorePassphrase();

        @ConfiguredOption(key = "key.alias")
        Optional<String> keyAlias();

        @ConfiguredOption(key = "cert.alias")
        Optional<String> certAlias();

        @ConfiguredOption(key = "cert-chain.alias")
        Optional<String> certChainAlias();

        Optional<Boolean> trustStore();
    }

    public interface Prototype extends ConfiguredPrototype<TypedConfig> {

        Resource keystore();
        boolean trustStore();
        List<String> certificateAliases();
    }

    private static final System.Logger LOGGER = System.getLogger(KeyStoreKeys.class.getName());
    private static final String DEFAULT_PRIVATE_KEY_ALIAS = "1";

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final X509Certificate publicCert;
    private final List<X509Certificate> certChain;
    private final List<X509Certificate> certs = new LinkedList<>();

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    KeyStoreKeys(Prototype prototype) {
        super(prototype);
        TypedConfig config = prototype.config();
        Resource resource = prototype.keystore();
        if (resource != null) {
            char[] keystorePassphrase = config.keystorePassphrase().orElse(null);
            char[] keyPassphrase = config.keyPassphrase().orElse(keystorePassphrase);
            String keyAlias = config.keyAlias().orElse(null);
            String certChainAlias = config.certChainAlias().orElse(null);
            String certAlias = config.certAlias().orElse(null);

            KeyStore keyStore = loadKeyStore(resource, config().keystoreType(), keystorePassphrase);
            privateKey = loadPrivateKey(keyStore, keyAlias, keyPassphrase);
            certChain = loadCertChain(keyStore, certChainAlias, keyAlias);
            publicCert = loadPublicCert(keyStore, certChain, certAlias);
            publicKey = publicCert != null ? publicCert.getPublicKey() : null;

            if (prototype.trustStore()) {
                certs.addAll(PkiUtil.loadCertificates(keyStore));
            } else {
                prototype.certificateAliases().forEach(it -> certs.add(PkiUtil.loadCertificate(keyStore, it)));
            }
        } else {
            privateKey = null;
            publicKey = null;
            certChain = null;
            publicCert = null;
        }
    }

    @Override
    public Optional<PublicKey> publicKey() {
        return Optional.ofNullable(publicKey);
    }

    @Override
    public Optional<PrivateKey> privateKey() {
        return Optional.ofNullable(privateKey);
    }

    @Override
    public Optional<X509Certificate> publicCert() {
        return Optional.ofNullable(publicCert);
    }

    @Override
    public List<X509Certificate> certChain() {
        return Collections.unmodifiableList(certChain);
    }

    @Override
    public List<X509Certificate> certs() {
        return Collections.unmodifiableList(certs);
    }

    static KeyStore loadKeyStore(Resource resource, String keystoreType, char[] passphrase) {
        InputStream is = resource.stream();
        String message = "keystore" + ":" + resource.sourceType() + ":" + resource.location();
        try {
            return PkiUtil.loadKeystore(keystoreType, is, passphrase, message);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "Failed to close input stream: " + message, e);
            }
        }
    }

    static PrivateKey loadPrivateKey(KeyStore keyStore, String keyAlias, char[] passphrase) {
        String alias = keyAlias;
        boolean fail;
        if (null == keyAlias) {
            alias = DEFAULT_PRIVATE_KEY_ALIAS;
            fail = false;
        } else {
            fail = true;
        }
        try {
            return PkiUtil.loadPrivateKey(keyStore, alias, passphrase);
        } catch (Exception e) {
            if (!fail) {
                LOGGER.log(Level.DEBUG, "Failed to read private key from default alias", e);
            } else {
                throw e;
            }
        }
        return null;
    }

    static List<X509Certificate> loadCertChain(KeyStore keyStore, String certChainAlias, String keyAlias) {
        boolean fail;
        String alias = certChainAlias;
        if (null == alias) {
            fail = false;
            // by default, cert chain uses the same alias as private key
            alias = keyAlias;
        } else {
            fail = true;
        }
        if (null != alias) {
            try {
                return PkiUtil.loadCertChain(keyStore, alias);
            } catch (Exception e) {
                if (!fail) {
                    LOGGER.log(Level.DEBUG, "Failed to certificate chain from alias \"" + alias + "\"", e);
                } else {
                    throw e;
                }
            }
        }
        return null;
    }

    static X509Certificate loadPublicCert(KeyStore keyStore, List<X509Certificate> certChain, String certAlias) {
        if (null == certAlias) {
            // no explicit public key certificate, just load it from cert chain if present
            if (null != certChain && !certChain.isEmpty()) {
                return certChain.get(0);
            }
            return null;
        }
        return PkiUtil.loadCertificate(keyStore, certAlias);
    }
}
