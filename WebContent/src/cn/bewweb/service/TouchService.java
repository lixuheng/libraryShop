package cn.bewweb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bewweb.dao.CommentMapper;
import cn.bewweb.dao.OrderItemMapper;
import cn.bewweb.dao.QuestionMapper;
import cn.bewweb.dao.ShareMapper;
import cn.bewweb.entities.Comment;
import cn.bewweb.entities.OrderItem;

@Service
public class TouchService implements TouchService_I {

	@Autowired
	private CommentMapper commentDao;
	@Autowired
	private QuestionMapper questionDao;
	@Autowired 
	private ShareMapper shareDao;
	@Autowired
	private OrderItemMapper orderItemDao;
	
	private static final Logger log = LoggerFactory.getLogger(TouchService.class);
	
	@Override
	public List<Comment> getOneGoodsComment(Long goodsId,Integer no,Integer size) {
		OrderItem orderItem = new OrderItem();
		orderItem.setGoodsId(goodsId);
		List<OrderItem> orderItemList = orderItemDao.find(orderItem, (no-1)*size, no*size);
		List<Comment> commentlist= new ArrayList<Comment>();
		Comment commentT = new Comment();
		for (OrderItem orderItem1 : orderItemList) {
			commentT.setOrderitermId(orderItem1.getOrderId());
			List<Comment> clist = commentDao.find(commentT, 0, 1);
			commentlist.add(clist==null?null:clist.get(0));
		}
		return commentlist;
	}
	
	
	
}
