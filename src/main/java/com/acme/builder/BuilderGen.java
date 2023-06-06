package com.acme.builder;

/**
 * Marks an interface as the contract for a code-generated builder.
 */
public @interface BuilderGen {

    /**
     * @return builder "target type"
     */
    Class<?> value();
}
