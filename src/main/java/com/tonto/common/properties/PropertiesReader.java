package com.tonto.common.properties;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.tonto.common.base.annotation.Convert;
import com.tonto.common.base.annotation.Property;
import com.tonto.common.base.annotation.PropertyConvert;
import com.tonto.common.base.util.ParseUtil;

public class PropertiesReader {
	
	private static Logger logger=Logger.getLogger(PropertiesReader.class);
	
	private Properties props; 	
	
	
	public PropertiesReader(Properties props)
	{
		if(props==null)
			throw new NullPointerException();
		this.props=props;
	}
	
	public PropertiesReader(InputStream input) throws IOException
	{
		props=new Properties();
		props.load(input);
	}
	
	
	public <T> T readObject(T obj)
	{
		Class<?> clazz=obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Property property = field.getAnnotation(Property.class);
			if (property != null) {
				// 暴力反射
				field.setAccessible(true);

			
				String name = property.name();
				
				if ("".equals(name)) {
					name = field.getName();
				}
				
				String str=(String) props.get(name);		
				Object value=null;
				
				Convert convert=field.getAnnotation(Convert.class);
				if(convert !=null)
				{
					Class<?> cla=convert.convert();									
					
					try {
						PropertyConvert<?> propertyconvert=(PropertyConvert<?>) cla.newInstance();
						value=propertyconvert.convert(str);					
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("不能创建"+cla.getName()+"的实例，无法转化值并赋值到"+name+"属性");
						continue;
					}	
				}	
				else
				{
					value=ParseUtil.parseString(str, property.type());
				}
				
						
				if(value==null&&!property.nullable())
				{
					logger.error(name+"不能为空！");
					continue;
				}
			
				try {
					field.set(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("无法把值:"+str+"赋值到"+name+"属性");
					continue;
				} 		
			}
		}
		return obj;
	}
	
	public Properties getProperties() {
		return props;
	}
	

}
