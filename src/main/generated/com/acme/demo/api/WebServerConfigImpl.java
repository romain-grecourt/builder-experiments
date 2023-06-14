package com.acme.demo.api;

import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ServerConnectionSelector;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServer.TypedConfig} implementation.
 */
public class WebServerConfigImpl extends SocketListenerConfigImpl implements WebServer.TypedConfig {

    private final Map<String, SocketListener.TypedConfig> sockets;
    private final boolean inheritThreadLocals;
    private final ServiceProviderConfig<ServerConnectionSelector> connectionProviders;

    /**
     * Create a new instance.
     *
     * @param builder builder
     */
    protected WebServerConfigImpl(WebServerBuilderBase<?> builder) {
        super(builder);
        sockets = new HashMap<>(builder.sockets());
        inheritThreadLocals = builder.inheritThreadLocals();
        connectionProviders = builder.connectionProviders();
    }

    @Override
    public Map<String, SocketListener.TypedConfig> sockets() {
        return sockets;
    }

    @Override
    public boolean inheritThreadLocals() {
        return inheritThreadLocals;
    }

    @Override
    public ServiceProviderConfig<ServerConnectionSelector> connectionProviders() {
        return connectionProviders;
    }
}
