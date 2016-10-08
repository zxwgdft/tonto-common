package com.tonto2.common.im.request.user;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tonto2.common.im.IMConstants;
import com.tonto2.common.im.IMServletContainer;
import com.tonto2.common.im.IMTokenHelper;
import com.tonto2.common.im.request.AbstrctIMRequest;
import com.tonto2.common.im.request.user.model.IMUser;

/**
 * IM用户注册（单个）
 * @author TontoZhou
 *
 */
public class SingleRegisterRequest extends AbstrctIMRequest {
	
	private IMUser user;
	
	public SingleRegisterRequest(String username,String nickname)
	{
		this(new IMUser(username,nickname));
	}
	
	public SingleRegisterRequest(String username)
	{
		this(new IMUser(username,username));
	}
	
	public SingleRegisterRequest(IMUser user)
	{
		this.user=user;
	}

	@Override
	public HttpUriRequest createHttpRequest() {
		HttpPost post=new HttpPost(IMServletContainer.getServlet().createIMServerUri("users"));
		
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());
		
		ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
		objectNode.put("username", user.getUsername());
		objectNode.put("password", IMServletContainer.getServlet().getPassword());
		objectNode.put("nickname", user.getNickname());
		
		post.setEntity(new StringEntity(objectNode.toString(),IMConstants.TEXT_PLAIN));
		
		return post;
	}
	
	@Override
	public String getRequestDescription() {
		return new StringBuilder("注册IM用户：[").append(user.getUsername())
				.append("]昵称：[").append(user.getNickname()).append("]").toString();
	}


	public IMUser getUser() {
		return user;
	}

	public void setUser(IMUser user) {
		this.user = user;
	}

}
