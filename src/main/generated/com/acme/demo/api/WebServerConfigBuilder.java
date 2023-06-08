package com.acme.demo.api;

import com.acme.builder.Builder;
import io.helidon.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for {@link WebServerConfig}.
 */
public final class WebServerConfigBuilder extends WebServerBuilderBase<WebServerConfigBuilder>
        implements Builder<WebServerConfig> {

    private final Map<String, ListenerConfig> sockets = new HashMap<>();

    /**
     * Create a new instance.
     */
    WebServerConfigBuilder() {
    }

    @Override
    protected void socket(String name, Config config) {
        sockets.put(name, ListenerConfigImpl.create(config));
    }

    @Override
    public Map<String, ListenerConfig> sockets() {
        return sockets;
    }

    @Override
    public WebServerConfig build() {
        return new WebServerConfigImpl(this);
    }
}
