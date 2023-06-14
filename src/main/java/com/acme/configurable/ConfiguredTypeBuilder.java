package com.acme.configurable;

import com.acme.builder.Builder;
import com.acme.builder.MetaBuilder;

/**
 * A meta-builder of {@link ConfiguredType}.
 *
 * @param <SELF>   the reference to the subtype
 * @param <TARGET> the target type of the target builder
 */
public interface ConfiguredTypeBuilder<SELF extends ConfiguredTypeBuilder<SELF, TARGET>, TARGET>
        extends MetaBuilder<Builder<TARGET>, TARGET>, Configurable<SELF> {
}
