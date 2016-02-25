package com.tonto.common.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.protocol.HttpContext;

/**
 * http请求响应后处理接口
 * @author	xwzhou
 * @date	2015-5-8
 */
public interface ResponseHandler {
	
	public void handle(HttpEntity entity,HttpContext context);
	
}
