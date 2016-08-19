package test.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.junit.Test;

import com.tonto.common.template.MessageTemplate;
import com.tonto.common.template.annotation.Template;
import com.tonto.common.template.annotation.TemplateType;



public class A {
	private String name;
	private int age;
	
	@Template(type=TemplateType.DEFAULT,content="我通过了你的好友验证请求，现在我们可以开始聊天了")
	private static MessageTemplate MESSAGE_TEMPLATE_NEW_FRIEND;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public static String getMessage(){
		
		return MESSAGE_TEMPLATE_NEW_FRIEND.createMessage();
	}
	
	@Test
	public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		
		Method m = A.class.getMethod("getAge");
		
		Object val = m.invoke(new A());
		
		System.out.println("class:"+val.getClass());
		
		
		BigDecimal bd = new BigDecimal("2323.44445");
		Double f = (double) 333.4534546456;
		
		
		System.out.println(f);
		
	}
}
