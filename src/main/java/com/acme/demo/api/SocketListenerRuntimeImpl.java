package com.acme.demo.api;

/**
 * {@link SocketListenerRuntime} implementation.
 * This class is code generated.
 */
public class SocketListenerRuntimeImpl implements SocketListenerRuntime {

    private final SocketListenerConfig config;
    private int port;

    SocketListenerRuntimeImpl(SocketListenerConfig config) {
        this.config = config;
    }

    /**
     * Get the port.
     *
     * @return port
     */
    public int port() {
        return port;
    }

    /**
     * Set the port.
     *
     * @param port port
     */
    void port(int port) {
        this.port = port;
    }

    @Override
    public SocketListenerConfig config() {
        return config;
    }
}
