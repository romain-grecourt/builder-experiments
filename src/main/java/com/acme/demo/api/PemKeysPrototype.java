package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

public interface PemKeysPrototype extends ConfiguredPrototype<PemKeysConfig> {

    Resource privateKey();

    Resource publicKey();

    Resource certChain();

    Resource certificates();
}
