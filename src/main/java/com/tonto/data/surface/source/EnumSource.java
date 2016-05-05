package com.tonto.data.surface.source;

import org.apache.log4j.Logger;

import com.tonto.data.core.source.EnumDataSource;

/**
 * 枚举类资源定义
 * @author TontoZhou
 *
 */
@SuppressWarnings("rawtypes")
public class EnumSource extends SourceSurface<EnumDataSource>{
	
	private final Logger logger = Logger.getLogger(EnumSource.class);
	
	/**
	 * 枚举的 class name
	 */
	private String className;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public EnumDataSource createDataSource() {
		
		if(className == null)
			throw new NullPointerException();
		
		try {
			Class<?> clazz = Class.forName(className);
			
			if(!clazz.isEnum())
			{
				logger.error("转化枚举数据资源错误，类："+className+"不是枚举类型");
				return null;
			}
				
			return new EnumDataSource(clazz);
			
		} catch (ClassNotFoundException e) {
			logger.error("转化枚举数据资源错误，不能找到对应的枚举类："+className,e);
		}
		
		
		return null;
	}
	
	@Override
	public Class<EnumDataSource> dataSourceType() {
		return EnumDataSource.class;
	}
	
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	
}
