package com.acme.demo.api;

import com.acme.builder.Builder;
import io.helidon.config.Config;

/**
 * {@link SocketListener} support.
 */
public class SocketListenerFactory {

    private SocketListenerFactory() {
    }

    public static SocketListener.Prototype prototype(SocketListener.TypedConfig config) {
        return new SocketListenerPrototypeImpl(config);
    }

    public static SocketListener.Prototype prototype(SocketListenerBuilder builder) {
        return new SocketListenerPrototypeImpl(new SocketListenerConfigImpl(builder), builder);
    }

    public static SocketListener create(SocketListener.TypedConfig config) {
        return new SocketListener(prototype(config));
    }

    public static SocketListener create(Config config) {
        return create(typedConfig(config));
    }

    public static SocketListener create() {
        return create(typedConfig());
    }

    public static SocketListener.TypedConfig typedConfig() {
        return new ConfigBuilder().build();
    }

    public static SocketListener.TypedConfig typedConfig(Config config) {
        return new ConfigBuilder().configure(config).build();
    }

    public static SocketListener.TypedConfig typedConfig(SocketListenerBuilder builder) {
        return new SocketListenerConfigImpl(builder);
    }

    private static final class ConfigBuilder
            extends SocketListenerBuilderBase<ConfigBuilder>
            implements Builder<SocketListener.TypedConfig> {

        @Override
        public SocketListener.TypedConfig build() {
            return new SocketListenerConfigImpl(this);
        }
    }
}
