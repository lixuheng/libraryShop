package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Address;

public interface AddressMapper {
    int deleteByPrimaryKey(Long addrId);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(Long addrId);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);
    //////////////////////////
    
    List<Address> selectByUserId(Long userId);
}