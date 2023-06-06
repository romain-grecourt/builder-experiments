package com.acme.demo.api;

import com.acme.builder.Factory;

/**
 * {@link SocketListener} factory.
 * This class is code generated.
 */
public class SocketListenerFactory extends Factory<SocketListener> {

    SocketListenerFactory(SocketListenerBuilder builder) {
        super(() -> SocketListener.create(builder));
    }
}
