package com.tonto.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * 
 * @author TontoZhou
 * 
 */
public class ReflectUtil {	
	
	/**
	 * 反射获取类中方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @param params
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethodByParam(Class<?> clazz, String methodName,
			Object... params) throws NoSuchMethodException, SecurityException {
		Class<?>[] paramsClass = new Class<?>[params.length];

		for (int i = 0; i < params.length; i++)
			paramsClass[i] = params[i].getClass();

		return getMethod(clazz, methodName, paramsClass);
	}

	/**
	 * 反射获取类中方法
	 * 
	 * @param clazz
	 * @param methodName
	 * @param paramsClass
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Method getMethod(Class<?> clazz, String methodName,
			Class<?>... paramsClass) throws NoSuchMethodException,
			SecurityException {
		return clazz.getMethod(methodName, paramsClass);
	}

	/**
	 * 获取Field（会去父类寻找，包括private，protected）
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field getField(Class<?> clazz,String name)
	{
		
		if(clazz == null || clazz.isInterface())
			return null;
		
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {			
			return getField(clazz.getSuperclass(),name);						
		} catch (SecurityException e) {
			return null;
		}
		
	}
	
	
}
