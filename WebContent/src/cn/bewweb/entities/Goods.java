package cn.bewweb.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Goods implements Comparable{
    private Long goodsId;

    private Long shopId;

    private String goodsName;

    private String aWordIntro;

    private String keyWords;

    private String goodsLabel;

    private Integer inStock;

    private Integer copyForBorrow;

    private BigDecimal originalPrice;

    private BigDecimal incomePrice;

    private BigDecimal price;

    private BigDecimal discount;

    private Date inSellDay;

    private String category;

    private String goodsClass;

    private Integer ratings;

    private String entityTable;

    private Long entityId;

    private String state;

    private Integer clickOk;

    private Integer clickMiddle;

    private Integer clickBad;

    private Long viewCount;

    private Integer todayViewCount;

    private Integer todaySell;

    private Long allsell;

    private Integer todyBorrow;

    private Long allBorrow;

    private Long allCare;
    
    private Book book;
  
    private String entryFolder;

    public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getaWordIntro() {
        return aWordIntro;
    }

    public void setaWordIntro(String aWordIntro) {
        this.aWordIntro = aWordIntro == null ? null : aWordIntro.trim();
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords == null ? null : keyWords.trim();
    }

    public String getGoodsLabel() {
        return goodsLabel;
    }

    public void setGoodsLabel(String goodsLabel) {
        this.goodsLabel = goodsLabel == null ? null : goodsLabel.trim();
    }

    public Integer getInStock() {
        return inStock;
    }

    public void setInStock(Integer inStock) {
        this.inStock = inStock;
    }

    public Integer getCopyForBorrow() {
        return copyForBorrow;
    }

    public void setCopyForBorrow(Integer copyForBorrow) {
        this.copyForBorrow = copyForBorrow;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getIncomePrice() {
        return incomePrice;
    }

    public void setIncomePrice(BigDecimal incomePrice) {
        this.incomePrice = incomePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getInSellDay() {
        return inSellDay;
    }

    public void setInSellDay(Date inSellDay) {
        this.inSellDay = inSellDay;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass == null ? null : goodsClass.trim();
    }

    public Integer getRatings() {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getEntityTable() {
        return entityTable;
    }

    public void setEntityTable(String entityTable) {
        this.entityTable = entityTable == null ? null : entityTable.trim();
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Integer getClickOk() {
        return clickOk;
    }

    public void setClickOk(Integer clickOk) {
        this.clickOk = clickOk;
    }

    public Integer getClickMiddle() {
        return clickMiddle;
    }

    public void setClickMiddle(Integer clickMiddle) {
        this.clickMiddle = clickMiddle;
    }

    public Integer getClickBad() {
        return clickBad;
    }

    public void setClickBad(Integer clickBad) {
        this.clickBad = clickBad;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getTodayViewCount() {
        return todayViewCount;
    }

    public void setTodayViewCount(Integer todayViewCount) {
        this.todayViewCount = todayViewCount;
    }

    public Integer getTodaySell() {
        return todaySell;
    }

    public void setTodaySell(Integer todaySell) {
        this.todaySell = todaySell;
    }

    public Long getAllsell() {
        return allsell;
    }

    public void setAllsell(Long allsell) {
        this.allsell = allsell;
    }

    public Integer getTodyBorrow() {
        return todyBorrow;
    }

    public void setTodyBorrow(Integer todyBorrow) {
        this.todyBorrow = todyBorrow;
    }

    public Long getAllBorrow() {
        return allBorrow;
    }

    public void setAllBorrow(Long allBorrow) {
        this.allBorrow = allBorrow;
    }

    public Long getAllCare() {
        return allCare;
    }

    public void setAllCare(Long allCare) {
        this.allCare = allCare;
    }

	public String getEntryFolder() {
		return entryFolder;
	}

	public void setEntryFolder(String entryFolder) {
		this.entryFolder = entryFolder;
	}

	@Override
	public String toString() {
		return "Goods [goodsId=" + goodsId + ", shopId=" + shopId + ", goodsName=" + goodsName + ", aWordIntro="
				+ aWordIntro + ", keyWords=" + keyWords + ", goodsLabel=" + goodsLabel + ", inStock=" + inStock
				+ ", copyForBorrow=" + copyForBorrow + ", originalPrice=" + originalPrice + ", incomePrice="
				+ incomePrice + ", price=" + price + ", discount=" + discount + ", inSellDay=" + inSellDay
				+ ", category=" + category + ", goodsClass=" + goodsClass + ", ratings=" + ratings + ", entityTable="
				+ entityTable + ", entityId=" + entityId + ", state=" + state + ", clickOk=" + clickOk
				+ ", clickMiddle=" + clickMiddle + ", clickBad=" + clickBad + ", viewCount=" + viewCount
				+ ", todayViewCount=" + todayViewCount + ", todaySell=" + todaySell + ", allsell=" + allsell
				+ ", todyBorrow=" + todyBorrow + ", allBorrow=" + allBorrow + ", allCare=" + allCare + ", book=" + book
				+ ", entryFolder=" + entryFolder + "]";
	}

	@Override
	public int compareTo(Object o) {
		if(o==null)
			return 0;
		if(!o.getClass().equals(Goods.class)){
			return 0;
		}
		Goods o2 = (Goods)o;
		if(this.getGoodsId().longValue()>o2.getGoodsId().longValue()){
			return 1;
		}else if(this.getGoodsId().longValue()==o2.getGoodsId().longValue()){
			return 0;
		}else {
			return -1;
		}
	}
	
	

	


}