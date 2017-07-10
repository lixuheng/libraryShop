package cn.bewweb.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bewweb.beans.CartForm;
import cn.bewweb.beans.Settlement;
import cn.bewweb.dao.AddressMapper;
import cn.bewweb.dao.BookMapper;
import cn.bewweb.dao.BorrowMapper;
import cn.bewweb.dao.CommentMapper;
import cn.bewweb.dao.GoodsMapper;
import cn.bewweb.dao.MyCareMapper;
import cn.bewweb.dao.OrderItemMapper;
import cn.bewweb.dao.OrderMapper;
import cn.bewweb.dao.ShopMapper;
import cn.bewweb.dao.UserMapper;
import cn.bewweb.docments.Category;
import cn.bewweb.entities.Address;
import cn.bewweb.entities.BookWithBLOBs;
import cn.bewweb.entities.Borrow;
import cn.bewweb.entities.Comment;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.MyCare;
import cn.bewweb.entities.Order;
import cn.bewweb.entities.OrderItem;
import cn.bewweb.entities.Shop;
import cn.bewweb.entities.User;
import cn.bewweb.mongo.CategoryDao;
import cn.bewweb.util.CommonTool;
import cn.bewweb.util.GenKey;

@Transactional
@Service
public class TradeService implements TradeService_I {
	private static final Logger log = LoggerFactory.getLogger(TradeService.class);

	@Autowired
	private OrderItemMapper orderItemDao;
	@Autowired
	private OrderMapper orderDao;
	@Autowired
	private UserMapper userDao;
	@Autowired
	private MyCareMapper myCareDao;
	@Autowired
	private BorrowMapper borrowDao;
	@Autowired
	private GoodsMapper goodsDao;
	@Autowired
	private AddressMapper addressDao;
	@Autowired
	private CommentMapper commentDao;
	@Resource
	private CategoryDao categoryDao;

	private final Integer maxDeliveryTime = 8 * 24 * 60 * 60 * 1000;// 8day

	
	@Override
	public Goods getOneGoods(Long goodsId) {
		return goodsDao.selectByPrimaryKey(goodsId);
	}
	
	@Override
	public Goods goodsDetail(Long goodsId) {
		return goodsDao.selectByPrimaryKeyWithBook(goodsId);
	}

	@Autowired
	BookMapper bookMapper;

	@Override
	public BookWithBLOBs getOneBookByPrimaryKey(Long id) {
		return bookMapper.selectByPrimaryKey(id);
	}

	@Autowired
	ShopMapper shopMapper;

	@Override
	public Shop getOneShop(Long id) {
		Goods goods = goodsDao.selectByPrimaryKey(id);
		return shopMapper.selectByPrimaryKey(goods.getShopId());
	}

	@Override
	public List<Goods> listGoodsByPage(Integer pageNo, Integer size) {
		pageNo = pageNo - 1 >= 0 ? --pageNo : 0;
		return goodsDao.selectGoodsByPage(pageNo, size);
	}

