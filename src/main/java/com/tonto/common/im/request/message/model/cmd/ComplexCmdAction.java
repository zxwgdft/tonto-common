package com.tonto.common.im.request.message.model.cmd;

/**
 * 复杂消息透传动作
 * @author TontoZhou
 *
 */
public class ComplexCmdAction extends CmdAction{

	public final static String TYPE="complex";
	
	@Override
	public String getActionType() {
		return TYPE;
	}

}
