package com.tonto2.common.utils.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class PathGetter {

	private static final Logger logger = Logger.getLogger(PathGetter.class);

	/**
	 * 根据寻路地址找到相应的值
	 * 
	 * @param source
	 *            数据源
	 * @param path
	 *            例如address.1.street 表示取address属性下数组的第一个值下的street属性
	 * @return
	 */
	public static Object get(Object source, String path) {

		if (source == null)
			return null;

		if (path == null || "".equals(path))
			return source;

		String[] keys = path.split("\\.");

		for (String key : keys)
			source = getAttr(source, key);
		return source;
	}

	private static Object getAttr(Object source, String key) {

		if (source == null)
			return null;

		if (source instanceof List) {
			List<?> list = (List<?>) source;
			int index = Integer.valueOf(key);
			source = list.get(index);
		} else {
			Class<?> clazz = source.getClass();
			if (clazz.isArray()) {
				int index = Integer.valueOf(key);
				source = Array.get(source, index);
			} else if (source instanceof Map) {
				source = getFromMap((Map<?, ?>) source, key);
			} else {
				source = getFromObject(source, key);
			}
		}

		return source;
	}

	

	/**
	 * 从Map中获取值
	 * 
	 * @param source
	 * @param key
	 * @return
	 */
	private static Object getFromMap(Map<?, ?> source, String key) {
		return source == null ? null : source.get(key);
	}

	/**
	 * 从Object中通过get方法获取对应属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	private static Object getFromObject(Object obj, String fieldName) {
		if (obj != null) {
			Class<?> clazz = obj.getClass();

			try {
				Method getMethod = clazz.getMethod(NameUtil.addGet(fieldName));
				return getMethod.invoke(obj);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				logger.error("无法从类:" + clazz + "的实例中获取对应属性:" + fieldName + "的值");
			}
		}

		return null;
	}

	
}
