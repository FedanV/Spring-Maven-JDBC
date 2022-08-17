package com.foxminded.vitaliifedan.task7.dao;

import com.foxminded.vitaliifedan.task7.models.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface StudentDao extends CrudDao<Student, Long> {

    List<Student> findAllStudentsByCourseName(Connection connection, String courseName) throws SQLException;
}
