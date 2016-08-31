package com.tonto2.common.base.property.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tonto2.common.base.property.PropertyValidate;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
	public Class<? extends PropertyValidate> validate();	
}
