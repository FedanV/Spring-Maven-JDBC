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
import java.sql.SQLException;
import java.util.Properties;

class CourseDaoImplTest {

    private static CourseDao courseDao;
    private static DataSource dataSource;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty(DataSource.DRIVER_CLASS_NAME, "org.h2.Driver");
        properties.setProperty(DataSource.JDBC_URL, "jdbc:h2:mem:db1;MODE=PostgreSQL");
        properties.setProperty(DataSource.USERNAME, "sa");
        properties.setProperty(DataSource.PASSWORD, "");
        courseDao = new CourseDaoImpl();
        dataSource = new DataSource(properties);
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        CoursesGenerator coursesGenerator = new CoursesGenerator(courseDao);
        coursesGenerator.generateCourses(dataSource.getConnection());
    }

    @AfterAll
    static void end() {
        dataSource.close();
    }

    @Test
    void should_CreateCourse() throws SQLException {
        Course expectedCourse = new Course(11, "TestCourse", "Description");
        Course actualCourse = courseDao.create(dataSource.getConnection(), expectedCourse);
        Assertions.assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void should_UpdateCourse() throws SQLException {
        Course course = courseDao.getAll(dataSource.getConnection()).get(0);
        course.setCourseName("TestUpdateCourse");
        course.setCourseDescription("Test");
        Course actualCourse = courseDao.update(dataSource.getConnection(), course);
        Assertions.assertEquals(course, actualCourse);
    }

    @Test
    void should_DeleteCourse() throws SQLException {
        courseDao.deleteById(dataSource.getConnection(), 2L);
        Assertions.assertTrue(courseDao.findById(dataSource.getConnection(), 2L).isEmpty());
    }

    @Test
    void should_ReturnCourseByName() throws SQLException {
        Course history = courseDao.findByCourseName(dataSource.getConnection(), "Social").get();
        Assertions.assertEquals("Social", history.getCourseName());
    }
}
