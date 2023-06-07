package com.acme.demo.api;

/**
 * Base implementation of {@link SocketListenerPrototype}.
 * This class is code generated.
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
