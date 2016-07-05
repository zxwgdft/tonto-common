package com.tonto.common.util.ducument.interfaces;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class InterfaceDocumentUtil {
	
	private static final Configuration cfg=new Configuration();	
	
	static{	
		cfg.setClassForTemplateLoading(InterfaceDocumentUtil.class,"");
	}
	
	
	public static void toList(Attribution attribution, List<Attribution> attributions) {
		
		if(attribution == null)
			return;
		
		

		List<Attribution> as = attribution.getAttributions();
		if(as != null)
		{
			String type = attribution.getType();
			boolean has = false;
			for(Attribution aa : attributions)
			{
				if(type.equals(aa.getType()))
				{
					has = true;
					break;
				}
			}
			
			if(!has)
				attributions.add(attribution);
			
			for (Attribution attr : as)
				toList(attr, attributions);
		}
	}
	
	
	public static void createInterfaceDoc(Class<?> clazz, Writer writer) throws IOException, TemplateException{		
		
		Attribution attribution = DefaultAttribution.createAttribution(clazz);
		
		List<Attribution> attributions = new ArrayList<>();		
		
		InterfaceDocumentUtil.toList(attribution, attributions);
	
		Map<String,Object> data=new HashMap<>();
		data.put("objects", attributions);
		
		Template template = cfg.getTemplate("interface.xml");
		template.process(data, writer);
		
	}
	
	
}
