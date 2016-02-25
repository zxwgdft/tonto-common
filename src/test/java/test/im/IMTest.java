package test.im;

import org.junit.Test;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.user.SingleRegisterRequest;


public class IMTest {
	
	@Test
	public void sendRegisteRequest() throws IMException
	{
		String username="lucygirl";
		String nickname="mary";
		IMManager.sendRequest(new SingleRegisterRequest(username,nickname));
	}
}
