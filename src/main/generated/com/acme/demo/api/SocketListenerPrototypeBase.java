package com.acme.demo.api;

/**
 * Base implementation of {@link SocketListenerPrototype}.
 */
abstract class SocketListenerPrototypeBase implements SocketListenerPrototype {

    private final SocketListenerConfig config;

    /**
     * Create a new instance.
     *
     * @param config config
     */
    protected SocketListenerPrototypeBase(SocketListenerConfig config) {
        this.config = config;
    }

    @Override
    public SocketListenerConfig config() {
        return config;
    }
}
