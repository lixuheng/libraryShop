package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Shop {
    private Long shopId;

    private String name;

    private Date beginDay;

    private Integer credit;

    private String businessScope;

    private String license;

    private BigDecimal deposit;

    private BigDecimal rentPerMonth;

    private BigDecimal rentPerYear;

    private String state;

    private String legalRepresentative;

    private String note;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getBeginDay() {
        return beginDay;
    }

    public void setBeginDay(Date beginDay) {
        this.beginDay = beginDay;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getRentPerMonth() {
        return rentPerMonth;
    }

    public void setRentPerMonth(BigDecimal rentPerMonth) {
        this.rentPerMonth = rentPerMonth;
    }

    public BigDecimal getRentPerYear() {
        return rentPerYear;
    }

    public void setRentPerYear(BigDecimal rentPerYear) {
        this.rentPerYear = rentPerYear;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	@Override
	public String toString() {
		return "Shop [shopId=" + shopId + ", name=" + name + ", beginDay=" + beginDay + ", credit=" + credit
				+ ", businessScope=" + businessScope + ", license=" + license + ", deposit=" + deposit
				+ ", rentPerMonth=" + rentPerMonth + ", rentPerYear=" + rentPerYear + ", state=" + state
				+ ", legalRepresentative=" + legalRepresentative + ", note=" + note + "]";
	}
    
    
}