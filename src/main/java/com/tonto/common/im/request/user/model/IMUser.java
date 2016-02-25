package com.tonto.common.im.request.user.model;

public class IMUser {
	
	private String username;
	private String nickname;
	
	public IMUser(String username,String nickname)
	{
		this.username=username;
		this.nickname=nickname;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
