package com.tonto.data.core;

/**
 * 数据源
 * @author TontoZhou
 *
 */
public interface DataSource {

	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	public Object getData(Object...id);
	
	/**
	 * 数据源的唯一标识
	 * @return
	 */
	public String getKey();
	
}
