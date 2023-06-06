package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;
import io.helidon.config.Config;

/**
 * {@link SocketListener} builder.
 * This class is code-generated.
 */
public class SocketListenerBuilder
        implements ConfiguredTypeBuilder<SocketListenerBuilder, SocketListenerFactory, SocketListener> {

    private int port;
    private String host;

    int port() {
        return port;
    }

    String host() {
        return host;
    }

    /**
     * Set the port.
     *
     * @param port port
     * @return this builder
     */
    public SocketListenerBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Set the host.
     *
     * @param host host
     * @return this builder
     */
    public SocketListenerBuilder host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public SocketListenerBuilder configure(Config config) {
        config.get("host").asString().ifPresent(this::host);
        config.get("port").asInt().ifPresent(this::port);
        return this;
    }

    @Override
    public SocketListenerFactory build0() {
        return new SocketListenerFactory(this);
    }

    SocketListenerRuntimeImpl buildRuntime() {
        return new SocketListenerRuntimeImpl(new SocketListenerConfigImpl(this));
    }
}
