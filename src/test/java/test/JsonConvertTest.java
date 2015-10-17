package test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.common.json.JsonConvert;
import com.tonto.common.json.JsonConvertException;
import com.tonto.common.json.JsonValueValidateException;
import com.tonto.common.validate.annotation.Length;
import com.tonto.common.validate.annotation.Max;

public class JsonConvertTest {
	@Test
	public void convertTest() throws JsonParseException, JsonMappingException, IOException, NoSuchFieldException, SecurityException{
		Max m=B.class.getDeclaredField("age").getAnnotation(Max.class);
		System.out.println(m.annotationType()==Max.class);
		
		
		
		String json="{\"age\":43,\"name\":\"jack\",\"address\":[{" +
				"\"city\":\"上海\",\"distance\":\"宝山\"},{" +
				"\"city\":\"上海\",\"distance\":\"陆家嘴\"}]}";
		Map<String,Object> map=new ObjectMapper().readValue(json, Map.class);
		JsonConvert convert=new JsonConvert();
		try {
			B b=convert.convertMap(map, B.class,"root", null);
			System.out.println(b);
		} catch (JsonValueValidateException | JsonConvertException e) {
			e.printStackTrace();
		}
	}
	
	public static class A{
		@Length(minLength=3,maxLength=5)
		private String city;
		private String distance;
		
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getDistance() {
			return distance;
		}
		public void setDistance(String distance) {
			this.distance = distance;
		}
		
	}
	
	public static class B{
		
		@Max("22")
		private int age;
		
		private String name;
		private List<A> address;
		
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<A> getAddress() {
			return address;
		}
		public void setAddress(List<A> address) {
			this.address = address;
		}
	}
}
