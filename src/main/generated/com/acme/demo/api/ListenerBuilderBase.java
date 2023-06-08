package com.acme.demo.api;

import com.acme.configurable.Configurable;
import io.helidon.config.Config;

/**
 * Base builder for {@link SocketListener}.
 *
 * @param <SELF> subtype reference
 */
public abstract class ListenerBuilderBase<SELF extends ListenerBuilderBase<SELF>>
        implements Configurable<SELF>, SocketListenerConfig {

    private int port = 0;
    private String host = "0.0.0.0";

    @Override
    public int port() {
        return port;
    }

    @Override
    public String host() {
        return host;
    }

    /**
     * Set the port.
     *
     * @param port port
     * @return this builder
     */
    public SELF port(int port) {
        this.port = port;
        return me();
    }

    /**
     * Set the host.
     *
     * @param host host
     * @return this builder
     */
    public SELF host(String host) {
        this.host = host;
        return me();
    }

    @Override
    public SELF configure(Config config) {
        config.get("host").asString().ifPresent(this::host);
        config.get("port").asInt().ifPresent(this::port);
        return me();
    }

    @SuppressWarnings("unchecked")
    private SELF me() {
        return (SELF) this;
    }
}
