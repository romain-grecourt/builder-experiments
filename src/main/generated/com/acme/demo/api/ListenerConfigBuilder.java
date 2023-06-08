package com.acme.demo.api;

import com.acme.builder.Builder;

/**
 * Builder of {@link ListenerConfig}.
 */
public final class ListenerConfigBuilder
        extends ListenerBuilderBase<ListenerConfigBuilder>
        implements Builder<ListenerConfig> {

    /**
     * Create a new instance.
     */
    ListenerConfigBuilder() {
    }

    @Override
    public ListenerConfig build() {
        return new ListenerConfigImpl(this);
    }
}
