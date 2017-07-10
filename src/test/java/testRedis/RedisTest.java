package testRedis;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bewweb.config.libraryShopConfig;
import cn.bewweb.redis.ProofDao_I;
import redis.clients.jedis.Jedis;

//@ContextConfiguration(classes=libraryShopConfig.class) //加载配置 //加载配置
@ContextConfiguration(locations = { "classpath:rootContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisTest {

	@Autowired
	JedisConnectionFactory jedisConnFactory;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void testConnection() {
		Jedis jedis = new Jedis("127.0.0.1", 10001);
		System.err.println(jedis.ping());

		JedisConnection jconn = jedisConnFactory.getConnection();
		System.out.println(jconn.ping());

		jedis.flushDB();
		BoundListOperations<String, String> list = redisTemplate.boundListOps("list");
		for (int i = 0; i < 100; i++) {
			list.rightPush("msn1" + i);
		}
		redisTemplate.opsForValue().set("孟盛能", "牛逼了");

	}

	@Test
	public void insert() {
		stringRedisTemplate.opsForValue().set("孟盛能123", "孟盛能123");

	}

	@Test
	public void testRedisClinet() throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		/*
		User user = new User();
		user.setName("msnmsn");
		user.setAge(23);
		
		
		String userstr = mapper.writeValueAsString(user);
		stringRedisTemplate.opsForValue().set("userobj", userstr);;
		*/
		String userstr = stringRedisTemplate.opsForValue().get("userobj");
		User user = mapper.readValue(userstr, User.class);
		System.out.println(user);
	}
	
	
	
	@Autowired
	private ProofDao_I proofDao;

	@Test
	public void testRead() {

		//System.out.println(redisTemplate.opsForValue().get("testJdk2"));
		Set<String> keys = proofDao.getKeys();
		//Iterator<String> it = keys.iterator();
		for (String string : keys) {
			System.err.print(string+"\t");
		}
	}
	
	

}
