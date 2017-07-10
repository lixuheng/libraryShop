package cn.bewweb.aop;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.bewweb.entities.User;

@Aspect
public class SafeAspect {
	private HttpServletRequest request;
	private HttpSession session;

	private static final Logger log = LoggerFactory.getLogger(SafeAspect.class);

	///libraryShop/src/main/java/cn/bewweb/controller/TradeHandler.java
	@Pointcut("execution(** cn.bewweb.controller.TradeHandler.*(..))")
	public void tradeHanlerCheck() {}
	
	@Around("tradeHanlerCheck()")
	public String checkTrade(ProceedingJoinPoint jp){
		Object[] args = jp.getArgs(); // 获取被切函数 参数
		for (Object object : args) {
			if(object instanceof HttpServletRequest){
				request = (HttpServletRequest)object;
				session = request.getSession();
				if(session.getAttribute("loginUser")==null){
					return null;
				}
			}
			System.err.println("object-"+object);
		}
		try {
			Object returnvalue = jp.proceed();
			System.err.println("returnvalue-"+returnvalue);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/*
	@Before("tradeHanlerCheck()")
	public void check(JoinPoint call) {
		//获取正在执行的连接点方法所属的类
		String clazz = call.getTarget().getClass().getName();
		//获取正在执行的方法名
		String methodName = call.getSignature().getName();
		log.info("前置通知:" + clazz + "类的" + methodName + "方法开始了...");
		//获取应用中的request
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//获取应用中的session
		HttpSession session = request.getSession();
		//尝试获取登录的的用户
		User loginUser = (User) session.getAttribute("loginUser");
		System.out.println("loginUser:"+ loginUser);
		//执行是否登录的检查
		
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		System.out.println("response"+response);

		if(null==loginUser){
			try {
				if(null!=response)
					response.sendRedirect("/login");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	*/
	
}
