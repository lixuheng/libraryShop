package cn.bewweb.entities;

import java.util.Date;

public class Proof {
    private Long proofId;

    private Long userId;

    private String proofCategory;

    private String proofType;

    private String sessionId;

    private String proofName;

    private String proofValue;

    private Date recordTime;

    private String note;

    public Long getProofId() {
        return proofId;
    }

    public void setProofId(Long proofId) {
        this.proofId = proofId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProofCategory() {
        return proofCategory;
    }

    public void setProofCategory(String proofCategory) {
        this.proofCategory = proofCategory == null ? null : proofCategory.trim();
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType == null ? null : proofType.trim();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName == null ? null : proofName.trim();
    }

    public String getProofValue() {
        return proofValue;
    }

    public void setProofValue(String proofValue) {
        this.proofValue = proofValue == null ? null : proofValue.trim();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

	@Override
	public String toString() {
		return "Proof [proofId=" + proofId + ", userId=" + userId + ", proofCategory=" + proofCategory + ", proofType="
				+ proofType + ", sessionId=" + sessionId + ", proofName=" + proofName + ", proofValue=" + proofValue
				+ ", recordTime=" + recordTime + ", note=" + note + "]";
	}
    
    
}