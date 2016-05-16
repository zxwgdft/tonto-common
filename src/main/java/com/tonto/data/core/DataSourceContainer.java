package com.tonto.data.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源容器
 * 
 * @author TontoZhou
 * 
 */
public class DataSourceContainer {

	/** 数据源集合 */
	private Map<String, DataSource> dataSourceMap = new HashMap<>();

	/**
	 * 获取一个数据源
	 * 
	 * @param key
	 * @return
	 */
	public DataSource getDataSource(String key) {
		return dataSourceMap.get(key);
	}

	/**
	 * 添加一个数据源
	 * 
	 * @param source
	 */
	public void addDataSource(DataSource source) {
		dataSourceMap.put(source.getKey(), source);
	}

	/**
	 * 移除数据源
	 * 
	 * @param key
	 */
	public void removeDataSource(String key) {
		dataSourceMap.remove(key);
	}

}
