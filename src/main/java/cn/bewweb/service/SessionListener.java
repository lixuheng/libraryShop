package cn.bewweb.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.bewweb.entities.Proof;
import cn.bewweb.entities.User;
import cn.bewweb.redis.ProofDao;
import cn.bewweb.redis.ProofDao_I;
import redis.clients.jedis.Jedis;

public class SessionListener implements HttpSessionListener {
	private static Logger log = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSessionListener.super.sessionCreated(se);
		log.info("sesssionCreated" + se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSessionListener.super.sessionDestroyed(se);
		HttpSession session = se.getSession();
		log.info("sessionDestroyed" + session.getId());
		JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) getBean(session.getServletContext(),
				"jedisConnFactory");
		JedisConnection jedisConnection = jedisConnectionFactory.getConnection();
		Jedis jedis = jedisConnection.getNativeConnection();
		ProofDao_I proofRDDao = null;
		try{
			proofRDDao = (ProofDao_I) getWebBean(session.getServletContext(),"proofRDDao");
		}catch(Exception e){
			try{
				proofRDDao = (ProofDao_I) getWebBean(session.getServletContext(),ProofDao_I.class);
			}catch(Exception e1){
				try{
					proofRDDao = (ProofDao) getWebBean(session.getServletContext(),ProofDao.class);
				}catch(Exception e2){
					try{
						proofRDDao = (ProofDao) getWebBean(session.getServletContext(),"proofRDDao");
					}catch(Exception e3){
						try{
							proofRDDao = (ProofDao) getBean(session.getServletContext(),"proofRDDao");
						}catch(Exception e4){
							log.info("失败");
						}
					}
				}
			}
		}
		
		ControlService_I control = null;
		try{
			control = (ControlService_I) getBean(session.getServletContext(),ControlService_I.class);
		}catch(Exception e){
			try{
				control = (ControlService) getWebBean(session.getServletContext(),ControlService.class);
			}catch(Exception e1){
				try{
					control = (ControlService) getBean(session.getServletContext(),"controlService");
				}catch(Exception e2){
					try{
						control = (ControlService) getBean(session.getServletContext(),ControlService.class);
					}catch(Exception e3){
						try{
							control = (ControlService) getWebBean(session.getServletContext(),"controlService");
						}catch(Exception e4){
							log.info("失败");
						}
					}
				}
			}
		}
		
		
		//保存用户行为数据到MySQL
		User user = (User) session.getAttribute("LOGGEDUSER");
		Long tempUserId = (Long) session.getAttribute("tempUserId");
		
		if(user!=null && control!=null){
			List<Proof> proofList =control.getRedisProofs(user.getUserId());
			if(!control.batchToSaveProofs(user.getUserId(), session.getId(), proofList)){
				log.error("保存到数据库失败");
			}
		}
		
		//清理工作
		if (user != null && jedis != null) {
			jedis.del("" + user.getUserId());
		}
		if (tempUserId != null && jedis != null) {
			jedis.del("" + tempUserId);
		}
	}

	/**
	 * 通过WebApplicationContextUtils 得到Spring容器的实例。根据bean的名称返回bean的实例。
	 * 
	 * @param servletContext
	 *            ：ServletContext上下文。
	 * @param beanName
	 *            :要取得的Spring容器中Bean的名称。
	 * @return 返回Bean的实例。
	 */
	private Object getBean(ServletContext servletContext, String beanName) {
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return application.getBean(beanName);
	}

	private Object getBean(ServletContext servletContext, Class clazz) {
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return application.getBean(clazz);
	}

	private Object getWebBean(ServletContext servletContext, String beanName) {
		WebApplicationContext webApp = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return webApp.getBean(beanName);
	}
	private Object getWebBean(ServletContext servletContext, Class clazz) {
		WebApplicationContext webApp = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		return webApp.getBean(clazz);
	}

}
