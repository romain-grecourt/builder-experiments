package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link SocketListener} builder.
 * This class is code-generated.
 */
public class SocketListenerBuilder extends SocketListenerBuilderBase<SocketListenerBuilder>
        implements ConfiguredTypeBuilder<SocketListenerBuilder, SocketListenerFactory, SocketListener> {

    @Override
    public SocketListenerFactory build0() {
        return new SocketListenerFactory(this);
    }
}
