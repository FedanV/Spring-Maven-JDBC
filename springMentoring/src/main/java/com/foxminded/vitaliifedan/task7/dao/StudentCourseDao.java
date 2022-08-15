package com.foxminded.vitaliifedan.task7.dao;

import com.foxminded.vitaliifedan.task7.models.StudentCourse;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface StudentCourseDao {

    void createStudentCourse(Connection connection, StudentCourse entity) throws SQLException;

    Optional<StudentCourse> findByStudentIdAndCourseId(Connection connection, int studentId, int courseId) throws SQLException;

    void removeStudentByCourseName(Connection connection, int studentId, String courseName) throws SQLException;

}
