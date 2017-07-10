package cn.bewweb.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import cn.bewweb.beans.CartForm;
import redis.clients.jedis.Jedis;

@Configuration
public class libraryShopConfig {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("message/messages");
		return messageSource;
	}

	@Autowired
	JedisConnectionFactory jedisConnectionFactory;

	@Bean
	public Jedis jedis() {
		JedisConnection jedisConnection = jedisConnectionFactory.getConnection();
		Jedis jedis = jedisConnection.getNativeConnection();
		return jedis;
	}

	/*
	 * @Bean public RedisConnectionFactory redisCF(){ JedisConnectionFactory cf
	 * = new JedisConnectionFactory(); cf.setPort(10001); return cf; }
	 */

	/*
	 * @Bean public RedisTemplate<String, Product> redisTemplate(){
	 * RedisTemplate<String, Product> redis = new RedisTemplate<String,
	 * Product>(); redis.setConnectionRactory(redisCF()); return redis; }
	 */

	/*
	 * @Bean public MultipartResolver multipartResolver(){ return new
	 * StandardServletMultipartResolver(); }
	 */

	/*
	 * public MultipartResolver multipartResolver(){ CommonsMultipartResolver
	 * multipartResolver = new CommonsMultipartResolver();
	 * multipartResolver.setDefaultEncoding("utf-8");
	 * multipartResolver.setMaxInMemorySize(102400000);
	 * multipartResolver.setMaxUploadSizePerFile(102400000); try {
	 * multipartResolver.setUploadTempDir(new
	 * FileSystemResource("G:/fileDatas/uploads")); } catch (IOException e) {
	 * System.err.println("没有路径"); } return multipartResolver; }
	 */

}
