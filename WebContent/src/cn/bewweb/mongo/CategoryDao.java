package cn.bewweb.mongo;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import cn.bewweb.docments.Category;


@Repository("CategoryDao")
public class CategoryDao implements CategoryDao_I {
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public void insert(Category object, String collectionName) {
		mongoTemplate.insert(object,collectionName);
	}

	@Override
	public Category findOne(Map<String, Object> params, String collectionName) {
		Query query = new Query(Criteria.where("id").is(params.get("id")));
		return mongoTemplate.findOne(query, Category.class,collectionName);
	}

	@Override
	public List<Category> find(Map<String, Object> params, String collectionName) {
		Query query = new Query(Criteria.where("name").is(params.get("name")));
		return mongoTemplate.find(query, Category.class,collectionName);
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {
		Query query = new Query(Criteria.where("id").is(params.get("id")));
		Update update = new Update();
		update.set("name", params.get("name"));
		mongoTemplate.upsert(query, update, Category.class);
	}

	@Override
	public void createCollection(String collectionName) {
		mongoTemplate.createCollection(collectionName);

	}

	@Override
	public void remove(Map<String, Object> params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),Category.class,collectionName);
	}

	@Override
	public List<Category> findAll(String collectionName) {
		return mongoTemplate.findAll(Category.class,collectionName);
	}

}
