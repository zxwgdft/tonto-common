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
      
        
        String h=getPdfHtml();
        
        String r = html.toString().replace("@HTML_PDF@", h);
		
        FileOutputStream out=new FileOutputStream("d:/aa.pdf");
        
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("d:/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(r);
        renderer.layout();
        renderer.createPDF(out);
        
	}
	
	
	private static int lineLength=18;
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
	
	 public static String getPdfHtml() {
	        StringBuffer html = new StringBuffer();
	        html.append("<table width=\"650\" border=\"0\" align=\"center\">");
	        html.append("<tr><td height=\"100\" align=\"center\"  nowrap=\"nowrap\" style=\"font-family:'SimSun';font-size:22px; color:balck\"><span style=\"font-weight:bold\">" + "订单详情" + "</span></td></tr>");
	        // -----------------------------产品信息--------------------------------------------------------------------------------------
	        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">" + "产品信息" + "</span></td></tr>");
	        html.append("<tr><td><table width=\"400\" border=\"1\" borderCollapse = \"collapse\" align=\"center\"  cellpadding=\"3\" cellspacing=\"0\" >");
	        // ------------------------------------------------------------------------------------------------------------------------
	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "产品名称：" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\" colspan=\"3\" align=\"center\">永恒一号</td>");
	        html.append("</tr>");

	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">认购金额：</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">1000.33元</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "交易类型" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "认购" + "</td>");
	        html.append("</tr>");

	        html.append("</table></td></tr>");

	        // -----------------------------投资人基本信息--------------------------------------------------------------------------------------
	        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">" + "投资人基本信息" + "</span></td></tr>");
	        html.append("<tr><td><table width=\"400\" border=\"1\" borderCollapse = \"collapse\" align=\"center\"  cellpadding=\"3\" cellspacing=\"0\" >");
	        // ------------------------------------------------------------------------------------------------------------------------
	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "姓名：" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">周旭武</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">性别</td>");	    
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">男</td>");
	        html.append("</tr>");

	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "国籍:" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "哥本哈根" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "手机:" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "13593929333" + "</td>");
	        html.append("</tr>");

	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "证件类型:" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "身份证" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "证件号码:" + "</td>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "320583198806119617" + "</td>");
	        html.append("</tr>");

	        html.append("</table></td></tr>");

	        // -----------------------------投资人银行卡信息--------------------------------------------------------------------------------------
	        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">" + "投资人银行卡信息" + "</span></td></tr>");
	        // ------------------------------------------------------------------------------------------------------------------------
            html.append("<tr><td align=\"left\"><span style=\"font-weight:normal\">" + "付款银行卡信息:" + "</span></td></tr>");
            html.append("<tr><td><table width=\"400\" border=\"1\" borderCollapse = \"collapse\" align=\"center\"  cellpadding=\"3\" cellspacing=\"0\" >");

            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "银行名称:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "中国银行" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "开卡地区:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "美国" + "</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "借记卡卡号:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "zhouXUUE" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "持卡人姓名:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "zhouxuwu" + "</td>");
            html.append("</tr>");
            
            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "开户支行:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\" colspan=\"3\"  align=\"center\">" + linefeed("的fads发射点法我是大法师范文芳噶为人十分巅峰的fads发射点法我是大法师范文芳噶为人十分巅峰的fads发射点法我是大法师范文芳噶为人十分巅峰") + "</td>");
            html.append("</tr>");

            html.append("</table></td></tr>");
	        
	        // ------------------------------------------------------------------------------------------------------------------------
            html.append("<tr><td align=\"left\"><span style=\"font-weight:normal\">" + "回款银行卡信息:" + "</span></td></tr>");
            html.append("<tr><td><table width=\"400\" border=\"1\" borderCollapse = \"collapse\" align=\"center\"  cellpadding=\"3\" cellspacing=\"0\" >");

            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "银行名称:" + "</td>");         
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "中国银行" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "开卡地区:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "美国" + "</td>");
            html.append("</tr>");

            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "借记卡卡号:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "123456789" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "持卡人姓名:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "Zhouxuwu" + "</td>");
            html.append("</tr>");
            
            html.append("<tr>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "开户支行:" + "</td>");
            html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\" colspan=\"3\"  align=\"center\">" + "的fads发射点法我是大法师范文芳噶为人十\n分师范文芳噶为人十分巅巅峰时f代" + "</td>");
            html.append("</tr>");
            
            html.append("</table></td></tr>");

	        // ----------------------------支付信息--------------------------------------------------------------------------------------
	        html.append("<tr><td align=\"left\"><span style=\"font-weight:bold\">" + "支付信息" + "</span></td></tr>");
	        html.append("<tr><td><table width=\"400\" border=\"1\" borderCollapse = \"collapse\" align=\"center\"  cellpadding=\"3\" cellspacing=\"0\" >");
	        // ------------------------------------------------------------------------------------------------------------------------
	      

	        html.append("<tr>");
	        html.append("<td style=\"font:'SimSun;font-size:10px\" nowrap=\"nowrap\"  align=\"center\">" + "通联支付" + "</td>");
	        html.append("</tr>");

	        html.append("</table></td></tr>");

	        html.append("</table>");
	        return html.toString();
	    }
}
