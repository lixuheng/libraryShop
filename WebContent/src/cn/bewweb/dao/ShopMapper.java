package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Shop;

public interface ShopMapper {
    int deleteByPrimaryKey(Long shopId);

    int insert(Shop record);

    int insertSelective(Shop record);

    Shop selectByPrimaryKey(Long shopId);

    int updateByPrimaryKeySelective(Shop record);

    int updateByPrimaryKeyWithBLOBs(Shop record);

    int updateByPrimaryKey(Shop record);
    /////////////////////
    List<Shop> find(Shop shop ,Integer begin, Integer end);
    List<Shop> selectAll(Integer begin,Integer end);
    List<Shop> selectByName(String name,Integer begin,Integer end);
    List<Shop> selectByNameState(String name,String state,Integer begin,Integer end);
    List<Shop> selectAllOrderByBeginDay(String method ,Integer begin,Integer end);
    
}