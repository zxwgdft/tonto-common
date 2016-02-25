package com.tonto.common.im.request.user;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.IMTokenHelper;
import com.tonto.common.im.request.AbstrctIMRequest;
import com.tonto.common.im.request.user.model.IMUser;

/**
 * IM用户删除（单个）
 * @author TontoZhou
 *
 */
public class SingleRemoveRequest extends AbstrctIMRequest {
	
	private String username;
	
	public SingleRemoveRequest(String username)
	{
		this.username=username;
	}
	
	public SingleRemoveRequest(IMUser user)
	{
		this(user.getUsername());
	}

	@Override
	public HttpUriRequest createHttpRequest() {
		HttpDelete delete=new HttpDelete(IMManager.createIMServerUri("users/"+username));
		
		delete.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());
				
		return delete;
	}
	
	@Override
	public String getRequestDescription() {
		return new StringBuilder("删除IM用户：[").append(username)
				.append("]").toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
