package com.tonto.common.im;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.IMRequest;
import com.tonto.common.im.request.RetransmissionRequest;
import com.tonto.common.util.http.HttpClientManager;

/**
 * IM服务
 * @author TontoZhou
 *
 */
public class IMServlet {
	
	private final static Logger logger = Logger.getLogger(IMServlet.class);
		
	//应用
	private String appKey;
	//客户端ID
	private String clientId;
	//客户端密码
	private String clientSecret;
	//授权注册密码
	private String password;	
	//是否启用运行
	private boolean isRun=true;	
	//访问IM的基础路径
	private String baseUri = "";
	
	//最大重传次数
	private int maxRetransmissionTime=2;
	
	// ------------------------------------------------------
	//
	// initiate
	//
	// ------------------------------------------------------
	
	/**
	 * 初始化
	 * @param appkey
	 * @param clientId
	 * @param clientSecret
	 */
	public IMServlet(String appkey,String clientId,String clientSecret,String password){
		
		this.appKey=appkey;
		this.clientId=clientId;
		this.clientSecret=clientSecret;
		this.password=password;
		
		createBaseUri();
		
		logger.info("初始化IM应用:\nappKey:"+appKey+"\nclientId:"+clientId+"\nclientSecret:"+clientSecret+"\nIM的基础路径:"+baseUri);
	}
	
	protected void createBaseUri(){				
		//初始化访问IM的基础路径
		baseUri = "http://a1.easemob.com/" + appKey.replace("#", "/") + "/";			
	}
	
	
	// ------------------------------------------------------
	//
	// Send HttpRequest
	//
	// ------------------------------------------------------
	
	
	/**
	 * 拼装成IM的完整URI
	 * 
	 * @param path
	 *            请求的资源定义部分
	 * @return
	 */
	public URI createIMServerUri(String path) {

		try {
			return new URI(baseUri + path);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送IM请求
	 * 
	 * @param request
	 * @return
	 * @throws IMException 
	 */
	public CloseableHttpResponse sendRequest(IMRequest request) throws IMException {
		
		if(!isRun)
			return null;
		
		HttpUriRequest httpRequest = request.getHttpRequest();
		HttpClientContext httpContext = request.getHttpContext();

		try (CloseableHttpResponse response = HttpClientManager
				.sendHttpRequest(httpRequest, httpContext);) {

			/*
			 * 一些异常需要特殊处理，例如超时重发，token失效等，日后需要补，并尽量不影响业务层
			 */	
						
			int httpStatus = response.getStatusLine().getStatusCode();
			
			if (httpStatus != 200) {				
				
				if(httpStatus==401)
				{
					IMTokenHelper.resetToken();
				}
				
				if(httpStatus==401||httpStatus==408)
				{
					RetransmissionRequest retransmissionRequest;
					
					if(request instanceof RetransmissionRequest)
						retransmissionRequest=(RetransmissionRequest) request;	
					else
						retransmissionRequest=new RetransmissionRequest(request);						
					 
					if(retransmissionRequest.getRetransmissionTime()<maxRetransmissionTime)
					{
						if(logger.isDebugEnabled())
							logger.debug("重发请求:"+retransmissionRequest.getRequestDescription());
						retransmissionRequest.addRetransmissionTimes();					
						return sendRequest(retransmissionRequest);										
					}
				}								
								
				String description=request.getRequestDescription();
				String error=request.getResponseError(httpStatus);
				String responseBody=EntityUtils.toString(response.getEntity(),Consts.UTF_8);
				
				logger.error("请求失败："+description);
				logger.error(error);
				logger.error("错误返回:"+responseBody);
				
				throw new IMException(error);
			}
			else
			{
				if(logger.isDebugEnabled())
					logger.debug("请求成功："+request.getRequestDescription());
			}
			
			return response;

		} catch (IOException e) {
			
			e.printStackTrace();

			throw new IMException(request.getRequestDescription()
					+ "请求失败");
		}
	}
	
	// ------------------------------------------------------
	//
	// get/set function
	//
	// ------------------------------------------------------
	

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
		this.createBaseUri();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public String getBaseUri() {
		return baseUri;
	}

	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}

	public int getMaxRetransmissionTime() {
		return maxRetransmissionTime;
	}

	public void setMaxRetransmissionTime(int maxRetransmissionTime) {
		this.maxRetransmissionTime = maxRetransmissionTime;
	}
	
	
}
