package com.foxminded.vitaliifedan.task7.dao.mappers;

import com.foxminded.vitaliifedan.task7.models.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper implements Mapper<Student> {

    @Override
    public Student get(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("student_id"),
                rs.getInt("group_id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
