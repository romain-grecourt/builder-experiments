package com.acme.configurable;

import com.acme.builder.Prototype;

/**
 * A {@link ConfiguredType} with an initialization contract.
 * It represents the "runtime" view of the configuration provided by the {@link ConfigType}.
 * I.e. if the {@link ConfigType} has nested {@link ConfigType} references, the corresponding {@link ConfiguredType}
 * references should be mirrored.
 * <p>
 * This interface is meant to be extended, only subtypes should be implemented.
 * </p>
 *
 * @param <T> config type
 */
public interface ConfiguredPrototype<T extends ConfigType> extends ConfiguredType<T>, Prototype {
}
