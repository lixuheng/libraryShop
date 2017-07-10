package cn.bewweb.entities;

public class Question {
    private Long questionId;

    private Long userId;

    private String title;

    private String questionLabel;

    private Integer reword;

    private String question;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getQuestionLabel() {
        return questionLabel;
    }

    public void setQuestionLabel(String questionLabel) {
        this.questionLabel = questionLabel == null ? null : questionLabel.trim();
    }

    public Integer getReword() {
        return reword;
    }

    public void setReword(Integer reword) {
        this.reword = reword;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question == null ? null : question.trim();
    }
}