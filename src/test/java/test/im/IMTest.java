package test.im;


import org.junit.Test;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.user.AddFriendRequest;
import com.tonto.common.im.request.user.SingleRegisterRequest;


public class IMTest {
	
	@Test
	public void sendRegisteRequest() throws IMException
	{
		String username="TONTOZHOU1";
		String nickname="zhouxuwu22s";
		IMManager.sendRequest(new SingleRegisterRequest(username,nickname));

	}
	
	
	public void sendAddFriendRequest() throws IMException
	{
		String aUserName="ABC";
		String aNickName="ABC";
		
		IMManager.sendRequest(new SingleRegisterRequest(aUserName,aNickName));
		
		String bUserName="XYZ";
		String bNickName="XYZ";
		
		IMManager.sendRequest(new SingleRegisterRequest(bUserName,bNickName));
		
	
		IMManager.sendRequest(new AddFriendRequest(aUserName,bUserName));

	}
}
