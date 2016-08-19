package com.tonto.redis;

import java.util.List;

public class Test {
	
	
	
	public static void main(String[] args){
		
		ShardedJedisContainer source = new ShardedJedisContainer(new String[]{"127.0.0.1:6379"});
		
		
		//source.set("tonto", "zhouxuwu");
		
		
		List<String> message = source.getJedis().blpop(0,"tonto_list");
		
		System.out.println(message);
		
		
		
		
	
	}
}
