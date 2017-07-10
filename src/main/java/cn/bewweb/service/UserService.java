package cn.bewweb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bewweb.dao.AddressMapper;
import cn.bewweb.dao.ManagerMapper;
import cn.bewweb.dao.SellerMapper;
import cn.bewweb.dao.UserMapper;
import cn.bewweb.entities.Manager;
import cn.bewweb.entities.User;

@Service
public class UserService implements UserService_I{
	@Autowired
	private UserMapper userDao;
	@Autowired
	private AddressMapper addressDao;
	@Autowired
	private ManagerMapper managerDao;
	@Autowired
	private SellerMapper sellerDao;

	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Override
	public boolean hasPhone(String phone) {
		return userDao.selectPhoneByPhone(phone)!=null?true:false;
	}

	@Override
	public boolean hasUname(String uname) {
		return userDao.selectUnameByUname(uname)!=null?true:false;
	}

	@Override
	public boolean hasEmail(String email) {
		return userDao.selectEmailByEmail(email)!=null?true:false;
	}

	@Override
	public boolean doRegist(User user) {
		int status = userDao.insertSelective(user);
		return status==1?true:false;
	}

	@Override
	public User doLogin(User user) {
		if(null==user.getPassword()||"".equals(user.getPassword())){
			log.info("没有输入密码，登录失败");
			return null;
		}
		User libUser = null ;
		if(null!=user.getUname()&&!"".equals(user.getUname())){
			libUser = userDao.selectUserByUname(user.getUname());
		}
		else if(null!=user.getEmail()&&!"".equals(user.getEmail())){
			libUser = userDao.selectUserByEmail(user.getEmail());
		}else if(null!=user.getPhone()&&!"".equals(user.getPhone())){
			libUser = userDao.selectUserByPhone(user.getPhone());
		}else {
			return null;
		}
		if(libUser==null){
			log.info("没有查询到用户："+user.getUname());
			return null;
		}
		if(!user.getPassword().equals(libUser.getPassword())){
			log.info("密码不匹配");
			return null;
		}
		return libUser;
	}
	
	
	@Override
	public User selectUserByID(Long user_id) {
		return userDao.selectByPrimaryKey(user_id);
	}

	@Override
	public User selectUserByUserName(String uname) {
		return userDao.selectUserByUname(uname);
	}

	@Override
	public boolean updateUser(User user) {
		if(userDao.updateByPrimaryKeySelective(user)>=1){
			return true;
		}
		return false;
	}

	@Override
	public String getPhoneByUser(User user) {
		String phone = null;
		if((phone=userDao.selectPhoneByUser(user))!=null){
			return phone;
		}
		return null;
	}

	@Override
	public String selectPwdByUserId(Long userId) {
		String pwd = null;
		if((pwd=userDao.selectPwdByUserId(userId))!=null){
			return pwd;
		}
		return null;
	}

	@Override
	public Manager selectManageByUserId(Long userId) {
		return managerDao.selectManagerByUserId(userId);
	}

	@Override
	public User getNowUser(HttpServletRequest request) {
		return (User)(request.getSession().getAttribute("LOGGEDUSER"));
	}


}
