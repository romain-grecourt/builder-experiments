package com.acme.demo.api;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;

/**
 * Keys and certificates.
 */
public interface KeysSupport {

    /**
     * Public key.
     *
     * @return public key
     */
    Optional<PublicKey> publicKey();

    /**
     * Private key.
     *
     * @return private key
     */
    Optional<PrivateKey> privateKey();

    /**
     * Public X.509 Certificate.
     *
     * @return public certificate
     */
    Optional<X509Certificate> publicCert();

    /**
     * X.509 Certificate Chain.
     *
     * @return certificate chain
     */
    List<X509Certificate> certChain();

    /**
     * X.509 Certificates.
     *
     * @return certificates
     */
    List<X509Certificate> certs();
}
