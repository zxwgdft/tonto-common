package test.im;


import org.junit.Test;

import com.tonto.common.im.IMServletContainer;
import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.user.AddFriendRequest;
import com.tonto.common.im.request.user.SingleRegisterRequest;


public class IMTest {
	
	@Test
	public void sendRegisteRequest() throws IMException
	{
		String username="TontoZhou1";
		String nickname="zhouxuwu22s";
		IMServletContainer.getServlet().sendRequest(new SingleRegisterRequest(username,nickname));

	}
	
	
	public void sendAddFriendRequest() throws IMException
	{
		String aUserName="ABC";
		String aNickName="ABC";
		
		IMServletContainer.getServlet().sendRequest(new SingleRegisterRequest(aUserName,aNickName));
		
		String bUserName="XYZ";
		String bNickName="XYZ";
		
		IMServletContainer.getServlet().sendRequest(new SingleRegisterRequest(bUserName,bNickName));
		
	
		IMServletContainer.getServlet().sendRequest(new AddFriendRequest(aUserName,bUserName));

	}
}
