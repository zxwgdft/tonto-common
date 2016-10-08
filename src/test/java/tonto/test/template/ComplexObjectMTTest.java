package tonto.test.template;

import org.junit.Test;

import tonto.test.base.InstanceFactory;

import com.tonto2.common.template.ComplexObjectMessageTemplate;

public class ComplexObjectMTTest {
	
	@Test
	public void test()
	{
		
		String str = "我的座驾[#{name}]，#{engin}，第二个轮子颜色：#{wheels.1.color},速度可达#{datas.speed}时速";
		
		ComplexObjectMessageTemplate template = new ComplexObjectMessageTemplate(str,'#');
		
		String message = template.createMessage(InstanceFactory.getCar());
		
		System.out.println(message);
	}
	
}
