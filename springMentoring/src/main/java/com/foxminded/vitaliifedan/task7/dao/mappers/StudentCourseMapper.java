package com.foxminded.vitaliifedan.task7.dao.mappers;

import com.foxminded.vitaliifedan.task7.models.StudentCourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentCourseMapper implements Mapper<StudentCourse>{
    @Override
    public StudentCourse get(ResultSet rs) throws SQLException {
        return new StudentCourse(
                rs.getLong("student_id"),
                rs.getLong("course_id")
        );
    }
}
