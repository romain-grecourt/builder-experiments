package com.acme.demo.api;

import com.acme.configurable.ConfigType;

public interface ProxyConfig extends ConfigType {

    String proxyHost();

    int proxyPort();

    boolean useProxy();
}
