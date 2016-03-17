package com.tonto.common.word;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import freemarker.template.Template;

public class WordFreeMakerTemplate implements WordTemplate{

	private Template template;
	private Object data;
	
	public WordFreeMakerTemplate(Template template,Object data)
	{
		this.template=template;
		this.data=data;
	}
	
	@Override
	public void write(OutputStream out) {
		
		try
		{
			Writer write=new OutputStreamWriter(out, "utf-8");
			
			if(data==null)
			{
				template.process(null, write);
			}		
			else if(data instanceof Collection)
			{
				Collection<?> coll=(Collection<?>) data;
				for(Object obj:coll)
				{
					template.process(obj, write);
				}
			}
			else
			{
				template.process(data, write);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

}
