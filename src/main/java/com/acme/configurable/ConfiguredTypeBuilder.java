package com.acme.configurable;

import com.acme.builder.Builder;
import com.acme.builder.MetaBuilder;
import io.helidon.config.Config;

/**
 * A meta-builder of {@link ConfiguredType}.
 *
 * @param <SELF>    the reference to the subtype
 * @param <FACTORY> the target builder type (pseudo factory)
 * @param <TARGET>  the target type of the target builder
 */
public interface ConfiguredTypeBuilder<
        SELF extends ConfiguredTypeBuilder<SELF, FACTORY, TARGET>,
        FACTORY extends Builder<TARGET>,
        TARGET extends ConfiguredType<?, ?>
        > extends MetaBuilder<FACTORY, TARGET> {

    /**
     * Populate this builder from configuration.
     *
     * @param config config tree
     * @return this builder
     */
    SELF configure(Config config);
}
