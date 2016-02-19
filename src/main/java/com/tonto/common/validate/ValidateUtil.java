package com.tonto.common.validate;

import java.lang.reflect.Array;
import java.util.Collection;

public class ValidateUtil {
	
	/**
	 * 判断一个数组中的全部数字是否在另一个数组中，或判断一个数是否在一个数组中
	 * @param value	可以是一个{@link Number}的实现类，或者数组
	 * @param array
	 * @return 
	 */
	public static boolean isInIntegerArray(Object value,int[] array)
	{
		if(value!=null&&array!=null)
		{
			Class<?> clazz=value.getClass();
			if(Number.class.isAssignableFrom(clazz))
			{
				Number num=(Number) value;
				int val=num.intValue();
				for(int i:array)
					if(i==val)
						return true;
			}		
			else if(clazz.isArray())
			{
				int len=Array.getLength(value);
				if(len==0)
					return false;
				for(int i=0;i<len;i++)
				{
					Object obj=Array.get(value, i);
					if(!isInIntegerArray(obj,array))
						return false;
				}
				return true;
			}
			else if(Collection.class.isAssignableFrom(clazz))
			{
				Collection<?> coll=(Collection<?>) value;
				if(coll.size()==0)
					return false;
				for(Object obj:coll)
				{
					if(!isInIntegerArray(obj,array))
						return false;
				}
				return true;
			}
	
		}
		
		return false;
	}
	
	public static boolean isBetweenDigit(){
		
		return false;
	}
	
}
