package com.firenay.gmall.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>Title: RedisUtils</p>
 * Description：
 * date：2020/5/1 9:17
 */
public class RedisUtils {

	private JedisPool jedisPool;

	/**
	 * 最大链接数
	 */
	public static final int MaxTotal = 200;

	/**
	 * 最大等待时间 单位：s
	 */
	public static final int MaxWaitMillis = 10;

	/**
	 * 设置最小剩余数
	 */
	public static final int MinIdle = 10;

	/**
	 * 单位：200毫秒
	 */
	public static final int TimeOut = 1;


	/**
	 *
	 * @param dataBase 连接几号库
	 */
	public void iniRedisPool(String host, int port,int dataBase){

		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(MaxTotal);
		jedisPoolConfig.setMaxWaitMillis(1000 * MaxWaitMillis);
		jedisPoolConfig.setMinIdle(MinIdle);

		// 当用户获取到一个连接池之后子检查是否可以使用
		jedisPoolConfig.setTestOnBorrow(true);
		// 开启缓冲池	[当所有线程用完之后如果还有后面的就等着]
		jedisPoolConfig.setBlockWhenExhausted(true);				// 200毫秒连接不上就算挂了
		jedisPool = new JedisPool(jedisPoolConfig, host, port,TimeOut * 200);
	}

	public Jedis getRedis(){
		return jedisPool.getResource();
	}
}
