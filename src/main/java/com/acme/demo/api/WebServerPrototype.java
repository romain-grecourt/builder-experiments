package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

import java.util.Map;

/**
 * Prototype for {@link WebServer}.
 * This class is hand-crafted.
 */
public interface WebServerPrototype extends ConfiguredPrototype<WebServerConfig> {

    /**
     * Get the socket listeners.
     *
     * @return socket listeners
     */
    Map<String, SocketListener> sockets();
}
