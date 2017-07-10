	package testMongo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import cn.bewweb.docments.Category;
import cn.bewweb.docments.FirstItem;
import cn.bewweb.docments.SecondItem;
import cn.bewweb.mongo.CategoryDao;

@ContextConfiguration(locations={"classpath:rootContext.xml"}) //加载配置 //加载配置
@RunWith(SpringJUnit4ClassRunner.class)
public class MongoTest {
	
	/*
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired MongoOperations mongoOps;
	
	@Test
	public void testMongoRepository() {
		assertEquals(0, categoryRepository.count());
		Category Category = createAnCategory();
		
		// Saving an Category
		Category savedCategory = categoryRepository.save(Category);		
		assertEquals(1, categoryRepository.count());
		
		// Finding an Category by ID
		Category foundCategory = categoryRepository.findOne(savedCategory.getId());
		assertEquals("Chuck Wagon", foundCategory.getCustomer());
		assertEquals(2, foundCategory.getItems().size());

		// Finding an Category by a single field value
		List<Category> chucksCategorys = categoryRepository.findByCustomer("Chuck Wagon");
		assertEquals(1, chucksCategorys.size());
		assertEquals("Chuck Wagon", chucksCategorys.get(0).getCustomer());
		assertEquals(2, chucksCategorys.get(0).getItems().size());

		
		
		// Deleting an Category
		categoryRepository.delete(savedCategory.getId());
		assertEquals(0, categoryRepository.count());

	}

	private Category createAnCategory() {
		Category category = new Category();
		category.setName("文学");
		category.setId("001");
		Collection<FirstItem> fItems = new LinkedHashSet<FirstItem>();
		FirstItem fitem = new FirstItem();
		fitem.setName("小说");
		category.setFitems(fItems);
		return category;
	}
	*/
	@Test
	public void testMongodb() {
		try {
			// 连接到 mongodb 服务
			Mongo mongo = new MongoClient("127.0.0.1", 27017);
			// 根据mongodb数据库的名称获取mongodb对象 ,
			DB db = mongo.getDB("msnTest");
			Set<String> collectionNames = db.getCollectionNames();
			// 打印出test中的集合
			for (String name : collectionNames) {
				System.err.println("collectionName===" + name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private  String collectionName=null;
	
	@Resource
	private  CategoryDao categoryDao;
	@Test 
	public void testAdd()
	{
	
		collectionName="testCategory";
		
		Category category = new Category();
		category.setId("10000000000");
		category.setName("计算机");
		List<FirstItem> flist = new ArrayList<FirstItem>();
		List<SecondItem> slist1 = new ArrayList<SecondItem>();
		List<SecondItem> slist2 = new ArrayList<SecondItem>();
		FirstItem f1 = new FirstItem("算法");
		FirstItem f2 = new FirstItem("编程语言");
		flist.add(f1);
		flist.add(f2);
		SecondItem s1 = new SecondItem("算法导论");
		SecondItem s2 = new SecondItem("数据结构");
		SecondItem s3 = new SecondItem("java");
		SecondItem s4 = new SecondItem("c");
		slist1.add(s1);
		slist1.add(s2);
		slist2.add(s3);
		slist2.add(s4);
		
		
		f1.setSitems(slist1);
		f2.setSitems(slist2);
		
		
		category.setFitems(flist);
		
		categoryDao.insert(category, collectionName);

	}
	
	
	@Test
	public void testRead(){
		
		
		collectionName="category";
		/*List<Category> clist = categoryDao.findAll(collectionName);
		for (Category category : clist) {
			System.out.println(category);
		}*/
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", "58fd6846e25cbb07543e57d2");
		System.out.println(categoryDao.findOne(params, collectionName));
	}
	
	

}
