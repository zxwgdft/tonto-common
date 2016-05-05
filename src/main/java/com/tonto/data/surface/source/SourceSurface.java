package com.tonto.data.surface.source;

import com.tonto.data.core.DataSource;

/**
 * 资源定义，资源描述或者为资源的外部表现
 * <p>用于资源对外的描述定义，从描述定义中获取到相应的{@link DataSource}</p>
 * @author TontoZhou
 *
 */
public abstract class SourceSurface<T extends DataSource> {

	/**
	 * 数据资源
	 */
	protected T dataSource;
	
	/**
	 * 获取数据资源
	 * @return
	 */
	public T getDataSource(){
		if(dataSource == null)
			dataSource = createDataSource();
		return dataSource;
	}
	
	
	/**
	 * 根据描述定义创建数据资源
	 * @return
	 */
	public abstract T createDataSource();
	
	/**
	 * 数据资源类型
	 * @return
	 */
	public abstract Class<T> dataSourceType();
}
