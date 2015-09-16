package com.tonto.common.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Property {
	public String name() default "";	
	public PropertyType type() default PropertyType.STRING;
	public boolean nullable() default true;
	public String regex() default "";
	public int minLength() default -1;
	public int maxLength() default -1;
}
