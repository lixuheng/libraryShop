package cn.bewweb.service;

import java.util.Date;
import java.util.List;

import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;

public interface ControlService_I {
	
	boolean dologin(String uname,String pwd);
	
	/**
	 * 缓存数据到redis
	 * @param userId 谁的
	 * @param name 证据名
	 * @param value 证据值
	 * @param category 范畴 b,base; u,user; s,safe;
	 * @param date 记录日期
	 * @return 成功与否
	 */
	boolean saveToRedis(Long userId, String name,String value,String category,Date date);
	boolean saveToRedis(Long userId, Proof proof);
	
	List<User> getLoggedUsers();
	
	List<Proof> getRedisProofs(Long userId);
	
	boolean proofAutoAdd(Long userId,String name,Long increment,String type);
	
	boolean changeProofKey(Long newUserId,List<Proof> proofs);
	
	boolean batchToSaveProofs(Long userId,String sessionId,List<Proof> proofs);
	
	void delete(Long key);
	
	
	//////////////数据库相关
	boolean saveOneProof(Proof proof);

	boolean saveOneProof(String name, String value, Long userId, String sessionId, String type, String category);

	boolean delOneProof(Long proofId);

	boolean delProofByUser(User user);

	boolean delProofByDateInterval(Date begin, Date end);

	boolean updateOneProof(Proof proof);

	/**
	 * 获取一项行为
	 * 
	 * @param proofId
	 * @return
	 */
	Proof getOneProof(Long proofId);

	/**
	 * 获取一个用户的所有行为
	 * 
	 * @param user
	 * @param beginInt
	 *            开始读取位置
	 * @param endInt
	 *            读取的结束位置
	 * @return
	 */
	List<Proof> findProofByUser(User user, Integer beginInt, Integer endInt);

	/**
	 * 获取用户一次会话的行为
	 * 
	 * @param user
	 * @param sessionId
	 * @param beginInt
	 * @param endInt
	 * @return
	 */
	List<Proof> findProofByUserAndSession(User user, String sessionId, Integer beginInt, Integer endInt);

	/**
	 * 按指定的排序方式获一个用户一个会话的行为
	 * 
	 * @param user
	 * @param sessionId
	 * @param record_time
	 * @param beginInt
	 * @param endInt
	 * @return
	 */
	List<Proof> findProofByUserAndSessionOrderByDate(User user, String sessionId, String record_time, Integer beginInt,
			Integer endInt);

	/**
	 * 按指定的排序方式获一个用户一个时期的用户行为
	 * 
	 * @param user
	 * @param record_time
	 * @param beginDate
	 * @param endDate
	 * @param beginInt
	 * @param endInt
	 * @return
	 */
	List<Proof> findProofByUserOrderByDateByInterval(User user, String record_time, Date beginDate, Date endDate,
			Integer beginInt, Integer endInt);

}
