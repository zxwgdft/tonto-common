package com.tonto2.common.utils.xml;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * XML 工具类
 * @author TontoZhou
 *
 */
public class XmlUtil {
	
	/**
	 * xml 字符串转换为 map
	 * @param content
	 * @return
	 */
	public static Map<String,Object> convert2map(String content)
	{
		return convert2map(new StringReader(content));			
	}
	
	/**
	 * xml 字符串转换为 map
	 * @param content
	 * @return
	 */
	public static Map<String,Object> convert2map(Reader reader)
	{
		SAXReader saxReader=new SAXReader();
		
		Document document;
		
		try {
			
			document = saxReader.read(reader);
			Element root=document.getRootElement();				
			return convertElement(root);
			
		} catch (DocumentException e) {
			e.printStackTrace();			
		}   
		
		return null;			
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String,Object> convertElement(Element element)
	{	
		Map<String,Object> dataMap=new HashMap<>();	
		
		List<Element> childElements=element.elements();			
					
		for(Element childElement:childElements)
		{
			String key = childElement.getName();
					
			Object value = null;
			
			if(childElement.isTextOnly())
				value = childElement.getText();
			else
				value = convertElement(childElement);
			
			Object preVal = dataMap.get(key);
			
			if(preVal != null)
			{
				if(preVal instanceof List)
				{
					value = ((List) preVal).add(value);
					continue;
				}
				else
				{
					List<Object> list = new ArrayList<>();
					list.add(preVal);
					list.add(value);
					
					value = list;
				}
			}
			
			dataMap.put(key, value);
		}
		
		return dataMap;
		
	}
	
	
}
