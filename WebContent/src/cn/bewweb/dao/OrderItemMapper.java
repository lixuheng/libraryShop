package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.OrderItem;

public interface OrderItemMapper {
	int deleteByPrimaryKey(Long orderitermId);

	int insert(OrderItem record);

	int insertSelective(OrderItem record);

	OrderItem selectByPrimaryKey(Long orderitermId);

	int updateByPrimaryKeySelective(OrderItem record);

	int updateByPrimaryKey(OrderItem record);

	/////////////////////////
	int coutItemOnOneOrder(Long orderId);
	
	int setStateDoPayByOrderId(Long orderId);
	
	List<OrderItem> find(OrderItem orderItem, Integer begin, Integer end);

	List<OrderItem> findAndeOrderBy(OrderItem orderItem, String method, Integer begin, Integer end);
	
	int isHadGoods(Long goodsId,Long shopId);
	
	
	int deleteByUser(Long userId);
	
	
	
	
}