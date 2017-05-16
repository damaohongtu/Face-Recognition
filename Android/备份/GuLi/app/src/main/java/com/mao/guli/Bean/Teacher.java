package com.mao.guli.Bean;

/**
 * Created by mao on 2017/5/12.
 * 老师的基本信息
 */
public class Teacher {
    private String imgUrl;
    private String teacherName;
    private String teacherDepartment;

    public Teacher(String imgUrl, String teacherName, String teacherDepartment) {
        this.imgUrl = imgUrl;
        this.teacherName = teacherName;
        this.teacherDepartment = teacherDepartment;
    }
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDepartment() {
        return teacherDepartment;
    }

    public void setTeacherDepartment(String teacherDepartment) {
        this.teacherDepartment = teacherDepartment;
    }
}
