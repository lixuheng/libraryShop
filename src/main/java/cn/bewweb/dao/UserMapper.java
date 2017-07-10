package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.User;

public interface UserMapper {
	int deleteByPrimaryKey(Long userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Long userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKeyWithBLOBs(User record);

	int updateByPrimaryKey(User record);

	/////////////////////////////////////
	User selectMyCareByPrimaryKey(User user);
	
	User selectUserByUname(String uname);

	User selectUserByEmail(String email);

	User selectUserByPhone(String phone);

	String selectUnameByUname(String uname);

	String selectPhoneByPhone(String phone);
	
	String selectPhoneByUser(User user);

	String selectEmailByEmail(String email);
	
	String selectPwdByUserId(Long userId);

	List<User> selectUsersByRname(String Rname);
	
	List<User> find(User user, Integer begin,Integer end);
	List<User> findAndOrderBy(User user,String method , Integer begin,Integer end);
	
	
}