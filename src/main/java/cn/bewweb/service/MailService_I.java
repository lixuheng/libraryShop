package cn.bewweb.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import cn.bewweb.beans.Mail;
import cn.bewweb.beans.ProofEnum;

public interface MailService_I {
	/**
	 * 顺序1获取验证码
	 * @param recipient
	 * @param proofEnum
	 * @return
	 */
	String creatCode(String recipient, ProofEnum proofEnum);
	/**
	 * 顺序2生成邮箱模板
	 * @param subject 1必易为电子商城注册验证  2 必易为：本操作重新验证你的邮箱 3必易为：绑定新邮箱验证
	 * @param code
	 * @return 邮件模板
	 */
	String getEmailTemplate(String subject,String code);
	/**
	 * 顺序3创建邮件
	 * @param recipient
	 * @param subject
	 * @param code
	 * @param content
	 * @return 邮件对象
	 */
	Mail createEmail(String recipient,String subject, String code,String content);
	/**
	 * 顺序4发送邮件
	 * @param mail 邮件参数
	 * return 成功与否
	 */
	boolean sendEmail(Mail mail);
	/**
	 * 在摘要串中注入日期
	 * @param code
	 * @return
	 */
	String injectDate(String code,Date date);
	/**
	 * 解析摘要串中的日期
	 * @param code 摘要串
	 * @return 日期
	 */
	Date decodeDate(String code);
	

}
