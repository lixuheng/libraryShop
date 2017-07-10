package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    /////////////////////////
    
    Order selectByPrimaryKeyWithAddrs(Long orderId);
    List<Order> find(Order order,Integer begin,Integer end);
    List<Order> findAndeOrderBy(Order order,String method ,Integer begin,Integer end);
    
    int deleteByUser(Long userId);
}