package com.tonto2.common.utils.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientManager {
	//private static final Logger logger=Logger.getLogger(HttpClientManager.class);
	
	private static final CloseableHttpClient httpClient;
	
	static{
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(300);
		// Increase default max connection per route to 30
		cm.setDefaultMaxPerRoute(30);
				
		httpClient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .build();
	}	
	
	/**
	 * HTTP请求
	 * @param request
	 * @param handler
	 */
	public static void httpRequest(HttpUriRequest request,ResponseHandler handler)
	{
		httpRequest(request,handler,HttpClientContext.create());
	}
	
	/**
	 * HTTP请求
	 * @param request
	 * @param handler
	 * @param httpContext
	 */
	public static void httpRequest(HttpUriRequest request,ResponseHandler handler,HttpClientContext httpContext)
	{
		try {			
			CloseableHttpResponse response = httpClient.execute(
					request, httpContext);
			try{
				HttpEntity entity = response.getEntity();
				handler.handle(entity,httpContext);			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				response.close();
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 发出请求，该请求需要自己关闭
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static CloseableHttpResponse sendHttpRequest(HttpUriRequest request) throws IOException
	{
		return sendHttpRequest(
				request, HttpClientContext.create());
	}
	
	/**
	 * 发出请求，该请求需要自己关闭
	 * @param request
	 * @param handler
	 * @param httpContext
	 * @return
	 * @throws IOException
	 */
	public static CloseableHttpResponse sendHttpRequest(HttpUriRequest request,HttpClientContext httpContext) throws IOException
	{
		return httpClient.execute(
				request, httpContext);
	}
	
	
	
	/**
	 * 异步请求
	 * @param request
	 * @param handler
	 */
	public static void asynchronousHttpRequest(final HttpUriRequest request,final ResponseHandler handler)
	{
		asynchronousHttpRequest(request,handler,HttpClientContext.create());
	}
	
	/**
	 * 异步请求
	 * @param request
	 * @param handler
	 * @param httpContext
	 */
	public static void asynchronousHttpRequest(final HttpUriRequest request,final ResponseHandler handler,final HttpClientContext httpContext)
	{		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					CloseableHttpResponse response = httpClient.execute(
							request, httpContext);
					try{
						HttpEntity entity = response.getEntity();
						handler.handle(entity,httpContext);			
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						response.close();
					}
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}		
		}).start();		
	}
	
	public static CloseableHttpClient getHttpClient()
	{
		return httpClient;
	}
}
