package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;

import java.util.Optional;

public interface KeyStoreKeysConfig extends ConfigType {

    @ConfiguredOption(key = "resource")
    Optional<ResourceConfig> keystore();

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
