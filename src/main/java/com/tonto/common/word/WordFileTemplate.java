package com.tonto.common.word;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WordFileTemplate implements WordTemplate{
	
	private InputStream input;
	
	public WordFileTemplate(InputStream input)
	{
		this.input=input;
	}
	
	@Override
	public void write(OutputStream out) {
	
		try {
			byte[] cache = new byte[2048];
	        int nRead = 0;
	        while ((nRead = input.read(cache)) != -1) {
	        	out.write(cache, 0, nRead);
	        }
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
	}

}
