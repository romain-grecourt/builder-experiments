package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link SocketListener} builder.
 */
public class ListenerBuilder extends ListenerBuilderBase<ListenerBuilder>
        implements ConfiguredTypeBuilder<ListenerBuilder, SocketListenerFactory, SocketListener> {

    @Override
    public SocketListenerFactory build0() {
        return new SocketListenerFactory(this);
    }
}
