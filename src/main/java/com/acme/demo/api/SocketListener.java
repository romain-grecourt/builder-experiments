package com.acme.demo.api;

import com.acme.configurable.Configured;
import com.acme.configurable.ConfiguredTypeBase;

/**
 * Socket listener.
 */
@Configured
public class SocketListener extends ConfiguredTypeBase<SocketListenerConfig> {

    private int port;

    /**
     * Create a new instance.
     *
     * @param prototype config
     */
    SocketListener(SocketListenerPrototype<?> prototype) {
        super(prototype);
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static ListenerBuilder builder() {
        return new ListenerBuilder();
    }

    /**
     * Get the effective port.
     *
     * @return port
     */
    public int port() {
        return port;
    }
}
