package cn.bewweb.mongo;

import java.util.List;
import java.util.Map;



public interface BaseMongoDao_I<T> {

	/**
	 * 保存新的文档到集合
	 * @param object 文档
	 * @param collectionName 集合名
	 */
    public void insert(T object,String collectionName);  
    /**
     * 按照主键查找一个文档
     * @param params 条件
     * @param collectionName 集合名
     * @return
     */
    public T findOne(Map<String,Object> params,String collectionName);  
    /**
     * 查询一个集合中的所有文档
     * @param params
     * @param collectionName
     * @return
     */
    public List<T> findAll(String collectionName); 
    /**
     * 按条件查找
     * @param params
     * @param collectionName
     * @return
     */
    public List<T> find(Map<String,Object> params,String collectionName); 
    /**
     * 更新满足条件的文档
     * @param params
     * @param collectionName
     */
    public void update(Map<String,Object> params,String collectionName); 
    /**
     * 创建一个集合
     * @param collectionName
     */
    public void createCollection(String collectionName);
    /**
     * 删除满足条件的文档
     * @param params
     * @param collectionName
     */
    public void remove(Map<String,Object> params,String collectionName);
    

}