package com.tonto2.common.im.request.message.body.action;

/**
 * 简单视图消息动作
 * @author TontoZhou
 *
 */
public class SimpleViewCmdAction extends ViewCmdAction{
	
	public final static String TYPE="simple";
	
	@Override
	public String getActionType() {
		return TYPE;
	}

}
