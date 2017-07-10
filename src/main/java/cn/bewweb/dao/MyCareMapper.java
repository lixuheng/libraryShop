package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.MyCare;

public interface MyCareMapper {
    int deleteByPrimaryKey(Long careId);

    int insert(MyCare record);

    int insertSelective(MyCare record);

    MyCare selectByPrimaryKey(Long careId);

    int updateByPrimaryKeySelective(MyCare record);

    int updateByPrimaryKey(MyCare record);
    ////////////
    MyCare selectCareForIdByCareForId(Long careForId);
    
    List<MyCare> find(MyCare myCare,Integer begin,Integer end);
    List<MyCare> findAndeOrderBy(MyCare myCare,String method ,Integer begin,Integer end);
    
}