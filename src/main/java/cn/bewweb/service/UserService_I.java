package cn.bewweb.service;


import javax.servlet.http.HttpServletRequest;

import cn.bewweb.entities.Manager;
import cn.bewweb.entities.User;

public interface UserService_I {
	/**
	 * 获取当前用户
	 * @param request 请求
	 * @return 当前用户
	 */
	User getNowUser(HttpServletRequest request);
	//校验
	boolean hasPhone(String phone);
	boolean hasUname(String uname);
	boolean hasEmail(String email);
	//注册
	boolean doRegist(User user);
	//登录
	User doLogin(User user);
	//获取用户详情
	User selectUserByID(Long user_id);
	User selectUserByUserName(String uname);
	//更新
	boolean updateUser(User user);
	//获取手机号
	String getPhoneByUser(User user);
	//获取密码
	String selectPwdByUserId(Long userId);
	
	/**
	 * 按照用户ID获取管理员信息
	 * @param userId 用户主键
	 * @return 管理员
	 */
	Manager selectManageByUserId(Long userId);
	
}