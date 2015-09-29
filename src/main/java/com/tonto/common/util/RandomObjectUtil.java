package com.tonto.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RandomObjectUtil extends ReflectUtil{
	private String name;
	private int age;
	private Double income;
	
	
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		RandomObjectUtil obj =new RandomObjectUtil();
		Class<?> cla=RandomObjectUtil.class;
		Method[] ms=cla.getDeclaredMethods();
		for(Method m:ms)
		{
			System.out.println(cla.getSimpleName()+":"+m.getName());
			mm(m,obj);
		}
		Class<?> superCla=cla.getSuperclass();
		ms=superCla.getDeclaredMethods();
		for(Method m:ms)
		{
			System.out.println(superCla.getSimpleName()+":"+m.getName());
			mm(m,obj);
		}
		
		System.out.println(obj.age);
	}
	
	private static void mm(Method m,Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		 if(m.getName().startsWith("set"))
		 {
			 Class<?>[] types=m.getParameterTypes();
			 if(types.length==1)
			 {
				 Class<?> type=types[0];
				 if(type.equals(int.class))
				 {
					 m.invoke(obj, new Integer(3));
				 }
				 else if(type.equals(Double.class))
				 {
					 m.invoke(obj, 3.34);
				 }
				 else if(type.equals(String.class))
				 {
					 m.invoke(obj, "sfsd");
				 }
			 }
		 }
	}
	
	
	public String getAA()
	{
		return "aa";
	}
	public static int getBB()
	{
		return 1;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public Double getIncome() {
		return income;
	}


	public void setIncome(Double income) {
		this.income = income;
	}
}
