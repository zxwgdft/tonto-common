package com.tonto.common.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import com.tonto.common.validate.AnnotationValidate;
import com.tonto.common.validate.Validate;
import com.tonto.common.validate.annotation.Cellphone;
import com.tonto.common.validate.annotation.Email;
import com.tonto.common.validate.annotation.Digit;
import com.tonto.common.validate.annotation.IntegerEnum;
import com.tonto.common.validate.annotation.Length;
import com.tonto.common.validate.annotation.Max;
import com.tonto.common.validate.annotation.MaxLength;
import com.tonto.common.validate.annotation.Min;
import com.tonto.common.validate.annotation.MinLength;
import com.tonto.common.validate.annotation.NotNull;
import com.tonto.common.validate.annotation.Pattern;

public class FieldValidator {

	private final static Map<Class<? extends Annotation>, AnnotationValidate<?>> validators;

	static {
		validators = new HashMap<Class<? extends Annotation>, AnnotationValidate<?>>();
		
		/*
		 * 创建默认验证规则
		 */
		
		validators.put(Cellphone.class, new AnnotationValidate<Cellphone>() {
			
			java.util.regex.Pattern phonePattern=java.util.regex.Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|17[0|6|7|8]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
			
			@Override
			public boolean validate(Cellphone annotation, Object value,
					Object validateObj) {
				if(value==null)
					return false;
				return phonePattern.matcher(value.toString()).matches();
			}
			
		});
		
		
		validators.put(Email.class, new AnnotationValidate<Email>() {
			
			java.util.regex.Pattern emailPattern=java.util.regex.Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
			
			@Override
			public boolean validate(Email annotation, Object value,
					Object validateObj) {
				if(value==null)
					return false;
				return emailPattern.matcher(value.toString()).matches();
			}
			
		});
		
		validators.put(IntegerEnum.class, new AnnotationValidate<IntegerEnum>() {
			
			private boolean numberIn(Object value,int[] values){
				Integer val=null;
				if(value instanceof Integer)
					val=(Integer) value;
				if(value instanceof Short)
					val=(Integer) value;
				if(value instanceof BigDecimal)
					val=((BigDecimal) value).intValue();
				if(value instanceof BigInteger)
					val=((BigInteger) value).intValue();
				if(value instanceof Long)
					val=((Long) value).intValue();
				
				if(val!=null)
				{
					for(int i:values)
					{
						if(i==val)
							return true;
					}
				}
			
				return false;
			}
			
			@SuppressWarnings("rawtypes")
			@Override
			public boolean validate(IntegerEnum annotation, Object value,Object validateObj) {
				if(value==null)
					return false;
				int[] vs=annotation.value();
				
				Class<?> clazz=value.getClass();
				if(clazz.isArray())
				{
					int len=Array.getLength(value);
					for(int i=0;i<len;i++)
					{
						Object obj=Array.get(value, i);
						if(!numberIn(obj,vs))
							return false;
					}
					return true;
				}
				else if(Collection.class.isAssignableFrom(clazz))
				{
					Collection coll=(Collection) value;
					for(Object obj:coll)
					{
						if(!numberIn(obj,vs))
							return false;
					}
					return true;
				}
				else
				{
					return numberIn(value,vs);
				}
				
			}

		});
		
		validators.put(Max.class, new AnnotationValidate<Max>() {

			@Override
			public boolean validate(Max annotation, Object value,Object validateObj) {
				if(value==null)
					return false;
				try {
					BigDecimal max = new BigDecimal(annotation.value());
					boolean inclusive = annotation.inclusive();
					int comparisonResult = new BigDecimal(value.toString())
							.compareTo(max);
					return inclusive ? comparisonResult <= 0
							: comparisonResult < 0;
				} catch (NumberFormatException nfe) {
					return false;
				}
			}

		});

		validators.put(Min.class, new AnnotationValidate<Min>() {

			@Override
			public boolean validate(Min t, Object value,Object validateObj) {
				if(value==null)
					return false;			
				
				try {
					BigDecimal min = new BigDecimal(t.value());
					boolean inclusive = t.inclusive();
					int comparisonResult = new BigDecimal(value.toString())
							.compareTo(min);
					return inclusive ? comparisonResult >= 0
							: comparisonResult > 0;
				} catch (NumberFormatException nfe) {
					return false;
				}
			}

		
		});

		validators.put(Digit.class, new AnnotationValidate<Digit>() {

			@Override
			public boolean validate(Digit t, Object value,Object validateObj) {
				if(value==null)
					return false;
				
				
				try {
					BigDecimal max = new BigDecimal(t.max());
					BigDecimal min = new BigDecimal(t.min());
					boolean inclusive = t.inclusive();
					BigDecimal val=new BigDecimal(value.toString());
					int maxResult = val.compareTo(max);
					int minResult = val.compareTo(min);
					return inclusive ? (maxResult <= 0 && minResult >= 0)
							: (maxResult < 0 && minResult > 0);
				} catch (NumberFormatException nfe) {
					return false;
				}
			}

		
		});
		
		validators.put(Length.class, new AnnotationValidate<Length>() {

			@Override
			public boolean validate(Length t, Object value,Object validateObj) {
				if(value==null)
					return false;
				
				int max=t.maxLength();
				int min=t.minLength();
				int length=value.toString().length();
				boolean inclusive = t.inclusive();
				return inclusive?(length<=max&&length>=min):(length<max&&length>min);
				
			}	
		});
		
		validators.put(MaxLength.class, new AnnotationValidate<MaxLength>() {

			@Override
			public boolean validate(MaxLength t, Object value,Object validateObj) {
				if(value==null)
					return false;
				
				int max=t.value();
				int length=value.toString().length();
				boolean inclusive = t.inclusive();
				return inclusive?length<=max:length<max;				
			}

		});
		
		validators.put(MinLength.class, new AnnotationValidate<MinLength>() {

			@Override
			public boolean validate(MinLength t, Object value,Object validateObj) {
				if(value==null)
					return false;
				
				int min=t.value();
				int length=value.toString().length();
				boolean inclusive = t.inclusive();
				return inclusive?length>=min:length>min;				
			}

		});
		
		
		validators.put(NotNull.class, new AnnotationValidate<NotNull>() {

			@Override
			public boolean validate(NotNull t, Object value,Object validateObj) {
				return value!=null;						
			}

		});
		
		validators.put(Pattern.class, new AnnotationValidate<Pattern>() {

			@Override
			public boolean validate(Pattern t, Object value,Object validateObj) {
				if(value==null)
					return false;
				String valStr=value.toString();
				return valStr==null?false:valStr.matches(t.value());						
			}
		});
	}
	
