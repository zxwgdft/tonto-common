package com.tonto.common.im.request.message;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.common.im.IMManager;
import com.tonto.common.im.IMTokenHelper;
import com.tonto.common.im.request.AbstrctIMRequest;

/**
 * 消息请求基类
 * 
 * @author TontoZhou
 * 
 */
public class MessageRequest extends AbstrctIMRequest {

	private static ObjectMapper objectMapper = new ObjectMapper();

	private Message message;

	public MessageRequest(Message message) {
		this.message = message;
	}

	@Override
	public HttpUriRequest createHttpRequest() {
		HttpPost post = new HttpPost(IMManager.createIMServerUri("messages"));

		post.addHeader("Content-Type", "application/json");
		post.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());
		
		try {
			
			//替换成小写
			String from=message.getFrom();	
			if(from!=null)
				message.setFrom(from.toLowerCase());
			
			String[] target=message.getTarget();
			if(target!=null)
			{
				String[] newTarget=new String[target.length];
				for(int i=0;i<target.length;i++)
				{
					String t=target[i];
					if(t!=null)
						newTarget[i]=t.toLowerCase();
				}
				message.setTarget(newTarget);
			}
			
			String bodyJson = objectMapper.writeValueAsString(message);
			
			post.setEntity(new StringEntity(bodyJson, IMManager.TEXT_PLAIN));

			return post;

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}

	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public String getRequestDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("用户[").append(message.getFrom()).append("]向[");
		for (String target : message.getTarget())
			sb.append(target).append(",");
		sb.append("]").append("发送消息").append(message.getMsg());
		return sb.toString();
	}

}
