package com.tonto.common.util.ducument.interfaces;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.tonto.common.util.ducument.interfaces.test.TestB;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServiceDocument {

	private static final Configuration templateConfig=new Configuration();	
	
	static{		
		templateConfig.setClassForTemplateLoading(ServiceDocument.class,"");
	}
	
	
	
	public static void createServiceDocument(WebService service, Writer writer)
	{
		
		try {
			Template template = templateConfig.getTemplate("web_interface.xml");
			template.process(service, writer);
		} catch (TemplateException | IOException e) {
			e.printStackTrace();			
			throw new RuntimeException("生成文档失败");
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) throws IOException{
		WebService service = new WebService();
		service.setObjectListClass(TestB.class, false);
		service.setName("测试");
		service.setUrl("ssssss");
		service.setMethod("sdfsdfsd");
		service.setRequestType("Get");
		
		createServiceDocument(service , new FileWriter("d:/interface.doc"));
	}
	
	
	
}
