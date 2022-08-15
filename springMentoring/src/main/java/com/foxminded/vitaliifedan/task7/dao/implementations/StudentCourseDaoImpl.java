package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.StudentCourseDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.StudentCourseMapper;
import com.foxminded.vitaliifedan.task7.models.Course;
import com.foxminded.vitaliifedan.task7.models.Student;
import com.foxminded.vitaliifedan.task7.models.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentCourseDaoImpl implements StudentCourseDao {

    private final StudentCourseMapper studentCourseMapper;
    private final CourseDao courseDao;
    private final StudentDao studentDao;

    public StudentCourseDaoImpl() {
        this.studentCourseMapper = new StudentCourseMapper();
        this.studentDao = new StudentDaoImpl();
        this.courseDao = new CourseDaoImpl();
    }

    @Override
    public void createStudentCourse(Connection connection, StudentCourse entity) throws SQLException {
        List<Integer> studentIds = studentDao.getAll(connection).stream().map(Student::getStudentId).toList();
        List<Integer> courseIds = courseDao.getAll(connection).stream().map(Course::getCourseId).toList();

        if (!studentIds.contains(entity.getStudentId())) {
            throw new IllegalArgumentException("Table 'students' doesn't contain this student");
        }

        if (!courseIds.contains(entity.getCourseId())) {
            throw new IllegalArgumentException("Table 'courses' doesn't contain this course");
        }

        if (findByStudentIdAndCourseId(connection, entity.getStudentId(), entity.getCourseId()).isPresent()) {
            throw new IllegalArgumentException("The table 'students_courses' already has such value");
        }

        String createStudentCourse = "INSERT INTO students_courses(student_id, course_id) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(createStudentCourse)) {
            statement.setInt(1, entity.getStudentId());
            statement.setInt(2, entity.getCourseId());
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<StudentCourse> findByStudentIdAndCourseId(Connection connection, int studentId, int courseId) throws SQLException {
        String findStudentIdAndCourseId = "SELECT * FROM students_courses WHERE student_id=? AND course_id=?";
        try (PreparedStatement statement = connection.prepareStatement(findStudentIdAndCourseId)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(studentCourseMapper.get(resultSet));
            }
        }
        return Optional.empty();
    }

    @Override
    public void removeStudentByCourseName(Connection connection, int studentId, String courseName) throws SQLException {
        String removeStudentByCourseName = "DELETE FROM students_courses AS sc " +
                "USING courses AS c " +
                "WHERE sc.course_id = c.course_id AND sc.student_id = ? AND c.course_name = ? ";
        try(PreparedStatement statement = connection.prepareStatement(removeStudentByCourseName)) {
            statement.setInt(1, studentId);
            statement.setString(2, courseName);
            statement.executeUpdate();
        }
    }

}

