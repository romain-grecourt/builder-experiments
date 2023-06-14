package com.acme.demo.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.helidon.common.context.Context;

import com.acme.configurable.ConfiguredTypeBase;

/**
 * Base implementation of {@link SocketListener.Prototype}.
 */
public class SocketListenerPrototypeImpl<T extends SocketListener.TypedConfig> extends ConfiguredTypeBase<T>
        implements SocketListener.Prototype<T> {

    private final Context context;
    private final InetAddress bindAddress;

    private SocketListenerPrototypeImpl(T config, Context context, InetAddress bindAddress) {
        super(config);
        this.context = context;
        this.bindAddress = bindAddress;
    }

    /**
     * Create a new instance.
     *
     * @param config  config
     * @param builder builder
     */
    protected SocketListenerPrototypeImpl(T config, SocketListenerBuilderBase<?> builder) {
        this(config, builder.context(), builder.bindAddress());
    }

    /**
     * Create a new instance.
     *
     * @param config config
     */
    protected SocketListenerPrototypeImpl(T config) {
        this(config, Context.create(), inetAddress(config.host()));
    }

    @Override
    public Context context() {
        return context;
    }

    @Override
    public InetAddress bindAddress() {
        return bindAddress;
    }

    private static InetAddress inetAddress(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
