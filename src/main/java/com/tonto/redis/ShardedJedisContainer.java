package com.tonto.redis;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * 分布式Redis容器
 * 
 * @author TontoZhou
 * 
 */
public class ShardedJedisContainer implements JedisContainer{

	private ThreadLocal<JedisCommands> localJedis = new ThreadLocal<JedisCommands>();

	private ShardedJedisPool shardedJedisPool;

	/**
	 * 
	 * @param ips
	 * @param ports
	 */
	public ShardedJedisContainer(String[] ips) {

		JedisPoolConfig config = new JedisPoolConfig();

		List<JedisShardInfo> shards = new ArrayList<>();

		for (int i = 0; i < ips.length; i++) {
			
			String[] ip = ips[i].split(":");
			shards.add(new JedisShardInfo(ip[0], Integer.parseInt(ip[1])));
		}

		shardedJedisPool = new ShardedJedisPool(config, shards);

	}

	/**
	 * 获取当前redis链接
	 * 
	 * @return
	 */
	public JedisCommands getJedis() {

		JedisCommands jedis = localJedis.get();

		if (jedis == null) {
			jedis = shardedJedisPool.getResource();
			localJedis.set(jedis);
		}

		return jedis;
	}

	/**
	 * 关闭当前redis的链接
	 */
	public void closeJedis() {

		JedisCommands jedis = localJedis.get();

		if (jedis != getJedis()) {
			try {
				((Closeable) jedis).close();
			} catch (IOException e) {
			}

			localJedis.remove();
		}
	}

}
