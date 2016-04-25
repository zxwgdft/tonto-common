package com.tonto.common.util;

/**
 * 运算工具类
 * @author TontoZhou
 *
 */
public class AlgorithmUtil {
	
	/**
	 * 获取n的阶乘,0的阶乘为1,非自然数没有阶乘
	 * @param n 
	 * @return 如果n为非自然数返回-1
	 */
	public static int getFactorial(int n)
	{
		if(n<0)
			return -1;
		int s=1;
		for(;n>0;n--)
			s*=n;
		return s;
	}
	

	
}
