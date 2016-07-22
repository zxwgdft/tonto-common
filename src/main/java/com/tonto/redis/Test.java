package com.tonto.redis;

public class Test {
	
	public static void main(String[] args){
		
		String[] ips = new String[]{"10.66.28.79"};
		int[] ports = new int[]{6380};
		
		RedisDataSource source = new RedisDataSource(ips,ports);
		
		
		//source.set("tonto", "zhouxuwu");
		
		
		source.del("tonto");
		
		String value = source.get("tonto");
		System.out.println(value);
	}
}