	@Override
	public Boolean joinShoppingCar(OrderItem oitem, User user) {
		if (null == user || null == user.getUserId()) {
			log.error("非法用户！");
			return false;
		}
		// 传入的oitem仅数量count和goodsid 可靠
		// 查询库中价格
		Goods goods = goodsDao.findByPrimaryKey(oitem.getGoodsId());
		oitem.setOnePrice(goods.getPrice());
		// 约定成购物车使用的订单号
		long carOrderId = 1000000000000000L;// 16位
		oitem.setOrderId(carOrderId);
		oitem.setState("0");// 处于购物车状态
		oitem.setUserId(user.getUserId());

		// 如果购物车中已经有加入，更新
		OrderItem orderItem = null;
		if (orderItemDao.isHadGoods(oitem.getGoodsId(), oitem.getShopId()) > 0) {
			OrderItem model = new OrderItem();
			model.setGoodsId(oitem.getGoodsId());
			model.setShopId(oitem.getShopId());
			List<OrderItem> oitList = orderItemDao.find(model, 0, 1);
			if (oitList != null) {
				orderItem = oitList.get(0);
			} else {
				log.info("邪门错误");
				throw new RuntimeException("邪门错误");
			}
			// 更新数量
			oitem.setCount(oitem.getCount() + orderItem.getCount());
			// 更新小计
			BigDecimal subtotal = (oitem.getOnePrice()).multiply(new BigDecimal(oitem.getCount()));
			oitem.setSubtotal(subtotal);
			// 更新时间
			oitem.setJoinTime(new Date());
			oitem.setOrderItemId(orderItem.getOrderItemId());
			// System.err.println(oitem);
			// 更新数据到库
			if (orderItemDao.updateByPrimaryKeySelective(oitem) < 1) {
				log.info("购物车已有项更新失败");
				throw new RuntimeException("购物车已有项更新失败");
			}
			return true;
		}
		// 新增
		oitem.setOrderItemId(GenKey.genKey(OrderItem.class));
		oitem.setJoinTime(new Date());

		if (null == oitem.getOnePrice()) {
			log.error("没有价格,无法小计");
			throw new RuntimeException("没有价格,无法小计");
		}
		BigDecimal subtotal = (oitem.getOnePrice()).multiply(new BigDecimal(oitem.getCount()));
		oitem.setSubtotal(subtotal);
		if (orderItemDao.insert(oitem) < 1) {
			log.error("插入orderItem失败");
			throw new RuntimeException("插入orderItem失败");
		}
		return true;
	}

	/*
	 * 关注字符串： 由于没有对关注做专门的数据表处理，故采用带分隔符的字符串来描述关注的内容。
	 * 且为了提高程序运行效率，建议控制关注的数量小于1000件物品每人次。 Goods_id&shop_id| Goods_id&shop_id|
	 * Goods_id&shop_id|
	 */
	@Override
	public Boolean joinCare(OrderItem orderItem, User user) {
		if (null == user || null == user.getUserId()) {
			log.error("非法用户！");
			return false;
		}
		
		MyCare model = new MyCare();
		model.setUserId(user.getUserId());
		model.setCareForId(orderItem.getGoodsId());
		List list = myCareDao.find(model, 0, 1);
		if(list!=null&&list.size()>0){
			log.error("添加关注失败,重复关注");
			return false;
		}
		
		MyCare libCare = myCareDao.selectCareForIdByCareForId(orderItem.getGoodsId());
		if (null != libCare) {
			libCare.setCareTime(new Date());
			if (myCareDao.updateByPrimaryKeySelective(libCare) < 1) {
				log.error("添加关注失败");
				throw new RuntimeException("添加关注失败");
			}
			return true;
		}
		MyCare myCare = new MyCare();
		myCare.setCareId(GenKey.genKey(MyCare.class));
		myCare.setCareTime(new Date());
		myCare.setCareForId(orderItem.getGoodsId());
		myCare.setCareType("1");// 1商品 2店铺
		myCare.setUserId(user.getUserId());
		if (myCareDao.insert(myCare) < 1) {
			log.error("添加关注失败");
			throw new RuntimeException("添加关注失败");
		}
		return true;
	}

	/* 初始订单，状态为0 */
	@Override
	public Long createOrder(List<OrderItem> oitems, User user) {
		if (null == user || null == user.getUserId()) {
			log.error("非法用户！");
			return null;
		}
		Order order = new Order();
		order.setOrderId(GenKey.genKey(OrderItem.class));
		order.setGenerateDatetime(new Date());
		order.setState("0");// 生成订单（订单中有订单项）
		order.setUserId(user.getUserId());
		if (orderDao.insert(order) < 1) {
			log.info("生成订单失败");
			throw new RuntimeException("生成订单失败");
		}
		int n = 0;
		Goods goods = null;
		BigDecimal total = new BigDecimal(0.0);
		for (OrderItem orderItem : oitems) {
			goods = goodsDao.findPriceByPrimaryKey(orderItem.getGoodsId());
			orderItem.setOnePrice(goods.getPrice());
			orderItem.setState("1");// 组成了订单的 订单项（购物车中是0）
			orderItem.setOrderId(order.getOrderId());
			orderItem.setUserId(user.getUserId());
			orderItem.setSubtotal(orderItem.getOnePrice().multiply(new BigDecimal(orderItem.getCount())));
			// 更新一下订单项的状态
			if (orderItemDao.updateByPrimaryKeySelective(orderItem) < 1) {
				throw new RuntimeException("执行订单项更新错误");
			}
			if (null == orderItem.getSubtotal()) {
				log.error("没有价格，无法计费");
				throw new RuntimeException("没有价格，无法计费");
			}
			total = total.add(orderItem.getSubtotal());
			n += orderItemDao.updateByPrimaryKeySelective(orderItem);
		}
		order.setDeliveryCost(new BigDecimal(0.0));
		order.setMaxDeliveryTime(maxDeliveryTime);
		order.setTotalCost(total);
		int m = orderDao.updateByPrimaryKeySelective(order);
		if (n != oitems.size() && m < 1) {
			log.error("执行订单更新错误");
			throw new RuntimeException("执行订单更新错误");
		}
		return order.getOrderId();
	}

