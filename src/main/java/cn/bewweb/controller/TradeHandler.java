package cn.bewweb.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.util.JSONPObject;

import cn.bewweb.beans.CartForm;
import cn.bewweb.beans.Code;
import cn.bewweb.beans.Json;
import cn.bewweb.beans.Settlement;
import cn.bewweb.entities.Address;
import cn.bewweb.entities.Borrow;
import cn.bewweb.entities.Goods;
import cn.bewweb.entities.MyCare;
import cn.bewweb.entities.Order;
import cn.bewweb.entities.OrderItem;
import cn.bewweb.entities.User;
import cn.bewweb.service.ControlService_I;
import cn.bewweb.service.TradeService_I;

@Controller
@RequestMapping(value = "/trade")
public class TradeHandler {
	
	private static Logger log = LoggerFactory.getLogger(TradeHandler.class);
	@Autowired
	private TradeService_I tradeService;
	@Autowired
	private ControlService_I control;

	/*指定数据对象头，实现一个表单绑定到不同对象
	@InitBinder("user")
	public void initBinderUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}
	@InitBinder("orderItem")
	public void initBinderOrderItem(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("orderItem.");
	}
	*/
	
	
	/***********************关注相关***************************/
	
	@RequestMapping(value = "/joinCare", method = RequestMethod.POST)
	public @ResponseBody Json joinCare(OrderItem orderItem,HttpServletRequest request) {
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			tipList.add("notLogin");
			json.setCode(Code.c0).setList(tipList);
			return json;
		}
		if(!tradeService.joinCare(orderItem, user)){
			tipList.add("joinCare error");
			return json.setCode(Code.c0);
		}
		json.setCode(Code.c1);
		return json;
	}
	
	
	@RequestMapping(value = "/care", method = RequestMethod.GET)
	public @ResponseBody Json getCare(Integer pageno, Integer size,HttpServletRequest request) {
		Json json = new Json();
		List<MyCare> list = new ArrayList<MyCare>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.noPermission);
			return json;
		}
		list = tradeService.viewCare(user, pageno, size);
		json.setCode(Code.c1).setList(list);
		return json;
	}
	
	
	
	/*****************************************借阅相关********************/
	
	
	/**
	 * 借书
	 * @param orderItem
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doBorrow", method = RequestMethod.POST)
	public @ResponseBody Json doBorrow(OrderItem orderItem,HttpServletRequest request) {
		Json json = new Json();
		List<String> list = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			list.add("notLogin");
			return json.setList(list);
		}
		List<String> tipList = new ArrayList<String>();
		Goods goods = new Goods();
		goods.setGoodsId(orderItem.getGoodsId());
		goods.setGoodsName(orderItem.getGoodsName());
		String tip = tradeService.doBorrow(goods, user);
		if(!"true".equals(tip)){
			json.setCode(Code.c0);
			tipList.add(tip);
			return json.setList(tipList);
		}
		return json.setCode(Code.c1);
	}
	
	
	/**
	 * 还书
	 * @param borrow
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/returnGoods", method = RequestMethod.POST)
	public @ResponseBody Json returnGoods(Borrow borrow,HttpServletRequest request) {
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		String tip = tradeService.returnGoods(borrow);
		if(!"true".equals(tip)){
			json.setCode(Code.c0);
			tipList.add(tip);
			return json.setList(tipList);
		}
		return json.setCode(Code.c1);
	}
	
	
	@RequestMapping(value = "/nowBorrow", method = RequestMethod.POST)
	public @ResponseBody Json nowBorrow(Integer page,Integer size,HttpServletRequest request) {
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		json.setList(tradeService.viewHoldBorrow(user, page, size));
		return json.setCode(Code.c1);
	}
	
	
	@RequestMapping(value = "/expiredBorrow", method = RequestMethod.POST)
	public @ResponseBody Json expiredBorrow(Integer page,Integer size,HttpServletRequest request) {
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		json.setList(tradeService.viewExpiredBorrow(user, page, size));
		return json.setCode(Code.c1);
	}
	
	@RequestMapping(value = "/HisBorrow", method = RequestMethod.POST)
	public @ResponseBody Json HisBorrow(Integer page,Integer size,HttpServletRequest request) {
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		json.setList(tradeService.viewHisBorrow(user, page, size));
		return json.setCode(Code.c1);
	}
	
	
	
	/********************************订单项和订单相关**************************/
	

	
	/**
	 * 添加到购物车
	 * @param orderItem
	 * @param request
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "/joinShopCar", method = RequestMethod.POST)
	public @ResponseBody Json joinShopCar(OrderItem orderItem,HttpServletRequest request,Errors errors) {
		Json json = new Json();
		(request.getSession()).getId();
		List<String> list = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			list.add("notLogin");
			return json.setList(list);
		}
		log.info(orderItem.toString());
		
		if (tradeService.joinShoppingCar(orderItem,user)) {
			return json.setCode(Code.c1);
		}
		json.setCode(Code.c0);
		return json;
	}

	
	/**
	 * 转到购物车页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/shoppingCar",method=RequestMethod.GET)
	public String goShoppingCar(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		if(session.getAttribute("LOGGEDUSER")==null){
			model.addAttribute("reason", "没有登录");
			return "error";
		}
		return "trade/shoppingCar";
	}
	/**
	 * 查看购物车中数据
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/shoppingCar",method=RequestMethod.POST)
	public @ResponseBody Json  shoppingCar(Integer pageNo,Integer pageSize,HttpServletRequest request){
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		//处理默认页数
		pageNo = null==pageNo ? 1 :pageNo;
		pageSize = null == pageSize ? 10 :pageSize;
		List<CartForm> list = tradeService.viewCart(user, pageNo, pageSize);
		json.setCode(Code.c1);
		json.setList(list);
		return json;
	}
	
	/**
	 * 生成一个订单（使其处于未支付状态）
	 * @param oitems
	 * @param request
	 * @return
	 */
	// List<OrderItem> oitems
	@RequestMapping(value="/createOrder",method=RequestMethod.POST)
	public @ResponseBody Json  createOrder(@RequestBody List<OrderItem> oitems,HttpServletRequest request){
		Json json = new Json();
		List<String> tipList = new ArrayList<String>();
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户用户未登陆");
			json.setCode(Code.c0);
			tipList.add("notLogin");
			return json.setList(tipList);
		}
		/*for (OrderItem oitem : oitems) {
			System.err.println(oitem);
		}*/
		if(null == oitems){
			return json.setCode(Code.paramTypeError);
		}
		Long orderId = tradeService.createOrder(oitems, user);
		if(null==orderId){
			json.setCode(Code.c0);
			return json;
		}
		List<Long> list = new ArrayList<Long>();
		list.add(orderId);
		json.setCode(Code.c1).setList(list);
		return json;
	}
	
	/**
	 * 跳转到生成的订单来结算
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/settlement",method=RequestMethod.GET)
	public String  goViewSettlement(Model model,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			model.addAttribute("reason","没有登录");
			return "error";
		}
		return "trade/settlement";
	}
	
	
	/**
	 * 查看生成的订单信息(查询操作)
	 * @param orderId  订单号
	 * @param page 订单当前要展示订单项的页号
	 * @param size 订单项没页大小
	 * @param request 
	 * @return 一个位支付的订单以及详细订单项等相关信息
	 */
	@RequestMapping(value="/settlement",method=RequestMethod.POST)
	public @ResponseBody Json  viewSettlement(Long orderId, Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		if(null == orderId){
			log.warn("缺失重要参数");
			return new Json().setCode(Code.lostParam);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		Order order = new Order();
		order.setOrderId(orderId);
		Settlement settlement = tradeService.viewOneSettlement(user,order, page, size);
		List<Settlement> list = new ArrayList<Settlement>();
		list.add(settlement);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	
	@RequestMapping(value="/viewNoCommitOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewNoCommitOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page || page <1 ? 1 :page;
		size = null == size || size<1 || size> 100 ? 10: size;
		
		List<Settlement> list = tradeService.viewNoCommitOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	@RequestMapping(value="/viewNoPayOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewNoPayOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		List<Order> list = tradeService.viewNoPayOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	@RequestMapping(value="/viewNoOkOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewNoOkOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		List<Order> list = tradeService.viewNoOkOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	@RequestMapping(value="/viewOkOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewOkOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		List<Order> list = tradeService.viewOkOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	@RequestMapping(value="/viewFailedOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewFailedOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		List<Order> list = tradeService.viewFailedOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	@RequestMapping(value="/viewAllOrder",method=RequestMethod.POST)
	public @ResponseBody Json  viewAllOrder(Integer page, Integer size,HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		page = null == page? 1 :page;
		size = null == size? 10: size;
		List<Order> list = tradeService.viewAllOrder(user, page, size);
		return new Json().setCode(Code.c1).setList(list);
	}
	
	
	/**
	 * 提交收货信息，确认订单(更新订单操作)
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/attachInfoToOrder",method=RequestMethod.POST)
	public @ResponseBody Json  attachInfoToOrder(Order order,
			HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(null==user){
			log.warn("用户未登陆");
			return new Json().setCode(Code.noPermission);
		}
		if(order == null){
			return new Json().setCode(Code.lostParam);
		}
		
		//付款方式和邮寄方式证据
		control.saveToRedis(user.getUserId(), "pawWay", order.getPayMethod(), "u", null);
		control.saveToRedis(user.getUserId(), "deliveryWay", order.getDeliveryWay(), "u", new Date(System.currentTimeMillis()+3000));
		
		if(!tradeService.attachInfoToOrder(order)){
			log.error("提交订单失败");
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}
	
	
	@RequestMapping(value="/doPay",method=RequestMethod.GET)
	public String goDoPay(Long orderId,HttpServletRequest request,Model model){
		HttpSession session  = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		
		if(null==user){
			log.warn("用户未登陆");
		}
		if(orderId == null){
			log.warn("参数为空");
		}
		Order order = tradeService.selectOrder(orderId);
		model.addAttribute("shouldPay", order.getTotalCost());
		model.addAttribute("orderId", order.getOrderId());
		session.setAttribute("shouldPay", order.getTotalCost());
		return "trade/doPay";
		//return "forward:../trade/doPay";
	}
	
	/**
	 * 支付操作（更新订单操作）
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/doPay",method=RequestMethod.POST)
	public @ResponseBody Json  doPay(Order order, HttpServletRequest request){
		HttpSession session  = request.getSession();
		User user = (User) session.getAttribute("LOGGEDUSER");
		BigDecimal shouldPayS = (BigDecimal) session.getAttribute("shouldPay");
		session.removeAttribute("shouldPay");
		if(user==null){return new Json().setCode(Code.noPermission);}
		List<String> tip  = new ArrayList<String>(1);
		String shouldStr = request.getParameter("shouldPay");
		BigDecimal shouldPay = new BigDecimal(shouldStr);
		order = tradeService.selectOrder(order.getOrderId());
		if(!shouldPay.equals(shouldPayS) &&!shouldPay.equals(order.getTotalCost())){//模拟支付接口成功调用
			log.error("支付接口调用失败");
			tip.add("支付接口调用失败");
			return new Json().setCode(Code.c0).setList(tip);
		}
		if(!tradeService.doPay(order)){
			log.error("数据更新失败");
			tip.add("数据更新失败");
			return new Json().setCode(Code.c0).setList(tip);
		}
		
		//购买量
		control.proofAutoAdd(user.getUserId(), "buySum",Long.valueOf(tradeService.countItemOfOneOrder(order.getOrderId())), null);
		return new Json().setCode(Code.c1);
	}
	
	
	/**
	 * 确认收货
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/overOder",method=RequestMethod.POST)
	public @ResponseBody Json  overOder(Order order, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(user==null){return new Json().setCode(Code.noPermission);}
		List<String> tip  = new ArrayList<String>(1);
		if(!tradeService.doOk(order)){
			log.error("数据更新失败");
			tip.add("数据更新失败");
			return new Json().setCode(Code.c0).setList(tip);
		}
		return new Json().setCode(Code.c1);
	}
	
	
	/**
	 * 删除购物车的物品到回收站
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteFromCart",method=RequestMethod.POST)
	public @ResponseBody Json  deleteFromCart(OrderItem orderItem, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(user==null){return new Json().setCode(Code.noPermission);}
		List<String> tip  = new ArrayList<String>(1);
		if(!tradeService.removeCartItemToTash(orderItem)){
			log.error("数据更新失败");
			tip.add("数据更新失败");
			return new Json().setCode(Code.c0).setList(tip);
		}
		return new Json().setCode(Code.c1);
	}
	
	/**
	 * 删除一个订单
	 * @param order
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteOneOrder",method=RequestMethod.POST)
	public @ResponseBody Json  deleteOneOrder(Order order, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(user==null){return new Json().setCode(Code.noPermission);}
		List<String> tip  = new ArrayList<String>(1);
		if(!tradeService.removeOrderToTash(order)){
			log.error("数据更新失败");
			tip.add("数据更新失败");
			return new Json().setCode(Code.c0).setList(tip);
		}
		return new Json().setCode(Code.c1);
	}
	
	
	/***********************地址操作相关***********************/

	@RequestMapping(value="/getAddrs",method=RequestMethod.POST)
	public @ResponseBody Json  getAddrs( HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		List<Address> addrs = new ArrayList<Address>();
		addrs = tradeService.getUserAllAddress(user);
		return new Json().setCode(Code.c1).setList(addrs);
	}
	
	@RequestMapping(value="/addAddrs",method=RequestMethod.POST)
	public @ResponseBody Json addAddrs( Address address, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(!tradeService.addAddr(address)){
			new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}
	
	@RequestMapping(value="/delAddrs",method=RequestMethod.POST)
	public @ResponseBody Json  delAddrs(Long addrId, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(!tradeService.deleteAddr(addrId)){
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}
	
	@RequestMapping(value="/updateAddrs",method=RequestMethod.POST)
	public @ResponseBody Json  updateAddrs(Address address, HttpServletRequest request){
		User user = (User) ((request.getSession()).getAttribute("LOGGEDUSER"));
		if(!tradeService.updataAddr(address)){
			return new Json().setCode(Code.c0);
		}
		return new Json().setCode(Code.c1);
	}
	
	
	
	
	

}
