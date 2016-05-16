package com.tonto.common.im.request.message.body;

/**
 * 视频消息，未完善
 * @author TontoZhou
 *
 */
public class VideoMessageBody implements MessageBody{

	@Override
	public String getType() {
		return MESSAGE_TYPE_VIDEO;
	}
	
	/**视频消息*/
	public final static String MESSAGE_TYPE_VIDEO="video";
	
}
