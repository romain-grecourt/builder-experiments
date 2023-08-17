package com.acme.demo.api;

import com.acme.builder.Builder;
import io.helidon.config.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link WebServer} static factory methods.
 */
public class WebServerFactory {

    private WebServerFactory() {
    }

    private static WebServer.Prototype prototype(WebServer.TypedConfig config) {
        return new WebServerPrototypeImpl(config);
    }

    private static WebServer.Prototype prototype(WebServerBuilder builder) {
        return new WebServerPrototypeImpl(builder);
    }

    public static WebServer create(WebServerBuilder builder) {
        return new WebServer(prototype(builder));
    }

    public static WebServer create(WebServer.TypedConfig config) {
        return new WebServer(prototype(config));
    }

    public static WebServer create() {
        return create(typedConfig());
    }

    public static WebServer create(Config config) {
        return create(typedConfig(config));
    }

    public static WebServer.TypedConfig typedConfig() {
        return new ConfigBuilder().build();
    }

    public static WebServer.TypedConfig typedConfig(Config config) {
        return new ConfigBuilder().configure(config).build();
    }

    public static WebServer.TypedConfig typedConfig(WebServerBuilder builder) {
        return new WebServerConfigImpl(builder);
    }

    private static final class ConfigBuilder extends WebServerBuilderBase<ConfigBuilder>
            implements Builder<WebServer.TypedConfig> {

        private final Map<String, SocketListener.TypedConfig> sockets = new HashMap<>();

        @Override
        protected void socket(String name, Config config) {
            sockets.put(name, SocketListenerFactory.typedConfig(config));
        }

        @Override
        public Map<String, SocketListener.TypedConfig> sockets() {
            return sockets;
        }

        @Override
        public WebServer.TypedConfig build() {
            return new WebServerConfigImpl(this);
        }
    }
}
