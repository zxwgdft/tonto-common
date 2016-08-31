package com.tonto2.common.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;

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

	/**
	 * 是否标准GET封装方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isGetMethod(Method method) {
		String name = method.getName();

		return name.startsWith("get") && !"getClass".equals(name) && name.length() > 3 && method.getParameterTypes().length == 0
				&& method.getReturnType() != void.class;
	}

	/**
	 * 是否标准SET封装方法
	 * 
	 * @param method
	 * @return
	 */
	public static boolean isSetMethod(Method method) {
		String name = method.getName();

		return name.startsWith("set") && name.length() > 3 && method.getParameterTypes().length == 1
				&& method.getReturnType() == void.class;
	}

	/**
	 * 是否基础类型(Object,Number,Enum,Date,String)
	 * 
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseClass(Class<?> clazz) {
		return clazz == Object.class || clazz.isEnum() || Number.class.isAssignableFrom(clazz) || clazz == Date.class
				|| clazz == String.class;

	}

	/**
	 * <p>
	 * 根据get封装方法反射获取Collection的泛型类型
	 * </p>
	 * <p>
	 * 例如: public List(Integer) getIds()方法返回的Integer类型
	 * </p>
	 * 
	 * @param getMethod
	 * @return
	 */
	public static Class<?> getCollectionType(Method getMethod) {
		return getActualType((ParameterizedType) getMethod.getGenericReturnType(), 0);
	}

	/**
	 * <p>
	 * 根据Field反射获取Collection的泛型类型
	 * </p>
	 * 
	 * <p>
	 * 例如: private List(Integer) ids方法返回的Integer类型
	 * </p>
	 * 
	 * @param field
	 * @return
	 */
	public static Class<?> getCollectionType(Field field) {
		return getActualType((ParameterizedType) field.getGenericType(), 0);
	}

	
	/**
	 * 获取泛型实际类型
	 * @param type
	 * @param index
	 * @return
	 */
	public static Class<?> getActualType(ParameterizedType type, int index) {
		return (Class<?>) type.getActualTypeArguments()[index];
	}
	
}
