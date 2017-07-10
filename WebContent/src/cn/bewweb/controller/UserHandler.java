package cn.bewweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.beans.Mail;
import cn.bewweb.beans.ProofEnum;
import cn.bewweb.entities.Address;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.redis.ProofDao_I;
import cn.bewweb.service.ControlService_I;
import cn.bewweb.service.FileSysService;
import cn.bewweb.service.FileSysService_I;
import cn.bewweb.service.MailService_I;
import cn.bewweb.service.SafeService_I;
import cn.bewweb.service.UserService;
import cn.bewweb.service.UserService_I;
import cn.bewweb.util.CommonTool;

@Controller
@RequestMapping(value = "/user")
public class UserHandler {
	private static final Logger log = LoggerFactory.getLogger(UserHandler.class);
	@Resource
	private UserService_I userService;
	@Autowired
	private MailService_I mailService;
	@Autowired
	private FileSysService_I fileSysService;
	@Autowired
	private SafeService_I safe;
	@Autowired
	Environment env;
	@Autowired
	private ProofDao_I proofRDDao;
	
	@Autowired
	private ControlService_I control;

	/**
	 * 用户注销
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String exit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		if (user != null) {
			log.info("用户" + user.getUname() + "退出登录");
		} else {
			log.info("用户退出登录");
		}
		session.invalidate();
		
		//保存用户行为证据到数据库
		if(user!=null && control!=null){
			List<Proof> proofList =control.getRedisProofs(user.getUserId());
			if(!control.batchToSaveProofs(user.getUserId(), session.getId(), proofList)){
				log.error("保存到数据库失败");
			}
		}
		//清理残留文件
		proofRDDao.delete(user.getUserId());
		return "redirect:../index";
	}

	/**
	 * 跳转和展示用户信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
		User test = (User) request.getSession().getAttribute("LOGGEDUSER");
		if (null == test) {
			model.addAttribute("reason", "没有登录");
			return "error";
		}
		User user = userService.selectUserByID(id);
		user.setPassword(null);
		model.addAttribute("user", user);
		return "user/detail";
	}
	
	/**
	 * 异步获取修改后的用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public @ResponseBody Json getUserInfo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		if(user==null)
			return new Json().setCode(Code.noPermission);
		User loggedUser = new User();
		loggedUser.setUserId(user.getUserId());
		loggedUser.setUname(user.getUname());
		loggedUser.setEmail(user.getEmail());
		loggedUser.setPhone(user.getPhone());
		loggedUser.setSex(user.getSex());
		List<User> list = new ArrayList<User>(1);
		list.add(loggedUser);
		return new Json().setCode(Code.c1).setList(list);
	}

	/*
	 * @RequestMapping(value="/addHeadPic",method=RequestMethod.POST)
	 * public @ResponseBody Json postHeadPic(@RequestPart("headFile") Part
	 * headFile){ System.err.println(headFile.getName()); return new
	 * Json().setCode(Code.c1); }
	 */
	/**
	 * 保存头像
	 * 
	 * @param headFile
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addHeadPic", method = RequestMethod.POST)
	public @ResponseBody Json postHeadPic(@RequestPart("headFile") MultipartFile headFile, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		List<String> name = new ArrayList<String>();
		if (null == user) {
			return new Json().setCode(Code.noPermission);
		}
		String contentType = headFile.getContentType();
		log.info("上传文件名：" + headFile.getOriginalFilename() + " 文件类型：" + contentType);
		if (!"image".equals(contentType.substring(0, contentType.indexOf("/")))) {
			return new Json().setCode(Code.paramTypeError);
		}
		if (headFile.isEmpty()) {
			log.info("文件是空的");
			return new Json().setCode(Code.lostParam);
		}
		if (headFile.getSize() > 1048576 * 2) {// 头像限制在2M
			log.info("超出限制");
			return new Json().setCode(Code.overflow);
		}
		try {
			String[] path = fileSysService.genNewName(user.getUserId(), contentType, headFile.getOriginalFilename(),
					"h_", "1");
			if (null != path[0]) {
				path[0] = env.getProperty("uploads") + path[0];
				fileSysService.creatPath(path[0], null);
			} else {
				path[0] = env.getProperty("uploads");
			}
			path[0] = path[0] + path[1];
			// 清理没有确定的临时文件
			String tmpHPic = (String) session.getAttribute("headPicFile");
			if (tmpHPic != null) {
				fileSysService.delete(tmpHPic);
			}
			// 保存图片的临时路径
			session.setAttribute("headPicFile", path[0]);
			// System.err.println(path[0]);
			headFile.transferTo(new File(path[0]));
			// 给前端的名字
			name.add(path[1]);
		} catch (IllegalStateException e) {
			log.error("状态异常");
			return new Json().setCode(Code.unkownError);
		} catch (IOException e) {
			log.error("路径错误");
			return new Json().setCode(Code.unkownError);
		}
		return new Json().setCode(Code.c1).setList(name);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @param s_province
	 * @param s_city
	 * @param s_county
	 * @param birthdayStr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody Json updateUser(User user, String s_province, String s_city, String s_county,
			String birthdayStr, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User LOGGEDUSER = (User) session.getAttribute("LOGGEDUSER");
		List<String> name = new ArrayList<String>();
		if (null == LOGGEDUSER) {
			return new Json().setCode(Code.noPermission);
		}
		user.setBirthday(null == birthdayStr ? null : CommonTool.StringToDate(birthdayStr));
		String birthCity = null;
		if (!"省份".equals(s_province)) {
			birthCity = s_province + " ";
			if (!"地级市".equals(s_city)) {
				birthCity += (s_city + " ");
				if (!"市、县级".equals(s_county)) {
					birthCity += (s_county + " ");
					if (null != user.getBirthCity() && !"".equals(user.getBirthCity())) {
						birthCity = birthCity + user.getBirthCity();
					}
				}
			}
		}
		user.setUserId(LOGGEDUSER.getUserId());
		user.setBirthCity(birthCity);
		System.err.println(user);
		if (!userService.updateUser(user)) {
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1).setList(name);
	}

	@RequestMapping(value = "/saveHeads", method = RequestMethod.POST)
	public @ResponseBody Json savaHeadPic(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		String tmp = (String) session.getAttribute("headPicFile");
		if (null != tmp) {
			String fileStr = env.getProperty("userHome") + user.getUserId() + "/headPic";
			if (fileSysService.getSize(fileStr) > 0) {
				fileSysService.delete(fileStr);
			}
			if (!fileSysService.tranToHeads(user.getUserId())) {
				log.warn("头像保存失败");
				return new Json().setCode(Code.c0);
			}
			session.setAttribute("headPicFile", null);
		}
		return new Json().setCode(Code.c1);
	}

	/*
	 * phone & mail 的mode 仅支持 checkNew,checkOld,change三种模式
	 * 
	 */
	/**
	 * 跳转到手机界面
	 * @param mode
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	public String goPhone(String mode, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		model.addAttribute("mode", mode);
		return "user/phone";
	}

	/**
	 * 检查当前登录用户手机是否存在 (存在的漏洞：手机存在性后端验证值的session不能正确描述两个值）
	 * 
	 * @param request
	 * @return 转态值和存在的号码
	 */
	@RequestMapping(value = "/checkPhone", method = RequestMethod.GET)
	public @ResponseBody Json checkPhone(String phone, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		if (phone == null || user == null || user.getPhone() == null) {
			return new Json().setCode(Code.c0);
		}
		if (!phone.equals(user.getPhone())) {
			return new Json().setCode(Code.c0);
		}
		session.setAttribute("phone", user.getPhone());
		List<String> list = new ArrayList<String>(1);
		list.add(user.getPhone());
		return new Json().setCode(Code.c1);
	}

