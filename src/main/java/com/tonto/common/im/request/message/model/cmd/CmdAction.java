package com.tonto.common.im.request.message.model.cmd;

/**
 * 命令动作
 * @author TontoZhou
 *
 */
public abstract class CmdAction {
	
	/**来自用户消息*/
	public static final String FROM_TYPE_USER="user";
	/**来自系统消息*/
	public static final String FROM_TYPE_SYSTEM="system";
	/**来自公司消息*/
	public static final String FROM_TYPE_COMPANY="company";
	
	
	//user:用户，system:系统，company:公司
	private String from;
	
	//数据部分
	private Object data;
	
	//命令
	private String commend;
	
	//标题
	private String title;
	//内容
	private String content;	
	/**
	 * 获取动作类型
	 * @return
	 */
	public abstract String getActionType();
	
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getCommend() {
		return commend;
	}

	public void setCommend(String commend) {
		this.commend = commend;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}

}
