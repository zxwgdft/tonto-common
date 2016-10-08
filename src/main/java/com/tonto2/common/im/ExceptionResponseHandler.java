package com.tonto2.common.im;

import org.apache.http.client.methods.CloseableHttpResponse;

import com.tonto2.common.im.request.IMRequest;

public interface ExceptionResponseHandler {

	public void handle(CloseableHttpResponse response,IMRequest request);
	
}
