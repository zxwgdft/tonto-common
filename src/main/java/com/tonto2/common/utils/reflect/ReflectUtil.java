package com.tonto2.common.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {

	/**
	 * 根据属性名称获取对应的get封装方法
	 * <p>
	 * 例如：String name ==> public String getName()
	 * </p>
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static Method getGetMethod(String name, Class<?> clazz) {
		String methodName = NameUtil.addGet(name);
		try {
			Method method = clazz.getMethod(methodName);
			if (method != null && method.getReturnType() != void.class)
				return method;			
		} catch (NoSuchMethodException | SecurityException e) {
		}
		return null;
	}

	/**
	 * 根据属性名称获取对应的set封装方法
	 * <p>
	 * 例如：String name ==> public String setName(String name)
	 * </p>
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	public static Method getSetMethod(String name, Class<?> clazz, Class<?> paramType) {
		String methodName = NameUtil.addSet(name);
		
		try {
			Method method = clazz.getMethod(methodName, paramType);
			if (method != null && method.getReturnType() == void.class)
				return method;
		} catch (NoSuchMethodException | SecurityException e) {			
		}		
		return null;
	}

	/**
	 * 获取Field（会去父类寻找，包括private，protected）
	 * 
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field getField(Class<?> clazz, String name) {

		if (clazz == null || clazz.isInterface())
			return null;

		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			return getField(clazz.getSuperclass(), name);
		} catch (SecurityException e) {
			return null;
		}

	}

}
