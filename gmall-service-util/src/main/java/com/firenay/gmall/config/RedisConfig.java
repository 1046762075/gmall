package com.firenay.gmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: RedisConfig</p>
 * Description：redis配置文件加载类
 * date：2020/5/1 9:33
 */
@Configuration
public class RedisConfig {

	/**
	 * 如果未获取到值 则默认为 disable
	 */
	@Value("${spring.redis.host:disable}")
	private String host;

	@Value("${spring.redis.port:6379}")
	private int port;

	@Value("${spring.redis.database:0}")
	private int dataBase;

	@Bean
	public RedisUtils getRedisUtils(){
		if ("disable".equals(host)){
			return null;
		}
		RedisUtils redisUtils = new RedisUtils();
		redisUtils.iniRedisPool(host,port,dataBase);
		return redisUtils;
	}
}
