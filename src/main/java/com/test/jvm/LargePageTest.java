package com.test.jvm;

public class LargePageTest {
	
	public static void main(String[] args){
		
		/***
		 * 下面的数组大小为 4*2048 >4K（最小分页大小），所以会出现跨页取值，从导致效率降低
		 * 
		 * 可使用大分页内存，需要在linux或2003windows server上才能使用大分页，这里不能测试
		 * 
		 * 
		 * 
		 */
		
		int size=2048;
		
		int[][] mul1 = new int[size][size];
		int[][] mul2 = new int[size][size];
		
		
		for(int m=0;m<size;m++)
			for(int n=0;n<size;n++)
			{
				mul1[n][m]=n+m;
				mul2[n][m]=m-n;
			}
		
		int[][] res = new int[size][size];
		
		long now = System.currentTimeMillis();
		
		for (int i = 0; i < size; i++)  
		    for (int j = 0; j < size; j++)  
		        for (int k = 0; k < size; k++)  
		            res[i][j] += mul1[i][k] * mul2[k][j];  
		
		System.out.println(System.currentTimeMillis()-now);
		
	}
	
}
