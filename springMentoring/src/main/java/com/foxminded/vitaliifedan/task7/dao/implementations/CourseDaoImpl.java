package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.CourseMapper;
import com.foxminded.vitaliifedan.task7.models.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {

    private final CourseMapper courseMapper;

    public CourseDaoImpl() {
        this.courseMapper = new CourseMapper();
    }

    @Override
    public void create(Connection connection, Course entity) throws SQLException {
        String createCourse = "INSERT INTO courses(course_name, course_description) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(createCourse)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Connection connection, Course entity) throws SQLException {
        String updateCourse = "UPDATE courses SET course_name=?, course_description=? WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(updateCourse)) {
            statement.setString(1, entity.getCourseName());
            statement.setString(2, entity.getCourseDescription());
            statement.setInt(3, entity.getCourseId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Course> getAll(Connection connection) throws SQLException {
        String getAllCourses = "SELECT * FROM courses ORDER BY course_id";
        List<Course> courses = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(getAllCourses)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                courses.add(courseMapper.get(resultSet));
            }
        }
        return courses;
    }

    @Override
    public Optional<Course> findById(Connection connection, Integer id) throws SQLException {
        String getCourseById = "SELECT * FROM courses WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(getCourseById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(courseMapper.get(resultSet));
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Connection connection, Integer id) throws SQLException {
        String deleteCourseById = "DELETE FROM courses WHERE course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteCourseById)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Course> findByCourseName(Connection connection, String courseName) throws SQLException {
        String findByCourseName = "SELECT * FROM courses WHERE course_name=?";
        try (PreparedStatement statement = connection.prepareStatement(findByCourseName)) {
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(courseMapper.get(resultSet));
            }
        }
        return Optional.empty();
    }
}
