package cn.bewweb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bewweb.dao.AbnormalMapper;
import cn.bewweb.dao.CSystemMapper;
import cn.bewweb.dao.ProofMapper;
import cn.bewweb.dao.ProofResultMapper;
import cn.bewweb.dao.UserMapper;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.redis.ProofDao_I;
import cn.bewweb.util.GenKey;
@Transactional
/*@Service*/
public class ControlService implements ControlService_I {

	
	@Autowired
	private ProofMapper proofDao;
	@Autowired
	private ProofResultMapper proofRSDao;
	@Autowired
	private CSystemMapper sysDao;
	@Autowired
	private AbnormalMapper abnormalDao;
	@Autowired
	private UserMapper userDao;
	@Autowired
	private ProofDao_I proofRDDao;
	
	private static final Logger log = LoggerFactory.getLogger(ControlService.class);
	
	@Override
	public boolean saveOneProof(Proof proof) {
		if(proofDao.insertSelective(proof)>0){
			return true;
		}
		return false;
	}


	@Override
	public boolean saveOneProof(String name,String value,Long userId,String sessionId,String type,String category) {
		Proof proof = new Proof();
		proof.setProofName(name);
		proof.setProofValue(value);
		proof.setUserId(userId);
		proof.setSessionId(sessionId);
		proof.setProofType(type);
		proof.setProofCategory(category);
		proof.setRecordTime(new Date());
		if(proofDao.updateByPrimaryKeySelective(proof)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delOneProof(Long proofId) {
		if(proofDao.deleteByPrimaryKey(proofId)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delProofByUser(User user) {
		if(proofDao.delProofByUser(user.getUserId())>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delProofByDateInterval(Date begin, Date end) {
		if(proofDao.delProofByDateInterval(begin, end)>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateOneProof(Proof proof) {
		if(proofDao.updateByPrimaryKeySelective(proof)>0){
			return true;
		}
		return false;
	}

	@Override
	public Proof getOneProof(Long proofId) {
		return proofDao.selectByPrimaryKey(proofId);
	}

	@Override
	public List<Proof> findProofByUser(User user, Integer beginInt, Integer endInt) {
		Proof model = new Proof();
		model.setUserId(user.getUserId());
		return proofDao.find(model, beginInt, endInt);
	}

	@Override
	public List<Proof> findProofByUserAndSession(User user, String sessionId, Integer beginInt, Integer endInt) {
		Proof model = new Proof();
		model.setUserId(user.getUserId());
		model.setSessionId(sessionId);
		return proofDao.find(model, beginInt, endInt);
	}

	@Override
	public List<Proof> findProofByUserAndSessionOrderByDate(User user, String sessionId, String record_time,
			Integer beginInt, Integer endInt) {
		Proof model = new Proof();
		model.setUserId(user.getUserId());
		model.setSessionId(sessionId);
		return proofDao.findAndOrdeBy(model, record_time, beginInt, endInt);
	}
	
	
	@Override
	public List<Proof> findProofByUserOrderByDateByInterval(User user, String record_time ,Date beginDate, Date endDate,
			Integer startInt, Integer endInt) {
		return proofDao.findProofByUserAndOrderByDateByInterval(user.getUserId(), record_time, beginDate, endDate, startInt, endInt);
	}


	@Override
	public boolean dologin(String uname,String pwd) {
		User user = userDao.selectUserByUname(uname);
		if(user!=null){
			return true;
		}
		return false;
	}


	@Override
	public boolean saveToRedis(Long userId,String name, String value, String category,Date date) {
		category = category == null? "u" : category;//base
		date = date == null? new Date() : date;
		Proof proof = new Proof();
		//proof.setUserId(userId);
		proof.setProofId(GenKey.genKey(Proof.class));
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		proof.setProofName(name);
		proof.setProofValue(value);
		proof.setRecordTime(date);
		proof.setProofCategory(category);
		if(!proofRDDao.put(userId,proof)){
			throw new RuntimeException("发生异常");
		}
		return true;
	}

	@Override
	public boolean saveToRedis(Long userId, Proof proof) {
		if(saveToRedis( userId, proof.getProofName(),  proof.getProofValue(),  proof.getProofCategory(), proof.getRecordTime()) ){
			return true;
		}
		return false;
	}


	@Override
	public boolean batchToSaveProofs(Long userId,String sessionId,List<Proof> proofs){
		for (Proof proof : proofs) {
			proof.setUserId(userId);
			proof.setSessionId(sessionId);
			if(!saveOneProof(proof)){
				throw new RuntimeException("发生异常");
			}
		}
		return true;
	}


	@Override
	public boolean proofAutoAdd(Long userId, String name,Long increment,String type) {
		type = type== null ? "u" : type;
		Proof proof =  proofRDDao.get(userId,name);
		if(proof==null){
			saveToRedis(userId, name, "1", type, null);
		}else{
			String value = proof.getProofValue();
			long count = 0l;
			try{
				count=Integer.parseInt(value);
			}catch(NumberFormatException e){
				log.error("此类型不支持计算");
				return false;
			}
			count+=increment;
			saveToRedis(userId, name,String.valueOf(count), type, null);
		}
		return true;
	}


	@Override
	public List<Proof> getRedisProofs(Long userId) {
		return proofRDDao.getProofs(userId);
	}


	@Override
	public boolean changeProofKey(Long newUserId, List<Proof> proofs) {
		for (Proof proof : proofs) {
			proof.setUserId(newUserId);
			if(!saveToRedis( newUserId,  proof)){
				return false;
			}
		}
		return true;
	}


	@Override
	public void delete(Long key) {
		proofRDDao.delete(key);
	}


	@Override
	public List<User> getLoggedUsers() {
		// TODO Auto-generated method stub
		List<User> onlineUser = new ArrayList<User>();
		User user = null;
		Set<String> keys = proofRDDao.getKeys();
		Long id = null;
		for (String string : keys) {
			try{
				id = Long.valueOf(string);
			}catch (NumberFormatException e){
				id = null;
			}
			if(id!=null){
				user = userDao.selectByPrimaryKey(id);
				onlineUser.add(user);
			}
		}
		return onlineUser;
	}




	

}
