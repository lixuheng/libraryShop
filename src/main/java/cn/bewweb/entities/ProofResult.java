package cn.bewweb.entities;

import java.util.Date;

public class ProofResult {
    private Long proofResultId;

    private Long userId;

    private String sessionId;

    private String proofName;

    private String proofValue;

    private Integer count;

    private Integer radices;

    private Float rating;

    private Float weight;

    private Date recordTime;

    private String note;

    public Long getProofResultId() {
        return proofResultId;
    }

    public void setProofResultId(Long proofResultId) {
        this.proofResultId = proofResultId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRadices() {
        return radices;
    }

    public void setRadices(Integer radices) {
        this.radices = radices;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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
}