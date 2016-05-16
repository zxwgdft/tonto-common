package com.tonto.common.im;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.common.im.exception.IMException;
import com.tonto.common.im.request.TokenRequest;

/**
 * IM token帮助者
 * @author TontoZhou
 *
 */
public class IMTokenHelper {

	private volatile static String accessToken;
	private volatile static long expiresTime = 0;
	@SuppressWarnings("unused")
	private static String application;

	private final static Object lock = new Object();

	/**
	 * 获取token
	 * 
	 * @return
	 */
	public static String getToken() {
		
		if (expiresTime < System.currentTimeMillis() || accessToken == null) {
			synchronized (lock) {
				if (expiresTime < System.currentTimeMillis()
						|| accessToken == null) {
					resetToken();
				}
			}
		}

		return accessToken;
	}

	/**
	 * 重置token
	 */
	public static void resetToken() {
		try {
			CloseableHttpResponse response = IMManager
					.sendRequest(new TokenRequest(IMManager.getIMServlet()
							.getClientId(), IMManager.getIMServlet()
							.getClientSecret()));

			if (response != null) {
				try {
					HttpEntity entity = response.getEntity();
					@SuppressWarnings("unchecked")
					Map<String, Object> result = objectMapper.readValue(
							entity.getContent(), Map.class);

					accessToken = (String) result.get("access_token");
					application = (String) result.get("application");

					Integer expires_in = (Integer) result.get("expires_in");
					// 5000毫秒为缓冲时间
					expiresTime = expires_in * 1000
							+ System.currentTimeMillis() - 5000;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					response.close();
				}
			}

		} catch (IOException | IMException e) {
			e.printStackTrace();
		}
	}

	private final static ObjectMapper objectMapper = new ObjectMapper();

}
