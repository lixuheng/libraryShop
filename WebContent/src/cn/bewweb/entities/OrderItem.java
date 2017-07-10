package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class OrderItem {
    private Long orderItemId;

    private Long goodsId;

    private Long orderId;

    private Long shopId;

    private Date joinTime;

    private Integer count;

    private BigDecimal subtotal;

    private String state;

    private BigDecimal shopPromotionTicket;
    
    private String goodsName;

    private BigDecimal onePrice;
    
    private Long userId;
    
    
	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public BigDecimal getShopPromotionTicket() {
        return shopPromotionTicket;
    }

    public void setShopPromotionTicket(BigDecimal shopPromotionTicket) {
        this.shopPromotionTicket = shopPromotionTicket;
    }

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public BigDecimal getOnePrice() {
		return onePrice;
	}

	public void setOnePrice(BigDecimal onePrice) {
		this.onePrice = onePrice;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "OrderItem [orderItemId=" + orderItemId + ", goodsId=" + goodsId + ", orderId=" + orderId + ", shopId="
				+ shopId + ", joinTime=" + joinTime + ", count=" + count + ", subtotal=" + subtotal + ", state=" + state
				+ ", shopPromotionTicket=" + shopPromotionTicket + ", goodsName=" + goodsName + ", onePrice=" + onePrice
				+ ", userId=" + userId + "]";
	}


    
    
}