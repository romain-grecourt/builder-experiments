package com.acme.demo.api;

import com.acme.builder.Builder;
import io.helidon.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * Builder for {@link WebServerConfig}.
 * This class is code generated.
 */
public final class WebServerConfigBuilder extends WebServerBuilderBase<WebServerConfigBuilder>
        implements Builder<WebServerConfig> {

    private final Map<String, SocketListenerConfig> sockets = new HashMap<>();

    /**
     * Create a new instance.
     */
    WebServerConfigBuilder() {
    }

    @Override
    protected void socket(String name, Config config) {
        sockets.put(name, SocketListenerConfigImpl.create(config));
    }

    @Override
    public Map<String, SocketListenerConfig> sockets() {
        return sockets;
    }

    @Override
    public WebServerConfig build() {
        return new WebServerConfigImpl(this);
    }
}
