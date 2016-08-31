package com.tonto2.common.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Digit{
	public String message() default "必须为小于{max}并且大于{min}";
	public String min();
	public String max();
	public boolean inclusive() default true;
}
