package com.foxminded.vitaliifedan.task7.models;

public class StudentCourse {
    private long studentId;
    private long courseId;

    public StudentCourse(long studentId, long courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
}