	/* 在结算页添加信息，转态要变迁为--->1（已结算，未支付状态） */
	@Override
	public Boolean attachInfoToOrder(Order order) {
		// TODO Auto-generated method stub 未完成的方法
		Order safeOrder = orderDao.selectByPrimaryKey(order.getOrderId());
		safeOrder.setAddrId(order.getAddrId());
		safeOrder.setOrderId(order.getOrderId());
		safeOrder.setPayMethod(order.getPayMethod());
		safeOrder.setDeliveryWay(order.getDeliveryWay());
		if (safeOrder.getState().equals("1")) {
			log.warn("重复提交");
			return false;
		}
		safeOrder.setState("1");
		//更新商品数量
		if(!updateGoodsNum(order.getOrderId())){
			log.warn("数量更新失败");
			throw new RuntimeException("数量更新失败");
		}
		if (orderDao.updateByPrimaryKeySelective(safeOrder) < 1) {
			log.error("更新订单失败");
			throw new RuntimeException("更新订单失败");
		}
		return true;
	}

	@Override
	public Order selectOrder(Long orderId) {
		return orderDao.selectByPrimaryKey(orderId);
	}

	/* 支付成功后的回调，转态要变迁为2 */
	@Override
	public Boolean doPay(Order order) {
		order.setState("2");// 已支付但未完成的订单
		if (orderDao.updateByPrimaryKeySelective(order) < 1) {
			log.error("执行更新错误");
			throw new RuntimeException("执行更新错误");
		}
		return true;
	}

	/* 用户确认收货 */
	@Override
	public Boolean doOk(Order order) {
		order.setState("3");// 已完成订单（货物到达目的地）
		int n = orderDao.updateByPrimaryKeySelective(order);
		int shouldM = orderItemDao.coutItemOnOneOrder(order.getOrderId());
		int m = orderItemDao.setStateDoPayByOrderId(order.getOrderId());
		if (n < 1 && m != shouldM) {
			log.error("执行更新错误");
			throw new RuntimeException("执行更新错误");
		}
		return true;
	}

