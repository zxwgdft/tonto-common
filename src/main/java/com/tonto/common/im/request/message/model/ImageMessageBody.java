package com.tonto.common.im.request.message.model;

/**
 * 图片消息主体
 * @author TontoZhou
 *
 */
public class ImageMessageBody implements MessageBody{
	
	//成功上传文件返回的uuid
	private String url;
	
	private String filename;
	// 成功上传文件后返回的secret
	private String secret;
	
	private Size size;
		
	
	@Override
	public String getType() {
		return MESSAGE_TYPE_IMAGE;
	}
	
	/**图片消息*/
	public final static String MESSAGE_TYPE_IMAGE="img";
	
	
	public static class Size{
		
		private int width;
		private int height;
		
		public Size(int width,int height)
		{
			this.width=width;
			this.height=height;
		}
		
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getSecret() {
		return secret;
	}


	public void setSecret(String secret) {
		this.secret = secret;
	}


	public Size getSize() {
		return size;
	}


	public void setSize(Size size) {
		this.size = size;
	}
}
