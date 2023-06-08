package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

import java.util.List;

public interface KeyStoreKeysPrototype extends ConfiguredPrototype<KeyStoreKeysConfig> {

    Resource keystore();

    boolean trustStore();

    List<String> certificateAliases();
}
