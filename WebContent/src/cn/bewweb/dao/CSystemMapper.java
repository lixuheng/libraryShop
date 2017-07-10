package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.CSystem;

public interface CSystemMapper {
    int deleteByPrimaryKey(Long systemId);

    int insert(CSystem record);

    int insertSelective(CSystem record);

    CSystem selectByPrimaryKey(Long systemId);

    int updateByPrimaryKeySelective(CSystem record);

    int updateByPrimaryKey(CSystem record);
////////////////
    List<CSystem> find(CSystem cSystem, Integer begin, Integer end);

    List<CSystem> findAndeOrderBy(CSystem cSystem, String method, Integer begin, Integer end);


}