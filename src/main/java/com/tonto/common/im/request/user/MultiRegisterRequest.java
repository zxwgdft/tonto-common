package com.tonto.common.im.request.user;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.tonto.common.im.IMConstants;
import com.tonto.common.im.IMServletContainer;
import com.tonto.common.im.IMTokenHelper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tonto.common.im.request.AbstrctIMRequest;
import com.tonto.common.im.request.user.model.IMUser;

/**
 * IM用户注册（批量）
 * @author TontoZhou
 *
 */
public class MultiRegisterRequest extends AbstrctIMRequest{
	
	private IMUser[] users;
	
	public MultiRegisterRequest(IMUser[] users)
	{
		this.users=users;
	}	

	@Override
	public HttpUriRequest createHttpRequest() {
		
		HttpPost post=new HttpPost(IMServletContainer.getServlet().createIMServerUri("users"));
		
		post.addHeader("Content-Type", "application/json");
		post.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());
		
		ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
		
		if(users!=null)
		{
			for(IMUser user:users)
			{
				ObjectNode objectNode=JsonNodeFactory.instance.objectNode();
				objectNode.put("username", user.getUsername());
				objectNode.put("password", IMServletContainer.getServlet().getPassword());
				objectNode.put("nickname", user.getNickname());
				arrayNode.add(objectNode);
			}
		}
		
		post.setEntity(new StringEntity(arrayNode.toString(),IMConstants.TEXT_PLAIN));
		
		return post;		
	}
		
	@Override
	public String getRequestDescription() {
		StringBuilder sb=new StringBuilder();
		
		if(users!=null)
		{
			for(IMUser user:users)
			{
				sb.append("注册IM用户：[").append(user.getUsername())
					.append("]昵称：[").append(user.getNickname()).append("]\n");
			}
		}
		
		return sb.toString();
	}


	public IMUser[] getUsers() {
		return users;
	}

	public void setUsers(IMUser[] users) {
		this.users = users;
	}
}
