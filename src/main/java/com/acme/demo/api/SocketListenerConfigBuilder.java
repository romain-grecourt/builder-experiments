package com.acme.demo.api;

import com.acme.builder.Builder;

/**
 * Builder of {@link SocketListenerConfig}.
 */
public final class SocketListenerConfigBuilder
        extends SocketListenerBuilderBase<SocketListenerConfigBuilder>
        implements Builder<SocketListenerConfig> {

    /**
     * Create a new instance.
     */
    SocketListenerConfigBuilder() {
    }

    @Override
    public SocketListenerConfig build() {
        return new SocketListenerConfigImpl(this);
    }
}
