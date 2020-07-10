package com.example.androidnews.entity;


import java.io.Serializable;
import java.util.Objects;


public class ClassifyListEntity implements Serializable {
    private Integer classifyId;
    private String classifyName;


    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public Integer getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Integer classifyId) {
        this.classifyId = classifyId;
    }

    @Override
    public String toString() {
        return "ClassifyListEntity{" +
                "classifyId=" + classifyId +
                ", classifyName='" + classifyName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassifyListEntity that = (ClassifyListEntity) o;
        return classifyId.equals(that.classifyId) &&
                Objects.equals(classifyName, that.classifyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classifyId, classifyName);
    }

}
