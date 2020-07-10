package com.example.androidnews.entity;


import java.io.Serializable;
import java.util.Objects;


public class NewsListEntity implements Serializable {
    private Integer newsId;
    private String newsTitle;
    private String newsInfo;
    private String newsPhoto;
    private String newsDate;
    private Integer state;
    private String userName;
    private String classifyName;




    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    @Override
    public String toString() {
        return "NewsListEntity{" +
                "newsId=" + newsId +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsInfo='" + newsInfo + '\'' +
                ", newsPhoto='" + newsPhoto + '\'' +
                ", newsDate='" + newsDate + '\'' +
                ", state=" + state +
                ", userName='" + userName + '\'' +
                ", classifyName='" + classifyName + '\'' +
                '}';
    }


    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsInfo() {
        return newsInfo;
    }

    public void setNewsInfo(String newsInfo) {
        this.newsInfo = newsInfo;
    }


    public String getNewsPhoto() {
        return newsPhoto;
    }

    public void setNewsPhoto(String newsPhoto) {
        this.newsPhoto = newsPhoto;
    }


    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsListEntity that = (NewsListEntity) o;
        return newsId == that.newsId &&
                Objects.equals(newsTitle, that.newsTitle) &&
                Objects.equals(newsInfo, that.newsInfo) &&
                Objects.equals(newsPhoto, that.newsPhoto) &&
                Objects.equals(newsDate, that.newsDate) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newsId, newsTitle, newsInfo, newsPhoto, newsDate, state);
    }

}
