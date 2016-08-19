package com.tonto.redis;

import redis.clients.jedis.JedisCommands;

/**
 * 
 * jedis 容器
 * 
 * @author TontoZhou
 *
 */
public interface JedisContainer {
	
	/**
	 * 得到jedis
	 * @return
	 */
	public JedisCommands getJedis();
	
	/**
	 * <p>关闭jedis</p>
	 * <p>用于生命周期结束关闭当前周期内jedis的方法，可不做任何操作</p>
	 */
	public void closeJedis();
}
