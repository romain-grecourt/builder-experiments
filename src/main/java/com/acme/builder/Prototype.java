package com.acme.builder;

/**
 * Defines the initialization contract of a type.
 */
public interface Prototype {
    @interface Alias {
        String value();
    }

    @interface Option {
        String initializer() default "";
        String value() default "";
    }
}
