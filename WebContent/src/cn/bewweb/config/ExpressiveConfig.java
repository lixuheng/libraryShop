package cn.bewweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/alisms.properties","classpath:/project.properties"})
public class ExpressiveConfig {
	
}
