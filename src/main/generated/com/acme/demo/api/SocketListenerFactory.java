package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeFactory;
import io.helidon.config.Config;

/**
 * {@link SocketListener} factory.
 */
public class SocketListenerFactory extends SocketListenerPrototypeBase
        implements ConfiguredTypeFactory<SocketListenerConfig, SocketListener> {

    SocketListenerFactory(ListenerBuilder builder) {
        super(builder);
    }

    @Override
    public SocketListener build() {
        return new SocketListener(this);
    }

    /**
     * Create a new instance.
     *
     * @param config typed config
     * @return new instance
     */
    public static SocketListener create(SocketListenerConfig config) {
        return new SocketListener(new SocketListenerConfigPrototype(config));
    }

    /**
     * Create a new instance.
     *
     * @param config config node
     * @return new instance
     */
    public static SocketListener create(Config config) {
        return create(ListenerConfigImpl.create(config));
    }

    /**
     * Create a new instance.
     *
     * @return new instance
     */
    public static SocketListener create() {
        return create(ListenerConfigImpl.create());
    }
}
