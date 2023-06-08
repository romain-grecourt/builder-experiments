package com.acme.demo.api;

import io.helidon.config.Config;

/**
 * {@link ListenerConfig} implementation.
 */
public class ListenerConfigImpl implements ListenerConfig {

    private final int port;
    private final String host;

    ListenerConfigImpl(ListenerBuilderBase<?> builder) {
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
    public static ListenerConfig create() {
        return builder().build();
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static ListenerConfig create(Config config) {
        return builder().configure(config).build();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static ListenerConfigBuilder builder() {
        return new ListenerConfigBuilder();
    }
}
