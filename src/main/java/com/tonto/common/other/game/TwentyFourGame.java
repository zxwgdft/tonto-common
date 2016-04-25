package com.tonto.common.other.game;

import java.util.List;

import com.tonto.common.util.AlgorithmUtil;

public class TwentyFourGame {
	
	
	public String getTwentyFour(int a,int b,int c,int d){				
		List<Integer[]> numberList=null;//AlgorithmUtil.getAllSortArray(new Integer[]{a,b,c,d});
		List<String[]> signList=null;//AlgorithmUtil.getAllSortArray(new String[]{"+","-","x","%"});
		
		for(Integer[] number:numberList)
		{
			for(String[] sign:signList)
			{
				int x=number[0];
				int i=1;
				boolean isGo=true;
				for(String s:sign)
				{
					
					switch(s){
						case "+":x+=number[i++];break;
						case "-":x-=number[i++];break;
						case "x":x*=number[i++];break;
						case "%":{
							if(x%number[i]==0)
							{
								x/=number[i++];
								break;
							}
							else
							{
								isGo=false;
							}
						}
					}	
					
					if(!isGo)
						break;
				}
				
				if(isGo&&x==24)
					return number[0]+sign[0]+number[1]+sign[1]+number[2]+sign[2]+number[3];
			}
			
		}
		
		
		return "";
	}
}
