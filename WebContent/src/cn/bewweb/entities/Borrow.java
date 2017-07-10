package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Borrow {
    private Long borrowId;

    private Long userId;

    private Long goodsId;

    private Date borrowDatetiem;

    private BigDecimal costPerBorrow;

    private String entityTable;

    private Long entiryId;

    private String state;

    private Date returnDatetim;

    private String note;
    
    private Goods goods;
    
    public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Long getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Date getBorrowDatetiem() {
        return borrowDatetiem;
    }

    public void setBorrowDatetiem(Date borrowDatetiem) {
        this.borrowDatetiem = borrowDatetiem;
    }

    public BigDecimal getCostPerBorrow() {
        return costPerBorrow;
    }

    public void setCostPerBorrow(BigDecimal costPerBorrow) {
        this.costPerBorrow = costPerBorrow;
    }

    public String getEntityTable() {
        return entityTable;
    }

    public void setEntityTable(String entityTable) {
        this.entityTable = entityTable == null ? null : entityTable.trim();
    }

    public Long getEntiryId() {
        return entiryId;
    }

    public void setEntiryId(Long entiryId) {
        this.entiryId = entiryId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getReturnDatetim() {
        return returnDatetim;
    }

    public void setReturnDatetim(Date returnDatetim) {
        this.returnDatetim = returnDatetim;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}