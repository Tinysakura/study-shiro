package com.cfh.studyshiro.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.cfh.studyshiro.common.RedisShardedPool;

/**
 * 使用shardedpool的工具类
 * @author Mr.Chen
 * date: 2018年8月4日 下午2:21:06
 */
public class RedisShardedPoolUtil {
	static Logger log = LoggerFactory.getLogger(RedisShardedPoolUtil.class);
	
	/**
	 * 设置key-value
	 * @param key
	 * @param value
	 * @return
	 */
	public static String set(String key,String value){
		ShardedJedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisShardedPool.getResource();
			result = jedis.set(key, value);
		} catch (Exception e) {
			log.error(e.getMessage());
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
	/**
	 * 按键索值
	 * @param key
	 * @return
	 */
	public static String get(String key){
		log.info("read redis");
		ShardedJedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisShardedPool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
	/**
	 * 设置超时时间
	 * @param key
	 * @param value
	 * @return
	 */
	public static Long expire(String key,int exTime){
		ShardedJedis jedis = null;
		Long result = null;
		
		try {
			jedis = RedisShardedPool.getResource();
			result = jedis.expire(key, exTime);
		} catch (Exception e) {
			log.error(e.getMessage());
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
	public static Long del(String key){
		ShardedJedis jedis = null;
		Long result = null;
		
		try {
			jedis = RedisShardedPool.getResource();
			result = jedis.del(key);
		} catch (Exception e) {
			log.error(e.getMessage());
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		
		RedisShardedPool.returnResource(jedis);
		return result;
	}
	
    /**
     * 设置key-value的同时设置超时时间
     * @param key
     * @param exTime
     * @return
     */
	public static String setEx(String key,String value,int exTime){
		ShardedJedis jedis = null;
		String result = null;
		
		try {
			jedis = RedisShardedPool.getResource();
			result = jedis.setex(key, exTime, value);
		} catch (Exception e) {
			log.error(e.getMessage());
			RedisShardedPool.returnBrokenResource(jedis);
			return result;
		}
		
		RedisShardedPool.returnResource(jedis);
		return result;
	}
}
