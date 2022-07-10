package com.triple.mileage.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.triple.mileage.event.domain.EventAction;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Record {
    EventAction action();
}
