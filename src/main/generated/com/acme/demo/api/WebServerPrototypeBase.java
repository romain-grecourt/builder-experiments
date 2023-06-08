package com.acme.demo.api;

import java.util.Map;

/**
 * Base implementation of {@link WebServerPrototype}.
 */
abstract class WebServerPrototypeBase implements WebServerPrototype {

    private final WebServerConfig config;
    private final Map<String, SocketListener> sockets;

    /**
     * Create a new instance.
     *
     * @param config  config
     * @param sockets sockets
     */
    protected WebServerPrototypeBase(WebServerConfig config, Map<String, SocketListener> sockets) {
        this.config = config;
        this.sockets = sockets;
    }

    @Override
    public WebServerConfig config() {
        return config;
    }

    @Override
    public Map<String, SocketListener> sockets() {
        return sockets;
    }
}
