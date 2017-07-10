package cn.bewweb.dao;

import java.util.Date;
import java.util.List;

import cn.bewweb.entities.Proof;

public interface ProofMapper {
	int deleteByPrimaryKey(Long proofId);

	int insert(Proof record);

	int insertSelective(Proof record);

	Proof selectByPrimaryKey(Long proofId);

	int updateByPrimaryKeySelective(Proof record);

	int updateByPrimaryKey(Proof record);

	//////////////////////////////

	int delProofByUser(Long userId);

	int delProofByDateInterval(Date begin, Date end);
	
	int delProofBySession(String sessionId);
	
	
	List<Proof> find(Proof model, Integer start, Integer end);
	
	List<Proof> findAndOrdeBy(Proof model,String method , Integer start, Integer end);
	
	
	List<Proof> findProofByUserByInterval(Long userId, Date beginDate, Date endDate ,Integer startInt, Integer endInt);
	
	List<Proof> findProofByUserAndOrderByDateByInterval(Long userId ,String method , Date beginDate, Date endDate , Integer startInt, Integer endInt);

	
	
	
	
	

}