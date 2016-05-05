package com.tonto.data.surface.source.service;

/**
 * 实例获取接口
 * @author TontoZhou
 *
 */
public interface BeanAcquire {
	
	/**
	 * 获取实例
	 * @param name
	 * @return
	 */
	public Object getBean(String name);
}
