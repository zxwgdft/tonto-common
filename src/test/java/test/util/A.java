package test.util;

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
}
