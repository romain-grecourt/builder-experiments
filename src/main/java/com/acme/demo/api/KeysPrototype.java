package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

public interface KeysPrototype extends ConfiguredPrototype<KeysConfig> {

    PemKeys pem();

    KeyStoreKeys keyStore();

    PrivateKey privateKey();

    PublicKey publicKey();

    X509Certificate publicCert();

    List<X509Certificate> certChain();

    List<X509Certificate> certificates();
}
