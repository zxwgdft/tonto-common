package com.tonto.common.im.request.message.model;

/**
 * 语言消息，未完善
 * @author TontoZhou
 *
 */
public class AudioMessageBody implements MessageBody{

	@Override
	public String getType() {
		return MESSAGE_TYPE_AUDIO;
	}
	
	/**语言消息*/
	public final static String MESSAGE_TYPE_AUDIO="audio";
}
