package com.acme.demo.api;

import com.acme.builder.BuilderGen;
import com.acme.configurable.RuntimeType;

import java.util.Map;

/**
 * {@link WebServer} runtime view.
 * This class is hand-crafted.
 */
@BuilderGen(WebServer.class)
public interface WebServerRuntime extends RuntimeType<WebServerConfig> {

    /**
     * Get the socket runtime views.
     *
     * @return sockets
     */
    Map<String, SocketListenerRuntime> sockets();
}
