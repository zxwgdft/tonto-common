package com.tonto2.common.im.request.message.body;

/**
 * 文本消息主体
 * @author TontoZhou
 *
 */
public class TextMessageBody implements MessageBody{
			
	private String msg;
	
	public TextMessageBody(String msg){
		this.msg=msg;
	} 

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getType() {
		return MESSAGE_TYPE_TEXT;
	}
	
	/**文本消息*/
	public final static String MESSAGE_TYPE_TEXT="txt";
	
	public String toString()
	{
		return "文本："+msg;
	}
}
