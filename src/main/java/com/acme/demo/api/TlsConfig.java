package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;
import com.acme.tls.TlsClientAuth;

import java.util.List;

public interface TlsConfig extends ConfigType {

    @ConfiguredOption("NONE")
    TlsClientAuth tlsClientAuth();

    KeysConfig privateKey();

    KeysConfig trustCertificates();

    List<String> enabledProtocols();

    @ConfiguredOption("1024")
    int sessionCacheSize();

    @ConfiguredOption(key = "cipher-suite")
    List<String> enabledCipherSuites();

    @ConfiguredOption(key = "session-timeout-seconds")
    int sessionTimeout();

    boolean enabled();
}
