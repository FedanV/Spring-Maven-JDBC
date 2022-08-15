package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.CourseDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.CoursesGenerator;
import com.foxminded.vitaliifedan.task7.models.Course;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

class CourseDaoImplTest {

    private static CourseDao courseDao;
    private static Connection connection;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        courseDao = new CourseDaoImpl();
        connection = DataSource.getConnection();
        SqlUtils.executeSqlScriptFile("task7/init_schema.sql");
        CoursesGenerator coursesGenerator = new CoursesGenerator(courseDao);
        coursesGenerator.generateCourses(connection);
    }

    @AfterAll
    static void end() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile("task7/drop_schema.sql");
    }

    @Test
    void should_CreateCourse() throws SQLException {
        Course course = new Course("TestCourse", "Description");
        courseDao.create(connection, course);
        Optional<Course> actualResult = courseDao.findByCourseName(connection, "TestCourse");
        Assertions.assertEquals(course.getCourseName(), actualResult.get().getCourseName());
    }

    @Test
    void should_UpdateCourse() throws SQLException {
        Course course = courseDao.getAll(connection).get(0);
        course.setCourseName("TestUpdateCourse");
        course.setCourseDescription("Test");
        courseDao.update(connection, course);
        Assertions.assertEquals(course, courseDao.findById(connection, course.getCourseId()).get());
    }

    @Test
    void should_DeleteCourse() throws SQLException {
        courseDao.deleteById(connection, 2);
        Assertions.assertTrue(courseDao.findById(connection, 2).isEmpty());
    }

    @Test
    void should_ReturnCourseByName() throws SQLException {
        Course history = courseDao.findByCourseName(connection, "Social").get();
        Assertions.assertEquals("Social", history.getCourseName());
    }
}
