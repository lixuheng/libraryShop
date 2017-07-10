package cn.bewweb.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.bewweb.entities.Proof;


/*@Repository("proofRDDao")*/
public class ProofDao implements ProofDao_I {

	private static Logger log = LoggerFactory.getLogger(ProofDao.class);
	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean put(Long userId,Proof proof) {
		try {
			stringRedisTemplate.opsForHash().put(""+userId, proof.getProofName(), jsonMapper.writeValueAsString(proof));
		} catch (JsonProcessingException e) {
			log.error("json转换异常");
			return false;
		}
		return true;
	}

	@Override
	public Proof get(Long userId,String proofName) {
		try {
			Object obj = stringRedisTemplate.opsForHash().get(""+userId, proofName);
			if(obj==null){
				return null;
			}
			Proof proof = jsonMapper.readValue((String)obj, Proof.class);
			return proof;
		} catch (JsonParseException e) {
			log.error("JsonParseException");
		} catch (JsonMappingException e) {
			log.error("JsonMappingException");
		} catch (IOException e) {
			log.error("IOException");
		}
		return null;
	}

	@Override
	public List<Proof> getProofs(Long userId) {
		List<String> pliststr = new ArrayList<String>(10);
		Set<Object> keys = stringRedisTemplate.opsForHash().keys(""+userId);
		for (Object object : keys) {
			pliststr.add((String)(stringRedisTemplate.opsForHash().get(""+userId, (String)object)));
		}
		List<Proof> plist = new ArrayList<Proof>();
		try {
			for (String str : pliststr) {
				if(str!=null){
					plist.add(jsonMapper.readValue(str, Proof.class));
				}
			}
		} catch (Exception e) {
			log.error("json转换异常");
		}
		return plist;
	}

	@Override
	public void delete(Long userId) {
		stringRedisTemplate.delete(""+userId);
	}
	
	@Override
	public void delete(Long userId,String proofName) {
		stringRedisTemplate.opsForHash().delete(""+userId, proofName);
	}

	@Override
	public boolean exist(Long userId) {
		return stringRedisTemplate.hasKey(""+userId);
	}
	
	@Override
	public boolean exist(Long userId,String proofName) {
		return stringRedisTemplate.opsForHash().hasKey(""+userId, proofName);
	}

	@Override
	public Set<String> getKeys() {
		// TODO Auto-generated method stub
		Set<String> keys =  stringRedisTemplate.keys("*");
		return keys;
	}

}
