package com.example.androidnews.entity;


import java.io.Serializable;
import java.util.Objects;


public class CommentEntity implements Serializable {
    private Integer id;
    private Integer newsId;
    private Integer userId;
    private String commentInfo;
    private String commentDate;
    private Integer state;
    private String userName;
    private String newsTitle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(String commentInfo) {
        this.commentInfo = commentInfo;
    }


    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "CommentEntity{" +
                "id=" + id +
                ", newsId=" + newsId +
                ", userId=" + userId +
                ", commentInfo='" + commentInfo + '\'' +
                ", commentDate='" + commentDate + '\'' +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", newsTitle='" + newsTitle + '\'' +
                '}';
    }



    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id.equals(that.id) &&
                Objects.equals(commentInfo, that.commentInfo) &&
                Objects.equals(commentDate, that.commentDate) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentInfo, commentDate, state);
    }

}
