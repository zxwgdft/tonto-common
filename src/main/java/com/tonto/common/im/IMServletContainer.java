package com.tonto.common.im;


/**
 * IM 服务器容器
 * @author TontoZhou
 *
 */
public class IMServletContainer {
	
	private static IMServletFactory servletFactory;	
	
	public static IMServlet getServlet()
	{
		return servletFactory.getIMServlet();
	}

	public static IMServletFactory getServletFactory() {
		return servletFactory;
	}

	public static void setServletFactory(IMServletFactory servletFactory) {
		IMServletContainer.servletFactory = servletFactory;
	}
	
	
}
