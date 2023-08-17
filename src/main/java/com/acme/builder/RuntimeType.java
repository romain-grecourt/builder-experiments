package com.acme.builder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A marker interface for types built using builder.
 */
public interface RuntimeType {

    /**
     * Define a shortcut method to be added on code generated builders.
     */
    @Target(ElementType.METHOD)
    @Inherited
    @Retention(RetentionPolicy.SOURCE)
    @interface ShortHand {
    }
}
