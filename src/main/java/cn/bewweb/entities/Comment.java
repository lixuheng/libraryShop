package cn.bewweb.entities;

import java.util.Date;

public class Comment {
    private Long commentId;

    private Long userId;

    private Long questionId;

    private Long shareId;

    private Long touCommentId;

    private Long orderitermId;

    private Date commentDatetime;

    private String state;

    private String comment;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getShareId() {
        return shareId;
    }

    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }

    public Long getTouCommentId() {
        return touCommentId;
    }

    public void setTouCommentId(Long touCommentId) {
        this.touCommentId = touCommentId;
    }

    public Long getOrderitermId() {
        return orderitermId;
    }

    public void setOrderitermId(Long orderitermId) {
        this.orderitermId = orderitermId;
    }

    public Date getCommentDatetime() {
        return commentDatetime;
    }

    public void setCommentDatetime(Date commentDatetime) {
        this.commentDatetime = commentDatetime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}