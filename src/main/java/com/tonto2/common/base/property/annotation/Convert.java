package com.tonto2.common.base.property.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tonto2.common.base.property.PropertyConvert;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Convert {
	public Class<? extends PropertyConvert<?>> convert();
}
