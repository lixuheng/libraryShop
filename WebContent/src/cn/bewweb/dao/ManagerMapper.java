package cn.bewweb.dao;

import java.util.List;

import cn.bewweb.entities.Manager;

public interface ManagerMapper {
	int deleteByPrimaryKey(Long managerId);

	int insert(Manager record);

	int insertSelective(Manager record);

	Manager selectByPrimaryKey(Long managerId);

	int updateByPrimaryKeySelective(Manager record);

	int updateByPrimaryKey(Manager record);

	/////////////////////////
	Manager selectManagerByUserId(Long userId);
	List<Manager> find(Manager manager, Integer begin, Integer end);

	List<Manager> findAndeOrderBy(Manager manager, String method, Integer begin, Integer end);
}