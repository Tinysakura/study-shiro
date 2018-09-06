package com.cfh.studyshiro.test;

import org.junit.Test;

import com.cfh.studyshiro.util.RedisShardedPoolUtil;

/**
 * 测试封装的redis util
 * @author Mr.Chen
 * date: 2018年8月4日 下午2:36:46
 */
public class TestShardedRedisUtil {
	@Test
	public void testSet(){
		RedisShardedPoolUtil.set("cfh", "habi");
	}
	
	@Test
	public void testGet(){
		System.out.println(RedisShardedPoolUtil.get("cfh"));
	}
	
	@Test
	public void testExpire(){
		RedisShardedPoolUtil.expire("liqian", 1800);
	}
	
	@Test
	public void testSetEx(){
		RedisShardedPoolUtil.setEx("xiepang", "guapi", 1800);
	}
	
}
