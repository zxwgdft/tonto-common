package com.tonto.common.im.request.message.model.cmd;

/**
 * 简单透传消息动作
 * @author TontoZhou
 *
 */
public class SimpleCmdAction extends CmdAction{
	
	public final static String TYPE="simple";
	
	
	
	@Override
	public String getActionType() {
		return TYPE;
	}

}
