package com.tonto2.common.im.request;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

/**
 * 用于重传的请求
 * @author TontoZhou
 *
 */
public class RetransmissionRequest implements IMRequest{
	
	/**
	 * 重传的具体请求
	 */
	private IMRequest request;
	
	/**
	 * 重传次数
	 */
	private int retransmissionTime=0;
	
	public RetransmissionRequest(IMRequest request)
	{
		this.request=request;
	}
	
	@Override
	public HttpUriRequest getHttpRequest() {
		return request.getHttpRequest();
	}

	@Override
	public HttpClientContext getHttpContext() {
		return request.getHttpContext();
	}

	@Override
	public String getResponseError(int httpStatus) {
		return request.getResponseError(httpStatus);
	}

	@Override
	public String getRequestDescription() {
		return request.getRequestDescription();
	}
	
	/**
	 * 重传次数+1
	 */
	public void addRetransmissionTimes(){
		retransmissionTime++;
	}
	
	/**
	 * 获取重传次数
	 * @return
	 */
	public int getRetransmissionTime() {
		return retransmissionTime;
	}
	
	/**
	 * 色环指重传次数
	 * @param retransmissionTimes
	 */
	public void setRetransmissionTime(int retransmissionTime) {
		this.retransmissionTime = retransmissionTime;
	}

	public IMRequest getRequest() {
		return request;
	}

}
