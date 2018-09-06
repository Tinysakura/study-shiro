package com.cfh.studyshiro.common;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 分布式的redis连接池
 * @author Mr.Chen
 * date: 2018年8月4日 下午2:01:39
 */
public class RedisShardedPool {
	public static ShardedJedisPool pool;//sharded jedis连接池
	//连接池相关属性
    private static Integer maxTotal = 20; //最大连接数
    private static Integer maxIdle = 20;//在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = 20;//在jedispool中最小的idle状态(空闲的)的jedis实例的个数

    private static Boolean testOnBorrow = true;//在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = true;//在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。

    private static String redis1Ip = "119.23.111.185";
    private static Integer redis1Port = 6379;
    private static String redis2Ip = "119.23.111.185";
    private static Integer redis2Port = 6380;
    
    //在静态代码块中初始化连接池
    static{
    	initPool();
    }
    
    public static void initPool(){
    	JedisPoolConfig config = new JedisPoolConfig();
    	config.setMaxTotal(maxTotal);
    	config.setMaxIdle(maxIdle);
    	config.setMinIdle(minIdle);
    	config.setTestOnBorrow(testOnBorrow);
    	config.setTestOnReturn(testOnReturn);
    	
    	//初始化两个分片
    	JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port);
    	JedisShardInfo info2 = new JedisShardInfo(redis2Ip,redis2Port);
    	
    	List<JedisShardInfo> infoList = new ArrayList<>();
    	infoList.add(info1);
    	infoList.add(info2);
    	
    	pool = new ShardedJedisPool(config, infoList);
    }
    
    public static ShardedJedis getResource(){
    	return pool.getResource();
    }
    
    public static void returnResource(ShardedJedis resource){
    	pool.returnResource(resource);
    }
    
    public static void returnBrokenResource(ShardedJedis resource){
    	pool.returnBrokenResource(resource);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
