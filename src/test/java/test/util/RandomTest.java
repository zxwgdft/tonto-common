package test.util;

import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.util.RandomObject;
import com.tonto.common.util.RandomObject.RandomValueCreator;

public class RandomTest {
	@Test
	public void randomObjectTest(){		
		
		
		RandomObject random=new RandomObject();
		random.addFieldCreator("phone", new RandomValueCreator<String>(){

			@Override
			public String getRandomValue() {
				return "13584950680";
			}			
		});
		B b=random.createRandomObject(B.class);
		Assert.assertTrue(b!=null);
	}
	
	
	
}
