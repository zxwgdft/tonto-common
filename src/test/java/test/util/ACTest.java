package test.util;

import org.junit.Test;

import com.tonto.common.other.game.TwentyFourGame;
import com.tonto.common.util.ProbabilityUtil;

public class ACTest {
	
	
	public void AATest(){
		
		int[][] as=ProbabilityUtil.AA(3, 2);
		
		for(int[] a:as)
		{
			String s="[";
			for(int b:a)
				s+=b+",";
			s+="]";
			System.out.println(s);
		}
	}
	
	@Test
	public void twentyfour(){
		TwentyFourGame game=new TwentyFourGame();		
		System.out.println(game.play1(7,8,2,4));
	}
}
