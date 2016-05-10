package com.tonto.common.pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;


public class PdfCreator {
	
	public static void main(String[] args) throws DocumentException, IOException
	{
		StringBuffer html = new StringBuffer();
        html.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
        html.append("<head>");
        html.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
        html.append("<style type=\"text/css\" mce_bogus=\"1\">body {font-family: SimSun;text-align：center;} .avanta {width: 180px;height: 180px;} @page{size:210mm 297mm;} </style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<p align=\"center\">&nbsp;</p>");
        html.append("<center>");
        html.append("@HTML_PDF@");
        html.append("</center>");
        html.append("</body></html>");
                   		
        FileOutputStream out=new FileOutputStream("d:/aa.pdf");
        
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("d:/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(html.toString());
        renderer.layout();
        renderer.createPDF(out);
        
	}
	
	
	private static int lineLength=40;
	public static String linefeed(String content)
	{
		if(content==null)
			return "";
		
		int length=content.length();
		if(length<lineLength)
			return content;
		
		int i=0;
		StringBuilder sb=new StringBuilder();
		for(;i<length;)
		{
			int j=i+lineLength;
			j=j>length?length:j;
			sb.append(content.substring(i, j)).append("\n");
			if(j>=length)
				break;
			i=j;
		}
		
		return sb.toString();
		
	}

	 
	 

}
