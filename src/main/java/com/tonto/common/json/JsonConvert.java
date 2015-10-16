package com.tonto.common.json;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.tonto.common.validate.FieldValidator;
import com.tonto.common.validate.FieldValidator.ValidateResult;

public class JsonConvert {
	
	public static <T> T convert(Map<String,Object> map,Class<T> clazz)
	{
		if(clazz==null)
			throw new NullPointerException("转化的目标类不能为空");
		
		try {
			T obj=clazz.newInstance();
			
			if(map==null)
				return obj;
			
			List<Class<?>> classes=new ArrayList<Class<?>>();
			
			Class<?> superClass;
			
			do{
				superClass=clazz.getSuperclass();
				classes.add(superClass);
			}while(superClass!=Object.class&&superClass!=null);
			
			
			FieldValidator validator=new FieldValidator();
			
			
			
			
			for(Entry<String,Object> entry:map.entrySet())
			{
				String key=entry.getKey();
				Object value=entry.getValue();
				
				if(value==null)
					continue;		
				
				for(Class<?> cla:classes)
				{
					try {
						Field field=cla.getField(key);
						
						Class<?> fieldClass=field.getType();
						
						String name=field.getName();
						char[] cs=name.toCharArray();
						cs[0]-=32;
						name=String.valueOf(cs);
						
						try {
							Method setMethod=clazz.getMethod("set"+name,fieldClass);
							ValidateResult validateResult=validator.validate(field, value);
							
							
							
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						}
						
					} catch (NoSuchFieldException | SecurityException e) {
						
					}
				}
			}
			
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();			 
		}

		return null;
	}
	
	
}
