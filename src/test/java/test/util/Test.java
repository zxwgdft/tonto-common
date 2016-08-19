package test.util;

import java.math.BigDecimal;
import java.util.Date;

import com.tonto.common.util.XmlUtil;

public class Test {
	
	public static void main(String[] args){
		int c=10000000;
		Date n1=new Date();
		for(int i=c;i>0;i--)
			b(134234,23543245);
		System.out.println(System.currentTimeMillis()-n1.getTime());
		
		n1=new Date();
		for(int i=c;i>0;i--)
			a("134234.4234","235432.45");
		System.out.println(System.currentTimeMillis()-n1.getTime());
	}
	
	public static boolean b(int a,int b){
		return a>b;
	}
	
	public static boolean a(String a,String b){
		BigDecimal c = new BigDecimal(a);
		BigDecimal d = new BigDecimal(b);
		return c.compareTo(d)>=0;
	}
	
	@org.junit.Test
	public void tt(){
		
		
		
	}
}
