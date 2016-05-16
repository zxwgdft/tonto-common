package com.tonto.common.im;

import java.net.URI;

import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;

import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.IMRequest;

/**
 * IM管理类
 * 
 * @author TontoZhou
 * 
 */
public class IMManager {

	private final static String appKey="tonto-zhou#tonto2im";
	private final static String clientId="YXA6hD2eUMdQEeWl9x_c_p5DzA";
	private final static String clientSecret="YXA6ScaXJef8itgcSwxo3SWS_qZWcSk";
	private final static String password="1988gdft";
	
	private static IMServlet imServlet;
	
	static{
		imServlet=new IMServlet(appKey,clientId,clientSecret,password);	
	}
	
	/**
	 * 创建IM URI
	 * @param path
	 * @return
	 */
	public static URI createIMServerUri(String path) {
		return imServlet.createIMServerUri(path);
	}
	
	/**
	 * 发送请求
	 * @param request
	 * @return
	 * @throws IMException
	 */
	public static CloseableHttpResponse sendRequest(IMRequest request) throws IMException {
		return imServlet.sendRequest(request);
	}
	
	public static IMServlet getIMServlet() {
		return imServlet;
	}
	
	public static void setIMServlet(IMServlet servlet) {
		imServlet = servlet;
	}

	public static final ContentType TEXT_PLAIN = ContentType.create(
			"text/plain", Consts.UTF_8);	
}
