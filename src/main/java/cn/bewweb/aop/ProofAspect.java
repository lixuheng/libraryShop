package cn.bewweb.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ProofAspect {

	//Json cn.bewweb.controller.WelCome.doLogin(String account, String password, Integer pwdErrorNum, String authCode, String local, HttpServletRequest request)
	@Pointcut("execution(** cn.bewweb.controller.WelCome.doLogin(..))")
	public void loginPotintcut() {
	}
	
	@Before("loginPotintcut()")
	public String checkTrade(JoinPoint jp){
		System.out.println("JoinPoint"+jp);
		Object[] args  = jp.getArgs();
		for (Object object : args) {
			System.err.println(object.toString());
		}
		return null;
	}

	
	
}
