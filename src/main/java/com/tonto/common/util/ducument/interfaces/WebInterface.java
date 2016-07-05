package com.tonto.common.util.ducument.interfaces;

import java.util.List;

public class WebInterface {
	
	//请求URL
	private String url;
	
	//请求方法
	private String method;
	
	//请求类型(GET/POST...)
	private String type;
	
	//请求属性
	private List<Attribution> requestAttributions;
	
	//响应属性
	private List<Attribution> responseAttributions;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Attribution> getRequestAttributions() {
		return requestAttributions;
	}

	public void setRequestAttributions(List<Attribution> requestAttributions) {
		this.requestAttributions = requestAttributions;
	}

	public List<Attribution> getResponseAttributions() {
		return responseAttributions;
	}

	public void setResponseAttributions(List<Attribution> responseAttributions) {
		this.responseAttributions = responseAttributions;
	}
	
}
