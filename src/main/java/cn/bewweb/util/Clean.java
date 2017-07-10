package cn.bewweb.util;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

public class Clean {
	
	@Autowired
	private Jedis jedis;
	public void testAutowired(){
		jedis.flushAll();
	}
}
