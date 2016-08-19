package com.tonto.common.im;

/**
 * 默认服务器工厂
 * @author TontoZhou
 *
 */
public class DefaultServletFactory implements IMServletFactory{

	private final static String appKey="tonto-zhou#tonto2im";
	private final static String clientId="YXA6hD2eUMdQEeWl9x_c_p5DzA";
	private final static String clientSecret="YXA6ScaXJef8itgcSwxo3SWS_qZWcSk";
	private final static String password="1988gdft";
	
	private IMServlet imServlet;
	
	public DefaultServletFactory(){
		imServlet=new IMServlet(appKey,clientId,clientSecret,password);	
	}
	
	@Override
	public IMServlet getIMServlet() {		
		return imServlet;
	}

}