	/**
	 * 手机验证，和更新
	 * 
	 * @param mode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/phone", method = RequestMethod.POST)
	public @ResponseBody Json phone(String mode, HttpServletRequest request) {
		if (null == mode) {
			return new Json().setCode(Code.c0);
		}
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		User loggedUser = (User) session.getAttribute("LOGGEDUSER");
		boolean smsValid = (boolean) session.getAttribute("smsValid");
		

		if (null == phone || null == loggedUser) {
			return new Json().setCode(Code.c0);
		}
		// 校验并更换手机
		if (mode.equals("change")) {
			// 短信验证没通过，原来的手机号不存在，通过的手机和库里的手机号是同一个手机号，都算失败
			if (!smsValid || loggedUser.getPhone() == null || phone.equals(loggedUser.getPhone())) {
				log.warn("mode怀疑被篡改导致错误");
				return new Json().setCode(Code.c0);
			}
			loggedUser.setPhone(phone);
			if (userService.updateUser(loggedUser)) {
				
				session.removeAttribute("phone");
				session.removeAttribute("smsValid");
				return new Json().setCode(Code.c1);
			}
			return new Json().setCode(Code.c0);
		}
		// 添加手机验证
		else if (smsValid && mode.equals("checkNew")) {
			// 如果此人本来是有手机号了，返回失败
			if (null != loggedUser.getPhone() && !"".equals(loggedUser.getPhone())) {
				log.warn("mode怀疑被篡改导致错误");
				return new Json().setCode(Code.c0);
			}
			loggedUser.setPhone(phone);
			if(loggedUser.getEmail()!=null)
				loggedUser.setState("5");
			else{
				loggedUser.setState("3");
			}
			if (userService.updateUser(loggedUser)) {
				session.setAttribute("LOGGEDUSER", loggedUser);
				
				session.removeAttribute("phone");
				session.removeAttribute("smsValid");
				return new Json().setCode(Code.c1);
			}
			return new Json().setCode(Code.c0);
		}
		// 重新检验旧手机
		else {
			if (smsValid && loggedUser.getPhone() != null) {
				session.setAttribute("LOGGEDUSER", loggedUser);
				session.setAttribute("PASS", "phonePass");
				
				session.removeAttribute("phone");
				session.removeAttribute("smsValid");
				return new Json().setCode(Code.c1);
			} else {
				return new Json().setCode(Code.c0);
			}
		}

	}

	/**
	 * 查询当前用户的email与输出的是否相同
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
	public @ResponseBody Json checkEmail(String email, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		if (email == null || user == null || user.getEmail() == null) {
			log.warn("丢失参数");
			return new Json().setCode(Code.lostParam);
		}
		if (!email.equals(user.getEmail())) {
			return new Json().setCode(Code.c0);
		}
		List<String> list = new ArrayList<String>(1);
		list.add(user.getEmail());
		return new Json().setCode(Code.c1).setList(list);
	}

	/**
	 * 分发邮箱验证码
	 * 
	 * @param mode
	 *            checkNew,checkOld,change
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/distributeCode", method = RequestMethod.GET)
	public @ResponseBody Json distributeCode(String mode, String recipient, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("LOGGEDUSER");
		if (loggedUser == null) {
			return new Json().setCode(Code.lostParam);
		}
		ProofEnum proof = (ProofEnum) session.getAttribute("proofEnum");
		String code = mailService.creatCode(recipient, proof);
		String content = null;
		Mail mail = null;
		String subject = "必易为：本操作重新验证你的邮箱";
		content = mailService.getEmailTemplate(subject, code);
		mail = mailService.createEmail(recipient, subject, code, content);
		mailService.sendEmail(mail);
		Map<String, String> emailCodes = null;
		try {
			Object object  =  session.getAttribute("emailCodes");
			if(object != null ){
				emailCodes = (Map<String, String>)object;
			}
		} catch (ClassCastException e) {
			log.error("类型转换异常");
			return new Json().setCode(Code.c0);
		}
		if ("change".equals(mode)) {
			if (emailCodes == null) {
				emailCodes = new HashMap<String, String>();
				emailCodes.put(recipient, code);
				session.setAttribute("emailCodes", emailCodes);
			} else {
				if (emailCodes.size() < 2) {
					emailCodes.put(recipient, code);
					session.setAttribute("emailCodes", emailCodes);
				} else {
					log.error("出现了多于两个的邮箱");
					session.removeAttribute("emailCodes");
					return new Json().setCode(Code.c0);
				}
			}

		} else if ("checkNew".equals(mode)) {
			if (recipient.equals(loggedUser.getEmail())) {
				return new Json().setCode(Code.c0);
			}
			if (emailCodes == null) {
				emailCodes = new HashMap<String, String>();
			}
			emailCodes.put(recipient, code);
			session.setAttribute("emailCodes", emailCodes);
		} else if ("checkOld".equals(mode)) {
			if (!recipient.equals(loggedUser.getEmail())) {
				return new Json().setCode(Code.c0);
			}
			if (emailCodes == null) {
				emailCodes = new HashMap<String, String>();
			}
			emailCodes.put(recipient, code);
			session.setAttribute("emailCodes", emailCodes);
		} else {
			log.error("错误");
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}

	/**
	 * email页面的跳转
	 * 
	 * @param mode
	 *            携带模式参数模式有 checkNew,checkOld,change
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String goMail(String mode, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		model.addAttribute("mode", mode);
		return "user/mail";
	}

	/**
	 * 验证和更新邮箱 (存在的漏洞：邮箱存在性后端验证值的session不能正确描述两个值，在此没做验证）
	 * 
	 * @param mode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public @ResponseBody Json mail(String mode, String oldEmail, String oldCode, String newEmail, String newCode,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		if (user == null || mode == null) {
			log.info("缺少参数");
			return new Json().setCode(Code.lostParam);
		}
		Map<String, String> emailCodes = null;
		try {
			emailCodes = (HashMap<String, String>) session.getAttribute("emailCodes");
			session.removeAttribute("emailCodes");
		} catch (Exception e) {
			log.warn("类型转换异常");
			return new Json().setCode(Code.unkownError);
		}
		if (emailCodes == null) {
			log.warn("操作超时");
			return new Json().setCode(Code.timeOut);
		}
		Set<Entry<String, String>> entrySet = emailCodes.entrySet();
		Date date = null;
		for (Entry<String, String> entry : entrySet) {
			date = mailService.decodeDate(entry.getValue());
			if (CommonTool.dateSUbDate(new Date(), date, "m") >= 5) {// 超过15分钟的没有认证的邮箱，验证失败
				log.warn("操作超时");
				return new Json().setCode(Code.timeOut);
			}
		}
		if ("change".equals(mode)) {
			if (oldEmail == null || oldCode == null) {
				log.warn("缺少参数");
				return new Json().setCode(Code.lostParam);
			}
			if (newEmail == null || newCode == null) {
				log.warn("缺少参数");
				return new Json().setCode(Code.lostParam);
			}
			if (!oldCode.equals(emailCodes.get(oldEmail)) || !newCode.equals(emailCodes.get(newEmail))) {
				log.warn("验证码不正确");
				return new Json().setCode(Code.c0);
			}
			user.setEmail(newEmail);
			if (!userService.updateUser(user)) {
				log.error("数据库操作错误");
				return new Json().setCode(Code.c0);
			}
		} else if ("checkNew".equals(mode)) {
			if (newEmail == null || newCode == null) {
				return new Json().setCode(Code.lostParam);
			}
			if (!newCode.equals(emailCodes.get(newEmail))) {
				log.warn("验证码不正确");
				return new Json().setCode(Code.c0);
			}
			user.setEmail(newEmail);
			if(user.getEmail()!=null)
				user.setState("5");
			else{
				user.setState("4");
			}
			if (!userService.updateUser(user)) {
				log.error("数据库操作错误");
				return new Json().setCode(Code.c0);
			}
		} else if ("checkOld".equals(mode)) {
			if (oldEmail == null || oldCode == null) {
				log.warn("缺少参数");
				return new Json().setCode(Code.lostParam);
			}
			if (!oldCode.equals(emailCodes.get(oldEmail))) {
				log.warn("验证码不正确");
				return new Json().setCode(Code.c0);
			}
			session.setAttribute("PASS", "emailPass");
		} else {
			log.error("错误");
		}
		return new Json().setCode(Code.c1);
	}



	/**
	 * 处理修改密码申请
	 * 
	 * @param oldPwd
	 * @param newPwd
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	public @ResponseBody Json modifyPwd(String oldPwd, String newPwd, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("LOGGEDUSER");
		oldPwd = oldPwd.trim();
		newPwd = newPwd.trim();
		List<String> tip = new ArrayList<String>(1);
		if (oldPwd.equals(newPwd)) {
			log.info("新旧密码相同，不能修改");
			tip.add("新旧密码相同，不能修改");
			return new Json().setCode(Code.c0).setList(tip);
		}
		String rand = (String) session.getAttribute("pwdRand");
		session.removeAttribute("pwdRand");
		oldPwd = safe.pretreatClientPwd(oldPwd, rand);
		newPwd = safe.pretreatClientPwd(newPwd, rand);
		if (oldPwd == null || newPwd == null) {
			log.info("加密处理失败");
			return new Json().setCode(Code.c0);
		}
		if (!oldPwd.equals(userService.selectPwdByUserId(loggedUser.getUserId()))) {
			tip.add("输入密码与原始密码不匹配");
			log.info("原始密码不匹配");
			return new Json().setCode(Code.c0).setList(tip);
		}
		session.setAttribute("newPwd", newPwd);
		if (!loggedUser.getState().equals("3")) {
			tip.add("user/mail?mode=checkOld");
		} else {
			tip.add("user/phone?mode=checkOld");
		}
		return new Json().setCode(Code.c1).setList(tip);
	}

	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doModifyPwd", method = RequestMethod.POST)
	public @ResponseBody Json doModifyPwd(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("LOGGEDUSER");
		if (loggedUser == null) {
			return new Json().setCode(Code.noPermission);
		}
		String isPass = (String) session.getAttribute("PASS");
		session.removeAttribute("PASS");
		if (isPass == null || !(isPass.equals("emailPass") || isPass.equals("phonePass"))) {
			log.warn("没有通过验证");
			return new Json().setCode(Code.c0);
		}
		String newPwd = (String) session.getAttribute("newPwd");
		session.removeAttribute("newPwd");
		if (newPwd == null) {
			log.error("没有获取到新的密码");
			return new Json().setCode(Code.unkownError);
		}
		User user = new User();
		user.setUserId(loggedUser.getUserId());
		user.setPassword(newPwd);
		if (!userService.updateUser(user)) {
			log.error("更新用户的数据库操作失败");
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}
	
	@RequestMapping(value = "/getAddrs", method = RequestMethod.POST)
	public @ResponseBody Json getAddrs(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User loggedUser = (User) session.getAttribute("LOGGEDUSER");
		if (loggedUser == null) {
			return new Json().setCode(Code.noPermission);
		}
		List<Address> addrs =  new ArrayList<Address>(10);
		return new Json().setCode(Code.c1);
	}
	
}
