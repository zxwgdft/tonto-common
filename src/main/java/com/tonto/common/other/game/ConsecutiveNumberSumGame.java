package com.tonto.common.other.game;

/**
 * 题目描述：一个正整数有可能可以被表示为n(n>=2)个连续正整数之和，如：
    15=1+2+3+4+5
    15=4+5+6
    15=7+8
	    请编写程序，根据输入的任何一个正整数，找出符合这种要求的所有连续正整数序列
	。    输入数据：一个正整数，以命令行参数的形式提供给程序。   输出数据：在标准输
	出上打印出符合题目描述的全部正整数序列，每行一个序列，每个序列都从该序列的最小
	正整数开始、以从小到大的顺序打印。如果结果有多个序列，按各序列的最小正整数的大
	小从小到大打印各序列。此外，序列不允许重复，序列内的整数用一个空格分隔。如果没
	有符合要求的序列，输出“NONE”。 
	    例如，对于15，其输出结果是：
	    1 2 3 4 5
	    4 5 6
	    7 8
	    对于16，其输出结果是：
	    NONE
	   评分标准：程序输出结果是否正确。
 * @author TontoZhou
 *
 */
public class ConsecutiveNumberSumGame {
	
	
	public static void main(String[] args){
		System.out.println("16:");
		getNums(16);
		System.out.println("15:");
		getNums(15);
		System.out.println("33:");
		getNums(33);
	}
	
	private static void getNums(int n)
	{
		if(n>2)
		{
			int m=n>6?(n%2==0?n/2:n/2+1):n-1;
			for(int i=2;i<m;i++)
			{
				int[] r=getM_N(i,n);
				if(r!=null)
				{
					int a=r[0];
					int b=r[1];
					String str="";
					for(;b<=a;b++)
					{
						str+=b+" ";
					}
					System.out.println(str);	
				}
				else
					System.out.println("NONE");
			}
		}
	}
	
	/**
	 * m-n=c-1
	 * (m+n)*c=2*x
	 * @param c
	 * return m,n or null
	 */
	private static int[] getM_N(int c,int x)
	{
		int y=2*x;
		if(y%c!=0) return null;
		int z=(y/c+c-1);
		if(z%2!=0) return null;
		int m=z/2;
		int n=m-c+1;
		if(n<=0) return null;
		return new int[]{m,n};
	}
	
	
	
}
