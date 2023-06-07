package com.acme.demo.api;

import com.acme.configurable.Configurable;
import io.helidon.config.Config;

/**
 * Base builder for {@link WebServer}.
 *
 * @param <SELF> subtype reference
 */
public abstract class WebServerBuilderBase<SELF extends WebServerBuilderBase<SELF>>
        implements Configurable<SELF>, WebServerConfig {

    /**
     * Configure a socket.
     *
     * @param name   socket name
     * @param config config node
     */
    protected abstract void socket(String name, Config config);

    @Override
    public SELF configure(Config config) {
        config.get("sockets").asNodeList().ifPresent(nodes ->
                nodes.forEach(n -> socket(n.name(), n)));
        return me();
    }

    @SuppressWarnings("unchecked")
    private SELF me() {
        return (SELF) this;
    }
}
