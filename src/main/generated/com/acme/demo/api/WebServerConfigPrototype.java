package com.acme.demo.api;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServerPrototype} adapter for {@link WebServerConfig}.
 */
final class WebServerConfigPrototype extends WebServerPrototypeBase {

    /**
     * Create a new instance.
     *
     * @param config config
     */
    WebServerConfigPrototype(WebServerConfig config) {
        super(config, createSockets(config));
    }

    private static Map<String, SocketListener> createSockets(WebServerConfig config) {
        Map<String, SocketListener> sockets = new HashMap<>();
        config.sockets().forEach((k, v) -> sockets.put(k, SocketListenerFactory.create(v)));
        return sockets;
    }
}
