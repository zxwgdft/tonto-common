package com.tonto.common.word;

import java.io.IOException;
import java.io.OutputStream;

public class WordStringTemplate implements WordTemplate{

	private static final String default_charset="utf-8";
	
	private String template;
	
	private String charset;
	
	private WordStringTemplate(String template,String charset)
	{
		this.template=template;
		this.charset=charset;
	}
	
	private WordStringTemplate(String template)
	{
		this(template,default_charset);
	}	
		
	@Override
	public void write(OutputStream out) {
		try {
			out.write(template.getBytes(charset));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
