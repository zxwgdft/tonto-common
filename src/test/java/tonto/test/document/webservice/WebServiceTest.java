package tonto.test.document.webservice;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.tonto2.common.utils.document.interfaces.ServiceDocument;
import com.tonto2.common.utils.document.interfaces.WebService;

public class WebServiceTest {
	
	@Test
	public void test() throws IOException
	{
		WebService s = new WebService();
		s.setObjectListClass(ElectronicContract.class, true);
		ServiceDocument.createServiceDocument(s, new FileWriter("d:/产品合同模板列表.doc"));
		
	}
}
