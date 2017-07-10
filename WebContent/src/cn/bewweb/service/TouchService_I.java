package cn.bewweb.service;

import java.util.List;

import cn.bewweb.entities.Comment;

public interface TouchService_I {
	
	/**
	 * 获取评论详情
	 * @param goodsId
	 * @param no
	 * @param size
	 * @return
	 */
	List<Comment> getOneGoodsComment(Long goodsId, Integer no, Integer size);

}
