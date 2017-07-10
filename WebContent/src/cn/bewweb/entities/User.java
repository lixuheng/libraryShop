package cn.bewweb.entities;

import java.util.Date;

public class User {
    private Long userId;

    private String uname;

    private String password;

    private String sex;

    private String rname;

    private String phone;

    private String email;

    private String birthCity;

    private String idCard;

    private Date birthday;

    private String nowCity;

    private String academicDegree;

    private Integer sumMark;

    private Integer degree;

    private Date registDay;

    private String state;

    private String debitCard;

    private String onlineState;

    private Integer maxPwdErrorTimes;

    private Integer maxBorrowNumber;

    private Integer maxBorrowTime;

    private String mycare;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname == null ? null : rname.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity == null ? null : birthCity.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNowCity() {
        return nowCity;
    }

    public void setNowCity(String nowCity) {
        this.nowCity = nowCity == null ? null : nowCity.trim();
    }

    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree == null ? null : academicDegree.trim();
    }

    public Integer getSumMark() {
        return sumMark;
    }

    public void setSumMark(Integer sumMark) {
        this.sumMark = sumMark;
    }

    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    public Date getRegistDay() {
        return registDay;
    }

    public void setRegistDay(Date registDay) {
        this.registDay = registDay;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(String debitCard) {
        this.debitCard = debitCard == null ? null : debitCard.trim();
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        this.onlineState = onlineState == null ? null : onlineState.trim();
    }

    public Integer getMaxPwdErrorTimes() {
        return maxPwdErrorTimes;
    }

    public void setMaxPwdErrorTimes(Integer maxPwdErrorTimes) {
        this.maxPwdErrorTimes = maxPwdErrorTimes;
    }

    public Integer getMaxBorrowNumber() {
        return maxBorrowNumber;
    }

    public void setMaxBorrowNumber(Integer maxBorrowNumber) {
        this.maxBorrowNumber = maxBorrowNumber;
    }

    public Integer getMaxBorrowTime() {
        return maxBorrowTime;
    }

    public void setMaxBorrowTime(Integer maxBorrowTime) {
        this.maxBorrowTime = maxBorrowTime;
    }

    public String getMycare() {
        return mycare;
    }

    public void setMycare(String mycare) {
        this.mycare = mycare == null ? null : mycare.trim();
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", uname=" + uname + ", password=" + password + ", sex=" + sex + ", rname="
				+ rname + ", phone=" + phone + ", email=" + email + ", birthCity=" + birthCity + ", idCard=" + idCard
				+ ", birthday=" + birthday + ", nowCity=" + nowCity + ", academicDegree=" + academicDegree
				+ ", sumMark=" + sumMark + ", degree=" + degree + ", registDay=" + registDay + ", state=" + state
				+ ", debitCard=" + debitCard + ", onlineState=" + onlineState + ", maxPwdErrorTimes=" + maxPwdErrorTimes
				+ ", maxBorrowNumber=" + maxBorrowNumber + ", maxBorrowTime=" + maxBorrowTime + ", mycare=" + mycare
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((academicDegree == null) ? 0 : academicDegree.hashCode());
		result = prime * result + ((birthCity == null) ? 0 : birthCity.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((debitCard == null) ? 0 : debitCard.hashCode());
		result = prime * result + ((degree == null) ? 0 : degree.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((idCard == null) ? 0 : idCard.hashCode());
		result = prime * result + ((maxBorrowNumber == null) ? 0 : maxBorrowNumber.hashCode());
		result = prime * result + ((maxBorrowTime == null) ? 0 : maxBorrowTime.hashCode());
		result = prime * result + ((maxPwdErrorTimes == null) ? 0 : maxPwdErrorTimes.hashCode());
		result = prime * result + ((mycare == null) ? 0 : mycare.hashCode());
		result = prime * result + ((nowCity == null) ? 0 : nowCity.hashCode());
		result = prime * result + ((onlineState == null) ? 0 : onlineState.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((registDay == null) ? 0 : registDay.hashCode());
		result = prime * result + ((rname == null) ? 0 : rname.hashCode());
		result = prime * result + ((sex == null) ? 0 : sex.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((sumMark == null) ? 0 : sumMark.hashCode());
		result = prime * result + ((uname == null) ? 0 : uname.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (academicDegree == null) {
			if (other.academicDegree != null)
				return false;
		} else if (!academicDegree.equals(other.academicDegree))
			return false;
		if (birthCity == null) {
			if (other.birthCity != null)
				return false;
		} else if (!birthCity.equals(other.birthCity))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (debitCard == null) {
			if (other.debitCard != null)
				return false;
		} else if (!debitCard.equals(other.debitCard))
			return false;
		if (degree == null) {
			if (other.degree != null)
				return false;
		} else if (!degree.equals(other.degree))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idCard == null) {
			if (other.idCard != null)
				return false;
		} else if (!idCard.equals(other.idCard))
			return false;
		if (maxBorrowNumber == null) {
			if (other.maxBorrowNumber != null)
				return false;
		} else if (!maxBorrowNumber.equals(other.maxBorrowNumber))
			return false;
		if (maxBorrowTime == null) {
			if (other.maxBorrowTime != null)
				return false;
		} else if (!maxBorrowTime.equals(other.maxBorrowTime))
			return false;
		if (maxPwdErrorTimes == null) {
			if (other.maxPwdErrorTimes != null)
				return false;
		} else if (!maxPwdErrorTimes.equals(other.maxPwdErrorTimes))
			return false;
		if (mycare == null) {
			if (other.mycare != null)
				return false;
		} else if (!mycare.equals(other.mycare))
			return false;
		if (nowCity == null) {
			if (other.nowCity != null)
				return false;
		} else if (!nowCity.equals(other.nowCity))
			return false;
		if (onlineState == null) {
			if (other.onlineState != null)
				return false;
		} else if (!onlineState.equals(other.onlineState))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (registDay == null) {
			if (other.registDay != null)
				return false;
		} else if (!registDay.equals(other.registDay))
			return false;
		if (rname == null) {
			if (other.rname != null)
				return false;
		} else if (!rname.equals(other.rname))
			return false;
		if (sex == null) {
			if (other.sex != null)
				return false;
		} else if (!sex.equals(other.sex))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (sumMark == null) {
			if (other.sumMark != null)
				return false;
		} else if (!sumMark.equals(other.sumMark))
			return false;
		if (uname == null) {
			if (other.uname != null)
				return false;
		} else if (!uname.equals(other.uname))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	
	
    
    
}