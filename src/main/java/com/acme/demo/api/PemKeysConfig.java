package com.acme.demo.api;

import com.acme.configurable.ConfigType;
import com.acme.configurable.ConfiguredOption;

import java.util.Optional;

public interface PemKeysConfig extends ConfigType {

    @ConfiguredOption(key = "key.resource")
    Optional<ResourceConfig> key();

    @ConfiguredOption(key = "key.passphrase")
    Optional<char[]> keyPassphrase();

    @ConfiguredOption(key = "cert-chain.resource")
    Optional<ResourceConfig> certChain();

    @ConfiguredOption(key = "certificates.resource")
    Optional<ResourceConfig> certificates();
}
