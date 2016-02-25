package com.tonto.common.template;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据传入的{@code Object}对模板进行创建消息，可传入{@link java.util.Map}类型参数或简单JAVA类型
 * <p>Map:通过key取值并替换模板中相应的值</p>
 * <p>简单类型:通过key相应的get方法取值并替换模板中相应的值</p>
 * 
 * @author TontoZhou
 *
 */
public class ObjectMessageTemplate implements MessageTemplate {
	
	private String template;
	
	public ObjectMessageTemplate(InputStream input) throws IOException
	{
		setTemplateInputStream(input);
	}
	
	public ObjectMessageTemplate(String template)
	{
		setTemplate(template);
	}
	
	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public String createMessage(Object... args) {
		
		String msg=template;
		
		if(args!=null)
			for(Object obj:args)
				msg=create(msg,obj);
		
		return msg;
	}
	
	/**
	 * 替换{key}关键字并创建新的字符串
	 * @param msg
	 * @param obj
	 * @return
	 */
	private String create(String msg,Object obj)
	{	
		if(mapper!=null&&obj!=null)
		{
			/*
			 * 为了效率使用StringBuilder拼接{key}格式前后的字符串
			 */
			if(obj instanceof Map)
			{
				@SuppressWarnings("rawtypes")
				Map mapObj=(Map) obj;
				
				Matcher matcher=pattern.matcher(msg);
				
				StringBuilder sb=new StringBuilder();
				
				int i=0;
				for(String name:mapper)
				{
					matcher.find();					
					Object value=mapObj.get(name);
					if(value!=null)
					{
						sb.append(msg,i,matcher.start()).append(value);
						i=matcher.end();
					}
					else	
					{
						sb.append(msg,i,matcher.end());
						i=matcher.end();
					}
				}
				
				sb.append(msg,i,msg.length());
				return sb.toString();
			}
			else
			{				
				Matcher matcher=pattern.matcher(msg);
				
				StringBuilder sb=new StringBuilder();
				int i=0;
				for(String name:mapper)
				{
					matcher.find();	
					Object value=null;
					
					try {
						char[] cs=name.toCharArray();
						cs[0]-=32;
						name=String.valueOf(cs);						
						Method getMethod = obj.getClass().getMethod("get"+name);
						value=getMethod.invoke(obj);	
					} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					}
					
					if(value!=null)
					{
						sb.append(msg,i,matcher.start()).append(value.toString());
						i=matcher.end();
					}
					else	
					{
						sb.append(msg,i,matcher.end());
						i=matcher.end();
					}
				}
				
				sb.append(msg,i,msg.length());
				return sb.toString();
			}			
		}
		return msg;
	}
	
	private final static Pattern namePattern=Pattern.compile("(?<=\\{)[a-z0-9A-Z_]+?(?=\\})");
	
	private final static Pattern pattern=Pattern.compile("\\{[a-z0-9A-Z_]+?\\}");
	
	//模板参数map
	private List<String> mapper;
	//初始化
	private void init()
	{		
		if(template!=null)
		{
			mapper=new ArrayList<String>();
			
			Matcher matcher=namePattern.matcher(template);
			while(matcher.find())
				mapper.add(matcher.group());				
		}
	}
	
	private final static Charset utf8=Charset.forName("utf-8");
	
	public void setTemplateInputStream(InputStream input) throws IOException
	{	
		byte[] data=new byte[input.available()];
		input.read(data);
		setTemplate(new String(data,utf8));
	}
	
	public void setTemplate(String template) {
		this.template = template;
		init();
	}
	
}
