package com.tonto.common.word;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class WordImage implements WordTemplate{
	
	private InputStream input;
		
	public WordImage(InputStream input)
	{
		this.input=input;
	}

	@Override
	public void write(OutputStream out) {
		
		ByteArrayOutputStream byteOut=null;
		
		try {
			byteOut = new ByteArrayOutputStream(2048);
	        byte[] cache = new byte[2048];
	        int nRead = 0;
	        while ((nRead = input.read(cache)) != -1) {
	        	byteOut.write(cache, 0, nRead);
	        	byteOut.flush();
	        }
	        	       
			out.write(Base64.encodeBase64(byteOut.toByteArray()));
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			
			try {
				if(byteOut!=null)
					byteOut.close();
				if(input!=null)
					input.close();
			} catch (IOException e) {
				
			}
		  
		}
        
	}
	
	
	
}
