package test.prop;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.properties.PropertiesReader;

public class PropertiesReaderTest {
	
	@Test
	public void readPropTest() throws IOException 
	{
//		PropertiesReader reader=new PropertiesReader(PropertiesReaderTest.class.getClassLoader().getResourceAsStream("config.properties"));
//		Config config=reader.readObject(new Config());
//		Assert.assertEquals(config.age,new Integer(17));
		
		
//		int i="N060".compareTo("N033");
//		
//		System.out.println(i);
//		
		
		String mchant="3,2,SAP073RQ316ZLKR2ZMEXC63DOKTA0ACS ,34";
		
		String[] ss=mchant.split(",");
		System.out.println(ss);
		
		Pattern p=Pattern.compile("[a-z0-9A-Z_]{32}");
		Matcher m=p.matcher(mchant);
		while(m.find())
			System.out.println(m.group());
	}
}
