package com.tonto2.common.im.request;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * IM请求接口
 * @author TontoZhou
 *
 */
public interface IMRequest {
	
	
	/**
	 * 获取当前HTTP请求
	 * @return
	 */
	public HttpUriRequest getHttpRequest();
	
		
	/**
	 * 获取HTTP上下文
	 * @return
	 */
	public HttpClientContext getHttpContext();
	
	/**
	 * 获取回复异常
	 * @param httpStatus
	 * @return
	 */
	public String getResponseError(int httpStatus);
	
	/**
	 * 获取请求描述
	 * @return
	 */
	public String getRequestDescription();
}
