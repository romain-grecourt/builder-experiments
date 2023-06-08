package com.acme.configurable;

public @interface ConfiguredOption {
    String value() default "";
    String key() default "";
}
