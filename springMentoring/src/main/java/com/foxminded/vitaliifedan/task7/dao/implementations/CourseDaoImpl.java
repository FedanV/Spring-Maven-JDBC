package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.CourseMapper;
import com.foxminded.vitaliifedan.task7.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {

    private final CourseMapper courseMapper;

    public CourseDaoImpl() {
        this.courseMapper = new CourseMapper();
    }

    @Override
    public Course create(Connection connection, Course entity) throws SQLException {
        String createCourse = "INSERT INTO courses(course_name, course_description) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(createCourse, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Creating course " + entity.getCourseName() + " failed");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Unable to retrieve id");
                }
                return new Course(generatedKeys.getLong("course_id"), entity.getCourseName(), entity.getCourseDescription());
            }
        }
    }

    @Override
    public Course update(Connection connection, Course entity) throws SQLException {
        String updateCourse = "UPDATE courses SET course_name=?, course_description=? WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(updateCourse)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.setLong(3, entity.getCourseId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Unable to update " + entity);
            }
            return new Course(entity.getCourseId(), entity.getCourseName(), entity.getCourseDescription());
        }
    }

    @Override
    public List<Course> getAll(Connection connection) throws SQLException {
        String getAllCourses = "SELECT * FROM courses ORDER BY course_id";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getAllCourses)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(courseMapper.get(resultSet));
                }
            }
        }
        return courses;
    }

    @Override
    public Optional<Course> findById(Connection connection, Long id) throws SQLException {
        String getCourseById = "SELECT * FROM courses WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(getCourseById)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(courseMapper.get(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Connection connection, Long id) throws SQLException {
        String deleteCourseById = "DELETE FROM courses WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteCourseById)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Course> findByCourseName(Connection connection, String courseName) throws SQLException {
        String findByCourseName = "SELECT * FROM courses WHERE course_name=?";
        try (PreparedStatement statement = connection.prepareStatement(findByCourseName)) {
            statement.setString(1, courseName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(courseMapper.get(resultSet));
                }
            }
        }
        return Optional.empty();
    }
}
