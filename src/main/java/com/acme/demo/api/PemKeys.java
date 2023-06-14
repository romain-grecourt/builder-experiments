package com.acme.demo.api;

import com.acme.common.PemReader;
import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;
import com.acme.configurable.ConfiguredPrototype;
import com.acme.configurable.ConfiguredTypeBase;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * {@code PKCS#8} based implementation of {@link KeysSupport}.
 */
public class PemKeys extends ConfiguredTypeBase<PemKeys.TypedConfig> implements KeysSupport {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final X509Certificate publicCert;
    private final List<X509Certificate> certChain;
    private final List<X509Certificate> certs;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    PemKeys(Prototype prototype) {
        super(prototype);
        TypedConfig config = prototype.config();
        Resource privateKeyResource = prototype.privateKey();
        if (privateKeyResource != null) {
            char[] keyPassphrase = config.keyPassphrase().orElse(null);
            privateKey = PemReader.readPrivateKey(privateKeyResource.stream(), keyPassphrase);
        } else {
            privateKey = null;
        }
        Resource publicKeyResource = prototype.publicKey();
        if (publicKeyResource != null) {
            publicKey = PemReader.readPublicKey(publicKeyResource.stream());
        } else {
            publicKey = null;
        }
        Resource certChainResource = prototype.certChain();
        if (certChainResource != null) {
            certChain = PemReader.readCertificates(certChainResource.stream());
            if (!certChain.isEmpty()) {
                publicCert = certChain.get(0);
            } else {
                publicCert = null;
            }
        } else {
            certChain = null;
            publicCert = null;
        }
        Resource certificatesResource = prototype.certificates();
        if (certificatesResource != null) {
            certs = PemReader.readCertificates(prototype.certificates().stream());
        } else {
            certs = null;
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

    public interface TypedConfig extends ConfigType {

        @ConfiguredOption(key = "key.resource")
        Optional<Resource.TypedConfig> key();

        @ConfiguredOption(key = "key.passphrase")
        Optional<char[]> keyPassphrase();

        @ConfiguredOption(key = "cert-chain.resource")
        Optional<Resource.TypedConfig> certChain();

        @ConfiguredOption(key = "certificates.resource")
        Optional<Resource.TypedConfig> certificates();
    }

    public interface Prototype extends ConfiguredPrototype<TypedConfig> {

        Resource privateKey();

        Resource publicKey();

        Resource certChain();

        Resource certificates();
    }
}
