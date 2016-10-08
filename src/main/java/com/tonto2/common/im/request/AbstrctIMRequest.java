
package com.tonto2.common.im.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * IM请求基础类
 * @author TontoZhou
 *
 */
public abstract class AbstrctIMRequest implements IMRequest{
	
	//错误信息对应表
	private static Map<Integer,String> errorMap;
	
	static{
		errorMap=new HashMap<>();
		errorMap.put(400, "请求错误");
		errorMap.put(401, "身份验证未通过");
		errorMap.put(403, "拒绝请求");
		errorMap.put(404, "找不到请求的接口");
		errorMap.put(408, "请求超时");
		errorMap.put(413, "请求体过大");
		errorMap.put(500, "服务器内部错误");
		errorMap.put(501, "请求功能尚未实施");
		errorMap.put(502, "错误网关");
		errorMap.put(503, "请求接口被限流");
		errorMap.put(504, "网关超时");		
	}
	
	
	protected HttpUriRequest httpRequest;
	protected HttpClientContext httpContext;
	
	
	public abstract HttpUriRequest createHttpRequest();
	
	//是否需要创建HttpRequest
	private boolean needCreateHttpRequest=true;
	
	//是否需要创建HttpContext
	private boolean needCreateHttpContext=true;
	
	@Override
	public HttpUriRequest getHttpRequest() {		
		if(needCreateHttpRequest)
		{
			httpRequest=createHttpRequest();
			needCreateHttpRequest=false;
		}
			
		return httpRequest;
	}

	@Override
	public HttpClientContext getHttpContext() {
		if(needCreateHttpContext)
		{
			httpContext=HttpClientContext.create();
			needCreateHttpContext=false;
		}
		return httpContext;
	}
	
	@Override
	public String getResponseError(int httpStatus) {
		String error= errorMap.get(httpStatus);
		return error==null?"请求失败,http status "+httpStatus:error;
	}
	
	
	public boolean isNeedCreateHttpRequest() {
		return needCreateHttpRequest;
	}

	public void setNeedCreateHttpRequest(boolean needCreateHttpRequest) {
		this.needCreateHttpRequest = needCreateHttpRequest;
	}

	public boolean isNeedCreateHttpContext() {
		return needCreateHttpContext;
	}

	public void setNeedCreateHttpContext(boolean needCreateHttpContext) {
		this.needCreateHttpContext = needCreateHttpContext;
	}

	

}
