package com.tonto.common.template.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模板注解
 * @author TontoZhou
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Template {
	/**模板类型*/
	public TemplateType type();		
	
	/**模板内容*/
	public String content();
		
}
