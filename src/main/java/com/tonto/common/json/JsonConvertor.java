package com.tonto.common.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class JsonConvertor {
	
	private final static Logger logger=Logger.getLogger(JsonConvertor.class);
	
	public static interface Convert<E>{
		public E convertValue(Object obj) throws JsonConvertException;
	}
	
	private final static Map<Class<?>,Convert<?>> convertMap;
	
	private final static SimpleDateFormat defaultDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static{
		convertMap=new HashMap<Class<?>,Convert<?>>();
		
		Convert<Short> shortConvert=new Convert<Short>(){
			@Override
			public Short convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				if(obj instanceof Number)
					return ((Number)obj).shortValue();
				throw new JsonConvertException(obj+"无法转化为Short");
			}			
		};
		
		convertMap.put(Short.class, shortConvert);
		convertMap.put(short.class, shortConvert);
		
		Convert<Byte> byteConvert=new Convert<Byte>(){
			@Override
			public Byte convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				if(obj instanceof Number)
					return ((Number)obj).byteValue();
						
				throw new JsonConvertException(obj+"无法转化为Byte");
			}			
		};
		
		convertMap.put(Byte.class, byteConvert);
		convertMap.put(byte.class, byteConvert);
		
		Convert<Boolean> booleanConvert=new Convert<Boolean>(){
			@Override
			public Boolean convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				try{
					if(obj instanceof String)
						return Boolean.parseBoolean((String)obj);						
					if(obj instanceof Boolean)
						return (Boolean) obj;
				}
				catch(Exception e){
					
				}
				
				throw new JsonConvertException(obj+"无法转化为Boolean");
			}			
		};
		
		convertMap.put(Boolean.class, booleanConvert);
		convertMap.put(boolean.class, booleanConvert);
		
		Convert<Integer> integerConvert=new Convert<Integer>(){
			@Override
			public Integer convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				if(obj instanceof Number)
					return ((Number)obj).intValue();
				
				throw new JsonConvertException(obj+"无法转化为Integer");
			}			
		};
		
		convertMap.put(Integer.class, integerConvert);
		convertMap.put(int.class, integerConvert);
		
		
		Convert<Long> longConvert=new Convert<Long>(){
			@Override
			public Long convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				if(obj instanceof Number)
					return ((Number)obj).longValue();
				
				throw new JsonConvertException(obj+"无法转化为Long");
			}			
		};
		
		convertMap.put(Long.class, longConvert);
		convertMap.put(long.class, longConvert);
		
		Convert<Double> doubleConvert=new Convert<Double>(){
			@Override
			public Double convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				if(obj instanceof Number)
					return ((Number)obj).doubleValue();
				
				throw new JsonConvertException(obj+"无法转化为Double");
			}			
		};
				
		convertMap.put(Double.class, doubleConvert);
		convertMap.put(double.class, doubleConvert);
		
		convertMap.put(BigInteger.class, new Convert<BigInteger>(){
			@Override
			public BigInteger convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				try{
					if(obj instanceof String)
						return new BigInteger((String)obj);						
					if(obj instanceof Long || obj instanceof Integer)
						return new BigInteger(obj.toString());
					if(obj instanceof BigInteger)
						return (BigInteger) obj;
				}
				catch(Exception e){
					
				}
				
				throw new JsonConvertException(obj+"无法转化为BigInteger");
			}			
		});
		
		convertMap.put(Date.class, new Convert<Date>(){
			@Override
			public Date convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				try{
					if(obj instanceof String)
					{
						return defaultDateFormat.parse((String) obj);						
					}
					
					if(obj instanceof Long)
					{
						Calendar date= Calendar.getInstance();
						date.setTimeInMillis((long) obj);
						return date.getTime();
					}
					
					if(obj instanceof BigInteger)
					{
						Calendar date= Calendar.getInstance();
						date.setTimeInMillis(((BigInteger) obj).longValue());
						return date.getTime();
					}
					
					if(obj instanceof Date)
						return (Date) obj;
					if(obj instanceof Calendar )
						return ((Calendar ) obj).getTime();
				}
				catch(Exception e){
					
				}
				
				throw new JsonConvertException(obj+"无法转化为Date");
			}			
		});
		
		convertMap.put(Calendar.class, new Convert<Calendar>(){
			@Override
			public Calendar convertValue(Object obj) throws JsonConvertException{
				if(obj==null)
					return null;
				try{
					if(obj instanceof String)
					{
						Calendar date= Calendar.getInstance();
						date.setTime(defaultDateFormat.parse((String) obj));
						return date;						
					}
					
					if(obj instanceof Long)
					{
						Calendar date= Calendar.getInstance();
						date.setTimeInMillis((long) obj);
						return date;
					}
					
					if(obj instanceof BigInteger)
					{
						Calendar date= Calendar.getInstance();
						date.setTimeInMillis(((BigInteger) obj).longValue());
						return date;
					}
					
					if(obj instanceof Date)
					{
						Calendar date= Calendar.getInstance();
						date.setTime((Date) obj);
						return date;
					}
					if(obj instanceof Calendar )
						return (Calendar ) obj;
				}
				catch(Exception e){
					
				}
				
				throw new JsonConvertException(obj+"无法转化为Calendar");
			}			
		});
	}
	
	private Map<Class<?>,Convert<?>> privateClass2ConvertMap;
	private Map<String,Convert<?>> privateName2ConvertMap;
	
	/**
	 * 根据类型添加自定义转化规则，
	 * 根据类型的自定义转化规则优先级次于根据属性名，高于默认
	 * @param clazz
	 * @param convert
	 */
	public void addConvertByClass(Class<?> clazz,Convert<?> convert)
	{
		if(privateClass2ConvertMap==null)
			privateClass2ConvertMap=new HashMap<Class<?>,Convert<?>>();
		privateClass2ConvertMap.put(clazz, convert);	
	}
	
	/**
	 * <p>根据属性地址和名称添加自定义的转化规则,该规则优先级最高</p>
	 * <p>
	 * 		{@code path}规则：例如A{String s,B[] bs},B{integer i},我要添加A类中bs的i的规则，path为bs.i
	 * </p>
	 * @param name
	 * @param convert
	 */
	public void addConvertByName(String path,Convert<?> convert)
	{
		if(privateName2ConvertMap==null)
			privateName2ConvertMap=new HashMap<String,Convert<?>>();
		privateName2ConvertMap.put(path, convert);	
	}
	
	private SimpleDateFormat dateFormat=defaultDateFormat;
	private Convert<Calendar> calendarConvert;
	private Convert<Date> dateConvert;
	
	public void setSimpleDateFormat(SimpleDateFormat format)
	{
		if(format==null)
			dateFormat=defaultDateFormat;
		else
			dateFormat=format;
		
		if(calendarConvert==null)
		{
			calendarConvert=new Convert<Calendar>(){

				@Override
				public Calendar convertValue(Object obj)
						throws JsonConvertException {
					if(obj==null)
						return null;
					try{
						if(obj instanceof String)
						{							
							Calendar date= Calendar.getInstance();
							date.setTime(dateFormat.parse((String) obj));
							return date;
						}
						
						if(obj instanceof Long)
						{
							Calendar date= Calendar.getInstance();
							date.setTimeInMillis((long) obj);
							return date;
						}
						
						if(obj instanceof BigInteger)
						{
							Calendar date= Calendar.getInstance();
							date.setTimeInMillis(((BigInteger) obj).longValue());
							return date;
						}
						
						if(obj instanceof Date)
						{
							Calendar date= Calendar.getInstance();
							date.setTime((Date) obj);
							return date;
						}
						if(obj instanceof Calendar )
							return (Calendar ) obj;
					}
					catch(Exception e){
						
					}
					
					throw new JsonConvertException(obj+"无法转化为Calendar");
				}
				
			};
			if(privateClass2ConvertMap==null||!privateClass2ConvertMap.containsKey(Calendar.class))
				addConvertByClass(Calendar.class,calendarConvert);
		}
		
		if(dateConvert==null)
		{
			dateConvert=new Convert<Date>(){

				@Override
				public Date convertValue(Object obj)
						throws JsonConvertException {
					if(obj==null)
						return null;
					try{
						if(obj instanceof String)
						{
							return dateFormat.parse((String) obj);						
						}
						
						if(obj instanceof Long)
						{
							Calendar date= Calendar.getInstance();
							date.setTimeInMillis((long) obj);
							return date.getTime();
						}
						
						if(obj instanceof BigInteger)
						{
							Calendar date= Calendar.getInstance();
							date.setTimeInMillis(((BigInteger) obj).longValue());
							return date.getTime();
						}
						
						if(obj instanceof Date)
							return (Date) obj;
						if(obj instanceof Calendar )
							return ((Calendar ) obj).getTime();
					}
					catch(Exception e){
						
					}
					
					throw new JsonConvertException(obj+"无法转化为Date");
				}
				
			};
			if(privateClass2ConvertMap==null||!privateClass2ConvertMap.containsKey(Date.class))
				addConvertByClass(Date.class,dateConvert);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> convertList(List<?> list,Class<T> clazz,String path) throws JsonConvertException
	{		
		if(list==null)
			return null;
		
		Convert<?> convert=getConvert(path,clazz);
				
		List<T> result=new ArrayList<T>(list.size());
		int i=0;
		for(Object obj:list)
		{
			if(convert!=null)
				result.add((T) convert.convertValue(obj));
			else if(obj instanceof Map)
			{
				result.add((T) convertMap((Map<String, Object>)obj,clazz,path==null?"["+i+"]":path+"["+i+"]"));			
			}			
			else
			{
				if(logger.isDebugEnabled())
					logger.debug("不符合转化规则");
				return null;
			}
			i++;
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] convertArray(List<?> list,Class<T> clazz,String path) throws JsonConvertException
	{
		if(list==null)
			return null;
		
		Convert<?> convert=getConvert(path,clazz);
		
		T[] result=(T[]) Array.newInstance(clazz, list.size());
		int i=0;
				
		for(Object obj:list)
		{
			if(convert!=null)
				result[i]=(T) convert.convertValue(obj);
			else if(obj instanceof Map)
			{
				result[i]=(T) convertMap((Map<String, Object>)obj,clazz,path==null?"["+i+"]":path+"["+i+"]");			
			}
			else
			{
				if(logger.isDebugEnabled())
					logger.debug("不符合转化规则");
				return null;
			}
			i++;
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T convertMap(Map<String,Object> map,Class<T> clazz,String path) throws JsonConvertException
	{
		if(clazz==null)
			return null;
		
		T object;
		
		try {
			object = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			throw new JsonConvertException("不能创建"+clazz+"的实例");
		}		
		
		if(map==null)
			return object;

		
		List<Class<?>> classes=new ArrayList<Class<?>>();
		
		Class<?> superClass=clazz;
		
		do{
			classes.add(superClass);
			superClass=superClass.getSuperclass();
		
		}while(superClass!=Object.class&&superClass!=null);
		
		for(Entry<String,Object> entry:map.entrySet())
		{
			String key=entry.getKey();
			Object value=entry.getValue();
			
			if(value==null)
				continue;		
			
			for(Class<?> cla:classes)
			{
				try {
					Field field=cla.getDeclaredField(key);
					
					Class<?> fieldClass=field.getType();
					
					String name=field.getName();
					char[] cs=name.toCharArray();
					cs[0]-=32;
					name=String.valueOf(cs);
					
					try {
						Method setMethod=clazz.getDeclaredMethod("set"+name,fieldClass);
						Object setValue=null;
						
						String subPath;
						if(path!=null)
							subPath=path+"."+key;
						else
							subPath=key;
						
						if(value instanceof List)
						{
							if(fieldClass.isArray())
							{
								Class<?> arrayType=fieldClass.getClass().getComponentType();								
								setValue=convertArray((List)value,arrayType,subPath);								
							}	
							else if(List.class.isAssignableFrom(fieldClass))
							{
								Class<?> listType=(Class<?>) ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];								
								setValue=convertList((List)value,listType,subPath);
								
							}
							else
							{
								if(logger.isDebugEnabled())
									logger.debug("不能解析List到"+fieldClass);
								continue;
							}
						}
						else if(value instanceof Map)
						{
							Map<String,Object> _map=(Map<String, Object>) value;
							setValue=convertMap(_map,fieldClass,subPath);							
						}
						else
						{
							Convert<?> convert=getConvert(subPath,fieldClass);
														
							if(convert!=null)
							{
								setValue=convert.convertValue(value);
							}
							else
							{
								if(fieldClass==value.getClass())
								{
									setValue=value;									
								}
								else
								{
									if(logger.isDebugEnabled())
										logger.debug(value.getClass()+"无法匹配到"+fieldClass);
									break;
								}
							}
						}

						
						try {									
							setMethod.invoke(object, setValue);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
							if(logger.isDebugEnabled())
								logger.debug("调用方法"+setMethod+"传入参数"+setValue+"失败！");
						}	
						
						break;
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
					
				} catch (NoSuchFieldException | SecurityException e) {
					
				}
			}
		}
				
		return object;
	}
	
	
	private Convert<?> getConvert(String path,Class<?> clazz)
	{		
		Convert<?> convert=null;
		
		if(path!=null&&privateName2ConvertMap!=null)	
		{
			String str=path.replaceAll("\\[\\d+\\]", "");
			convert=privateName2ConvertMap.get(str);
		}
		if(convert==null&&privateClass2ConvertMap!=null)
			convert=privateClass2ConvertMap.get(clazz);
		if(convert==null)
			convert=convertMap.get(clazz);
		return convert;
	}
}
