package testUtil;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;

import cn.bewweb.beans.Mail;
import cn.bewweb.beans.ProofEnum;
import cn.bewweb.entities.Book;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.Proof;
import cn.bewweb.entities.Shop;
import cn.bewweb.entities.User;
import cn.bewweb.service.FileSysService;
import cn.bewweb.service.MailService;
import cn.bewweb.service.SafeService;
import cn.bewweb.util.CommonTool;
import cn.bewweb.util.GenKey;
import cn.bewweb.util.PathTools;
import redis.clients.jedis.Jedis;

public class UtilTest {
	@Test
	public void testUserKey(){
		System.out.println(GenKey.genKey(User.class));
	}
	@Test
	public void testGoodsKey(){
		System.out.println(GenKey.genKey(Goods.class));
	}

	@Test
	public void testShopKey(){
		System.out.println(GenKey.genKey(Shop.class));
	}
	
	
	@Test
	public void testJRedis(){
		Jedis jedis = new Jedis("localhost",10001);
		jedis.set("nihao", "12");
		System.out.println(jedis.get("nihao"));
	}
	
	@Test
	public void testGetImgPath(){
		System.out.println(PathTools.getFaceImg("g:/fileDatas/books/computer/9787111532644",null));
		System.out.println(PathTools.getDownPath("g:/fileDatas/books/computer/9787111532644"));
	}
	
	
	
	public String[] attrName(User user) throws SecurityException, ClassNotFoundException {
		Field[] fields = Class.forName("cn.bewweb.entities.User").getDeclaredFields();
		for (Field field : fields) {
			System.err.println(field.getName());
		}
		return null;
	}
	
	@Test
	public void test1() throws SecurityException, ClassNotFoundException{
		User user =null;
		attrName(user);
	}

	 /*public static void main(String[] args){*/
	@Test
	public void testMS(){
		MailService ms = new MailService();
		ProofEnum proofEnum = new ProofEnum();
		proofEnum.setClientIp("196.168.1.1");
		proofEnum.setLocation("北京");
		//FF2:703:1CF7:D-2:0585:F-B:0BB4:0 1:17A9:5:B:5FC3:27:FE:34:7D:5A
		//FF::783:9CF?:D52:858=:F5B:8BB<:0(1::7A8:5BB:8FC::27:FE:34:7D:5A
		
	}
	
	
	//@TesttestDateSubDate
	public static void main(String[] args){
		System.out.println(CommonTool.dateSUbDate("2017-5-5 13:23:56", "2017-5-5 12:00:56", "m"));
	}
	
	@Test
	public void testCopyFile(){
		FileSysService fs = new FileSysService();
		/*if(fs.move("D:\\Documents\\temp1","C:\\Users\\Administrator\\Desktop\\temp2")){
			System.out.println("ok");
		}*/
		/*if(fs.copyFile("D:\\Documents\\毕业设计\\素材\\guilei\\care.png", "D:\\Documents\\毕业设计\\素材\\guilei","care1.png")){
			System.out.println("ok");
		}*/
		/*if(fs.cutFile("D:\\Documents\\毕业设计\\素材\\guilei\\care.png", "D:\\Documents\\毕业设计\\素材\\guilei","care2.png")){
			System.out.println("ok");
		}*/
		/*if(fs.renameFile("D:\\Documents\\毕业设计\\素材\\guilei\\care.png","care.png")){
			System.out.println("ok");
		}*/
		
		
		
	}
	
	@Test
	public void testNewName(){
		FileSysService fs = new FileSysService();
	}
	
	@Test
	public void testFind(){
		FileSysService fs = new FileSysService();
		Long t1 = System.currentTimeMillis();
		
	
		List<String> lists = fs.find("g:/fileDatas/books/computer/9787115281609", "pdf", "1", false, true, false, true);
		Long t2 = System.currentTimeMillis();
		
		String path = lists == null || lists.size() == 0 ? null : lists.get(0);
		System.err.println("finded path--"+path);
		
		System.out.println(lists);
		System.out.println(t2-t1);
	}
	
	@Test
	public void testSafe(){
		SafeService safe  = new SafeService();	
		/*String mi = safe.encrypt("孟盛能msn的test", "12345678");
		//mi =safe.encrypt("12345678910", "12345678");
		System.out.println("mi-"+mi);
		System.out.println(safe.decrypt(mi, "12345678"));
		System.out.println("---------------");
		*/
		
		/*for(int i=0 ;i<1 ;i++){
			System.out.println(safe.getRandomCode(2, 100));
		}
		byte[] rs = safe.getDigestByte("孟盛能", "md5");
		for (byte b : rs) {
			System.err.print(b+" ");
		}
		
		System.out.println();
		
		
		
		int [] nums = safe.randomNUM(10,0,188);
		for (int i : nums) {
			System.out.print(i+" ");
		}
		
		String rssss = safe.getDigest("孟盛能", "md5","");
		byte[] rs2 = safe.hexStringToBytes(rssss);
		for (byte b : rs2) {
			System.err.print(b+" ");
		}
		*/
		/*System.out.println("-------转换测试--------");
		byte[] bytes = {44, 56, -123, 81, -41, -12, -119, -20};
		System.out.println(safe.bytesToHexString(bytes, ""));
		bytes = safe.hexStringToBytes("2C388551D7F489EC");
		for (byte b : bytes) {
			System.out.print(b+" ");
		}
		System.out.println();
		*/
		
	}
	
	
	
	@Test
	public void pwdCheck() {
		SafeService safe = new SafeService();
		String rs = safe.xor("123456", "abcdefgh");
		byte[] bytes = rs.getBytes();
		String hex = safe.bytesToHexString(bytes);
		System.out.println(hex);
		
		
	}
	
	

}
