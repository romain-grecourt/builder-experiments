package com.acme.demo.api;

import io.helidon.config.Config;

import com.acme.configurable.Configurable;
import com.acme.configurable.ServiceProviderConfig;
import com.acme.demo.spi.ServerConnectionSelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Base builder for {@link WebServer}.
 *
 * @param <SELF> subtype reference
 */
public abstract class WebServerBuilderBase<SELF extends WebServerBuilderBase<SELF>>
        extends SocketListenerBuilderBase<SELF>
        implements Configurable<SELF> {

    private final Map<String, SocketListener> sockets = new HashMap<>();
    private boolean inheritThreadLocals;
    private ServiceProviderConfig<ServerConnectionSelector> connectionProviders;
    private ContentEncodingContext contentEncodingContext;
    private MediaContext mediaContext;
    private List<ServerConnectionSelector> connectionSelectors;

    boolean inheritThreadLocals() {
        return inheritThreadLocals;
    }

    ServiceProviderConfig<ServerConnectionSelector> connectionProviders() {
        return connectionProviders;
    }

    ContentEncodingContext contentEncodingContext() {
        return contentEncodingContext;
    }

    MediaContext mediaContext() {
        return mediaContext;
    }

    Map<String, SocketListener> socketListeners() {
        return sockets;
    }

    List<ServerConnectionSelector> connectionSelectors() {
        return null;
    }

    Map<String, SocketListener.TypedConfig> sockets() {
        Map<String, SocketListener.TypedConfig> socketsConfig = new HashMap<>();
        for (Map.Entry<String, SocketListener> entry : sockets.entrySet()) {
            SocketListener value = entry.getValue();
            socketsConfig.put(entry.getKey(), value.config());
        }
        return socketsConfig;
    }

    @Override
    public SELF configure(Config config) {
        config.get("sockets").asNodeList().ifPresent(nodes ->
                nodes.forEach(n -> socket(n.name(), n)));
        config.get("inheritThreadLocals").asBoolean().ifPresent(this::inheritThreadLocals);
        return me();
    }

    public SELF inheritThreadLocals(boolean inheritThreadLocals) {
        this.inheritThreadLocals = inheritThreadLocals;
        return me();
    }

    public SELF connectionProviders(ServiceProviderConfig<ServerConnectionSelector> connectionProviders) {
        this.connectionProviders = connectionProviders;
        return me();
    }

    public SELF contentEncodingContext(ContentEncodingContext contentEncodingContext) {
        this.contentEncodingContext = contentEncodingContext;
        return me();
    }

    public SELF mediaContext(MediaContext mediaContext) {
        this.mediaContext = mediaContext;
        return me();
    }

    protected void socket(String name, Config config) {
        sockets.put(name, SocketListenerFactory.create(config));
    }

    public SELF socket(String name, SocketListener socket) {
        sockets.put(name, socket);
        return me();
    }

    public SELF socket(String name, Supplier<SocketListener> supplier) {
        return socket(name, supplier.get());
    }

    public SELF socket(String name, Consumer<SocketListenerBuilder> consumer) {
        SocketListenerBuilder builder = SocketListener.builder();
        consumer.accept(builder);
        return socket(name, builder);
    }

    @SuppressWarnings("unchecked")
    private SELF me() {
        return (SELF) this;
    }
}
