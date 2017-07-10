package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Book;
import cn.bewweb.entities.BookWithBLOBs;

public interface BookMapper {
    int deleteByPrimaryKey(Long isbn);

    int insert(BookWithBLOBs record);

    int insertSelective(BookWithBLOBs record);

    BookWithBLOBs selectByPrimaryKey(Long isbn);

    int updateByPrimaryKeySelective(BookWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(BookWithBLOBs record);

    int updateByPrimaryKey(Book record);
    
    /////////////////////////////////////////////////
    List<BookWithBLOBs> selectBooksByPage(Integer no,Integer size); 
    List<BookWithBLOBs> selectBooksByName(String bookName, Integer no,Integer size);
    List<BookWithBLOBs> selectBooksByAuthor(String author, Integer no,Integer size);
    List<BookWithBLOBs> selectBooksByPress(String author, Integer no,Integer size);
    List<BookWithBLOBs> find(BookWithBLOBs book,Integer begin,Integer end);
    List<BookWithBLOBs> findAndeOrderBy(BookWithBLOBs book,String method ,Integer begin,Integer end);
    
}