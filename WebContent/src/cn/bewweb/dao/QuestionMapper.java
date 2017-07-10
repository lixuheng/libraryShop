package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Question;

public interface QuestionMapper {
    int deleteByPrimaryKey(Long questionId);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(Long questionId);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKeyWithBLOBs(Question record);

    int updateByPrimaryKey(Question record);
    
    
    List<Question> find(Question question,Integer begin,Integer end);
    List<Question> findAndeOrderBy(Question question,String method ,Integer begin,Integer end);
}