package com.acme.demo.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServerRuntime} implementation.
 * This class is code generated.
 */
public class WebServerRuntimeImpl implements WebServerRuntime {

    private final Map<String, SocketListenerRuntimeImpl> sockets = new HashMap<>();
    private final WebServerConfig config;

    /**
     * Create a new instance.
     */
    WebServerRuntimeImpl(WebServerConfig config) {
        this.config = config;
    }

    /**
     * Get the socket.
     *
     * @return sockets
     */
    public Map<String, SocketListenerRuntime> sockets() {
        return Collections.unmodifiableMap(sockets);
    }

    /**
     * Set socket.
     *
     * @param sockets socket
     */
    void sockets(Map<String, SocketListenerRuntimeImpl> sockets) {
        this.sockets.putAll(sockets);
    }

    @Override
    public WebServerConfig config() {
        return config;
    }
}
