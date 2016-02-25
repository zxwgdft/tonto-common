package com.tonto.common.im.request.user;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.tonto.common.im.IMManager;
import com.tonto.common.im.IMTokenHelper;
import com.tonto.common.im.request.AbstrctIMRequest;

/**
 * 添加好友关系
 * 
 * @author TontoZhou
 *
 */
public class AddFriendRequest extends AbstrctIMRequest{

	private final static String tpl="users/%s/contacts/users/%s";
	
	private String owner;
	private String friend;
	
	public AddFriendRequest(String owner,String friend)
	{
		this.owner=owner;
		this.friend=friend;		
	}	
	
	@Override
	public HttpUriRequest createHttpRequest() {
		
		HttpPost post=new HttpPost(IMManager.createIMServerUri(String.format(tpl, owner, friend)));
		
		post.addHeader("Authorization", "Bearer " + IMTokenHelper.getToken());
		
		return post;
	}



	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	@Override
	public String getRequestDescription() {
		return "向用户"+owner+"添加好友"+friend;
	}

}
