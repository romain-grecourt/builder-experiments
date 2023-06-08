package com.acme.demo.api;

import com.acme.configurable.ConfiguredPrototype;

/**
 * {@link SocketListener} prototype.
 */
public interface SocketListenerPrototype<T extends SocketListenerConfig> extends ConfiguredPrototype<T> {

    // TODO
    // Context context()
    // RequestedUriDiscoveryContext discoveryContext()
    // DirectHandlers directHandlers
    // InetAddress bindAddress() // alias to address

}