	private Map<Class<? extends Annotation>, AnnotationValidate<?>> privateAnnValidates;
	
	/**
	 * 添加针对某个{@link Annotation}的验证规则
	 * @param ann
	 * @param validate
	 */
	public <T extends Annotation> void addAnnotationValidate(Class<T> ann,AnnotationValidate<T> validate)
	{
		if(ann==null)
			throw new NullPointerException("Annotation class cann't be null");
		
		if(validate==null)
			throw new NullPointerException("Validate cann't be null");
		
		if(privateAnnValidates==null)
			privateAnnValidates=new HashMap<Class<? extends Annotation>, AnnotationValidate<?>>();
		privateAnnValidates.put(ann, validate);
	}

	private Map<String,Validate> privateValidates;
	
	/**
	 * <p>针对fieldName添加验证规则</p>
	 * <p>
	 * 		对于子类中的字段的验证添加{@code fieldName}时，使用点路径表示从第一个节点类到某个子类中字段，
	 * 		例如address.city表示验证字段address下的city子字段
	 * </p>
	 * @param fieldName
	 * @param validate
	 */
	public void addValidate(String fieldName,Validate validate)
	{
		if(validate==null)
			throw new NullPointerException("Validate class cann't be null");
		
		if(fieldName==null)
			throw new NullPointerException("FieldName cann't be null");
		
		if(privateValidates==null)
			privateValidates=new HashMap<String,Validate>();
		
		privateValidates.put(fieldName, validate);
	}
	
	
	/**
	 * 
	 * @param field
	 * @param value
	 * @param path				字段路径
	 * @param currentObj		当前验证的类
	 * @param validateObj		完整的需要验证的实例
	 * @return 如果有错误则返回{@link ValidateError},没有则返回null
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ValidateError validate(Field field,Object value,String path,Object currentObj,Object validateObj) {
		
		String fieldName=field.getName();
		if(path!=null)
			fieldName=path+"."+fieldName;
		
		if(privateValidates!=null)
		{
			String str=fieldName.replaceAll("\\[\\d+\\]", "");
			Validate validate=privateValidates.get(str);
			if(validate!=null)
			{
				String errorMsg=validate.validate(value,currentObj,validateObj);
				if(errorMsg!=null)
					return new ValidateError(fieldName,errorMsg);
			}
		}
		
		
		if(value==null)
		{
			NotNull notnull=field.getAnnotation(NotNull.class);
			if(notnull==null)
				return null;
			else
				return new ValidateError(fieldName,getErrorMessage(notnull));
		}
					
		Annotation[] anns=field.getDeclaredAnnotations();
		for(Annotation ann:anns)
		{
			AnnotationValidate annValidate=null;
			if(privateAnnValidates!=null)
				annValidate=privateAnnValidates.get(ann.annotationType());
			if(annValidate==null)
				annValidate=validators.get(ann.annotationType());
			if(annValidate!=null)
			{
				boolean result = annValidate.validate(ann, value, validateObj);
				if(!result)
					return new ValidateError(fieldName,getErrorMessage(ann));
			}
		}
		
		return null;
	}
	
	public ValidateError validate(Object obj)
	{
		return validate(obj,null,obj);
	}
	
	@SuppressWarnings("rawtypes")
	public ValidateError validate(Object obj,String path,Object validateObj)
	{
		if(obj==null)
			throw new NullPointerException("validate object can't be null");
		
		Class<?> clazz=obj.getClass();
		
		List<ValidateError> errors=new ArrayList<ValidateError>();
		
		boolean isArray=false;
		
		if(clazz.isArray())
		{
			int length=Array.getLength(obj);
			
			
			for(int i=0;i<length;i++)
			{
				String subPath;
				
				if(path!=null)
					subPath=path+"["+i+"]";
				else
					subPath="["+i+"]";
				
				Object subObj=Array.get(obj, i);
				ValidateError error=validate(subObj, subPath, validateObj);
				if(error!=null)
					errors.add(error);
			}	
			
			isArray=true;
		}	
		else if(Collection.class.isAssignableFrom(clazz))
		{
			Collection coll=(Collection) obj;
			int i=0;
			for(Object subObj:coll)
			{
				String subPath;
				
				if(path!=null)
					subPath=path+"["+(i++)+"]";
				else
					subPath="["+(i++)+"]";
				
				ValidateError error=validate(subObj, subPath, validateObj);
				if(error!=null)
					errors.add(error);			
			}
			
			isArray=true;
		}
		else
		{
			if(isBaseClass(clazz))	
				return null;
			
			List<Class<?>> classes=new ArrayList<Class<?>>();
			
			Class<?> superClass=clazz;
			
			do{
				classes.add(superClass);
				superClass=superClass.getSuperclass();			
			}while(superClass!=Object.class&&superClass!=null);
			
			for(Class<?> cla:classes)
			{				
				Field[] fields=cla.getDeclaredFields();
				
				for(Field field:fields)
				{
					Class<?> fieldClass=field.getType();
		
					if(Modifier.isStatic(field.getModifiers()))
						continue;
					
					String name=field.getName();
					char[] cs=name.toCharArray();
					cs[0]-=32;
					name=String.valueOf(cs);
											
					try {
						Method getMethod = clazz.getDeclaredMethod("get"+name);
						Object val=getMethod.invoke(obj);						
						
						ValidateError error=validate(field,val,path,obj,validateObj);
						if(error!=null)
							errors.add(error);
												
						if(!isBaseClass(fieldClass))
						{
							String subPath;
							if(path==null)
								subPath=field.getName();
							else
								subPath=path+"."+field.getName();
							
							error=validate(val,subPath,validateObj);
							if(error!=null)
								errors.add(error);
						}		
						
					} catch (NoSuchMethodException | SecurityException | 
							IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						
					}
				}	
				
			}
		}
		
		
		if(errors.size()>0)
			return new ValidateError(path,"验证失败",errors,isArray);
		return null;
	}
	
	/**
	 * 是否基础类型
	 * @param clazz
	 * @return
	 */
	private boolean isBaseClass(Class<?> clazz)
	{
		return clazz == Object.class || clazz.isEnum() || Number.class.isAssignableFrom(clazz) ||
				clazz==Date.class  || clazz==String.class;
		
	}
	
