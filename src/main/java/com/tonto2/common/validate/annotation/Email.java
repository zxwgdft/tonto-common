package com.tonto2.common.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.FIELD,ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Email{
	public String message() default "邮件地址格式错误";
}
