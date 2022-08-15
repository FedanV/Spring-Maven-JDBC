package com.foxminded.vitaliifedan.task7.dao.mappers;

import com.foxminded.vitaliifedan.task7.models.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements Mapper<Course> {

    @Override
    public Course get(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("course_id"),
                rs.getString("course_name"),
                rs.getString("course_description")
        );
    }
}
