package test.util;

import java.util.Map;

import org.junit.Test;

import com.tonto.common.util.XmlUtil;

public class XMLTest {
	
	private String xml = "<xml><a><b>1.b.1</b><b>1.b.2</b><c>1.c</c></a>" +
			"<a><b>2.b</b><c>2.c</c></a>" +
			"<a>3</a></xml>";
	
	@Test
	public void convertXmlTest(){
		
		Map m=XmlUtil.convert2map(xml);
		
		System.out.println(m);
		
	}
}
