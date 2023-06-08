package com.acme.demo.api;

import com.acme.common.PkiUtil;
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
 * {@link KeyStore} based implementation of {@link Keys}.
 */
public class KeyStoreKeys extends ConfiguredTypeBase<KeyStoreKeysConfig> implements KeysSupport {

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
    KeyStoreKeys(KeyStoreKeysPrototype prototype) {
        super(prototype);
        KeyStoreKeysConfig config = prototype.config();
        Resource resource = prototype.keystore();
        char[] keystorePassphrase = config.keystorePassphrase().orElse(null);
        if (resource != null) {
            KeyStore keyStore;
            InputStream is = resource.stream();
            String message = "keystore" + ":" + resource.sourceType() + ":" + resource.location();
            try {
                keyStore = PkiUtil.loadKeystore(config.keystoreType(), is, keystorePassphrase, message);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING, "Failed to close input stream: " + message, e);
                }
            }

            char[] keyPassphrase = config.keyPassphrase().orElse(keystorePassphrase);
            String keyAlias = config.keyAlias().orElse(null);
            String certChainAlias = config.certChainAlias().orElse(null);
            String certAlias = config.certAlias().orElse(null);

            privateKey = loadPrivateKey(keyStore, keyAlias, keyPassphrase);
            certChain = loadCertChain(keyStore, certChainAlias, keyAlias);
            publicCert = loadPublicCert(keyStore, certChain, certAlias);
            if (publicCert != null) {
                publicKey = publicCert.getPublicKey();
            } else {
                publicKey = null;
            }

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
