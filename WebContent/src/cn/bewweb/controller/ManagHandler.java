package cn.bewweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.entities.Manager;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.service.ControlService_I;
import cn.bewweb.service.SafeService_I;
import cn.bewweb.service.UserService_I;

@Controller
@RequestMapping(value = "/manager")
public class ManagHandler {
	private static Logger log = LoggerFactory.getLogger(ManagHandler.class);
	/**
	 * 管理员登录
	 * @return
	 */
	@Autowired
	private ControlService_I cservice;
	@Autowired
	private UserService_I uservice;
	@Autowired
	private SafeService_I safe;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest request){
		HttpSession session = request.getSession();
		String pwdRand = safe.getRandomCode(3, 8);
		request.setAttribute("pwdRand", pwdRand);
		session.setAttribute("pwdRand",pwdRand);
		return "manager/login";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public @ResponseBody Json dologin(HttpServletRequest request,String authCode,String uname,String password){
		Json json = new Json();
		HttpSession session = request.getSession();
		if(authCode== null || uname == null || password==null){
			return json.setCode(Code.c0);
		}
		
		uname = uname.trim();
		password = password.trim();
		String pwdRand = (String)session.getAttribute("pwdRand");
		if(pwdRand==null){
			return json.setCode(Code.c0);
		}
		session.removeAttribute("pwdRand");
		password = safe.pretreatClientPwd(password, pwdRand);
		if(!cservice.dologin(uname,password)){
			return json.setCode(Code.c0);
		}
		User user = uservice.selectUserByUserName(uname);
		//继续验证Manager表
		Manager mg = uservice.selectManageByUserId(user.getUserId());
		if(mg==null){
			return json.setCode(Code.c0);
		}
		session.setAttribute("MANAGER", user);
		return json.setCode(Code.c1);
	}
	
	
	/**
	 * 管理员注销
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("MANAGER");
		if (user != null) {
			log.info("用户" + user.getUname() + "退出登录");
		} else {
			log.info("用户退出登录");
		}
		session.invalidate();
		return "manager/login";
	}
	
	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	public String controlPanel(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("MANAGER");
		if(user!=null){
			return "manager/controlPanel";
		}else{
			return "manager/login";
		}
	}
	
	@RequestMapping(value="/getOnlineUser",method=RequestMethod.GET)
	public @ResponseBody Json getOnlineUser(HttpServletRequest request){
		HttpSession session = request.getSession();
		Json json = new Json();
		User user = (User) session.getAttribute("MANAGER");
		if (user == null) {
			json.setCode(Code.noPermission);
			log.info("没有权限");
		} 
		List<User> onlineUsers = cservice.getLoggedUsers();
		System.err.println("size--"+onlineUsers.size());
		json.setCode(Code.c1).setList(onlineUsers);
		return json;
	}
	
	
	@RequestMapping(value="/proofData",method=RequestMethod.GET)
	public @ResponseBody Json proofDetail(Long userId,HttpServletRequest request){
		HttpSession session = request.getSession();
		Json json = new Json();
		User user = (User) session.getAttribute("MANAGER");
		if (user == null) {
			json.setCode(Code.noPermission);
			log.info("没有权限");
		} 
		List<Proof> proofList = cservice.getRedisProofs(userId);
		//System.err.println(proofList.get(0));
		json.setCode(Code.c1).setList(proofList);
		return json;
	}
	
	/*@RequestMapping(value="/chat",method=RequestMethod.GET)
	public @ResponseBody Json proofChat(Long userId,HttpServletRequest request){
		HttpSession session = request.getSession();
		Json json = new Json();
		User user = (User) session.getAttribute("MANAGER");
		if (user == null) {
			json.setCode(Code.noPermission);
			log.info("没有权限");
		} 
		List<Proof> proofList = cservice.getRedisProofs(userId);
		json.setCode(Code.c1).setList(proofList);
		return json;
	}*/
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String goDetail(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Json json = new Json();
		User user = (User) session.getAttribute("MANAGER");
		if (user == null) {
			json.setCode(Code.noPermission);
			log.info("没有权限");
		} 
		return "manager/detail";
	}
	
	@RequestMapping(value = "/chat", method = RequestMethod.GET)
	public String goChat(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Json json = new Json();
		User user = (User) session.getAttribute("MANAGER");
		if (user == null) {
			json.setCode(Code.noPermission);
			log.info("没有权限");
		} 
		return "manager/chat";
	}
}
