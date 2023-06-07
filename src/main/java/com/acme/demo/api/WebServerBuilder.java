package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;
import io.helidon.config.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * {@link WebServer} meta-builder.
 * This class is code-generated.
 */
public class WebServerBuilder extends WebServerBuilderBase<WebServerBuilder>
        implements ConfiguredTypeBuilder<WebServerBuilder, WebServerFactory, WebServer> {

    final Map<String, SocketListener> sockets = new HashMap<>();

    /**
     * Add a socket listener.
     *
     * @param name   socket name
     * @param socket socket listener
     * @return this builder
     */
    public WebServerBuilder socket(String name, SocketListener socket) {
        sockets.put(name, socket);
        return this;
    }

    /**
     * Add a socket listener.
     *
     * @param name     socket name
     * @param supplier socket listener supplier
     * @return this builder
     */
    public WebServerBuilder socket(String name, Supplier<SocketListener> supplier) {
        return socket(name, supplier.get());
    }

    /**
     * Add a socket listener.
     *
     * @param name     socket name
     * @param consumer socket builder supplier
     * @return this builder
     */
    public WebServerBuilder socket(String name, Consumer<SocketListenerBuilder> consumer) {
        SocketListenerBuilder builder = SocketListener.builder();
        consumer.accept(builder);
        return socket(name, builder);
    }

    @Override
    protected void socket(String name, Config config) {
        sockets.put(name, SocketListener.builder().configure(config).build());
    }

    @Override
    public Map<String, SocketListenerConfig> sockets() {
        Map<String, SocketListenerConfig> socketsConfig = new HashMap<>();
        sockets.forEach((k, v) -> socketsConfig.put(k, v.config()));
        return socketsConfig;
    }

    @Override
    public WebServerFactory build0() {
        return new WebServerFactory(this);
    }
}
