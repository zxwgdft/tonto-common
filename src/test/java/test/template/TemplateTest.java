package test.template;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.tonto.common.template.ShortMessageTemplate;

public class TemplateTest {
	
	@Test
	public void test(){
		ShortMessageTemplate  t=new ShortMessageTemplate("我%是%name%,我在玩%game%");
		
		Map<String,String> data=new HashMap<>();
		data.put("name", "tonto");
		data.put("game", "dota");
		
		System.out.println(t.createMessage(data));
		
		
		System.out.println(TemplateTest.class.getName());
	}
}
