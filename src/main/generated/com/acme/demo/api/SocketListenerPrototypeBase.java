package com.acme.demo.api;

/**
 * Base implementation of {@link SocketListenerPrototype}.
 */
abstract class SocketListenerPrototypeBase implements SocketListenerPrototype {

    private final ListenerConfig config;

    /**
     * Create a new instance.
     *
     * @param config config
     */
    protected SocketListenerPrototypeBase(ListenerConfig config) {
        this.config = config;
    }

    @Override
    public ListenerConfig config() {
        return config;
    }
}
