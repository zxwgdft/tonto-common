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
        html.append("<body style='margin:0;padding:0'>");
        html.append("<p align=\"center\">&nbsp;</p>");
        html.append("<table width=\"750\" border=\"0\" align=\"center\" style=\"table-layout:fixed;word-break:break-strict;\">");
        html.append("<tr><td height=\"100\" align=\"center\"  nowrap=\"nowrap\" style=\"font-family:'SimSun';font-size:22px; color:balck\"><span style=\"font-weight:bold\">" + "客户投资风险承受能力测试问卷" + "</span></td></tr>");
        // 标题提示部分
        html.append("<tr><td align=\"left\"><span>请您填写以下测试问卷，我公司承诺对您的个人资料保密。以下一系列问题可协助评估您的风险承受能力。请您回答所有的问题，并在各题最合适的答案上打勾。我们将根据您的总分鉴定您对投资风险的适应度，并确定适合您投资的产品(每题只能选择一项)。</span></td></tr>");
        html.append("</table>");
        html.append("<table>");
        for(int i=0;i<31;i++)
        	 html.append("<tr><td>测试PDF\n我是学生"+i+"</td></tr>");
        html.append("</table>");
        html.append("<table width=\"750\" border=\"0\" align=\"center\" style=\"table-layout:fixed;word-break:break-strict;\">");
        html.append("<tr><td height=\"100\" align=\"center\"  nowrap=\"nowrap\" style=\"font-family:'SimSun';font-size:22px; color:balck\"><span style=\"font-weight:bold\">" + "客户投资风险承受能力测试问卷结果" + "</span></td></tr>");
        html.append("<tr><td align=\"left\"><span>此次测试得分：</span><span style=\"text-decoration:underline;\">&nbsp;&nbsp;40&nbsp;&nbsp;</span><span>分</span></td></tr>");
        // 分数所属类型
        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">尊敬的投资者，经过我们的测试，您的：</span></td></tr>");
        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">AAAAAAAAAAAAAAAAAAAAASDFADFSDF</span></td></tr>");
        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">AAAAAAAAAAAAAAAAAAAAASDFADFSDF</span></td></tr>");
        html.append("</table>");
        html.append("</body></html>");
                   		
        FileOutputStream out=new FileOutputStream("d:/ab.pdf");
        
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
