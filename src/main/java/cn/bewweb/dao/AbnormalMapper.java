package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Abnormal;

public interface AbnormalMapper {
    int deleteByPrimaryKey(Long abnormalId);

    int insert(Abnormal record);

    int insertSelective(Abnormal record);

    Abnormal selectByPrimaryKey(Long abnormalId);

    int updateByPrimaryKeySelective(Abnormal record);

    int updateByPrimaryKeyWithBLOBs(Abnormal record);

    int updateByPrimaryKey(Abnormal record);
    
    ////////////////
    List<Abnormal> find(Abnormal abnormal, Integer begin, Integer end);

    List<Abnormal> findAndeOrderBy(Abnormal abnormal, String method, Integer begin, Integer end);
}