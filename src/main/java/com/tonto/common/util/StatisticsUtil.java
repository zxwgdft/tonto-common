package com.tonto.common.util;

public class StatisticsUtil {
	
	/**
	 * 所有组合
	 * @param is
	 * @param m
	 * @return
	 */
	public static int[][] combination(int[] is,int m)
	{
		int[][] a=arrange(is,m);
		int[][] r=new int[C(is.length,m)][m];
		int c=0;
		int l=a.length;
		for(int i=0;i<l;i++)
		{
			int[] b=a[i];
			boolean z=true;
			for(int j=0;j<c;j++)
			{
				int[] d=r[j];
				if(equalArray(d,b))
				{
					z=false;
					break;
				}
			}
			
			if(z)
				r[c++]=b;
			if(c==r.length)
				break;
		}
	
		return r;		
	}
	
	
	private static boolean equalArray(int[] a,int[] b)
	{
		if(a==null||b==null||a.length!=b.length)
			return false;
		
		for(int i=0;i<a.length;i++)
		{
			int c=a[i];
			boolean d=false;
			for(int j:b)
				if(j==c)
				{
					d=true;
					break;
				}
			if(!d)
				return false;
		}
		
		return true;
	}
	/**
	 * 所有排列
	 * @param is
	 * @param m
	 * @return
	 */
	public static int[][] arrange(int[] is,int m)
	{
		
		if(is==null||is.length==0)
			throw new RuntimeException("array parameter can't be null or");
		if(m<=0||m>is.length)
			throw new RuntimeException("arrange number must <= array's length and >0");	
		int l=is.length;
		
		if(m==1)
		{
			
			int [][] r=new int[l][1];
			for(int i=0;i<l;i++)
				r[i][0]=is[i];
			return r;
		}
		else
		{
			int[][] r=new int[A(l,m)][m];
			
			int c=0;
			for(int i=0;i<l;i++)
			{
				int[] sis=new int[l-1];
				for(int j=0,k=0;j<l;j++)
					if(j!=i)
						sis[k++]=is[j];
				
				int[][] rs=arrange(sis,m-1);
				for(int p=0;p<rs.length;p++)
				{
					int[] a=rs[p];
					int[] b=new int[m];
					b[0]=is[i];
					System.arraycopy(a, 0, b, 1, a.length);
					r[c++]=b;
				}
			}
			
			return r;
		}	
	}
	
	
	
	/**
	 * 排列可能数，n>m
	 * @param n
	 * @param m
	 * @return
	 */
	public static int A(int n,int m)
	{
		if(n<0||m>n)
			return -1;
		int s=1;
		m=n-m;
		for(;n>m;n--)
			s*=n;			
		return s;
	}
	
	/**
	 * 组合可能数，n>m
	 * @param n
	 * @param m
	 * @return
	 */
	public static int C(int n,int m)
	{
		if(n<0||m>n)
			return -1;
		return A(n,m)/getFactorial(m);
	}
	
	
	
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
