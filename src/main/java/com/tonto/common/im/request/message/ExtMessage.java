package com.tonto.common.im.request.message;

import java.util.Map;

import com.tonto.common.im.request.message.model.MessageBody;

/**
 * 拥有扩展字段的Message，ext不能为空
 * @author TontoZhou
 *
 */
public class ExtMessage extends Message{
	
	public ExtMessage(Map<String,Object> ext)
	{
		if(ext==null)
			throw new NullPointerException();
		this.ext=ext;
	}
	
	public ExtMessage(String target_type,String[] target,MessageBody msg,String from,Map<String,Object> ext)
	{
		super(target_type,target,msg,from);
		if(ext==null)
			throw new NullPointerException();
		this.ext=ext;
	}
	
	private Map<String,Object> ext;

	public Map<String, Object> getExt() {
		return ext;
	}

}