	private final java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("(?<=\\{)[a-z0-9A-Z_]+?(?=\\})");
	
	/**
	 * 根据{@link Annotation}获取错误消息模板并得到具体消息
	 * @param annotation
	 * @return
	 */
	public String getErrorMessage(Annotation annotation)
	{
		Class<?> clazz=annotation.annotationType();
		
		try {
			Method method=clazz.getDeclaredMethod("message");
			Object obj=method.invoke(annotation);
			if(obj instanceof String)
			{
				String str=(String)obj;
				String message=str;
				Matcher matcher=pattern.matcher(str);
				while(matcher.find())
				{
					String s=matcher.group();
					Method meth=clazz.getDeclaredMethod(s);
					
					Object val=meth.invoke(annotation);
					String valStr=null;
					Class<?> valClass=val.getClass();
					if(valClass.isArray())
					{
						StringBuilder sb=new StringBuilder("[");
						int len=Array.getLength(val);
						for(int i=0;i<len;i++)
						{
							if(i==0)
								sb.append(Array.get(val, i));
							else
								sb.append(",").append(Array.get(val, i));
						}
						sb.append("]");
						valStr=sb.toString();
					}
					else 
						valStr=val.toString();
					
					message=message.replaceFirst("\\{"+s+"\\}", valStr);
				}
				return message;
			}
			
		} catch (NoSuchMethodException | SecurityException | 
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public static class ValidateError{
		
		private String name;			//验证的字段名称
		private String message;			//验证信息
		private List<ValidateError> subValidateResult;			//如果验证的字段有子字段需要验证，这些子字段的验证结果
		private boolean isArray=false;		//是否为数组，集合
		
		
		public ValidateError(String name,String message)
		{
			this.name=name;
			this.message=message;
		}
		
		public ValidateError(String name,String message,List<ValidateError> subValidateResult)
		{
			this.name=name;
			this.message=message;
			this.subValidateResult=subValidateResult;
		}
		
		public ValidateError(String name,String message,List<ValidateError> subValidateResult,boolean isArray)
		{
			this.name=name;
			this.message=message;
			this.subValidateResult=subValidateResult;
			this.isArray=isArray;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public List<ValidateError> getSubValidateResult() {
			return subValidateResult;
		}

		public void setSubValidateResult(List<ValidateError> subValidateResult) {
			this.subValidateResult = subValidateResult;
		}

		public boolean isArray() {
			return isArray;
		}

		public void setArray(boolean isArray) {
			this.isArray = isArray;
		}
		
		public String toString()
		{
			StringBuilder sb=new StringBuilder();
			if(name!=null)
				sb.append(name).append(":").append(message).append("\r\n");
			if(subValidateResult!=null)
			{
				for(ValidateError error:subValidateResult)
					sb.append(error.toString());
			}
			return sb.toString();
		}
		
	}
}