	@Override
	public String doBorrow(Goods goods, User user) {
		goods = goodsDao.getCopyNum(goods);
		if (null == goods || goods.getCopyForBorrow() < 1) {
			log.warn("库存不足");
			return "次数已借完！";
		}
		goods = goodsDao.selectByPrimaryKey(goods.getGoodsId());
		user = userDao.selectByPrimaryKey(user.getUserId());
		Borrow model = new Borrow();
		model.setUserId(user.getUserId());
		model.setGoodsId(goods.getGoodsId());
		if(borrowDao.find(model, 0, 1).size()>0){
			log.warn("此书已借阅");
			return "此书已借阅";
		}
		Borrow borrow = new Borrow();
		borrow.setBorrowId(GenKey.genKey(Borrow.class));
		borrow.setGoodsId(goods.getGoodsId());
		borrow.setBorrowDatetiem(new Date());
		borrow.setUserId(user.getUserId());
		borrow.setState("0");// 预借阅状态
		borrow.setCostPerBorrow(new BigDecimal("0"));
		borrow.setEntityTable(goods.getEntityTable());
		borrow.setEntiryId(goods.getEntityId());
		// 默认借书量是3本
		user.setMaxBorrowNumber(null == user.getMaxBorrowNumber() ? 3 : user.getMaxBorrowNumber());
		// 默认借书时长是60天
		user.setMaxBorrowTime(null == user.getMaxBorrowTime() ? 60 : user.getMaxBorrowTime());
		if (borrowDao.getNoReturnNum(user.getUserId()) + 1 > user.getMaxBorrowNumber()) {
			log.warn("超出借阅范围");
			return "超出最大借阅数，您的最大借阅数量为：" + user.getMaxBorrowNumber();
		}
		// 2 是借书超了期的
		if (borrowDao.getNumByState(user.getUserId(), "2") > 0) {
			log.warn("有超期书籍");
			return "有超期书籍，不能借阅";
		}

		goods.setCopyForBorrow(goods.getCopyForBorrow() - 1);
		if (goodsDao.updateByPrimaryKeySelective(goods) < 1) {
			log.warn("更新库存失败");
			throw new RuntimeException("更新库存失败！");
		}
		if (borrowDao.insert(borrow) < 1) {
			log.error("借阅插入失败！");
			throw new RuntimeException("借阅插入失败！");
		}
		// 模拟批准
		if (!okBorrow(borrow)) {
			log.error("借阅没有被批准！");
			throw new RuntimeException("借阅没有被批准！");
		}

		return "true";
	}

	@Override
	public boolean okBorrow(Borrow borrow) {
		borrow.setState("1");// 借阅状态
		borrow.setBorrowDatetiem(new Date());
		if (borrowDao.updateByPrimaryKeySelective(borrow) < 1) {
			throw new RuntimeException("借阅批准操作失败！");
		}
		return true;
	}

	@Override
	public String returnGoods(Borrow borrow) {
		// 核实还书信息
		/*
		 * user = userDao.selectByPrimaryKey(user.getUserId()); borrow =
		 * borrowDao.selectByPrimaryKey(borrow.getBorrowId()); Long interTime =
		 * CommonTool.dateSUbDate(new Date(), borrow.getBorrowDatetiem(), "d");
		 * if(interTime>user.getMaxBorrowTime()){ log.warn("超时"); return
		 * ""+(interTime-user.getMaxBorrowTime()); }
		 */
		Goods goods = goodsDao.findByPrimaryKey(borrow.getGoodsId());
		goods.setCopyForBorrow(goods.getCopyForBorrow() + 1);
		goods.setInStock(goods.getInStock() + 1);
		if (goodsDao.updateByPrimaryKeySelective(goods) < 1) {
			log.error("还书的商品更新失败");
			throw new RuntimeException("还书的商品更新失败");
		}
		borrow.setReturnDatetim(new Date());
		borrow.setState("3");
		if (borrowDao.updateByPrimaryKeySelective(borrow) < 1) {
			log.error("还书更新失败");
			throw new RuntimeException("还书更新失败");
		}
		return "true";
	}

	@Override
	public List<Borrow> viewHoldBorrow(User user, Integer page, Integer size) {
		return viewBorror(user, "1", page, size);
	}

	@Override
	public List<Borrow> viewExpiredBorrow(User user, Integer page, Integer size) {
		return viewBorror(user, "2", page, size);
	}

	@Override
	public List<Borrow> viewHisBorrow(User user, Integer page, Integer size) {
		return viewBorror(user, "3", page, size);
	}

	private List<Borrow> viewBorror(User user, String state, Integer page, Integer size) {
		Borrow borrow = new Borrow();
		state = null == state ? null : state.trim();
		borrow.setUserId(user.getUserId());
		if (null != state) {
			borrow.setState(state);
		}
		return borrowDao.findAndeOrderByWithGoods(borrow, "'borrow_datetiem' desc", (page - 1) * size, page * size);
	}

