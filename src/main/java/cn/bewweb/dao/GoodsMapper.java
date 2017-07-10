package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Goods;

public interface GoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
    ////////////
    Goods findByPrimaryKey(Long goodsId);
    
    Goods findPriceByPrimaryKey(Long goodsId);
    
    List<Goods> selectGoodsByPage(Integer no, Integer size);

    List<Goods> find(Goods goods, Integer begin, Integer end);

    List<Goods> findAndeOrderBy(Goods goods, String method, Integer begin, Integer end);
    
    Goods getCopyNum(Goods goods);
    
    Goods selectByPrimaryKeyWithBook(Long goodsId);
    
    
    Goods getGoodsNumber(Long goodsId);


}