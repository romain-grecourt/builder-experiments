package com.acme.demo.api;

import com.acme.builder.Builder;

/**
 * Builder of {@link SocketListenerConfig}.
 */
public final class ListenerConfigBuilder
        extends ListenerBuilderBase<ListenerConfigBuilder>
        implements Builder<SocketListenerConfig> {

    /**
     * Create a new instance.
     */
    ListenerConfigBuilder() {
    }

    @Override
    public SocketListenerConfig build() {
        return new ListenerConfigImpl(this);
    }
}
