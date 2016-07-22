package com.tonto.common.util.test;

public class TestUtil {
	
	public static long executionTime(Execution e,int count)
	{
		long start = System.currentTimeMillis();
		for(;count>0;count--)
			e.execute();
		
		return System.currentTimeMillis()-start;
	}	
	
}


