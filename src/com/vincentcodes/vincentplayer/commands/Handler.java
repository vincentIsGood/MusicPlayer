package com.vincentcodes.vincentplayer.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation used to indicate a method is able to handle something
 * The method must has an argument of type <code>CommandEvent</code>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler{
    
}