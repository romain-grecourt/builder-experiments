package com.acme.demo.api;

import com.acme.builder.Builder;

/**
 * {@link SocketListener} builder.
 */
public class SocketListenerBuilder extends SocketListenerMetaBuilderBase<SocketListenerBuilder, SocketListener> {

    SocketListenerBuilder() {
    }

    @Override
    protected Builder<SocketListener> doBuild0() {
        return () -> new SocketListener(SocketListenerFactory.prototype(this));
    }
}
