package com.acme.demo.api;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServerConfig} implementation.
 * This class is code generated.
 */
public class WebServerConfigImpl implements WebServerConfig {

    private final Map<String, SocketListenerConfig> sockets = new HashMap<>();

    WebServerConfigImpl(WebServerBuilder builder) {
        builder.sockets().forEach((k, v) -> sockets.put(k, v.config()));
    }

    @Override
    public Map<String, SocketListenerConfig> sockets() {
        return sockets;
    }
}
