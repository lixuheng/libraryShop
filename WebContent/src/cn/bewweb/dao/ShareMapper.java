package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Share;

public interface ShareMapper {
    int deleteByPrimaryKey(Long shareId);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(Long shareId);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKeyWithBLOBs(Share record);

    int updateByPrimaryKey(Share record);
    
    ////////////////
    List<Share> find(Share share,Integer begin ,Integer end);
    List<Share> selectByUserId(String userId,Integer begin,Integer end);
    List<Share> selectOrderBy(String method ,Integer begin,Integer end);
    List<Share> selectBySubject(String subject ,Integer begin,Integer end);
    List<Share> selectByTitle(String title ,Integer begin,Integer end);
    List<Share> selectByContent(String content,Integer begin,Integer end);
    List<Share> selectByKeyWords(String[] keyWords ,Integer begin,Integer end);
    List<Share> selectByMind(String mind ,Integer begin,Integer end);
    
}