package com.tonto.common.im.request.user;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.IMTokenHelper;
import com.tonto.common.im.request.AbstrctIMRequest;

/**
 * IM用户删除（单个）
 * @author TontoZhou
 *
 */
public class GetUserRequest extends AbstrctIMRequest {
	
	private String username;
	
	public GetUserRequest(String username)
	{
		this.username=username;
	}
	
	@Override
	public HttpUriRequest createHttpRequest() {
		HttpGet get=new HttpGet(IMManager.createIMServerUri("users/"+username));
		
		get.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());				
		return get;
	}
	
	@Override
	public String getRequestDescription() {
		return new StringBuilder("获取IM用户：[").append(username)
				.append("]").toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
