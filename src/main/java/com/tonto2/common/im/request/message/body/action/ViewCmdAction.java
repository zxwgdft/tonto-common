package com.tonto2.common.im.request.message.body.action;


/**
 * 视图显示动作
 * @author TontoZhou
 *
 */
public abstract class ViewCmdAction {
	
	//标题
	private String title;
	//内容
	private String content;
	
	/**
	 * 获取动作类型
	 * @return
	 */
	public abstract String getActionType();
	
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
