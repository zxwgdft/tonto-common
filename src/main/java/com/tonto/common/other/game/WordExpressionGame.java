package com.tonto.common.other.game;

import com.tonto.common.util.StatisticsUtil;

/**
 * 求满足ABCD*E=DCBA的数字，其中ABCD各代表一个0-9的数字
 * 
 * @author TontoZhou
 *
 */
public class WordExpressionGame {
	
	public static int[] is={0,1,2,3,4,5,6,7,8,9};
		
	public static void main(String[] args){

		int[][] result=StatisticsUtil.arrange(is, 5);
		for(int[] r:result)
		{
			test(r[0],r[1],r[2],r[3],r[4]);			
		}
	}	
	
	public static void test(int a,int b,int c, int d,int e){
		if((a*1000+b*100+c*10+d)*e==(d*1000+c*100+b*10+a))
		{
			System.out.println(""+a+b+c+d+"*"+e);
		}
	}
}
