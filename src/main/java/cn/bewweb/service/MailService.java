package cn.bewweb.service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import cn.bewweb.beans.Mail;
import cn.bewweb.beans.ProofEnum;
import cn.bewweb.entities.Proof;
import cn.bewweb.util.CommonTool;


@Service
public class MailService implements MailService_I {
	@Autowired
	private Environment env;
	@Autowired
	private SafeService_I safe;
	
	private static final Logger log = LoggerFactory.getLogger(MailService.class);
	
	public String creatCode(String recipient, ProofEnum proofEnum){
		String method = env.getProperty("mail.codeMethod");
		method =( null==method||"".equals(method))?"MD5":method.trim();
		try {
		if(null==recipient||"".equals(recipient)||null==proofEnum||null==proofEnum.getClientIp()||"".equals(proofEnum.getClientIp()))
			throw new NullPointerException();
		} catch(NullPointerException e){
			log.error("缺少重要参数（空指针异常）");
		}
		Date date = new Date();
		log.info("注入的时间："+CommonTool.dateToString(date, true));
		StringBuilder sbd = new StringBuilder();
		sbd.append(recipient+proofEnum.getClientIp());
		sbd.append(proofEnum.getLocation());
		sbd.append(safe.getRandomCode(3, 10));
		String code = sbd.toString();
		sbd.delete(0, sbd.length());
		sbd = null;
		code = safe.getDigest(code, method);
		code= injectDate(code,date);
		return code;
	}
	public String getEmailTemplate(String subject,String code){
		if(null==subject){
			log.error("没有选择主题");
			return null;
		}
		if(null==code){
			log.error("没有生成验证码");
			return null;
		}
		String content = null;
		if("必易为电子商城注册验证".equals(subject)){
			content="尊敬的用户：<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "您好！<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "欢迎开启必易为电子商务新生活,您此刻仅差一步之遥，请在5分钟内<a href='"
					+ env.getProperty("serverUrl")+"mail/verify?code="+code
					+ "'>点击验证邮箱</a><br/>"
					+ "若无反应请复制以下链接到浏览器地址栏<br/>"
					+ env.getProperty("serverUrl")+"mail/verify?code="+code
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "<br/>若您没有注册过必易为商城，请忽略此邮件。<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必易为团队小组敬上。系统邮件请勿回复！";
		}
		else if("必易为：本操作重新验证你的邮箱".equals(subject)){
			content="尊敬的用户：<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "您好！<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "您的账号存在安全风险，为此我们给你绑定的邮箱发送了验证码：<br/>"
					+code
					+ "<br/>请在5分钟内复制到验证处完成验证。<br/>"
					+ "若您没有注册过必易为商城，请忽略此邮件。<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必易为团队小组敬上。系统邮件请勿回复！";
		}else if("必易为：绑定新邮箱验证".equals(subject)){
			content="尊敬的用户：<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "您好！<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+ "您正在进行绑定邮箱操作，为此我们给你绑定的邮箱发送了验证码：<br/>"
					+code
					+ "<br/>请在5分钟内复制到验证处完成验证。<br/>"
					+ "若您没有注册过必易为商城，请忽略此邮件。<br/>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;必易为团队小组敬上。系统邮件请勿回复！";
		}
		return content;
	}
	
	public Mail createEmail(String recipient,String subject, String code,String content) {
			Mail mail = new Mail();
			mail.setFrom(env.getProperty("mail.sendAccount"));
			mail.setFromPwd(env.getProperty("mail.sendPassword"));
			mail.setProtocol(env.getProperty("mail.transport.protocol"));
			mail.setHost(env.getProperty("mail.smtp.host"));
			mail.setPort(env.getProperty("mail.smtp.port"));
			mail.setAuth(env.getProperty("mail.smtp.auth"));
			
			mail.setCode(code);
			mail.setRecipient(recipient);
			mail.setSubject(subject);
			mail.setContent(content);
			return mail;
	}

	@Override
	public boolean sendEmail(Mail mail) {
		Properties properties = new Properties();
		properties.put("mail.transport.protocol",mail.getProtocol());
		properties.put("mail.smtp.host",mail.getHost() );
		properties.put("mail.smtp.port", mail.getPort());
		properties.put("mail.smtp.auth",mail.getAuth() );
		Session session = Session.getDefaultInstance(properties, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mail.getFrom(), mail.getFromPwd());
			}
		});
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipient(RecipientType.TO, new InternetAddress(mail.getRecipient()));
			message.setSentDate(new Date());
			message.setSubject(mail.getSubject());
			message.setContent(mail.getContent(), "text/html;charset=utf-8");
			Transport.send(message);
			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	@Override
	public String injectDate(String code,Date date){
		date = null == date? new Date() :date;
		StringBuffer sbuffer = new StringBuffer();
		char[] codes = code.toCharArray();
		char[] chars = CommonTool.dateToString(date, true).toCharArray();
		for(int i=0,j=0;i<codes.length;i++){
			if(i<32&&(i+1)%2==0){
				sbuffer.append(codes[i]);
				sbuffer.append((char)(chars[j]+20));
				j++;
			}else{
				sbuffer.append(codes[i]);
			}
		}
		return sbuffer.toString();
	}
	
	
	@Override
	public Date decodeDate(String code){
		StringBuffer sbuffer = new StringBuffer();
		char[] codes = code.toCharArray();
		for(int i=0,j=1;i<codes.length;i++){
			if(i<32&&(i+1)%2==0){
				sbuffer.append((char)(codes[i+j]-20));
				j++;
			}
		}
		sbuffer.append(":00");
		return CommonTool.StringToDate(sbuffer.toString());
	}


}
