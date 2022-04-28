package com.offertechnicaltest.pierretuaillon.aspect;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Define the annotation @Timed
 * @author pierretuaillon
 *
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface Timed {}