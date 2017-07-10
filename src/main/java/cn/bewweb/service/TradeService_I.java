package cn.bewweb.service;

import java.math.BigDecimal;
import java.util.List;

import cn.bewweb.beans.CartForm;
import cn.bewweb.beans.Settlement;
import cn.bewweb.docments.Category;
import cn.bewweb.entities.Address;
import cn.bewweb.entities.BookWithBLOBs;
import cn.bewweb.entities.Borrow;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.MyCare;
import cn.bewweb.entities.Order;
import cn.bewweb.entities.OrderItem;
import cn.bewweb.entities.Shop;
import cn.bewweb.entities.User;

/**
 * 交易处理服务
 * 
 * @author 孟盛能 2017-5-5
 *
 */
public interface TradeService_I {

	
	
/***********************goods******************/
	
	
	List<Goods> categoryGoods(String label,Integer page,Integer size);
	
	List<Goods> searchGoods(String [] keyWords,Integer page,Integer size);
	
	
	List<Category> getAllCategory();
	Goods getOneGoods(Long goodsId);
	/**
	 * 商品详情
	 * @param goodsId
	 * @return
	 */
	Goods goodsDetail(Long goodsId);

	/**
	 * 书籍详情
	 * @param isbn
	 * @return
	 */
	BookWithBLOBs getOneBookByPrimaryKey(Long isbn);

	/**
	 * 店铺详情
	 * @param id
	 * @return
	 */
	Shop getOneShop(Long id);


	/**
	 * 加入购物车
	 * 
	 * @param goods
	 *            商品
	 * @param quantity
	 *            数量
	 * @param shop
	 *            所属商店
	 * @return 是否成功
	 */
	Boolean joinShoppingCar(OrderItem orderItem, User user);

	/**
	 * 加入我的关注
	 * 
	 * @param goods
	 * @return
	 */
	Boolean joinCare(OrderItem orderItem, User user);
	
	/**
	 * 查看某个用户的关注
	 * @param user
	 * @return
	 */
	List<MyCare> viewCare(User user,Integer pageno, Integer size);

	/**
	 * 生成订单
	 * 
	 * @param list
	 *            订单项列表
	 * @return 新的订单号，null表示生成失败
	 */
	Long createOrder(List<OrderItem> list, User user);
	
	
	Integer countItemOfOneOrder(Long orderId);
	/**
	 * 按主键查询
	 * @param orderId
	 * @return
	 */
	Order selectOrder(Long orderId);

	/**
	 * 添加收货信息到订单中
	 * 
	 * @param order
	 * @return
	 */
	Boolean attachInfoToOrder(Order order);
	
	
	/**
	 * 查询商品
	 * @param PageNo
	 * @param size
	 * @return
	 */
	List<Goods> listGoodsByPage(Integer PageNo,Integer size);

	/**
	 * 查看结算页信息
	 * 
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	Settlement viewOneSettlement(User user, Order order, Integer page, Integer size);
	
	
	/**
	 * 没有提交的订单
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	List<Settlement> viewNoCommitOrder(User user, Integer page, Integer size);
	/**
	 * 查看某个用户没有支付的订单
	 * 
	 * @param user
	 * @return 一个订单
	 */
	List<Order> viewNoPayOrder(User user, Integer page, Integer size);

	/**
	 * 查看某个用户正在进行的订单
	 * 
	 * @param user
	 * @return
	 */
	List<Order> viewNoOkOrder(User user, Integer page, Integer size);
	
	/**
	 * 查看某个用户取消的无效订单
	 * 
	 * @param user
	 * @return
	 */
	List<Order> viewFailedOrder(User user, Integer page, Integer size);

	/**
	 * 查看某个用户已完成的订单
	 * 
	 * @param user
	 * @return
	 */
	List<Order> viewOkOrder(User user, Integer page, Integer size);

	/**
	 * 查看某个用户所有订单
	 * 
	 * @param user
	 * @return
	 */
	List<Order> viewAllOrder(User user, Integer page, Integer size);

	/**
	 * 更新支付操作
	 * 
	 * @param order
	 *            要支付的订单
	 * @return
	 */
	Boolean doPay(Order order);

	/**
	 * 确认收货操作
	 * 
	 * @param order
	 *            要确认的订单
	 * @return
	 */
	Boolean doOk(Order order);
	
	
/********************************book*****************/
	/**
	 * 借书操作
	 * 
	 * @param goos
	 * @param user
	 * @return
	 */
	String doBorrow(Goods goods, User user);
	
	/**
	 * 确认借阅
	 * @param borrow
	 * @param goods
	 * @return
	 */
	boolean okBorrow(Borrow borrow);
	/**
	 * 查看当前借阅
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	List<Borrow> viewHoldBorrow(User user, Integer page, Integer size);
	/**
	 * 查看过期借阅
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	List<Borrow> viewExpiredBorrow(User user, Integer page, Integer size);
	/**
	 * 查看历史借阅
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	List<Borrow> viewHisBorrow(User user, Integer page, Integer size);
	/**
	 * 还书操作
	 * 
	 * @param borrow
	 * @param user
	 * @return
	 */
	String returnGoods(Borrow borrow);
	

	/**
	 * 查看购物车
	 * 
	 * @param user
	 * @param page
	 * @param size
	 * @return
	 */
	List<CartForm> viewCart(User user, Integer page, Integer size);
	/**
	 * 删除购物车中选中（更新状态到-1）
	 * @param orderItemId
	 * @return
	 */
	boolean removeCartItemToTash(OrderItem orderItem);
	
	boolean deletOneUserCartTash(User user);
	
	boolean removeOrderToTash(Order order);
	
	boolean deleteOneUserOrderTash(User user);
	
	boolean cancelOneOrder(Order order);
	
	
	
	//////////////////地址/////////////////////

	/**
	 * 获取用户收货地址
	 * 
	 * @param user
	 * @return
	 */
	List<Address> getUserAllAddress(User user);

	boolean deleteAddr(Long addrId);
	
	boolean updataAddr(Address address);
	
	boolean addAddr(Address address);
	
	boolean updateGoodsNum(Long orderId);
	
}
