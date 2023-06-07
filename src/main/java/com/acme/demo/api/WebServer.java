package com.acme.demo.api;

import java.util.Map;

/**
 * WebServer !
 * This class is hand-crafted.
 */
public class WebServer implements WebServerPrototype {

    private final WebServerConfig config;
    private final Map<String, SocketListener> sockets;
    private boolean started;

    /**
     * Create a new instance.
     *
     * @param prototype prototype
     */
    WebServer(WebServerPrototype prototype) {
        config = prototype.config();
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

    @Override
    public WebServerConfig config() {
        return config;
    }

    @Override
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
