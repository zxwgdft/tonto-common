package com.tonto.common.im.request.message.model.cmd;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tonto.common.im.request.message.model.MessageBody;

/**
 * 通过环信文本消息传递我们的命令动作
 * @author TontoZhou
 *
 */
public class CmdTextMessageBody implements MessageBody{
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	@JsonIgnore
	private CmdAction cmdAction;
	
	private String msg;
	
	public CmdTextMessageBody(CmdAction action)
	{
		setCmdAction(action);
	}
	
	@Override
	public String getType() {
		return MESSAGE_TYPE_TEXT;
	}
	
	/**文本消息*/
	public final static String MESSAGE_TYPE_TEXT="txt";

	
	public String getMsg() {		
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	public CmdAction getCmdAction() {
		return cmdAction;
	}

	public void setCmdAction(CmdAction cmdAction) {
		this.cmdAction = cmdAction;
		msg=null;
		if(cmdAction!=null)
			try {
				msg=objectMapper.writeValueAsString(cmdAction);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}		
	}

}
