package com.acme.configurable;

import com.acme.builder.MetaBuilder;

/**
 * A meta-builder of {@link ConfiguredType}.
 *
 * @param <SELF>    the reference to the subtype
 * @param <FACTORY> the target builder type
 * @param <TARGET>  the target type of the target builder
 */
public interface ConfiguredTypeBuilder<
        SELF extends ConfiguredTypeBuilder<SELF, FACTORY, TARGET>,
        FACTORY extends ConfiguredTypeFactory<?, TARGET>,
        TARGET extends ConfiguredType<?>
        > extends MetaBuilder<FACTORY, TARGET>, Configurable<SELF> {
}
