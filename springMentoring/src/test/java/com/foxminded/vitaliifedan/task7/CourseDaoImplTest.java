package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.CourseDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.CourseDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.CoursesGenerator;
import com.foxminded.vitaliifedan.task7.models.Course;
import com.foxminded.vitaliifedan.task7.utils.ResourcesUtils;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.transaction;

class CourseDaoImplTest {

    private static CourseDao courseDao;
    private static DataSource dataSource;

    @BeforeAll
    static void setup() throws IOException {
        Properties properties = ResourcesUtils.loadPropertiesFromResources("task7/database_test.properties");
        courseDao = new CourseDaoImpl();
        dataSource = new DataSource(properties);
    }

    @BeforeEach
    void initEach() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        transaction(dataSource, connection -> new CoursesGenerator(courseDao).generateCourses(dataSource.getConnection()));
    }

    @AfterAll
    static void end() {
        dataSource.close();
    }

    @Test
    void should_CreateCourse() throws SQLException {
        Course testCourse = new Course("TestCourse", "Description");
        Course actualCourse = courseDao.create(dataSource.getConnection(), testCourse);
        Assertions.assertNotNull(actualCourse);
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
