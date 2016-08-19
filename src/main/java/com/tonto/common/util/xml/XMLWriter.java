package com.tonto.common.util.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import com.tonto.common.util.NameUtil;

public class XMLWriter {
	
	
	
	
	
	/**
	 * Object to XML
	 * @param obj
	 * @return
	 */
	private String object2xml(Object obj, String key, StringBuilder sb)
	{		
		sb.append("<").append(key).append(">");
		
		if (obj != null)
		{
			Class<?> clazz = obj.getClass();
	
			if(clazz != Object.class)
			{	
				
				if(Map.class.isAssignableFrom(clazz))
				{
					//Map map = (Map) obj;
					
					
					
				}
				
				
				Method[] methods = clazz.getMethods();
		
				for (Method method : methods) {
					
					String name = method.getName();
								
					if (name.startsWith("get") && !"getClass".equals(name) && name.length() > 3 && method.getParameterTypes().length == 0 && method.getReturnType() != void.class) {
						
						name = NameUtil.removeGetOrSet(name);
						
						sb.append("<").append(name).append(">");
											
						try {
							
							Object value = method.invoke(obj);
							
							if(value != null)
							{
								Class<?> cal = value.getClass();
								
								if(Number.class.isAssignableFrom(cal))
								{
									sb.append(value.toString());
								}
								else if(cal == Date.class)
								{
									sb.append(((Date)value).getTime());
								}
								else if(cal == String.class)
								{
									sb.append((String)value);
								}
								else
								{
									object2xml(value,name,sb);
								}
							}
							
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						
						sb.append("<").append(name).append("/>");
		
					}
		
				}
			}
		}
		
		sb.append("<").append(key).append("/>");
		
		return sb.toString();
	}
	
	
	public String write(Object obj)
	{
		return object2xml(obj,"xml",new StringBuilder());
		
	}
	
	
	
	
	
	
}
