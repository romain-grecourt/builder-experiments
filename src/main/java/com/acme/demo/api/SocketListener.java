package com.acme.demo.api;

/**
 * Socket listener.
 */
public class SocketListener implements SocketListenerPrototype {

    private final SocketListenerConfig config;
    private int port;

    /**
     * Create a new instance.
     *
     * @param prototype config
     */
    SocketListener(SocketListenerPrototype prototype) {
        this.config = prototype.config();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static SocketListenerBuilder builder() {
        return new SocketListenerBuilder();
    }

    @Override
    public SocketListenerConfig config() {
        return config;
    }

    /**
     * Get the effective port.
     *
     * @return port
     */
    public int port() {
        return port;
    }
}
