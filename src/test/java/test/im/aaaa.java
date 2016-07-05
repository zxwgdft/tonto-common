package test.im;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.tonto.common.im.IMManager;
import com.tonto.common.util.http.HttpClientManager;
import com.tonto.common.util.http.ResponseHandler;

public class aaaa {
	
	@Test
	public void aa() throws FileNotFoundException, InterruptedException
	{
		String url="http://10.66.28.74:8080/wordToPdf/servlet/WordToPdfServlet?fileName=4EFE57D52F5111E5BE00FA163E061083.docx" +
				"&sfileFolder=C:/share/jinbao&tfileFolder=C:/share/jinbao";
		
		HttpPost post = new HttpPost(url);
		
		
		post.setEntity(new InputStreamEntity(new FileInputStream("d:/a.docx")));
		
		
		HttpClientManager.asynchronousHttpRequest(post, new ResponseHandler(){

			@Override
			public void handle(HttpEntity entity, HttpContext context) {
				
				try {
					System.out.println(EntityUtils.toString(entity, "utf-8"));
					
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		Thread.sleep(10000000);
	}
}
