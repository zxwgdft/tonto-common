package com.tonto.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Map工具类
 * 
 * @author TontoZhou
 * 
 */
public class MapUtil {

	private final static Logger logger = Logger.getLogger(MapUtil.class);

	/**
	 * 把对象中的属性赋值到Map中
	 * 
	 * @param map
	 * @param obj
	 */
	public static void object2Map(Map<String, Object> map, Object obj) {

		if (obj == null || map == null)
			return;

		Class<?> clazz = obj.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String name = method.getName();
						
			if (name.startsWith("get") && !"getClass".equals(name) && name.length() > 3 && method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {

				char[] cs = name.toCharArray();

				if (cs[3] <= 90)
					cs[3] += 32;

				name = new String(cs, 3, cs.length - 3);

				try {
					map.put(name, method.invoke(obj));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * MAP转化为OBJECT
	 * 
	 * @param valueMap
	 * @param object
	 */
	public static <T> T map2object(Map<String, Object> valueMap, Class<T> clazz) {

		try {
			T obj = clazz.newInstance();
			map2object(valueMap, obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			if (logger.isDebugEnabled())
				logger.error("创建实例失败：" + clazz.getName(), e);
			return null;
		}

	}

	/**
	 * map转化为赋值到object
	 * 
	 * @param valueMap
	 * @param object
	 */
	public static <T> T map2object(Map<String, Object> valueMap, T object) {

		if (valueMap == null || object == null)
			return null;

		Class<?> clazz = object.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("set") && name.length() > 3 && method.getParameterTypes().length == 1 && method.getReturnType() == void.class) {

				char[] cs = name.toCharArray();

				if (cs[3] <= 90)
					cs[3] += 32;

				name = new String(cs, 3, cs.length - 3);

				Object value = valueMap.get(name);

				if (value != null && !"".equals(value)) {
					Class<?> paramClass = method.getParameterTypes()[0];

					if (paramClass.isAssignableFrom(value.getClass())) {

					} else if (value instanceof String) {
						value = ParseUtil.parseString((String) value, paramClass);
					} else if (paramClass == String.class){						
						value = ParseUtil.parse2String(value);
					}
					else {
						continue;
					}

					try {
						method.invoke(object, value);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						if (logger.isDebugEnabled())
							logger.error("属性：" + name + "赋值错误！", e);
						continue;
					}

				}

			}

		}

		return object;
	}

	/**
	 * 字符串值得MAP转化为OBJECT
	 * 
	 * @param valueMap
	 * @param object
	 */
	public static <T> T stringMap2object(Map<String, String> valueMap, Class<T> clazz) {

		try {
			T obj = clazz.newInstance();
			stringMap2object(valueMap, obj);
			return obj;
		} catch (InstantiationException | IllegalAccessException e) {
			if (logger.isDebugEnabled())
				logger.error("创建实例失败：" + clazz.getName(), e);
			return null;
		}

	}

	/**
	 * 字符串值得MAP转化为OBJECT
	 * 
	 * @param valueMap
	 * @param object
	 */
	public static <T> T stringMap2object(Map<String, String> valueMap, T object) {
		if (valueMap == null || object == null)
			return null;

		Class<?> clazz = object.getClass();

		Method[] methods = clazz.getMethods();

		for (Method method : methods) {
			String name = method.getName();
			if (name.startsWith("set") && name.length() > 3 && method.getParameterTypes().length == 1 && method.getReturnType() == void.class) {
				char[] cs = name.toCharArray();

				if (cs[3] <= 90)
					cs[3] += 32;

				name = new String(cs, 3, cs.length - 3);

				String value = valueMap.get(name);

				if (value != null && !"".equals(value)) {
					Object objValue = ParseUtil.parseString(value, method.getParameterTypes()[0]);

					try {
						method.invoke(object, objValue);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						if (logger.isDebugEnabled())
							logger.error("属性：" + name + "赋值错误！", e);
						continue;
					}
				}

			}

		}

		return object;

	}
}
