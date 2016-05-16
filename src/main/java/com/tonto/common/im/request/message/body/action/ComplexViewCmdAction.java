package com.tonto.common.im.request.message.body.action;

/**
 * 复杂视图消息动作
 * @author TontoZhou
 *
 */
public class ComplexViewCmdAction extends ViewCmdAction{

	public final static String TYPE="complex";
	
	@Override
	public String getActionType() {
		return TYPE;
	}

}
