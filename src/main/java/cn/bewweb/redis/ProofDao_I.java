package cn.bewweb.redis;

import java.util.List;
import java.util.Set;

import cn.bewweb.entities.Proof;

public interface ProofDao_I {
	
	boolean put(Long userId,Proof proof);
	
	Proof get(Long userId,String proofName);
	
	List<Proof> getProofs(Long userId);
	
	void delete(Long userId);
	void delete(Long userId,String proofName);
	
	boolean exist(Long userId);
	boolean exist(Long userId,String proofName);
	
	Set<String> getKeys();

}
