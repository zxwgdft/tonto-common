package com.tonto.common.im.request;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.tonto.common.im.IMConstants;
import com.tonto.common.im.IMServletContainer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 获取IM token
 * 
 * @author TontoZhou
 * 
 */
public class TokenRequest extends AbstrctIMRequest {

	private final static String grantType = "client_credentials";

	private String clientId;
	private String clientSecret;

	public TokenRequest(String clientId, String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
	}

	@Override
	public HttpUriRequest createHttpRequest() {
		
		HttpPost post = new HttpPost(IMServletContainer.getServlet().createIMServerUri("token"));

		post.addHeader("Content-Type", "application/json");

		ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
		objectNode.put("grant_type", grantType);
		objectNode.put("client_id", clientId);
		objectNode.put("client_secret", clientSecret);

		post.setEntity(new StringEntity(objectNode.toString(), IMConstants.TEXT_PLAIN));

		return post;
	}

	@Override
	public String getRequestDescription() {
		return "获取环信token";
	}
}
