package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Seller;

public interface SellerMapper {
    int deleteByPrimaryKey(Long sellerId);

    int insert(Seller record);

    int insertSelective(Seller record);

    Seller selectByPrimaryKey(Long sellerId);

    int updateByPrimaryKeySelective(Seller record);

    int updateByPrimaryKey(Seller record);
    
    /////////////
    List<Seller> find(Seller seller,Integer begin,Integer end);
    List<Seller> findAndOrderBy(Seller seller,String method,Integer begin,Integer end);
}