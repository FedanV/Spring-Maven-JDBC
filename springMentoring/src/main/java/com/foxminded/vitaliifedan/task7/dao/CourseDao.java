package com.foxminded.vitaliifedan.task7.dao;

import com.foxminded.vitaliifedan.task7.models.Course;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface CourseDao extends CrudDao<Course, Integer> {

    Optional<Course> findByCourseName(Connection connection, String courseName) throws SQLException;
}
