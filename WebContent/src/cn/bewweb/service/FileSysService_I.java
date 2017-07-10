package cn.bewweb.service;

import java.io.File;
import java.util.List;

/**
 * 文件系统服务
 * @author 孟盛能 2017-05-14
 * @since 1.1.1
 * @version 1.1.1
 */
public interface FileSysService_I {
	
	/**
	 * 查询文件路径，搜索文件
	 * @param basePath 基础路径
	 * @param name 要匹配的名字
	 * @param mode 1仅搜索文件 2仅搜索目录 3搜索文件或目录
	 * @param isStrict 是否匹配大小写
	 * @param isLike 是否启用模糊查询
	 * @param isWapper 是否启用全词匹配
	 * @return 绝对路径列表
	 */
	List<String> find(String basePath, String name, String mode,Boolean isStrict,Boolean isLike,Boolean isWapper,Boolean quick);
	
	/**
	 * 转移头像文件
	 * @param id 用户id
	 * @return
	 */
	boolean tranToHeads(Long id);
	/**
	 * 获取文件或目录的名字
	 * @param absolutePath 所在路径
	 * @return 文件或目录的名字名字
	 */
	String getName(String absolutePath);
	
	/**
	 * 创建一个路径
	 * @param path 绝对路径
	 * @param fileName 如果则新建一个空文件
	 * @return
	 */
	boolean creatPath(String path,String fileName);
	/**
	 * 删除文件及目录
	 * @param path
	 * @return
	 */
	boolean delete(String path);
	
	/**
	 * 获取文件或目录的大小
	 * @param path
	 * @return
	 */
	long getSize(String path);
	

	/**
	 * 复制文件
	 * @param src 原文件全路径
	 * @param desc 目的目录路径
	 * @param newName 新的文件名
	 * @param mode 1,强制，2 和平， 3自动取名
	 * @return
	 */
	boolean copyFile(String src,String dest  ,String newName);
	
	/**
	 * 复制文件或文件夹
	 * @param src 文件或目录
	 * @param dest 目录
	 * @return
	 */
	boolean copy(String src,String dest);
	
	/**
	 * 移动文件
	 * @param src 文件或目录
	 * @param desc  目录
	 * @return
	 */
	boolean moveFile(String src,String dest, String newName);
	
	/**
	 * 移动文件或文件夹
	 * @param src
	 * @param dest
	 * @return
	 */
	boolean move(String src,String dest);
	
	/**
	 * 重命名
	 * @param src
	 * @param newName
	 * @return
	 */
	boolean renameFile(String src, String newName);
	
	/**
	 * 文件名生成器
	 * @param mode 1单文件模式，2，目录+文件模式，默认为1 
	 * @param id 文件服务的id
	 * @param contentType MIME类型
	 * @param oldName 原始名字，若无则不保留原始名字
	 * @param newName 新名字
	 * @return[0]路径和[1]文件名, 对于模式1分隔符是下划线
	 */
	String[] genNewName(Long id,String contentType,String oldName,String newName,String mode);
	
	

}
