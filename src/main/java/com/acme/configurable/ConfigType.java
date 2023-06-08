package com.acme.configurable;

/**
 * Base interface for type-safe configuration types.
 * Represents a strict "configuration" view, nested {@link ConfiguredType} references are forbidden.
 * <p>
 * This interface is meant to be extended, only subtypes should be implemented.
 * </p>
 */
public interface ConfigType {

    default String configKey() {
        return "unknown";
    }
}
