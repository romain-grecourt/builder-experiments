package com.acme.demo.api;

import com.acme.configurable.ConfigType;

/**
 * {@link SocketListener} typed config.
 * This class is hand-crafted.
 */
public interface SocketListenerConfig extends ConfigType {

    /**
     * Port.
     *
     * @return port
     */
    int port();

    /**
     * Host.
     *
     * @return host
     */
    String host();
}
