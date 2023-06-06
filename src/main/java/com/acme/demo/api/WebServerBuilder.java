package com.acme.demo.api;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import io.helidon.config.Config;

import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link WebServer} meta-builder.
 * This class is code-generated.
 */
public class WebServerBuilder implements ConfiguredTypeBuilder<WebServerBuilder, WebServerFactory, WebServer> {

    private final Map<String, SocketListener> sockets = new HashMap<>();

    /**
     * Get the socket runtimes.
     *
     * @return socket runtimes
     */
    Map<String, SocketListener> sockets() {
        return sockets;
    }

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
     * @param consumer socket listener builder consumer
     * @return this builder
     */
    public WebServerBuilder socket(String name, Consumer<SocketListenerBuilder> consumer) {
        SocketListenerBuilder builder = SocketListener.builder();
        consumer.accept(builder);
        return socket(name, builder.build());
    }

    @Override
    public WebServerBuilder configure(Config config) {
        config.get("sockets").asNodeList().ifPresent(nodes ->
                nodes.forEach(n -> socket(n.name(), socket -> socket.configure(n))));
        return this;
    }

    /**
     * Build and start the server.
     * TODO WebServerSpec that glues WebServerRuntime with WebServer and custom builder logic
     *
     * @return created server instance
     */
    public WebServer start() {
        return build().start();
    }

    @Override
    public WebServerFactory build0() {
        return new WebServerFactory(this);
    }


    WebServerRuntimeImpl buildRuntime() {
        return new WebServerRuntimeImpl(new WebServerConfigImpl(this));
    }
}