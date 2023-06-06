package com.acme.demo.api;

/**
 * {@link SocketListenerConfig} implementation.
 * This class is code generated.
 */
public class SocketListenerConfigImpl implements SocketListenerConfig {

    private final int port;
    private final String host;

    SocketListenerConfigImpl(SocketListenerBuilder builder) {
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
}
