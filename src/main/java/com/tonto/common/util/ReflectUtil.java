package com.tonto.common.util;

import java.math.BigDecimal;
import java.util.Date;

public class ReflectUtil {
	
	
	public static boolean isBaseClass(Class<?> clazz)
	{
		return clazz.isPrimitive()||clazz.isEnum()||clazz.equals(String.class)||clazz.equals(Integer.class)
				||clazz.equals(Double.class)||clazz.equals(Float.class)||clazz.equals(Date.class)
				||clazz.equals(Long.class)||clazz.equals(Object.class)||clazz.equals(BigDecimal.class);	
	}
	
	public static boolean isNumClass(Class<?> clazz)
	{
		return clazz.equals(int.class)||clazz.equals(Integer.class)||clazz.equals(double.class)
				||clazz.equals(Double.class)||clazz.equals(float.class)||clazz.equals(Float.class)
				||clazz.equals(long.class)||clazz.equals(Long.class)||clazz.equals(BigDecimal.class);
	}
}
