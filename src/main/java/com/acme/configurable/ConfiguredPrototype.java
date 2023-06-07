package com.acme.configurable;

/**
 * Defines the initialization contract of a {@link ConfiguredType}.
 *
 * @param <T> config type
 */
public interface ConfiguredPrototype<T extends ConfigType> extends ConfiguredType<T> {
}
