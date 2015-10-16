package com.tonto.common.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
	public String message() default "长度必须为介于{minLength}到{maxLength}之间";
	public int minLength();
	public int maxLength();
	public boolean inclusive() default true;
}
