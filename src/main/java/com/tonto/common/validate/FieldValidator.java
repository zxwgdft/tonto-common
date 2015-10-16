package com.tonto.common.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import com.tonto.common.validate.annotation.Digit;
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

		validators.put(Max.class, new AnnotationValidate<Max>() {

			@Override
			public boolean validate(Max annotation, Object value) {
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
			public boolean validate(Min t, Object value) {
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
			public boolean validate(Digit t, Object value) {
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
			public boolean validate(Length t, Object value) {
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
			public boolean validate(MaxLength t, Object value) {
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
			public boolean validate(MinLength t, Object value) {
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
			public boolean validate(NotNull t, Object value) {
				return value!=null;						
			}

		});
		
		validators.put(Pattern.class, new AnnotationValidate<Pattern>() {

			@Override
			public boolean validate(Pattern t, Object value) {
				if(value==null)
					return false;
				String valStr=value.toString();
				return valStr==null?false:valStr.matches(t.value());						
			}
		});
	}
	
	private Map<Class<? extends Annotation>, AnnotationValidate<?>> privateValidators;
	
	public <T extends Annotation> void addAnnotationValidate(Class<T> ann,AnnotationValidate<T> validate)
	{
		if(ann==null)
			throw new NullPointerException("Annotation class cann't be null");
		
		if(validate==null)
			throw new NullPointerException("Validate cann't be null");
		
		if(privateValidators==null)
			privateValidators=new HashMap<Class<? extends Annotation>, AnnotationValidate<?>>();
		privateValidators.put(ann, validate);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ValidateResult validate(Field field, Object value) {
				
		if(value==null)
		{
			NotNull notnull=field.getAnnotation(NotNull.class);
			if(notnull!=null)
				return new ValidateResult(true);
			else
				return new ValidateResult(false,getErrorMessage(notnull));
		}
					
		Annotation[] anns=field.getDeclaredAnnotations();
		for(Annotation ann:anns)
		{
			AnnotationValidate validate=null;
			if(privateValidators!=null)
				validate=privateValidators.get(ann.getClass());
			if(validate==null)
				validate=validators.get(ann.getClass());
			if(validate!=null)
			{
				boolean result = validate.validate(ann, value);
				if(!result)
				{
					return new ValidateResult(false,getErrorMessage(ann));
				}
			}
		}
		
		return new ValidateResult(true);
	}
	
	private final java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("(?<=\\{)[a-z0-9A-Z_]+?(?=\\})");
	
	public String getErrorMessage(Annotation annotation)
	{
		Class<?> clazz=annotation.getClass();
		
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
					message=message.replaceFirst("\\{"+s+"\\}", meth.invoke(annotation).toString());
				}
				return message;
			}
			
		} catch (NoSuchMethodException | SecurityException | 
				IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	public static class ValidateResult{
		private boolean result;
		private String message;
		
		public ValidateResult(boolean result)
		{
			this.result=result;
		}
		
		public ValidateResult(boolean result,String message)
		{
			this.result=result;
			this.message=message;
		}
		
		public boolean isResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
