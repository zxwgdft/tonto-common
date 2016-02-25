package com.tonto.common.im.request.message.model.cmd;

import com.tonto.common.im.request.message.model.MessageBody;

/**
 * 消息透传
 * @author TontoZhou
 *
 */
public class CmdMessageBody implements MessageBody{

	private CmdAction action;
	
	public CmdMessageBody(CmdAction action)
	{
		this.action=action;
	}
	
	@Override
	public String getType() {
		return MESSAGE_TYPE_CMD;
	}
	
	/**消息透传*/
	public final static String MESSAGE_TYPE_CMD="cmd";

	
	public CmdAction getAction() {
		return action;
	}

	public void setAction(CmdAction action) {
		this.action = action;
	}
}
