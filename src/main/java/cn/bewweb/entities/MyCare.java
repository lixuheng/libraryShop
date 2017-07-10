package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class MyCare {
    private Long careId;

    private Long userId;

    private Long careForId;

    private String careType;

    private BigDecimal pricenotice;

    private Date careTime;

    public Long getCareId() {
        return careId;
    }

    public void setCareId(Long careId) {
        this.careId = careId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCareForId() {
        return careForId;
    }

    public void setCareForId(Long careForId) {
        this.careForId = careForId;
    }

    public String getCareType() {
        return careType;
    }

    public void setCareType(String careType) {
        this.careType = careType == null ? null : careType.trim();
    }

    public BigDecimal getPricenotice() {
        return pricenotice;
    }

    public void setPricenotice(BigDecimal pricenotice) {
        this.pricenotice = pricenotice;
    }

    public Date getCareTime() {
        return careTime;
    }

    public void setCareTime(Date careTime) {
        this.careTime = careTime;
    }
}