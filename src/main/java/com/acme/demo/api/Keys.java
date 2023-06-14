package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredPrototype;
import com.acme.configurable.ConfiguredTypeBase;

import io.helidon.common.LazyValue;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Explicit implementation of {@link KeysSupport} merged with {@link PemKeys} and {@link KeyStoreKeys}.
 */
public class Keys extends ConfiguredTypeBase<Keys.TypedConfig> implements KeysSupport {

    public interface TypedConfig extends ConfigType {

        PemKeys.TypedConfig pem();
        KeyStoreKeys.TypedConfig keystore();
    }

    public interface Prototype extends ConfiguredPrototype<TypedConfig> {

        PemKeys pem();
        KeyStoreKeys keyStore();
        PrivateKey privateKey();
        PublicKey publicKey();
        X509Certificate publicCert();
        List<X509Certificate> certChain();
        List<X509Certificate> certificates();
    }

    private final PemKeys pemKeys;
    private final KeyStoreKeys keyStoreKeys;
    private final PublicKey explicitPublicKey;
    private final PrivateKey explicitPrivateKey;
    private final X509Certificate explicitPublicCert;
    private final List<X509Certificate> explicitCertChain;
    private final List<X509Certificate> explicitCerts;
    private final LazyValue<Optional<PublicKey>> mergedPublicKey = LazyValue.create(this::publicKey0);
    private final LazyValue<Optional<PrivateKey>> mergedPrivateKey = LazyValue.create(this::privateKey0);
    private final LazyValue<Optional<X509Certificate>> mergedPublicCert = LazyValue.create(this::publicCert0);
    private final LazyValue<List<X509Certificate>> mergedCertChain = LazyValue.create(this::certChain0);
    private final LazyValue<List<X509Certificate>> mergedCerts = LazyValue.create(this::certs0);

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    protected Keys(Prototype prototype) {
        super(prototype);
        pemKeys = prototype.pem();
        keyStoreKeys = prototype.keyStore();
        explicitPublicKey = prototype.publicKey();
        explicitPrivateKey = prototype.privateKey();
        explicitPublicCert = prototype.publicCert();
        explicitCertChain = prototype.certChain();
        explicitCerts = prototype.certificates();
    }

    /**
     * The public key of this config if configured.
     *
     * @return the public key of this config or empty if not configured
     */
    public Optional<PublicKey> publicKey() {
        return mergedPublicKey.get();
    }

    /**
     * The private key of this config if configured.
     *
     * @return the private key of this config or empty if not configured
     */
    public Optional<PrivateKey> privateKey() {
        return mergedPrivateKey.get();
    }

    /**
     * The public X.509 Certificate if configured.
     *
     * @return the public certificate of this config or empty if not configured
     */
    public Optional<X509Certificate> publicCert() {
        return mergedPublicCert.get();
    }

    /**
     * The X.509 Certificate Chain.
     *
     * @return the certificate chain or empty list if not configured
     */
    public List<X509Certificate> certChain() {
        return mergedCertChain.get();
    }

    /**
     * The X.509 Certificates.
     *
     * @return the certificates configured or empty list if none configured
     */
    public List<X509Certificate> certs() {
        return mergedCerts.get();
    }

    private Optional<PublicKey> publicKey0() {
        Optional<PublicKey> publicKey = Optional.ofNullable(explicitPublicKey);
        if (pemKeys != null) {
            publicKey = publicKey.or(pemKeys::publicKey);
        }
        if (keyStoreKeys != null) {
            publicKey = publicKey.or(keyStoreKeys::publicKey);
        }
        return publicKey;
    }

    private Optional<PrivateKey> privateKey0() {
        Optional<PrivateKey> privateKey = Optional.ofNullable(explicitPrivateKey);
        if (pemKeys != null) {
            privateKey = privateKey.or(pemKeys::privateKey);
        }
        if (keyStoreKeys != null) {
            privateKey = privateKey.or(keyStoreKeys::privateKey);
        }
        return privateKey;
    }

    private Optional<X509Certificate> publicCert0() {
        Optional<X509Certificate> publicCert = Optional.ofNullable(explicitPublicCert);
        if (pemKeys != null) {
            publicCert = publicCert.or(pemKeys::publicCert);
        }
        if (keyStoreKeys != null) {
            publicCert = publicCert.or(keyStoreKeys::publicCert);
        }
        return publicCert;
    }

    private List<X509Certificate> certChain0() {
        List<X509Certificate> certChain = new LinkedList<>(explicitCertChain);
        if (pemKeys != null) {
            certChain.addAll(pemKeys.certChain());
        }
        if (keyStoreKeys != null) {
            certChain.addAll(keyStoreKeys.certChain());
        }
        return Collections.unmodifiableList(certChain);
    }

    private List<X509Certificate> certs0() {
        List<X509Certificate> certs = new LinkedList<>(explicitCerts);
        if (pemKeys != null) {
            certs.addAll(pemKeys.certs());
        }
        if (keyStoreKeys != null) {
            certs.addAll(keyStoreKeys.certs());
        }
        return Collections.unmodifiableList(certs);
    }
}
