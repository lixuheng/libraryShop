package cn.bewweb.entities;

public class Address {
    private Long addrId;

    private Long userId;

    private String userName;

    private String tel;

    private String info;

    private String isdefault;

    public Long getAddrId() {
        return addrId;
    }

    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault == null ? null : isdefault.trim();
    }

	@Override
	public String toString() {
		return "Address [addrId=" + addrId + ", userId=" + userId + ", userName=" + userName + ", tel=" + tel
				+ ", info=" + info + ", isdefault=" + isdefault + "]";
	}
    
    
}