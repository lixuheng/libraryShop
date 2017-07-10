package cn.bewweb.entities;

import java.util.Date;

public class Share {
    private Long shareId;

    private Long userId;

    private String subject;

    private String selfLabel;

    private Date shareDatetime;

    private String shareImagePath;

    private String title;

    private String keywords;

    private String mind;

    private String state;

    private String shareContent;

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getSelfLabel() {
        return selfLabel;
    }

    public void setSelfLabel(String selfLabel) {
        this.selfLabel = selfLabel == null ? null : selfLabel.trim();
    }

    public Date getShareDatetime() {
        return shareDatetime;
    }

    public void setShareDatetime(Date shareDatetime) {
        this.shareDatetime = shareDatetime;
    }

    public String getShareImagePath() {
        return shareImagePath;
    }

    public void setShareImagePath(String shareImagePath) {
        this.shareImagePath = shareImagePath == null ? null : shareImagePath.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    public String getMind() {
        return mind;
    }

    public void setMind(String mind) {
        this.mind = mind == null ? null : mind.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
    }

	@Override
	public String toString() {
		return "Share [shareId=" + shareId + ", userId=" + userId + ", subject=" + subject + ", selfLabel=" + selfLabel
				+ ", shareDatetime=" + shareDatetime + ", shareImagePath=" + shareImagePath + ", title=" + title
				+ ", keywords=" + keywords + ", mind=" + mind + ", state=" + state + ", shareContent=" + shareContent
				+ "]";
	}
    
    
}