package com.tonto.data.core.source;

import com.tonto.data.core.DataSource;

/**
 * 枚举数据源
 * 
 * <p>根据枚举类型名称或序号获取对应枚举类</p>
 * @author TontoZhou
 *
 */
public class EnumDataSource<T extends Enum<T>> implements DataSource{

	private T[] constants;
	private Class<T> enumClass; 
	
	public EnumDataSource(Class<T> enumClass)
	{
		if(!enumClass.isEnum())
			throw new IllegalArgumentException("参数必须为枚举类型");
		
		this.constants=enumClass.getEnumConstants();	
		this.enumClass=enumClass;
		
	}
	
	@Override
	public Object getData(Object... id) {
		Object key=id[0];
		
		if(key!=null && constants.length>0)
		{
			if(key instanceof Integer)
			{
				int index=(Integer)key;
				if(index>=0&&index<constants.length)
					return constants[index];
			}
			else if(key instanceof String)
			{
				String name=(String) key;				
				return Enum.valueOf(enumClass, name);
			}			
		}

		return null;
	}

	@Override
	public String getKey() {
		return enumClass.getName();
	}

}
