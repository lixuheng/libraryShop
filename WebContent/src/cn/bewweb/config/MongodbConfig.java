package cn.bewweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/*@Configuration
@EnableMongoRepositories(basePackages = "cn.bewweb.mongo.db")*/
public class MongodbConfig extends AbstractMongoConfiguration{

	@Override
	protected String getDatabaseName() {
		return "libraryShop";
	}

	@Override
	public Mongo mongo() throws Exception {
		return new MongoClient("localhost",27017);
	}
	
	
/*	public MongoClientOptions getOptions() {
		return new MongoClientOptions.Builder()
				.socketKeepAlive(true) // 是否保持长链接
				.connectTimeout(1500) // 链接超时时间
				.socketTimeout(5000) // read数据超时时间
				.readPreference(ReadPreference.primary()) // 最近优先策略
				.autoConnectRetry(false) // 是否重试机制
				.connectionsPerHost(30) // 每个地址最大请求数
				.maxWaitTime(1000 * 60 * 2) // 长链接的最大等待时间
				.threadsAllowedToBlockForConnectionMultiplier(50) // 一个socket最大的等待请求数
				.writeConcern(WriteConcern.NORMAL).build();
	}
	
	@Bean
	public MongoClient mongoClient(){
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient(new ServerAddress("localhost",27017), getOptions());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return mongoClient;
	}
	
	@Bean
	public MongoOperations mongoTempLate(Mongo mongo){
		return new MongoTemplate(mongo,"db");
	}*/
}
