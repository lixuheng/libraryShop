package cn.bewweb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.beans.Mail;
import cn.bewweb.beans.ProofEnum;
import cn.bewweb.docments.Category;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.redis.ProofDao_I;
import cn.bewweb.service.ControlService_I;
import cn.bewweb.service.MailService_I;
import cn.bewweb.service.SafeService_I;
import cn.bewweb.service.TradeService_I;
import cn.bewweb.service.UserService_I;
import cn.bewweb.util.CommonTool;
import cn.bewweb.util.GenKey;
import redis.clients.jedis.Jedis;

@Controller
public class WelCome {
	private static final Logger log = LoggerFactory.getLogger(WelCome.class);

	@Autowired
	private ProofDao_I proofRDDao;
	@Autowired
	private UserService_I userService;
	@Autowired
	private TradeService_I tradeService;
	@Autowired
	private SafeService_I safe;
	@Autowired
	private ControlService_I control;
	@Autowired
	Environment env;
	@Autowired
	private Jedis jedis;

	/**
	 * 呈现首页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welCome(HttpServletRequest request) {
		index(request);
		return "index";
	}

	/**
	 * 呈现目录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public @ResponseBody Json categroy(HttpServletRequest request) {
		List<Category> ctlist = tradeService.getAllCategory();
		return new Json().setCode(Code.c1).setList(ctlist);
	}

	/**
	 * 呈现首页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request) {
		log.info("WelCome " + request.getRemoteHost());
		// 开启Session后的第一步
		recordTempData(request);
		HttpSession session = request.getSession();
		List<Goods> goodslist = tradeService.listGoodsByPage(0, 10);
		// Goods goods = goodslist==null? new Goods(): goodslist.get(0);
		/*
		 * Goods goods = new Goods(); goods.setGoodsName("fdsfsf");
		 */
		request.setAttribute("goodslist", goodslist);
		return "index";
	}

	@RequestMapping(value = "/saveBaseProof", method = RequestMethod.POST)
	public @ResponseBody Json saveBaseProof(String local, String ip, String os, String ua, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		Long tempUserID = (Long) session.getAttribute("tempUserId");
		if (user == null&&tempUserID==null) {
			return new Json().setCode(Code.c0);
		}
		if (tempUserID!=null&&proofRDDao.exist(tempUserID) ) {
			return new Json().setCode(Code.c1);
		}
		if (user!=null&&proofRDDao.exist(user.getUserId())) {
			return new Json().setCode(Code.c1);
		}
		
		ProofEnum proofEnum = (ProofEnum) session.getAttribute("proofEnum");
		local = local.replace("|", " ");
		ip = ip + "(" + request.getRemoteAddr() + ")";
		// IP地址
		control.saveToRedis(tempUserID, "ip", ip, "b", null);
		// 地区
		control.saveToRedis(tempUserID, "local", local, "b", new Date(System.currentTimeMillis() + 5000));
		// 操作系统类型
		control.saveToRedis(tempUserID, "os", os, "b", new Date(System.currentTimeMillis() + 5000));
		// 浏览器标识
		control.saveToRedis(tempUserID, "browser", ua, "b",
				new Date(System.currentTimeMillis() + 5000));
		//有必要记录吗？？？？
		proofEnum.setClientIp(ip);
		proofEnum.setLocation(local);
		proofEnum.setOsType(os);
		proofEnum.setBrowserType(ua);
		session.setAttribute("proofEnum", proofEnum);
		return new Json().setCode(Code.c1);
	}

	// 404错误
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String return404(Map<String, String> map) {
		map.put("notFound", "对不起，您访问服务不存在！");
		map.put("contact", "如有疑问或需要，请联系<a href=\"mailto:msnqqmail@qq.com\">管理员</a>");
		return "notFound";
	}

	/**
	 * 登陆相关
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goLogin(Model model, HttpServletRequest request) {
		return "user/login";
	}

	/**
	 * 执行登录
	 * 
	 * @param account
	 *            登录名，可以是用户名、已验证邮箱、已验证的手机号
	 * @param password
	 *            密码
	 * @param pwdErrorNum
	 *            密码输错次数，错误次数大于3时，会启用图形验证码
	 * @param authCode
	 *            图形验证码
	 * @param request
	 * @return 一个json
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Json doLogin(String account, String password, Integer pwdErrorNum, String authCode,

			Integer[] backspace,

			HttpServletRequest request) {

		beforeLogin(account, password, pwdErrorNum, authCode, backspace, request);

		Json json = new Json();
		HttpSession session = request.getSession();
		if (pwdErrorNum != null && pwdErrorNum >= 3) {
			Boolean imgcode = (Boolean) session.getAttribute("imgValid");
			session.removeAttribute("imgValid");
			if (imgcode == null || authCode == null || "".equals(authCode)) {
				return json.setCode(Code.lostParam);
			}
			if (!imgcode) {
				return json.setCode(Code.c0);
			}
		}

		User user = new User();
		String loginMethod = (String) session.getAttribute("loginMethod");
		log.info("pwdErrorNum:" + pwdErrorNum + " account:" + account + " password:" + password + " loginMethod:"
				+ loginMethod + " pwdErrorNum:" + pwdErrorNum + " authCode:" + authCode);
		if (null == loginMethod || null == account || null == password) {
			log.info("丢失参数");
			return json.setCode(Code.lostParam);
		}
		account = account.trim();
		password = password.trim();

		if ("".equals(account) || "".equals(password)) {
			log.info("缺少用户名或密码");
			return json.setCode(Code.lostParam);
		}
		String pwdRand = (String) session.getAttribute("pwdRand");
		session.removeAttribute("pwdRand");
		// 安全处理
		password = safe.pretreatClientPwd(password, pwdRand);
		user.setPassword(password);
		int mode = 0;
		if ("uname".equals(loginMethod)) {
			mode = 5;
			user.setUname(account);
		} else if ("phone".equals(loginMethod)) {
			mode = 3;
			user.setPhone(account);
		} else if ("email".equals(loginMethod)) {
			mode = 4;
			user.setEmail(account);
		} else {
			log.info("没有指定登录方式");
			return json.setCode(Code.lostParam);
		}
		User libUser = userService.doLogin(user);
		if (null != libUser) {
			String state = libUser.getState();
			state = null == state ? "0" : state;
			if (mode == 3) {
				if (4 != Integer.valueOf(state) && Integer.valueOf(state) >= 3) {
					afterLogin(request, libUser);
					return json.setCode(Code.c1);
				}
			} else if (mode == 4) {// 邮件方式登录，但要判断邮件是否已经验证过
				session.removeAttribute("loginMethod");
				if (Integer.valueOf(state) > 3) {
					afterLogin(request, libUser);
					return json.setCode(Code.c1);
				} else {
					log.info("登录失败，邮箱还未验证");
					session.setAttribute("tempUser", libUser);
					List<String> list = new ArrayList<String>(1);
					list.add("not verify email");
					if (sendEmailCode(account, request)) {
						log.error("发送邮件失败");
						list.add("邮件发送失败");
					}
					return json.setCode(Code.c0).setList(list);
				}
			} else if (mode == 5 && Integer.valueOf(state) >= 3) {
				afterLogin(request, libUser);
				return json.setCode(Code.c1);
			} else {
				return json.setCode(Code.c0);
			}

		}
		log.info("登录失败");
		return json.setCode(Code.c0);
	}

	private void recordTempData(HttpServletRequest request) {
		HttpSession session = request.getSession();
		ProofEnum proofEnum =  (ProofEnum) session.getAttribute("proofEnum");
		if(proofEnum==null){
			proofEnum = new ProofEnum();
			session.setAttribute("proofEnum", proofEnum);
		}
		
		Long tempUserId = (Long) session.getAttribute("tempUserId");
		if(tempUserId==null){
			tempUserId = GenKey.genKey(User.class);
			session.setAttribute("tempUserId",tempUserId);
		}
		log.info("做好了准备工作！");
	}

	/**
	 * 登录前的处理工作
	 * 
	 * @param request
	 */
	private void beforeLogin(String account, String password, Integer pwdErrorNum, String authCode, Integer[] backspace,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		ProofEnum proofEnum = (ProofEnum) session.getAttribute("proofEnum");

		if (proofEnum == null) {
			log.error("proofEnum是个空的");
			proofEnum = new ProofEnum();
		}
		
		Long tempUserId = (Long) session.getAttribute("tempUserId");
		if(tempUserId == null){
			log.warn("访问顺序有问题");
			return ;
		}
		control.saveToRedis(tempUserId,"accountBackspace", String.valueOf(backspace[0]), "b", null);
		control.saveToRedis(tempUserId,"setPwdBackspace", String.valueOf(backspace[1]), "b", null);
		
		/////临时存储
		proofEnum.setAccoutBackspace(backspace[0]);
		proofEnum.setPwdBackspace(backspace[1]);
		session.setAttribute("proofEnum", proofEnum);
		log.info(proofEnum.toString());

	}

	/**
	 * 登录成功的处理工作
	 * 
	 * @param request
	 */
	private void afterLogin(HttpServletRequest request, User libUser) {
		HttpSession session = request.getSession();
		log.info("登录成功");
		session.removeAttribute("loginMethod");
		session.setAttribute("LOGGEDUSER", libUser);

		ProofEnum proofEnum = (ProofEnum) session.getAttribute("proofEnum");
		if(proofEnum!=null){
			proofEnum.setLoginTime(new Date());
		}
		/////////切换用户Key
		Long tempUserId = (Long) session.getAttribute("tempUserId");
		if(tempUserId!=null){
			List<Proof> proofList = control.getRedisProofs(tempUserId);
			control.changeProofKey(libUser.getUserId(), proofList);
			control.delete(tempUserId);
		}
		
	}

	/**
	 * 检查登录名是否符合标准
	 * 
	 * @param account
	 *            登录名
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkAccount", method = RequestMethod.POST)
	public @ResponseBody Json checkAccount(String account, HttpServletRequest request) {
		Json json = new Json();
		if (null == account || "".equals(account)) {
			return json.setCode(Code.lostParam);
		}
		account = account.trim();
		String regexEmail = "^[A-Za-z0-9_\\-]+@[A-Za-z0-9_\\-]+\\.[A-Za-z0-9_\\-]+$";
		String regextPhoen = "^\\d\\d+\\d$";
		List<String> list = new ArrayList<String>(1);
		HttpSession session = request.getSession();
		if (account.matches(regexEmail)) {
			log.info("email");
			list.add("email");
			session.setAttribute("loginMethod", "email");
			json.setList(list);
			json.setCode(Code.c1);
			/*
			 * if(userService.hasEmail(account)){ return json.setCode(Code.c1);
			 * }else{ return json.setCode(Code.c0); }
			 */
		} else if (account.matches(regextPhoen)) {
			log.info("phone");
			list.add("phone");
			session.setAttribute("loginMethod", "phone");
			json.setList(list);
			json.setCode(Code.c1);
			/*
			 * if(userService.hasPhone(account)){ return json.setCode(Code.c1);
			 * }else{ return json.setCode(Code.c0); }
			 */
		} else {
			log.info("uname");
			list.add("uname");
			session.setAttribute("loginMethod", "uname");
			json.setList(list);
			json.setCode(Code.c1);
		}
		return json;
	}

	/**
	 * 获取异或随机码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getXorCode", method = RequestMethod.POST)
	public @ResponseBody Json getXorCode(HttpServletRequest request) {
		// 发个随机码
		String pwdRand = safe.getRandomCode(2, 8);
		HttpSession session = request.getSession();
		session.setAttribute("pwdRand", pwdRand);
		List<String> list = new ArrayList<String>(1);
		list.add(pwdRand);
		return new Json().setCode(Code.c1).setList(list);
	}

	/**
	 * 注册相关
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String goRegist(HttpServletRequest request) {
		return "user/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public @ResponseBody Json doRegist(@Valid User user, HttpServletRequest request, String local, Errors errors,
			String method) {
		user.setUserId(GenKey.genKey(User.class));
		user.setState("0");
		HttpSession session = request.getSession();
		Object imgValid = session.getAttribute("imgValid");
		Object smsValid = session.getAttribute("smsValid");
		session.removeAttribute("imgValid");
		session.removeAttribute("smsValid");
		log.info("bengin doregist");
		if (errors.hasErrors()) {
			log.info("有错误");
			List<Errors> list = new ArrayList<Errors>(1);
			list.add(errors);
			return (new Json()).setCode(Code.c0).setList(list);
		}
		// 密码安全处理
		String pwd = user.getPassword();// 16进制字符串异或密文
		if (pwd.length() < 6) {
			log.info("密码长度不够");
			return new Json().setCode(Code.paramTypeError);
		}
		String pwdRand = (String) session.getAttribute("pwdRand");
		session.removeAttribute("randPwd");
		if (null == pwdRand) {
			return new Json().setCode(Code.c0);
		}
		pwd = safe.pretreatClientPwd(pwd, pwdRand);// 得摘要
		user.setPassword(pwd);// 写入到用户
		// 注册方式判断
		if ("phone".equals(method)) {
			log.info("以手机的方式注册");
			user.setEmail(null);
			if (null == imgValid || null == smsValid) {
				log.info("没有完成校验");
				return (new Json()).setCode(Code.lostParam);
			}
			if (!(boolean) imgValid) {
				log.info("图片校验错误");
				List<String> list = new ArrayList<String>(1);
				list.add("imgValid fail");
				return (new Json()).setCode(Code.c0).setList(list);
			}
			if (!(boolean) smsValid) {
				log.info("短信校验错误");
				List<String> list = new ArrayList<String>(1);
				list.add("smsValid fail");
				return (new Json()).setCode(Code.c0).setList(list);
			}
			user.setState("3");
			if (userService.doRegist(user)) {
				log.info("注册成功");
				return (new Json()).setCode(Code.c1);
			} else {
				log.info("注册失败");
				return (new Json()).setCode(Code.c0);
			}
		} else if ("email".equals(method)) {
			log.info("以邮件的方式注册");
			user.setPhone(null);
			if (null == imgValid) {
				log.info("没有完成图形校验");
				return (new Json()).setCode(Code.lostParam);
			}
			user.setState("0");
			if (userService.doRegist(user)) {
				log.info("注册成功");

				ProofEnum proofEnum = (ProofEnum) session.getAttribute("proofEnum");
				proofEnum = proofEnum == null ? new ProofEnum() : proofEnum;
				proofEnum.setClientIp(request.getRemoteAddr());
				proofEnum.setLocation(local);

				session.setAttribute("tempUser", user);
				session.setAttribute("proofEnum", proofEnum);
				List<String> list = new ArrayList<String>(1);
				log.warn("没有验证过邮箱");
				list.add("not verify email");
				if (sendEmailCode(user.getEmail(), request)) {
					log.error("发送邮件失败");
				}
				log.info("发送了一份邮件验证");
				return (new Json()).setCode(Code.c1);
			} else {
				log.info("注册失败");
				return (new Json()).setCode(Code.c0);
			}
		} else {
			log.info("验证方式错误");
			return (new Json()).setCode(Code.c0);
		}
	}

	@RequestMapping(value = "/checkPhone", method = RequestMethod.GET)
	public @ResponseBody Json checkPhone(@RequestParam(value = "phone", required = true) String phone,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		if ((phone == null || "".equals(phone)) ? true : false) {
			return (new Json()).setCode(Code.lostParam);
		}
		phone = phone.trim();
		if (!phone.matches("^\\d\\d+\\d$")) {
			log.info("手机格式错误");
			return (new Json()).setCode(Code.paramTypeError);
		}
		session.setAttribute("phone", phone);
		if (userService.hasPhone(phone)) {
			return (new Json()).setCode(Code.c1);
		} else {
			return (new Json()).setCode(Code.c0);
		}

	}

	@RequestMapping(value = "/checkUname", method = RequestMethod.GET)
	public @ResponseBody Json checkUname(@RequestParam(value = "uname", required = true) String uname) {
		if ((uname == null || "".equals(uname)) ? true : false) {
			return (new Json()).setCode(Code.lostParam);
		}
		uname = uname.trim();
		String regex = "[`~!%@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]";
		if (uname.matches(regex)) {
			log.info("用户名不规范");
			return (new Json()).setCode(Code.paramTypeError);
		}
		if (userService.hasUname(uname)) {
			return (new Json()).setCode(Code.c1);
		} else {
			return (new Json()).setCode(Code.c0);
		}

	}

	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
	public @ResponseBody Json checkEmail(@RequestParam(value = "email", required = true) String email) {
		if ((email == null || "".equals(email)) ? true : false) {
			return (new Json()).setCode(Code.lostParam);
		}
		email = email.trim();
		String regex = "^[A-Za-z0-9_\\-]+@[A-Za-z0-9_\\-]+\\.[A-Za-z0-9_\\-]+$";
		if (!email.matches(regex)) {
			log.info("邮件名不规范");
			return (new Json()).setCode(Code.paramTypeError);
		}
		if (userService.hasEmail(email)) {
			return (new Json()).setCode(Code.c1);
		} else {
			return (new Json()).setCode(Code.c0);
		}

	}

	@Autowired
	MailService_I mailService;

	/**
	 * 发送验证邮件
	 * 
	 * @param email
	 *            接收方
	 * @param request
	 * @return
	 */
	public boolean sendEmailCode(String email, HttpServletRequest request) {
		ProofEnum proofEnum = (ProofEnum) ((request.getSession()).getAttribute("proofEnum"));
		if (proofEnum == null) {
			log.error("邮件发送失败，行为不足");
			return false;
		}
		String code = mailService.creatCode(email, proofEnum);
		Mail mail = mailService.createEmail(email, "必易为电子商城注册验证", code,
				mailService.getEmailTemplate("必易为电子商城注册验证", code));
		if (null == mail) {
			log.error("邮件发送失败，未能产生mail对象");
			return false;
		}
		if (mailService.sendEmail(mail)) {
			HttpSession session = request.getSession();
			session.setAttribute("emailCode", mail.getCode());
		} else {
			log.error("邮件发送失败，发送机制有错误");
			return false;
		}
		return false;
	}

	@Autowired
	UserService_I selfService;

	/**
	 * 核实邮件里面的验证码
	 * 
	 * @param code
	 *            验证码
	 * @param authCode
	 *            图形验证码
	 * @param request
	 * @return json提示
	 */
	@RequestMapping(value = "/mail/verify", method = RequestMethod.GET)
	public String verifyEmail(String code, HttpServletRequest request, Map<String, String> map) {
		code = null == code || "".equals(code) ? "" : code.trim();
		Date date = mailService.decodeDate(code);
		log.info("解析的时间" + CommonTool.dateToString(date, true));
		if (CommonTool.dateSUbDate(new Date(), date, "m") >= 5) {// 超过15分钟的没有认证的邮箱，验证失败
			map.put("reason", "校验码已过期");
		}
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute("emailCode");
		session.removeAttribute("emailCode");
		if (sessionCode == null || !code.equals(sessionCode)) {
			map.put("reason", "校验码已过期");
		}
		if (code.equals(sessionCode)) {
			User user = (User) session.getAttribute("tempUser");
			if (user == null) {
				map.put("reason", "校验码已过期");
				return "error";
			}
			user.setState("4");
			if (selfService.updateUser(user)) {
				session.removeAttribute("tempUser");
				session.setAttribute("LOGGEDUSER", user);
				return "forward:../index";
			} else {
				session.removeAttribute("tempUser");
				map.put("reason", "系统错误");
			}
		}
		return "error";
	}

	@Resource
	CookieLocaleResolver resolver;

	/**
	 * 语言切换
	 */
	@RequestMapping(value = "/language/{language}", method = RequestMethod.GET)
	public ModelAndView language(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "language") String language) {
		log.info("开始切换语言");
		if (language == null || "".equals(language) || language.equalsIgnoreCase("zh_CN")) {
			resolver.setLocale(request, response, Locale.CHINA);
		} else if (language.equals("en_US")) {
			resolver.setLocale(request, response, Locale.ENGLISH);
		}
		return new ModelAndView("forward:/");
	}

}
