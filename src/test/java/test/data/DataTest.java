package test.data;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tonto.data.core.source.ServiceDataSource;

public class DataTest {
	
	@Test
	public void serviceDataSourceTest(){
		
		Object[] params=new Object[2];
		params[0] = 1;
		params[1] = "AAA";
		
		DataService service = new DataService();
		service.paramTest(params);
		
		ServiceDataSource source=new ServiceDataSource(new DataService(),"getData");
		
		
		@SuppressWarnings("unchecked")
		Map<String,Object> result=(Map<String, Object>) source.getData(4,"carry");
		
		System.out.println(result.get("name"));
	}
	
	
	public class DataService{
		
		public Map<String,Object> getData(Integer orderId,String name){
			
			Map<String,Object> data=new HashMap<>();
			data.put("id", orderId);
			data.put("name", name);
			return data;
			
		}
		
		public void paramTest(Object... params)
		{
			String s="";
			for(Object p:params)
				s+=p.toString()+"/";
			
			System.out.print(s);
			
		}
		
	}
}


