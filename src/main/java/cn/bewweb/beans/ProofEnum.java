package cn.bewweb.beans;

import java.math.BigDecimal;
import java.util.Date;

public class ProofEnum {
	
	//登入时间
    private Date loginTime;
    //密码输错次数
    private Integer pwdErrorTimes;
    //输入用户名是删除键使用次数
    private Integer accoutBackspace;
    //输入密码时删除键使用次数
    private Integer pwdBackspace;
	//浏览器类型
    private String browserType;
    //操作系统类型
    private String osType;
    //客户端IP地址
    private String clientIp;
    //客户端地理位置
    private String location;
   
    
    //购买物品类型综合
    private Integer goodsTypeSum;
    //购买物品的件数
    private Integer goodsCount;
    //所有物品价值和
    private Integer goodsOnceMoneySum;
    //下载数量
    private Integer downSum;
    //下载消费总数
    private BigDecimal downCostSum;
    //下载类型和
    private Integer downTypeSum;
    //借阅数量
    private Integer borrowCount;
    //没有还书的数量
    private Integer notReturnCount;
    //借阅消费总和
    private BigDecimal borrowCostSum;
    //付款方式
    private String payWay;
    //搜索的关键字
    private String searchKeyWords;
    //是否修改过密码
    private String isModifyPwd;
    //结束时间
    private Integer overLoginTime;
    //购买的商品名称
    private String goods;

    
    
    
    
    
    
    
    
    
    
    
    
	public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}

	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}

	public Integer getGoodsTypeSum() {
		return goodsTypeSum;
	}

	public void setGoodsTypeSum(Integer goodsTypeSum) {
		this.goodsTypeSum = goodsTypeSum;
	}

	public Integer getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(Integer goodsCount) {
		this.goodsCount = goodsCount;
	}

	public Integer getGoodsOnceMoneySum() {
		return goodsOnceMoneySum;
	}

	public void setGoodsOnceMoneySum(Integer goodsOnceMoneySum) {
		this.goodsOnceMoneySum = goodsOnceMoneySum;
	}

	public Integer getDownSum() {
		return downSum;
	}

	public void setDownSum(Integer downSum) {
		this.downSum = downSum;
	}

	public BigDecimal getDownCostSum() {
		return downCostSum;
	}

	public void setDownCostSum(BigDecimal downCostSum) {
		this.downCostSum = downCostSum;
	}

	public Integer getDownTypeSum() {
		return downTypeSum;
	}

	public void setDownTypeSum(Integer downTypeSum) {
		this.downTypeSum = downTypeSum;
	}

	public Integer getBorrowCount() {
		return borrowCount;
	}

	public void setBorrowCount(Integer borrowCount) {
		this.borrowCount = borrowCount;
	}


	public Integer getNotReturnCount() {
		return notReturnCount;
	}

	public void setNotReturnCount(Integer notReturnCount) {
		this.notReturnCount = notReturnCount;
	}

	public BigDecimal getBorrowCostSum() {
		return borrowCostSum;
	}

	public void setBorrowCostSum(BigDecimal borrowCostSum) {
		this.borrowCostSum = borrowCostSum;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getSearchKeyWords() {
		return searchKeyWords;
	}

	public void setSearchKeyWords(String searchKeyWords) {
		this.searchKeyWords = searchKeyWords;
	}

	public String getIsModifyPwd() {
		return isModifyPwd;
	}

	public void setIsModifyPwd(String isModifyPwd) {
		this.isModifyPwd = isModifyPwd;
	}

	public Integer getAllLoginTimes() {
		return overLoginTime;
	}

	public void setAllLoginTimes(Integer overLoginTime) {
		this.overLoginTime = overLoginTime;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Integer getAccoutBackspace() {
		return accoutBackspace;
	}

	public void setAccoutBackspace(Integer accoutBackspace) {
		this.accoutBackspace = accoutBackspace;
	}

	public Integer getPwdBackspace() {
		return pwdBackspace;
	}

	public void setPwdBackspace(Integer pwdBackspace) {
		this.pwdBackspace = pwdBackspace;
	}

	public Integer getOverLoginTime() {
		return overLoginTime;
	}

	public void setOverLoginTime(Integer overLoginTime) {
		this.overLoginTime = overLoginTime;
	}

	@Override
	public String toString() {
		return "ProofEnum [loginTime=" + loginTime + ", pwdErrorTimes=" + pwdErrorTimes + ", accoutBackspace="
				+ accoutBackspace + ", pwdBackspace=" + pwdBackspace + ", browserType=" + browserType + ", osType="
				+ osType + ", clientIp=" + clientIp + ", location=" + location + ", goodsTypeSum=" + goodsTypeSum
				+ ", goodsCount=" + goodsCount + ", goodsOnceMoneySum=" + goodsOnceMoneySum + ", downSum=" + downSum
				+ ", downCostSum=" + downCostSum + ", downTypeSum=" + downTypeSum + ", borrowCount=" + borrowCount
				+ ", notReturnCount=" + notReturnCount + ", borrowCostSum=" + borrowCostSum + ", payWay=" + payWay
				+ ", searchKeyWords=" + searchKeyWords + ", isModifyPwd=" + isModifyPwd + ", overLoginTime="
				+ overLoginTime + ", goods=" + goods + "]";
	}


}