package com.tonto2.common.excel.read.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadProperty {
	/**
	 * EXCEL列序号
	 * @return
	 */
	public int cellIndex();
	
	//验证部分
	public boolean nullable() default true;
	public String regex() default "";
	public int minLength() default -1;
	public int maxLength() default -1;	
	public String max() default "";
	public String min() default "";
	public int[] intEnum() default {};
}
