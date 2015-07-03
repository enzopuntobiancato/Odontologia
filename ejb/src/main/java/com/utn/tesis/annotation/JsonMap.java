package com.utn.tesis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: Enzo
 * Date: 2/07/15
 * Time: 11:01
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JsonMap {

    public static class Public {}
    public static class Extended extends Public {}
    public static class Internal extends Extended {}

    Class view();
}
