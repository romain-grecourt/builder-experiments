package com.acme.demo.api;

import com.acme.builder.BuilderGen;
import com.acme.configurable.RuntimeType;

/**
 * {@link SocketListener} runtime view.
 * This class is hand-crafted.
 */
@BuilderGen(SocketListener.class)
public interface SocketListenerRuntime extends RuntimeType<SocketListenerConfig> {

    /**
     * Get the port.
     *
     * @return port
     */
    int port();
}
