package com.tonto.common.util;

public class ProbabilityUtil {
	
	
	
	
	/**
	 * 从m个元素中选取n个元素的所有可能排列
	 * @param m
	 * @param n
	 * @return
	 */
	@SuppressWarnings("unused")
	public int[][] A(int m,int n)
	{
		if(m>=n&&n>0)
		{
			int probableCount=AlgorithmUtil.getFactorial(m)/AlgorithmUtil.getFactorial(m-n);
			
			int[][] result=new int[probableCount][n];
			
			for(int i=0;i<n;i++)
				result[0][i]=i;
			
			int r=0;
			
			int[] sign=new int[m];
			
//			while(true)
//			{
//				int c=0;
//				
//				for(int i=0;i<m;i++)
//				{
//					
//					
//				}
//			}
			
			//for(int j=0;j<m;j++)
			//{
				//int[i++]=j;
				//for()
				
			//}
			
			
			return null;
		}
		else
		{
			throw new IllegalArgumentException();
		}				
	}
	
	
	@SuppressWarnings("unused")
	private void A(int[][] matrix,int r,int c,int m,int n)
	{
		//if()
		
	}
	
	/**
	 * 从m个元素中选取n个元素的所有可能组合
	 * @param m
	 * @param n
	 * @return
	 */
	public int[][] C(int m,int n)
	{
		
		return null;
	}
	
	
	/**
	 * m个元素所有可能排列
	 * @param m
	 * @return
	 */
	@SuppressWarnings("unused")
	private int[][] P(int m)
	{
		return A(m,m);				
	}
	
	
	
}
