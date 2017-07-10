package testContext;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.bewweb.service.SessionListener;

@ContextConfiguration(locations={"classpath:rootContext.xml"}) //加载配置 //加载配置
@RunWith(SpringJUnit4ClassRunner.class)
public class TestContext {
	
	private Object getBean(ServletContext servletContext,String beanName){  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        return application.getBean(beanName);
    } 
	
	private Object getWebBean(ServletContext servletContext,String beanName){  
        WebApplicationContext webApp=WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);  
        return webApp.getBean(beanName);
    } 
	
	private Object getBean(ServletContext servletContext,Class clazz){  
        ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(servletContext);  
        return application.getBean(clazz);
    }  
	
	@Test
	public void testWebApplication(){
	}
}
