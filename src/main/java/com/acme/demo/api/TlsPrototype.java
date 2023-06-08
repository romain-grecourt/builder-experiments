package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;
import com.acme.tls.TlsReloadableComponent;

import javax.net.ssl.SSLParameters;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

public interface TlsPrototype extends ConfiguredPrototype<TlsConfig> {

    String secureRandomAlgorithm();

    String secureRandomProvider();

    SecureRandom secureRandom();

    Keys privateKey();
    Keys trustCertificates();

    List<TlsReloadableComponent> reloadableComponents();

    Duration sessionTimeout();

    List<String> applicationProtocols();

    boolean trustAll();

    @Option(initializer = "java.security.KeyStore::getDefaultType")
    String internalKeystoreType();

    String internalKeystoreProvider();

    @Option(initializer = "javax.net.ssl.KeyManagerFactory::getDefaultAlgorithm")
    String keyManagerFactoryAlgorithm();

    String keyManagerFactoryProvider();

    @Option(initializer = "javax.net.ssl.TrustManagerFactory::getDefaultAlgorithm")
    String trustManagerFactoryAlgorithm();

    String trustManagerFactoryProvider();

    SSLParameters sslParameters();

    @Option(Tls.ENDPOINT_IDENTIFICATION_HTTPS)
    String endpointIdentificationAlgorithm();

    String provider();

    @Option("TLS")
    String protocol();
}
