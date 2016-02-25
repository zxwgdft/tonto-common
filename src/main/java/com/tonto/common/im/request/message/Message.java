package com.tonto.common.im.request.message;

import com.tonto.common.im.request.message.model.MessageBody;

/**
 * 消息
 * @author TontoZhou
 *
 */
public class Message {
	
	/**用户*/
	public final static String TARGET_TYPE_USER="users";
	/**群组*/
	public final static String TARGET_TYPE_CHATGROUP="chatgroups";
	/**聊天室*/
	public final static String TARGET_TYPE_CHATROOM="chatrooms";
	
	//users 给用户发消息, chatgroups 给群发消息, chatrooms 给聊天室发消息
	private String target_type;
	
	// 注意这里需要用数组,数组长度建议不大于20, 即使只有一个  
    // 此数组元素是用户名,给群组发送时数组元素是groupid
	private String[] target;
	
	//消息主题
	private MessageBody msg;
	
	//消息发送者
	private String from;
	
	public Message()
	{
		
	}
	
	public Message(String target_type,String[] target,MessageBody msg,String from)
	{
		this.target_type=target_type;
		this.target=target;
		this.msg=msg;
		this.from=from;
	}


	public String getTarget_type() {
		return target_type;
	}


	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}


	public String[] getTarget() {
		return target;
	}


	public void setTarget(String[] target) {
		this.target = target;
	}


	public MessageBody getMsg() {
		return msg;
	}


	public void setMsg(MessageBody msg) {
		this.msg = msg;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}
	
	
	
}
