package com.tonto2.common.excel.read.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import com.tonto2.common.base.property.PropertyType;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadProperty {
	public String name() default "";	
	public PropertyType type() default PropertyType.STRING;
	public boolean nullable() default true;
	public String regex() default "";
	public int minLength() default -1;
	public int maxLength() default -1;
}
