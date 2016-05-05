package test.util;

import org.junit.Assert;
import org.junit.Test;

import com.tonto.common.util.RandomObject;
import com.tonto.common.util.RandomObject.RandomValueCreator;
import com.tonto.data.core.util.DataGetter;

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
		
		Object v1=DataGetter.get(b, "ts.0.0");
		Object v2=DataGetter.get(b, "addresses.0");
		Object v3=DataGetter.get(b, "a.name");
		
		System.out.println(":ss");
		Assert.assertTrue(b!=null);
	}
	
	
	
}
