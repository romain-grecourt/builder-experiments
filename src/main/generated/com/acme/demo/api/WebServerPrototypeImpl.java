package com.acme.demo.api;

import com.acme.demo.spi.ServerConnectionSelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base implementation of {@link WebServer.Prototype}.
 */
public class WebServerPrototypeImpl extends SocketListenerPrototypeImpl<WebServer.TypedConfig>
        implements WebServer.Prototype {

    private final Map<String, SocketListener> sockets;
    private final ContentEncodingContext contentEncodingContext;
    private final MediaContext mediaContext;
    private final List<ServerConnectionSelector> connectionSelectors;

    /**
     * Create a new instance.
     *
     * @param builder builder
     */
    protected WebServerPrototypeImpl(WebServerBuilder builder) {
        super(new WebServerConfigImpl(builder), builder);
        this.sockets = builder.socketListeners();
        this.contentEncodingContext = builder.contentEncodingContext();
        this.mediaContext = builder.mediaContext();
        this.connectionSelectors = builder.connectionSelectors();
    }

    /**
     * Create a new instance.
     *
     * @param config typed config
     */
    protected WebServerPrototypeImpl(WebServer.TypedConfig config) {
        super(config);
        this.sockets = createSockets(config);
        this.contentEncodingContext = null; // TODO
        this.mediaContext = null; // TODO
        this.connectionSelectors = null; // TODO
    }

    @Override
    public Map<String, SocketListener> sockets() {
        return sockets;
    }

    @Override
    public ContentEncodingContext contentEncodingContext() {
        return contentEncodingContext;
    }

    @Override
    public MediaContext mediaContext() {
        return mediaContext;
    }

    @Override
    public List<ServerConnectionSelector> connectionSelectors() {
        return connectionSelectors;
    }

    private static Map<String, SocketListener> createSockets(WebServer.TypedConfig config) {
        Map<String, SocketListener> sockets = new HashMap<>();
        config.sockets().forEach((k, v) -> sockets.put(k, SocketListenerFactory.create(v)));
        return sockets;
    }
}
