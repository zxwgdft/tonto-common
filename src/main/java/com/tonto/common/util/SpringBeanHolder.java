package com.tonto.common.util;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * spring bean 持有类
 * @author TontoZhou
 *
 */
public class SpringBeanHolder {

	private final Logger logger = Logger.getLogger(SpringBeanHolder.class);

	private ApplicationContext appContext;

	private SpringBeanHolder(ApplicationContext context){
		appContext = context;
	}

	/**
	 * 
	 * @param name
	 * @return 如果异常或找不到则返回null
	 */
	public Object getBean(String name) {
		try {
			return appContext.getBean(name);
		} catch (Exception e) {
			logger.error("获取SpringBean(Name:" + name + ")失败：" + e.getMessage());
			return null;
		}
	}

	/**
	 * @param name
	 * @param requiredType
	 * @return 如果异常或找不到则返回null
	 */
	public Object getBean(String name, Class<?> requiredType) {
		try {
			return appContext.getBean(name, requiredType);
		} catch (Exception e) {
			logger.error("获取SpringBean(Name:" + name + "/Class:"
					+ requiredType.getName() + ")失败：" + e.getMessage());
			return null;
		}
	}

	/**
	 * 获取某一类的所有bean
	 * 
	 * @param type
	 * @return 如果异常或找不到则返回null
	 */
	public Map<String, ?> getBeansByType(Class<?> type) {
		try {
			return appContext.getBeansOfType(type);
		} catch (Exception e) {
			logger.error("获取SpringBeansMap(Class:" + type.getName() + ")失败："
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * 获取某一类的所有bean
	 * 
	 * @param type
	 *            bean的类型
	 * @param includeNonSingletons
	 *            是否允许非单例
	 * @param allowEagerInit
	 *            是否初始化lazy-init的bean
	 * @return 如果异常或找不到则返回null
	 */
	public Map<String, ?> getBeansByType(Class<?> type,
			boolean includeNonSingletons, boolean allowEagerInit) {
		try {
			return appContext.getBeansOfType(type, includeNonSingletons,
					allowEagerInit);
		} catch (Exception e) {
			logger.error("获取SpringBeansMap(Class:" + type.getName() + ")失败："
					+ e.getMessage());
			return null;
		}

	}

	/**
	 * 获取某一类的所有bean
	 * 
	 * @param type
	 * @return 如果异常或找不到则返回null
	 */
	@SuppressWarnings("rawtypes")
	public Object getFirstBeanByType(Class<?> type) {
		try {
			Map beansMap = appContext.getBeansOfType(type);
			return getMapFirstValue(beansMap);
		} catch (Exception e) {
			logger.error("获取SpringBean(Class:" + type.getName() + ")失败："
					+ e.getMessage());
			return null;
		}
	}

	/**
	 * 获取某一类的所有bean中的第一个
	 * 
	 * @param type
	 *            bean的类型
	 * @param includeNonSingletons
	 *            是否允许非单例
	 * @param allowEagerInit
	 *            是否初始化lazy-init的bean
	 * @return 如果异常或找不到则返回null
	 */
	@SuppressWarnings("rawtypes")
	public Object getFirstBeanByType(Class<?> type,
			boolean includeNonSingletons, boolean allowEagerInit) {
		try {
			Map beansMap = appContext.getBeansOfType(type,
					includeNonSingletons, allowEagerInit);
			return getMapFirstValue(beansMap);
		} catch (Exception e) {
			logger.error("获取SpringBean(Class:" + type.getName() + ")失败："
					+ e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	private Object getMapFirstValue(Map map) {
		return map.isEmpty() ? null : map.values().toArray()[0];
	}
}
