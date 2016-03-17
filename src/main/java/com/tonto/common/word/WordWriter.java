package com.tonto.common.word;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WordWriter implements WordTemplate{
	
	private List<WordTemplate> templates;	

	public WordWriter()
	{
		
	}
	
	public void add(WordTemplate template)
	{
		if(templates==null)
			templates=new ArrayList<>();
		templates.add(template);
	}
	
	@Override
	public void write(OutputStream out) {
		for(WordTemplate template:templates)
		{
			template.write(out);
		}
	}
	
}
