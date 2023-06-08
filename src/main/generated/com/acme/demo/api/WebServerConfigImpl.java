package com.acme.demo.api;

import io.helidon.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServerConfig} implementation.
 */
public class WebServerConfigImpl implements WebServerConfig {

    private final Map<String, SocketListenerConfig> sockets;

    /**
     * Create a new instance.
     *
     * @param builder builder
     */
    WebServerConfigImpl(WebServerBuilderBase<?> builder) {
        sockets = new HashMap<>(builder.sockets());
    }

    @Override
    public Map<String, SocketListenerConfig> sockets() {
        return sockets;
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static WebServerConfig create() {
        return builder().build();
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static WebServerConfig create(Config config) {
        return builder().configure(config).build();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static WebServerConfigBuilder builder() {
        return new WebServerConfigBuilder();
    }
}
