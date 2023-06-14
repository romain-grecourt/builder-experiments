package com.acme.builder;

/**
 * Defines the contract of a builder.
 */
public interface Prototype {
    @interface Alias {
        String value();
        String initializer() default "";
    }

    @interface Option {
        String initializer() default "";
        String value() default "";
    }
}
