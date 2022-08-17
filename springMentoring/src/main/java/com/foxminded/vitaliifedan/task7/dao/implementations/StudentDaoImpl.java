package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.StudentMapper;
import com.foxminded.vitaliifedan.task7.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {
    private final StudentMapper studentMapper;

    public StudentDaoImpl() {
        this.studentMapper = new StudentMapper();
    }

    @Override
    public Student create(Connection connection, Student entity) throws SQLException {
        String createStudent = "INSERT INTO students(group_id, first_name, last_name) VALUES(?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(createStudent, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, entity.getGroupId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Creation user " + entity.getFirstName() + " " + entity.getLastName() + " failed");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Unable to create student");
                }
                return new Student(generatedKeys.getLong("student_id"),
                        entity.getGroupId(), entity.getFirstName(), entity.getLastName());
            }
        }
    }

    @Override
    public Student update(Connection connection, Student entity) throws SQLException {

        String updateStudent = "UPDATE students SET group_id=?, first_name=?, last_name=? WHERE student_id=?";
        try (PreparedStatement statement = connection.prepareStatement(updateStudent)) {
            statement.setLong(1, entity.getGroupId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setLong(4, entity.getStudentId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Unable to update student with id " + entity.getStudentId());
            }
            return new Student(entity.getStudentId(), entity.getGroupId(), entity.getFirstName(), entity.getLastName());
        }
    }

    @Override
    public List<Student> getAll(Connection connection) throws SQLException {
        String getAllStudents = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getAllStudents)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(studentMapper.get(resultSet));
                }
            }
        }
        return students;
    }

    @Override
    public Optional<Student> findById(Connection connection, Long id) throws SQLException {
        String getStudentById = "SELECT * FROM students WHERE student_id=?";
        try (PreparedStatement statement = connection.prepareStatement(getStudentById)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(studentMapper.get(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Connection connection, Long id) throws SQLException {
        String deleteStudentById = "DELETE FROM students WHERE student_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteStudentById)) {
            statement.setLong(1, id);
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Unable to deleted student with id " + id);
            }
        }
    }

    @Override
    public List<Student> findAllStudentsByCourseName(Connection connection, String courseName) throws SQLException {
        List<Student> students = new ArrayList<>();
        String findAllStudentsByCourseName = "SELECT st.student_id, st.group_id, st.first_name, st.last_name FROM students AS st " +
                "JOIN students_courses AS sc ON sc.student_id = st.student_id " +
                "JOIN courses c ON sc.course_id = c.course_id " +
                "WHERE c.course_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(findAllStudentsByCourseName)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(studentMapper.get(resultSet));
                }
            }
        }
        return students;
    }
}
