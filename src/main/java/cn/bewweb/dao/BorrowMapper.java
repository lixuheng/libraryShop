package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Borrow;

public interface BorrowMapper {
    int deleteByPrimaryKey(Long borrowId);

    int insert(Borrow record);

    int insertSelective(Borrow record);

    Borrow selectByPrimaryKey(Long borrowId);

    int updateByPrimaryKeySelective(Borrow record);

    int updateByPrimaryKey(Borrow record);
    
    ////////////////
    List<Borrow> find(Borrow borrow, Integer begin, Integer end);

    List<Borrow> findAndeOrderBy(Borrow borrow, String method, Integer begin, Integer end);
   
    
    List<Borrow> findAndeOrderByWithGoods(Borrow borrow, String method, Integer begin, Integer end);
    
    
    //获取没有还的书的数量
    int getNoReturnNum(Long userId);
    
    //获取不同状态的数量
    int getNumByState(Long userId,String state);
    
    
    Borrow selectByPrimaryKeyWithGoods(Long borrowId);
    
    Borrow selectByUserWithGoods(Long borrowId,String state);
}