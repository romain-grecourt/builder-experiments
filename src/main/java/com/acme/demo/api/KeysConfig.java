package com.acme.demo.api;

import com.acme.configurable.ConfigType;

public interface KeysConfig extends ConfigType {

    PemKeysConfig pem();

    KeyStoreKeysConfig keystore();
}
