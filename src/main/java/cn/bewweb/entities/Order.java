package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private Long orderId;

    private Long userId;
    
    private Long addrId;

    private Long promotionsTicket;

    private BigDecimal deliveryCost;

    private BigDecimal assureCost;

    private BigDecimal reallyCost;

    private BigDecimal totalCost;

    private Date generateDatetime;

    private Date payDatetime;

    private String state;

    private Integer maxValidTime;

    private BigDecimal minSentCost;

    private BigDecimal maxAssureMoney;

    private Integer maxDeliveryTime;
    
    private String payMethod;
    
    private String deliveryWay;
    
    //一份确切的订单只有一个收获地址
    private Address address;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPromotionsTicket() {
        return promotionsTicket;
    }

    public void setPromotionsTicket(Long promotionsTicket) {
        this.promotionsTicket = promotionsTicket;
    }

    public BigDecimal getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(BigDecimal deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public BigDecimal getAssureCost() {
        return assureCost;
    }

    public void setAssureCost(BigDecimal assureCost) {
        this.assureCost = assureCost;
    }

    public BigDecimal getReallyCost() {
        return reallyCost;
    }

    public void setReallyCost(BigDecimal reallyCost) {
        this.reallyCost = reallyCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Date getGenerateDatetime() {
        return generateDatetime;
    }

    public void setGenerateDatetime(Date generateDatetime) {
        this.generateDatetime = generateDatetime;
    }

    public Date getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(Date payDatetime) {
        this.payDatetime = payDatetime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getMaxValidTime() {
        return maxValidTime;
    }

    public void setMaxValidTime(Integer maxValidTime) {
        this.maxValidTime = maxValidTime;
    }

    public BigDecimal getMinSentCost() {
        return minSentCost;
    }

    public void setMinSentCost(BigDecimal minSentCost) {
        this.minSentCost = minSentCost;
    }

    public BigDecimal getMaxAssureMoney() {
        return maxAssureMoney;
    }

    public void setMaxAssureMoney(BigDecimal maxAssureMoney) {
        this.maxAssureMoney = maxAssureMoney;
    }

    public Integer getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public void setMaxDeliveryTime(Integer maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getDeliveryWay() {
		return deliveryWay;
	}

	public void setDeliveryWay(String deliveryWay) {
		this.deliveryWay = deliveryWay;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", userId=" + userId + ", addrId=" + addrId + ", promotionsTicket="
				+ promotionsTicket + ", deliveryCost=" + deliveryCost + ", assureCost=" + assureCost + ", reallyCost="
				+ reallyCost + ", totalCost=" + totalCost + ", generateDatetime=" + generateDatetime + ", payDatetime="
				+ payDatetime + ", state=" + state + ", maxValidTime=" + maxValidTime + ", minSentCost=" + minSentCost
				+ ", maxAssureMoney=" + maxAssureMoney + ", maxDeliveryTime=" + maxDeliveryTime + ", payMethod="
				+ payMethod + ", deliveryWay=" + deliveryWay + ", address=" + address + "]";
	}

	

	
    
}