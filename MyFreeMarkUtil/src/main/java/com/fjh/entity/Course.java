package com.fjh.entity;
    import java.lang.Integer;
    import java.util.Date;

/**
*
*/
public class Course {

    /**
    * 主键
    */
    private Integer courseId;
    /**
    * 课程名
    */
    private String courseName;
    /**
    * 记录课程创建的时间
    */
    private Date courseTime;

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public Date getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(Date courseTime) {
        this.courseTime = courseTime;
    }


}