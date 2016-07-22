package test.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.junit.Test;

import com.tonto.common.util.PropertyUtil;
import com.tonto.common.util.test.Execution;
import com.tonto.common.util.test.TestUtil;

public class PropertiesTest {
	
	//测试多线程
	public void test() throws UnsupportedEncodingException, InterruptedException{
		
		Thread t1 = new Thread(new Trun());
		Thread t2 = new Thread(new Trun());
		Thread t3 = new Thread(new Trun());
		
		t1.setName("t1");
		t2.setName("t2");
		t3.setName("t3");
		
		t1.start();
		t2.start();
		t3.start();
		
		
		Thread.sleep(100000000);
	}
	
	@Test
	public void testEfficiency(){	
		int count = 100000;
		
		long t1 = TestUtil.executionTime(new Execution(){

			@Override
			public void execute() {
				
				Properties props = new Properties();
				Map<String, String> properties = new HashMap<>();

				try {

					props.load(PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties"));

					for (Entry<Object, Object> entry : props.entrySet()) {
						Object oKey = entry.getKey();

						if (oKey != null && oKey instanceof String) {

							Object oVal = entry.getValue();

							properties.put((String) oKey, (oVal == null) ? "" : oVal.toString());
						}
					}
					
					properties.get("name");

				} catch (IOException e) {

				}
				
				
			}
			
			
		}, count);
		

		long t2 = TestUtil.executionTime(new Execution(){

			@Override
			public void execute() {
				PropertyUtil.getInstantProperty("config.properties","name");
			}
		},count);
		
		System.out.println("t1:"+t1);
		System.out.println("t2:"+t2);
	}
	
	
	private static class Trun implements Runnable{

		@Override
		public void run() {
			
			while(true)
			{
				
				System.out.println(Thread.currentThread().getName()+":"+PropertyUtil.getInstantProperty("config.properties","name"));
			
				
			}
			
		}
		
	}

}