	@Override
	public List<CartForm> viewCart(User user, Integer page, Integer size) {
		OrderItem model = new OrderItem();
		// 购物车专用订单号
		model.setOrderId(1000000000000000L);
		// 谁的购物车
		model.setUserId(user.getUserId());
		// 处于购物车状态的都是0状态
		model.setState("0");
		List<CartForm> list = new ArrayList<CartForm>();
		CartForm cartForm = null;

		// 返回分页查询的结果
		List<OrderItem> oitemList = orderItemDao.find(model, (page - 1) * size, page * size);
		for (OrderItem orderItem : oitemList) {
			cartForm = new CartForm();
			cartForm.setOrderItem(orderItem);
			Goods goods = goodsDao.findByPrimaryKey(orderItem.getGoodsId());
			if (null != goods) {
				cartForm.setFolder(goods.getEntryFolder());
			}
			list.add(cartForm);
		}
		return list;
	}

	private List<Order> viewOrder(User user, String state, Integer page, Integer size) {
		Order model = new Order();
		if (null == user) {
			log.warn("notLogin");
			return null;
		}
		model.setUserId(user.getUserId());
		if (null != state) {
			model.setState(state);
		}
		return orderDao.findAndeOrderBy(model, "'generate_datetime' desc", (page - 1) * size, page * size);
	}

	@Override
	public Settlement viewOneSettlement(User user, Order order, Integer page, Integer size) {
		Settlement settlement = new Settlement();
		// 查询未确认的订单
		List<Order> orderList = viewOrder(user, "0", page, size);
		if (null == orderList || orderList.size() < 1) {
			return null;
		}
		if (null == order) {
			log.warn("没有指定订单");
			return null;
		}
		Order curOrder = null;
		for (Order temp : orderList) {
			if (temp.getOrderId().equals(order.getOrderId())) {
				curOrder = temp;
				break;
			}
		}
		if (null == curOrder) {
			log.warn("未查询到指定的订单号");
			return null;
		}
		settlement.setOrder(curOrder);
		List<Address> addressList = addressDao.selectByUserId(user.getUserId());
		settlement.setAddrs(addressList);
		OrderItem model = new OrderItem();
		model.setOrderId(curOrder.getOrderId());
		model.setState("1");
		List<OrderItem> orderItemList = orderItemDao.find(model, (page - 1) * size, (page) * size);
		CartForm cartForm = null;
		List<CartForm> cfList = new ArrayList<CartForm>();
		for (OrderItem oit : orderItemList) {
			cartForm = new CartForm();
			cartForm.setOrderItem(oit);
			Goods goods = goodsDao.findByPrimaryKey(oit.getGoodsId());
			if (null != goods) {
				cartForm.setFolder(goods.getEntryFolder());
			}
			cfList.add(cartForm);
		}
		settlement.setCartForms(cfList);
		return settlement;
	}

	// 没确认提交的订单
	@Override
	public List<Settlement> viewNoCommitOrder(User user, Integer page, Integer size) {
		List<Order> orderlist = viewOrder(user, "0", page, size);
		List<Settlement> settlementList = new ArrayList<Settlement>(3);
		Settlement settlement = null;
		for (Order order : orderlist) {
			settlement = viewOneSettlement(user, order, 1, 100);
			settlementList.add(settlement);
		}
		return settlementList;
	}

	// 已结算（附加了支付快递等信息的订单） 没支付
	@Override
	public List<Order> viewNoPayOrder(User user, Integer page, Integer size) {
		return viewOrder(user, "1", page, size);
	}

	// 已支付，没收货
	@Override
	public List<Order> viewNoOkOrder(User user, Integer page, Integer size) {
		return viewOrder(user, "2", page, size);
	}

	// 已确认收货
	@Override
	public List<Order> viewOkOrder(User user, Integer page, Integer size) {
		return viewOrder(user, "3", page, size);
	}

	// 中途取消或失败的订单
	@Override
	public List<Order> viewFailedOrder(User user, Integer page, Integer size) {
		return viewOrder(user, "4", page, size);
	}

	// 所有历史订单
	@Override
	public List<Order> viewAllOrder(User user, Integer page, Integer size) {
		return viewOrder(user, null, page, size);
	}

