package test.im;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.user.SingleRegisterRequest;


public class IMTest {
	
	@Test
	public void sendRegisteRequest() throws IMException
	{
		//String username="lucygirl";
		//String nickname="mary";
		//IMManager.sendRequest(new SingleRegisterRequest(username,nickname));
		
		try {
			FileInputStream input=new FileInputStream("d:/4444.mp4");
			System.out.println(input.available());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
