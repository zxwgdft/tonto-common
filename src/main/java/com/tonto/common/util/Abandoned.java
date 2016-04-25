package com.tonto.common.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Abandoned {
	@SuppressWarnings("unchecked")
	public static <T> List<T[]> getAllSortArray(T[] arr)
	{
		if(arr==null)
			return null;
		int l=arr.length;
		if(l==0)
			return null;
		
		int[] is=new int[l];
		for(int i=0;i<l;i++)
			is[i]=i;
		
		int[][] sorts=sortArray(is);
		
		List<T[]> list=new ArrayList<T[]>(sorts.length);
		
		for(int[] sort:sorts)
		{
			Object obj= Array.newInstance(arr.getClass().getComponentType(), sort.length);
			for(int j=0;j<sort.length;j++)
				Array.set(obj, j, arr[sort[j]]);
			list.add((T[]) obj);
		}
		
		return list;
	}
	
	/**
	 * 所有可能的排序，长度为n的数组的可能排序为n的阶乘
	 * @param arr
	 * @return
	 */
	public static int[][] sortArray(int[] arr)
	{
		if(arr==null)
			return null;
		int l=arr.length;
		
		if(l==1)
		{
			return new int[][]{{arr[0]}};
		}
			
		if(l==2)
		{
			int[][] ts= new int[2][2];
			ts[0][0]=arr[1];
			ts[0][1]=arr[0];
			ts[1][0]=arr[0];
			ts[1][1]=arr[1];
			return ts;
		}
		else{			
			List<int[]> arrlist=new ArrayList<int[]>(AlgorithmUtil.getFactorial(l));
			for(int i=0;i<l;i++)
			{
				int[] x=new int[l-1];
				for(int j=0,m=0;j<l;j++)
				{
					if(j!=i)
						x[m++]=arr[j];
				}
				int [][] r=sortArray(x);
				
				for(int n=0;n<r.length;n++)
				{
					int[] a=r[n];
					int[] b=new int[l];
					b[0]=arr[i];
					for(int k=1;k<l;k++)
					{
						b[k]=a[k-1];
					}
					arrlist.add(b);
				}
				
			}
			
			int[][] ts=new int[arrlist.size()][l];
			int s=0;
			for(int[] c:arrlist)
			{
				ts[s++]=c;
			}
			return ts;
		}	
	}
}
