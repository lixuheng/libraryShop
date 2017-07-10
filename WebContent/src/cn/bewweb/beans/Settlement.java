package cn.bewweb.beans;

import java.util.List;

import cn.bewweb.entities.Address;
import cn.bewweb.entities.Order;

public class Settlement {
	//订单
	private Order order;
	//地址
	private List<Address> addrs;
	//订单项
	private List<CartForm> cartForms;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<Address> getAddrs() {
		return addrs;
	}

	public void setAddrs(List<Address> addrs) {
		this.addrs = addrs;
	}

	public List<CartForm> getCartForms() {
		return cartForms;
	}

	public void setCartForms(List<CartForm> cartForms) {
		this.cartForms = cartForms;
	}

	@Override
	public String toString() {
		return "Settlement [order=" + order + ", addrs=" + addrs + ", cartForms=" + cartForms + "]";
	}
	
	
}
