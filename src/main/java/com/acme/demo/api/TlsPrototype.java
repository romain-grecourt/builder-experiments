package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;
import com.acme.tls.TlsReloadableComponent;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

public interface TlsPrototype extends ConfiguredPrototype<TlsConfig> {

    String secureRandomAlgorithm();

    String secureRandomProvider();

    SecureRandom secureRandom();

    Keys privateKey();

    Keys trust();

    List<TlsReloadableComponent> reloadableComponents();

    Duration sessionTimeout();

    List<String> applicationProtocols();

    boolean trustAll();

    String internalKeystoreType();

    String internalKeystoreProvider();

    String keyManagerFactoryProvider();

    String trustManagerFactoryAlgorithm();

    String trustManagerFactoryProvider();

    SSLParameters sslParameters();

    String endpointIdentificationAlgorithm();
}
