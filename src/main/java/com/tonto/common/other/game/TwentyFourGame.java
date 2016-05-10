package com.tonto.common.other.game;

import com.tonto.common.util.ProbabilityUtil;


/**
 * 24点游戏
 * @author TontoZhou
 *
 */
public class TwentyFourGame {
	
	private final int result_number=24;
	
	/**
	 * 列出所有可能计算公式计算
	 * @param iArr
	 * @return
	 */
	public String play1(int... iArr)
	{
		if(iArr.length>1)
		{
			StringBuilder result=new StringBuilder();
						
			int s=iArr.length;
						
			int[][] numberArr=ProbabilityUtil.P(s);
			int[][] signArr=ProbabilityUtil.AA(4,s-1);
			
			for(int[] numbers:numberArr)
			{
				for(int[] signs:signArr)
				{
					int t=iArr[numbers[0]];
					for(int i=1;i<s;i++)
					{
						int sign=signs[i-1];
						int num=iArr[numbers[i]];
												
						if(sign==0)
						{
							t+=num;
						}
						else if(sign==1)
						{
							t-=num;
						}
						else if(sign==2)
						{
							t*=num;
						}
						else
						{
							if(num!=0&&t%num==0)
								t/=num;
							else
							{
								if(t==result_number)
									t++;
								break;
							}
						}						
					}
					
					if(t==result_number)
					{
						print(result,iArr,numbers,signs);
						result.append("\n");
					}
					
				}
			}
			
			return result.toString();
		}
		else
		{
			return "impossible";
		}
	}
	
	String[] signStr={"+","-","*","/"};
	private void print(StringBuilder sb,int[] arr,int[] numbers,int[] signs){
		int i=0;
		for(;i<signs.length;i++)
			sb.append(arr[numbers[i]]).append(signStr[signs[i]]);
		sb.append(arr[numbers[i]]);
	}
	
	/**
	 * 倒推算
	 * @param iArr
	 * @return
	 */
	public String play2(int... iArr)
	{
		return "";
	}
}
