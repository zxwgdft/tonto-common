package test.prop;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.properties.PropertiesReader;

public class PropertiesReaderTest {
	
	@Test
	public void readPropTest() throws IOException 
	{
		PropertiesReader reader=new PropertiesReader(PropertiesReaderTest.class.getClassLoader().getResourceAsStream("config.properties"));
		Config config=reader.readObject(new Config());
		Assert.assertEquals(config.age,new Integer(17));
		
		
	}
}
