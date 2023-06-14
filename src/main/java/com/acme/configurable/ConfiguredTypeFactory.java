package com.acme.configurable;

import com.acme.builder.Builder;

/**
 * Defines the contract for creating {@link ConfiguredType}.
 *
 * @param <T> config type
 * @param <U> configured type
 */
public interface ConfiguredTypeFactory<T extends ConfigType, U extends ConfiguredType<T>> extends Builder<U> {
}
