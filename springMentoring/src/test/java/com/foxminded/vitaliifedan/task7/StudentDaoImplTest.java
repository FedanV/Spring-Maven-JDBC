package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.GroupDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.StudentDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.GroupsGenerator;
import com.foxminded.vitaliifedan.task7.misc.StudentsGenerator;
import com.foxminded.vitaliifedan.task7.models.Student;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.transaction;

class StudentDaoImplTest {

    private static StudentDao studentDao;
    private static GroupDao groupDao;
    private static final int STUDENT_COUNT = 100;
    private static DataSource dataSource;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty(DataSource.DRIVER_CLASS_NAME, "org.h2.Driver");
        properties.setProperty(DataSource.JDBC_URL, "jdbc:h2:mem:db1;MODE=PostgreSQL");
        properties.setProperty(DataSource.USERNAME, "sa");
        properties.setProperty(DataSource.PASSWORD, "");
        dataSource = new DataSource(properties);
        studentDao = new StudentDaoImpl();
        groupDao = new GroupDaoImpl();
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        transaction(dataSource, connection -> new GroupsGenerator(groupDao).generateGroups(connection, 10));
        transaction(dataSource, connection -> new StudentsGenerator(studentDao, groupDao).generateStudents(connection, STUDENT_COUNT));
    }

    @AfterAll
    static void end() {
        dataSource.close();
    }

    @Test
    void should_getAllStudents() throws SQLException {
        List<Student> all = studentDao.getAll(dataSource.getConnection());
        Assertions.assertEquals(STUDENT_COUNT, all.size());
    }

    @Test
    void should_CreateStudent() throws SQLException {
        Student expectedStudent = new Student(STUDENT_COUNT + 1, 3, "TestName", "TestLastName");
        Student actualStudent = studentDao.create(dataSource.getConnection(), expectedStudent);
        Assertions.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    void should_UpdateStudent() throws SQLException {
        Student student = new Student(99, 3, "Update", "LastName");
        Student updatedStudent = studentDao.update(dataSource.getConnection(), student);
        Assertions.assertEquals(student, updatedStudent);
    }

    @Test
    void should_deleteStudent() throws SQLException {
        studentDao.deleteById(dataSource.getConnection(), 70L);
        Assertions.assertTrue(studentDao.findById(dataSource.getConnection(), 70L).isEmpty());
    }

}
