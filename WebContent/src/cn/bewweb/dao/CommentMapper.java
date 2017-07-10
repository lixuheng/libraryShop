package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Comment;

public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKeyWithBLOBs(Comment record);

    int updateByPrimaryKey(Comment record);
    ////////////////
    List<Comment> find(Comment comment, Integer begin, Integer end);

    List<Comment> findAndeOrderBy(Comment comment, String method, Integer begin, Integer end);

}