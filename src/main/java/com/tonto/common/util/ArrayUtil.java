package com.tonto.common.util;

import java.util.List;

public class ArrayUtil {
	
	/**
	 * 判断是否object在数组里
	 * @param obj
	 * @param array
	 * @return	如果obj或array为空则返回false，如果obj不在array中返回false
	 */
	public static boolean isObjectInArray(Object obj,List<Object> array)
	{
		if(obj!=null&&array!=null)
			for(Object o:array)
			{
				if(obj.equals(o))
					return true;
			}
		return false;
	}
	
	/**
	 * 判断是否object在数组里
	 * @param obj
	 * @param array
	 * @return 如果obj或array为空则返回false，如果obj不在array中返回false
	 */
	public static boolean isObjectInArray(Object obj,Object[] array)
	{
		if(obj!=null&&array!=null)
			for(Object o:array)
			{
				if(obj.equals(o))
					return true;
			}
		return false;
	}
	
	/**
	 * 
	 * @param str
	 * @param array
	 * @return
	 */
	public static boolean isStringInArray(String str,Object[] array)
	{
		if(str!=null&&array!=null)
		{
			for(Object o:array)
			{
				if(o!=null&&str.equals(o.toString()))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param i
	 * @param array
	 * @return
	 */
	public static boolean isIntegerInArray(int i,Object[] array)
	{
		if(array!=null)
		{
			if(Number.class.isAssignableFrom(array.getClass().getComponentType()))
			{
				Number[] arr=(Number[]) array;
				for(Number a:arr)
				{
					if(a!=null&&a.intValue()==i)
						return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param i
	 * @param array
	 * @return
	 */
	public static boolean isIntegerInArray(int i,int[] array)
	{
		if(array!=null)
		{
			for(int a:array)
				if(a==i)
					return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param l
	 * @param array
	 * @return
	 */
	public static boolean isLongInArray(long l,Object[] array)
	{
		if(array!=null)
		{
			if(Number.class.isAssignableFrom(array.getClass().getComponentType()))
			{
				Number[] arr=(Number[]) array;
				for(Number a:arr)
				{
					if(a!=null&&a.longValue()==l)
						return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param l
	 * @param array
	 * @return
	 */
	public static boolean isLongInArray(long l,long[] array)
	{
		if(array!=null)
		{
			for(long a:array)
				if(a==l)
					return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param d
	 * @param array
	 * @return
	 */
	public static boolean isDoubleInArray(double d,Object[] array)
	{
		if(array!=null)
		{
			if(Number.class.isAssignableFrom(array.getClass().getComponentType()))
			{
				Number[] arr=(Number[]) array;
				for(Number a:arr)
				{
					if(a!=null&&a.doubleValue()==d)
						return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param d
	 * @param array
	 * @return
	 */
	public static boolean isDoubleInArray(double d,double[] array)
	{
		if(array!=null)
		{
			for(double a:array)
				if(a==d)
					return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param f
	 * @param array
	 * @return
	 */
	public static boolean isFloatInArray(float f,Object[] array)
	{
		if(array!=null)
		{
			if(Number.class.isAssignableFrom(array.getClass().getComponentType()))
			{
				Number[] arr=(Number[]) array;
				for(Number a:arr)
				{
					if(a!=null&&a.floatValue()==f)
						return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 * 
	 * @param f
	 * @param array
	 * @return
	 */
	public static boolean isDoubleInArray(float f,float[] array)
	{
		if(array!=null)
		{
			for(double a:array)
				if(a==f)
					return true;
		}
		return false;
	}
	
	
}
