package com.axgrid.tools.service;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FSMEnter {
    int value() default 0;
}
