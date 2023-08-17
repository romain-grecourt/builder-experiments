package com.acme.demo.api;

import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link SocketListener} builder.
 */
public final class SocketListenerBuilder
        extends SocketListenerBuilderBase<SocketListenerBuilder>
        implements ConfiguredTypeBuilder<SocketListenerBuilder, SocketListener> {

    SocketListenerBuilder() {
        // package-private to force the use of SocketFactory
    }

    @Override
    public SocketListener build() {
        resolve();
        return SocketListenerFactory.create(this);
    }
}
