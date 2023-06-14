package com.acme.demo.api;

import com.acme.builder.Builder;
import com.acme.configurable.ConfiguredType;
import com.acme.configurable.ConfiguredTypeBuilder;

/**
 * {@link SocketListener} meta builder base.
 *
 * @param <SELF> self reference
 * @param <T>    target type
 */
public abstract class SocketListenerMetaBuilderBase
        <SELF extends SocketListenerMetaBuilderBase<SELF, T>, T extends ConfiguredType<? extends SocketListener.TypedConfig>>
        extends SocketListenerBuilderBase<SELF>
        implements ConfiguredTypeBuilder<SELF, T> {

    @Override
    public Builder<T> builder() {
        resolve();
        return doBuild0();
    }

    /**
     * Create the target builder.
     *
     * @return builder
     */
    protected abstract Builder<T> doBuild0();
}
