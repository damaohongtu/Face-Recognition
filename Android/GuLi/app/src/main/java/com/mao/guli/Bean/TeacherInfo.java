package com.mao.guli.Bean;

/**
 * Created by mao on 2017/5/16.
 */
public class TeacherInfo {
    private String teacherId;
    private String teacherName;
    private int teacherAge;
    private String teacherGender;
    private String teacherInfo;
    private int teacherLabel;

    public TeacherInfo() {
        super();
    }

    public TeacherInfo(String teacherId, int teacherLabel, String teacherInfo, String teacherGender, int teacherAge, String teacherName) {
        super();
        this.teacherId = teacherId;
        this.teacherLabel = teacherLabel;
        this.teacherInfo = teacherInfo;
        this.teacherGender = teacherGender;
        this.teacherAge = teacherAge;
        this.teacherName = teacherName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(int teacherAge) {
        this.teacherAge = teacherAge;
    }

    public String getTeacherGender() {
        return teacherGender;
    }

    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    public String getTeacherInfo() {
        return teacherInfo;
    }

    public void setTeacherInfo(String teacherInfo) {
        this.teacherInfo = teacherInfo;
    }

    public int getTeacherLabel() {
        return teacherLabel;
    }

    public void setTeacherLabel(int teacherLabel) {
        this.teacherLabel = teacherLabel;
    }
}
