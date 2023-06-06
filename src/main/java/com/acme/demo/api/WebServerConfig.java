package com.acme.demo.api;

import java.util.Map;

import com.acme.configurable.ConfigType;

/**
 * {@link WebServer} typed configuration.
 * This class is hand-crafted.
 */
public interface WebServerConfig extends ConfigType {

    /**
     * Sockets.
     *
     * @return sockets
     */
    Map<String, SocketListenerConfig> sockets();
}
