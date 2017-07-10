package cn.bewweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.service.UserService_I;

@Controller
@RequestMapping(value = "/sms")
public class SmsVerCode {
	private static final Logger log = LoggerFactory.getLogger(WelCome.class);
	private static final int codeLength = 4;

	@Resource
	Environment evn;
	
	@Resource
	private UserService_I userService;
	
	
	/**
	 * 生成验证码
	 * 
	 * @param phone
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public @ResponseBody Json getVerSmsCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String phone = (String) session.getAttribute("phone");
		if (null == phone || "".equals(phone)) {
			log.warn("未能从session中获取可靠的手机号");
			return (new Json()).setCode(Code.lostParam);
		}
		String url = evn.getProperty("sms_url");
		String AppKey = evn.getProperty("sms_AppKey");
		String secret = evn.getProperty("sms_secret");
		StringBuffer sb = new StringBuffer();
		String vercode = null;
		sb.delete(0, sb.length());
		// 生成随机数
		for (int i = 0; i < codeLength; i++) {
			sb.append((char) (Math.random() * 10 + 48));
		}
		vercode = sb.toString();
		session.setAttribute("vercode", vercode);
		session.setAttribute("phone", phone);
		log.info("phone is" + session.getAttribute("phone") + " vercode is" + session.getAttribute("vercode")
				+ "  ipAddr" + request.getRemoteAddr());
		/* //调用淘宝接口 */
		TaobaoClient client = new DefaultTaobaoClient(url, AppKey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(evn.getProperty("sms_AppName"));
		req.setSmsParamString("{vercoder:'" + vercode + "'}");
		req.setRecNum(phone);
		req.setSmsTemplateCode(evn.getProperty("sms_TemplateCode"));
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			log.error("ApiExcepiton。短信接口客户端获取失败。");
			List<String> list = new ArrayList<String>();
			list.add("短信接口客户端获取失败");
			return (new Json()).setCode(Code.c0).setList(list);
			
		}
		// 打印接口调用返回信息
		log.info("短信接口调用结果：" + rsp.getBody());

		if (rsp == null || rsp.getBody() == null ) {
			List<String> list = new ArrayList<String>();
			list.add("短信接口客户端获取失败");
			return (new Json()).setCode(Code.c0).setList(list);
		}
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				sb.delete(0, sb.length());
				for (int i = 0; i < 4; i++) {
					sb.append((char) (Math.random() * 10 + 48));
				}
				try{
					session.setAttribute("vercode", sb.toString());
				}catch(IllegalStateException e){
					this.cancel();
				}finally{
					log.warn("短信验证码到期失效一个");
				}
			}
		}, 1800000);
		return (new Json()).setCode(Code.c1);
	}

	@RequestMapping(value = "/checkCode", method = RequestMethod.POST)
	public @ResponseBody Json checkCode(String code, HttpServletRequest request) {
		if (null == code || "".equals(code)) {
			return (new Json()).setCode(Code.lostParam);
		}
		HttpSession session = request.getSession();
		String vercode = (String) session.getAttribute("vercode");
		if(vercode==null){
			return (new Json()).setCode(Code.c0);
		}
		log.info("vercode:"+vercode+"  code:"+code);
		if (vercode.equals(code)) {
			session.setAttribute("smsValid", true);
			return (new Json()).setCode(Code.c1);
		} else {
			session.setAttribute("smsValid", false);
			return (new Json()).setCode(Code.c0);
		}
	}
}
