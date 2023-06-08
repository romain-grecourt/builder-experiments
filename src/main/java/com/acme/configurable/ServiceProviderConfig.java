package com.acme.configurable;

/**
 * Base config type for service providers.
 *
 * @param <T> provider type
 */
public interface ServiceProviderConfig<T> extends ConfigType {

    /**
     * Whether Java Service Loader should be used to load {@link T}.
     *
     * @return {@code true} if services should be discovered
     */
    boolean discoverServices();
}
