package cn.bewweb.beans;

import cn.bewweb.entities.OrderItem;

public class CartForm {
	
	private OrderItem orderItem;
	//主要是这张图
	private String folder;
	
	
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	@Override
	public String toString() {
		return "CartForm [orderItem=" + orderItem + ", folder=" + folder + "]";
	}


}
