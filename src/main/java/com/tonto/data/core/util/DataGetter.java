package com.tonto.data.core.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 数据获取工具类
 * @author TontoZhou
 *
 */
public class DataGetter {
	
	private static final Logger logger=Logger.getLogger(DataGetter.class);
	
	/**
	 * 根据寻路地址找到相应的值
	 * @param source 数据源
	 * @param path 例如address.1.street 表示取address属性下数组的第一个值下的street属性
	 * @return
	 */
	public static Object get(Object source,String path)
	{
		if(source == null || path == null)
			return null;
		
		path=path.trim();
		
		if("".equals(path))
			return null;
			
		
		String[] keys=path.split("\\.");
		
		for(String key:keys)
		{
	
			if(source instanceof List)
			{
				List<?> list=(List<?>) source;				
				int index=Integer.valueOf(key);
				source=list.get(index);				
			}
			else
			{
				Class<?> clazz=source.getClass();
				if(clazz.isArray())
				{
					int index=Integer.valueOf(key);
					source=Array.get(source, index);					
				}
				else if(source instanceof Map)
				{
					source = getFromMap((Map<?, ?>) source,key);
				}
				else
				{
					source = getFromObject(source,key);
				}
						
			}
			
			if(source == null)
				break;
		}
		
		
		return source;
	}
	
	/**
	 * 从Map中获取值
	 * @param source
	 * @param key
	 * @return
	 */
	public static Object getFromMap(Map<?,?> source,String key)
	{
		if(source==null)
			return null;
		return source.get(key);
	}
	
	/**
	 * 从Object中通过get方法获取对应属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getFromObject(Object obj,String fieldName)
	{
		if(obj==null)
			return null;
		
		Class<?> clazz=obj.getClass();
		
		
		char[] cs=fieldName.toCharArray();
		cs[0]-=32;
		fieldName=String.valueOf(cs);
		
		try {
			Method getMethod = clazz.getMethod("get"+fieldName);		
			return getMethod.invoke(obj);			
		} catch (NoSuchMethodException | SecurityException | 
				IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException e) {
			logger.error("无法从类:"+clazz+"的实例中获取对应属性:"+fieldName+"的值");
		}	
		
		return null;		
	}
	
}
