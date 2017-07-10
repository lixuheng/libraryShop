package testMybatis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.bewweb.dao.BookMapper;
import cn.bewweb.dao.GoodsMapper;
import cn.bewweb.dao.OrderMapper;
import cn.bewweb.dao.ProofMapper;
import cn.bewweb.dao.ShareMapper;
import cn.bewweb.dao.ShopMapper;
import cn.bewweb.dao.UserMapper;
import cn.bewweb.entities.BookWithBLOBs;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.Share;
import cn.bewweb.entities.Shop;
import cn.bewweb.entities.User;
import cn.bewweb.service.UserService_I;



@RunWith(SpringJUnit4ClassRunner.class)
//"classpath:springmvcContext.xml"
@ContextConfiguration(locations={"classpath:rootContext.xml"}) //加载配置
public class MybatisTest {
	@Autowired
	private UserMapper userMapper;
	@Test
	public void testUser() {
		System.out.println(userMapper.selectByPrimaryKey(7975392919020510l));
		User user = new User();
		user.setUserId(7975392919020510l);
		List<User> ulist = userMapper.findAndOrderBy(user, "'user_id' desc", 0, 1);
		for (User user2 : ulist) {
			System.err.println(user2);
		}
		
	}
	
	@Autowired
	private BookMapper bookMapper;
	@Test
	public void testBook(){
		//System.out.println(bookMapper.selectByPrimaryKey("9787111532644"));
		//List<BookWithBLOBs> books = bookMapper.selectBooksByPage(0,1);
		BookWithBLOBs book = new BookWithBLOBs();
		//book.setIsbn("9787111532644");
		List<BookWithBLOBs> books = bookMapper.find(book,0,10);
		for (BookWithBLOBs bookWithBLOBs : books) {
			System.err.println(bookWithBLOBs);
		}
	}
	

	/*
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private IndexService_I indexService;
	@Test
	public void testGoods(){
		List<Goods> goodsList =indexService.listGoodsByPage(1,1);
		
		for (Goods goods : goodsList) {
			System.out.println(goods);
		}
	}
	*/
	
	
	@Autowired
	private ShareMapper shareMapper;
	@Test
	public void testShare(){
		Share share = new Share();
		share.setShareId(323423432l);
		shareMapper.find(share,0, 10);
		for (Share share1 : shareMapper.find(share,0, 10)) {
			System.err.println(share1);
		}
		shareMapper.selectByContent("%eeiw%", 0, 1);
		shareMapper.selectBySubject("%fds%", 0, 1);
		shareMapper.selectByTitle("%fds%", 0, 1);
		shareMapper.selectByMind("%fds%", 0, 1);
		shareMapper.selectOrderBy("'share_date' desc", 0, 1);
		String [] strs = {"dfsfd","fdf","44"};
		shareMapper.selectByKeyWords(strs, 0, 1);
		for (Share share1 : shareMapper.selectByKeyWords(strs, 0, 1)) {
			System.err.println(share1);
		}
		
	}
	
	@Autowired
	ShopMapper shopMapper;
	@Test 
	public void  testShop(){
		Shop shopr = new Shop();
		shopr.setShopId(10000000000000000l);
		shopr.setBusinessScope("图书");
		List<Shop> list = shopMapper.find(shopr,0, 1);
		for (Shop shop : list) {
			System.err.println(shop);
		}
	}
	
	@Autowired
	OrderMapper orderMapper;
	@Test
	public void testOrder(){
		System.err.println(orderMapper.selectByPrimaryKeyWithAddrs(145294581441153l));
	}
	

	@Autowired
	private ProofMapper pm;
	@Test
	public void testProofSave(){
		//pm.up
	}
	
	

}
