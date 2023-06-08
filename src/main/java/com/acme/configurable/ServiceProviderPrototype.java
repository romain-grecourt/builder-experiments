package com.acme.configurable;

/**
 * Base prototype for service providers.
 *
 * @param <T> provider type
 */
public interface ServiceProviderPrototype<T> extends ConfiguredPrototype<ServiceProviderConfig<T>> {

    /**
     * Get the providers.
     *
     * @return list of {@link T}
     */
    T providers();
}
