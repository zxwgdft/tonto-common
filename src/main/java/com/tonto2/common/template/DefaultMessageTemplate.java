package com.tonto2.common.template;

/**
 * 默认消息模板，消息和模板为相同时候使用，即不需要动态生成的消息
 * @author TontoZhou
 *
 */
public class DefaultMessageTemplate implements MessageTemplate{

	private String template;
	
	public DefaultMessageTemplate(String template)
	{
		this.template=template;
	}
	
	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public String createMessage(Object... args) {
		return template;
	}

}
