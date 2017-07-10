package cn.bewweb.entities;

import java.util.Date;

public class Abnormal {
    private Long abnormalId;

    private Long userId;

    private String ioException;

    private String sqlException;

    private String nullPointerException;

    private String classCastException;

    private String cloneException;

    private String parseException;

    private String otherException;

    private Date exceptionDatetime;

    private Date receiveReportDatetime;

    private String note;

    private String receiveRepoft;

    public Long getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(Long abnormalId) {
        this.abnormalId = abnormalId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIoException() {
        return ioException;
    }

    public void setIoException(String ioException) {
        this.ioException = ioException == null ? null : ioException.trim();
    }

    public String getSqlException() {
        return sqlException;
    }

    public void setSqlException(String sqlException) {
        this.sqlException = sqlException == null ? null : sqlException.trim();
    }

    public String getNullPointerException() {
        return nullPointerException;
    }

    public void setNullPointerException(String nullPointerException) {
        this.nullPointerException = nullPointerException == null ? null : nullPointerException.trim();
    }

    public String getClassCastException() {
        return classCastException;
    }

    public void setClassCastException(String classCastException) {
        this.classCastException = classCastException == null ? null : classCastException.trim();
    }

    public String getCloneException() {
        return cloneException;
    }

    public void setCloneException(String cloneException) {
        this.cloneException = cloneException == null ? null : cloneException.trim();
    }

    public String getParseException() {
        return parseException;
    }

    public void setParseException(String parseException) {
        this.parseException = parseException == null ? null : parseException.trim();
    }

    public String getOtherException() {
        return otherException;
    }

    public void setOtherException(String otherException) {
        this.otherException = otherException == null ? null : otherException.trim();
    }

    public Date getExceptionDatetime() {
        return exceptionDatetime;
    }

    public void setExceptionDatetime(Date exceptionDatetime) {
        this.exceptionDatetime = exceptionDatetime;
    }

    public Date getReceiveReportDatetime() {
        return receiveReportDatetime;
    }

    public void setReceiveReportDatetime(Date receiveReportDatetime) {
        this.receiveReportDatetime = receiveReportDatetime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getReceiveRepoft() {
        return receiveRepoft;
    }

    public void setReceiveRepoft(String receiveRepoft) {
        this.receiveRepoft = receiveRepoft == null ? null : receiveRepoft.trim();
    }
}