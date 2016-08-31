package com.tonto2.common.template.annotation;

import java.lang.reflect.Field;

import com.tonto2.common.template.DefaultMessageTemplate;
import com.tonto2.common.template.MessageTemplate;
import com.tonto2.common.template.ObjectMessageTemplate;
import com.tonto2.common.template.SimpleMessageTemplate;

/**
 * 模板创建器
 * @author TontoZhou
 *
 */
public class TemplateCreator {
	
	/**
	 * 创建类中静态消息模板（注解方式，并且有值为null）
	 * @param clazz
	 */
	public static void createStatic(Class<?> clazz){		
		create(clazz,clazz);
	}
	
	/**
	 * 创建实例中的消息模板（注解方式，并且有值为null）
	 * @param obj
	 */
	public static void create(Object obj){			
		create(obj.getClass(),obj);
	}
	
	/**
	 * 创建消息
	 * @param clazz
	 * @param obj
	 */
	private static void create(Class<?> clazz,Object obj){
		
		while(clazz != null)
		{
			Field[] fields=clazz.getDeclaredFields();
			
			for(Field field:fields)
			{
				try {
					if(field.get(obj) != null)
						continue;
				} catch (IllegalArgumentException | IllegalAccessException e) {
				}
				
				Template template=field.getAnnotation(Template.class);
				
				if(template==null)
					continue;
				
				TemplateType type=template.type();
				String content=template.content();
				
				MessageTemplate messageTemplate=null;
				
				if(type == TemplateType.DEFAULT)
				{
					messageTemplate = new DefaultMessageTemplate(content);				
				}
				else if(type == TemplateType.SIMPLE_FORMAT)
				{
					messageTemplate = new SimpleMessageTemplate(content);				
				}
				else if(type == TemplateType.OBJECT_MAPPER)
				{
					messageTemplate = new ObjectMessageTemplate(content);				
				}
				
				if(messageTemplate!=null)
				{
					field.setAccessible(true);
					try {
						field.set(obj,messageTemplate);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						
					}				
				}			
			}
			
			clazz = clazz.getSuperclass();
		}
	}
		
}