	@Override
	public boolean removeCartItemToTash(OrderItem orderItem) {
		orderItem.setState("t");
		if (orderItemDao.updateByPrimaryKeySelective(orderItem) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deletOneUserCartTash(User user) {
		if (orderItemDao.deleteByUser(user.getUserId()) > 0) {
			return true;
		}
		return false;
	}

	// "t" trash, 回收站
	@Override
	public boolean removeOrderToTash(Order order) {
		order.setState("t");
		if (orderDao.updateByPrimaryKeySelective(order) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteOneUserOrderTash(User user) {
		if (orderDao.deleteByUser(user.getUserId()) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean cancelOneOrder(Order order) {
		order.setState("4");
		if (orderDao.updateByPrimaryKeySelective(order) > 0) {
			return true;
		}
		return false;
	}

	/********************** 地址相关 *****************************/
	@Override
	public List<Address> getUserAllAddress(User user) {
		return addressDao.selectByUserId(user.getUserId());
	}

	@Override
	public boolean deleteAddr(Long addrId) {
		if (addressDao.deleteByPrimaryKey(addrId) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean updataAddr(Address address) {
		if (addressDao.updateByPrimaryKeySelective(address) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean addAddr(Address address) {
		if (addressDao.insert(address) > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryDao.findAll("category");
	}

	@Override
	public List<MyCare> viewCare(User user, Integer pageno, Integer size) {
		MyCare mc = new MyCare();
		mc.setUserId(user.getUserId());
		return myCareDao.find(mc, pageno, size);
	}

	@Override
	public List<Goods> searchGoods(String[] keyWords, Integer page, Integer size) {
		// TODO Auto-generated method stub
		List<Goods> glist = null;
		Set<Goods> gset = new TreeSet<Goods>();
		Goods model = new Goods();
		if (keyWords.length < 1) {
			return null;
		} else if (keyWords.length == 1) {
			model.setKeyWords(keyWords[0]);
			glist = goodsDao.find(model, (page - 1) * size, page * size);
			for (Goods goods : glist) {
				gset.add(goods);
			}
			model.setKeyWords(null);
			model.setGoodsName(keyWords[0]);
			glist = goodsDao.find(model, (page - 1) * size, page * size);
			for (Goods goods : glist) {
				gset.add(goods);
			}
			model.setGoodsName(null);
			model.setaWordIntro(keyWords[0]);
			glist = goodsDao.find(model, (page - 1) * size, page * size);
			for (Goods goods : glist) {
				gset.add(goods);
			}
		} else {
			model.setGoodsName(keyWords[0]);
			model.setaWordIntro(keyWords[1]);
			if(keyWords.length>2){
				model.setCategory(keyWords[2]);
			}
			glist = goodsDao.find(model, (page - 1) * size, page * size);
			for (Goods goods : glist) {
				gset.add(goods);
			}
		}
		glist = new ArrayList<Goods>(10);
		Iterator<Goods> it = gset.iterator();
		int i =0 ;
		while(it.hasNext()&&i<size){
			glist.add(it.next());
			++i;
		}
		return glist;
	}

	@Override
	public boolean updateGoodsNum(Long orderId) {
		OrderItem model = new OrderItem();
		model.setOrderId(orderId);
		List<OrderItem> oiList = orderItemDao.find(model, 0, 1000);
		int oldStock = 0;
		Goods tg = new Goods();
		Goods temp = null;
		for (OrderItem orderItem : oiList) {
			temp = goodsDao.getGoodsNumber(orderItem.getGoodsId());
			if(temp==null){
				continue;
			}
			oldStock = temp.getInStock();
			tg.setGoodsId(temp.getGoodsId());
			tg.setInStock(oldStock-orderItem.getCount());
			if( goodsDao.updateByPrimaryKeySelective(tg)<1 ){
				log.error("商品数量更新错误");
				throw new RuntimeException("商品数量更新错误");
			}
		}
		return true;
	}

	@Override
	public List<Goods> categoryGoods(String label, Integer page, Integer size) {
		Goods model = new Goods();
		model.setGoodsLabel(label);
		return goodsDao.find(model, (page-1)*size, page*size);
	}

	@Override
	public Integer countItemOfOneOrder(Long orderId) {
		return orderItemDao.coutItemOnOneOrder(orderId);
	}
	
	
	
	

	// power by 孟盛能 2017-5-5 contact with msnqqmail@qq.com
}
