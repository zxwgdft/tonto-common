package com.tonto.common.util.ducument.interfaces;

import java.io.IOException;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServiceDocument {

	private static final Configuration templateConfig=new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);	
	
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
	
	
}
