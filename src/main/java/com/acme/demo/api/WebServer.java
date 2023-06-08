package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBase;

import java.util.Map;

/**
 * WebServer !
 */
public class WebServer extends ConfiguredTypeBase<WebServerConfig> {

    private final Map<String, SocketListener> sockets;
    private boolean started;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    WebServer(WebServerPrototype prototype) {
        super(prototype);
        sockets = prototype.sockets();
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static WebServerBuilder builder() {
        return new WebServerBuilder();
    }

    /**
     * Get the socket listeners.
     *
     * @return map of socket listeners keyed by socket names
     */
    public Map<String, SocketListener> sockets() {
        return sockets;
    }

    /**
     * Start the server.
     *
     * @return this instance
     */
    public WebServer start() {
        started = true;
        return this;
    }

    /**
     * Indicate if the server is started.
     *
     * @return {@code true} if started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Stop the server.
     *
     * @return this instance
     */
    public WebServer stop() {
        started = false;
        return this;
    }
}
