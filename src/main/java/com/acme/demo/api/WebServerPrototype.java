package com.acme.demo.api;

import com.acme.demo.spi.ServerConnectionSelector;

import java.util.List;
import java.util.Map;

/**
 * Prototype for {@link WebServer}.
 */
public interface WebServerPrototype extends SocketListenerPrototype<WebServerConfig> {

    /**
     * Get the socket listeners.
     *
     * @return map of {@link SocketListener} keyed by socket names
     */
    Map<String, SocketListener> sockets();

    // TODO Map<String, Router> routers();

    /**
     * Get the content encoding context.
     *
     * @return content encoding context
     */
    ContentEncodingContext contentEncodingContext();

    /**
     * Get the media context.
     *
     * @return media context
     */
    MediaContext mediaContext();

    /**
     * Get the connection providers.
     *
     * @return list of {@link ServerConnectionSelector}
     */
    List<ServerConnectionSelector> connectionProviders();
}
