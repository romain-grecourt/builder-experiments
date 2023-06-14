package com.acme.builder;

import java.util.function.Supplier;

/**
 * A builder of builder.
 *
 * @param <BUILDER> the "target builder" type
 * @param <TARGET>  the "target type" of the "target builder"
 */
public interface MetaBuilder<BUILDER extends Builder<TARGET>, TARGET> extends Supplier<TARGET> {

    /**
     * Build the "target builder".
     *
     * @return target builder
     */
    BUILDER builder();

    /**
     * Build a "target type" instance via a "target builder".
     *
     * @return new "target type" instance
     */
    default TARGET build() {
        return builder().build();
    }

    @Override
    default TARGET get() {
        return build();
    }
}
