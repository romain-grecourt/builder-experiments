package com.acme.builder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Supplier;

/**
 * Builder.
 *
 * @param <T> target type
 */
public interface Builder<T> extends Supplier<T> {

    /**
     * Build the instance.
     *
     * @return instance
     */
    T build();

    @Override
    default T get() {
        return build();
    }

    /**
     * Defines the contract of a builder.
     */
    interface Prototype {

        @Target(ElementType.METHOD)
        @Inherited
        @Retention(RetentionPolicy.SOURCE)
        @interface Alias {
            String value();

            String resolver() default "";
        }

        @Target(ElementType.METHOD)
        @Inherited
        @Retention(RetentionPolicy.SOURCE)
        @interface Option {
            String initializer() default "";

            String value() default "";
        }

        @interface Combined {
            String value();
        }
    }
}
