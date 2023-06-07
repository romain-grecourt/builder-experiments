package com.acme.demo.api;

import io.helidon.config.Config;

/**
 * {@link SocketListenerConfig} implementation.
 * This class is code generated.
 */
public class SocketListenerConfigImpl implements SocketListenerConfig {

    private final int port;
    private final String host;

    SocketListenerConfigImpl(SocketListenerBuilderBase<?> builder) {
        this.port = builder.port();
        this.host = builder.host();
    }

    @Override
    public int port() {
        return port;
    }

    @Override
    public String host() {
        return host;
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static SocketListenerConfig create() {
        return builder().build();
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static SocketListenerConfig create(Config config) {
        return builder().configure(config).build();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static SocketListenerConfigBuilder builder() {
        return new SocketListenerConfigBuilder();
    }
}
