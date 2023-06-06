package com.acme.demo.api;

import com.acme.configurable.ConfiguredType;

/**
 * Socket listener.
 */
public class SocketListener implements ConfiguredType<SocketListenerConfig, SocketListenerRuntime> {

    private final SocketListenerRuntimeImpl runtime;

    private SocketListener(SocketListenerRuntimeImpl runtime) {
        this.runtime = runtime;
    }

    /**
     * Create a new builder.
     *
     * @return builder
     */
    public static SocketListenerBuilder builder() {
        return new SocketListenerBuilder();
    }

    /**
     * Create a new default instance.
     *
     * @return new instance
     */
    public static SocketListener create() {
        return builder().build();
    }

    /**
     * Create a new instance.
     *
     * @param config typed configuration
     * @return new instance
     */
    static SocketListener create(SocketListenerConfig config) {
        return new SocketListener(new SocketListenerRuntimeImpl(config));
    }

    /**
     * Create a new instance.
     *
     * @param builder builder
     * @return new instance
     */
    static SocketListener create(SocketListenerBuilder builder) {
        return new SocketListener(builder.buildRuntime());
    }

    @Override
    public SocketListenerRuntime runtime() {
        return runtime;
    }
}
