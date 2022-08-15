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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.transaction;

class StudentDaoImplTest {

    private static StudentDao studentDao;
    private static GroupDao groupDao;
    private static Connection connection;
    private static final int STUDENT_COUNT = 100;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        studentDao = new StudentDaoImpl();
        groupDao = new GroupDaoImpl();
        connection = DataSource.getConnection();
        SqlUtils.executeSqlScriptFile("task7/init_schema.sql");
        transaction(connection -> new GroupsGenerator(groupDao).generateGroups(connection, 10));
        transaction(connection -> new StudentsGenerator(studentDao, groupDao).generateStudents(connection, STUDENT_COUNT));
    }

    @AfterAll
    static void end() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile("task7/drop_schema.sql");
    }

    @Test
    void should_getAllStudents() throws SQLException {
        List<Student> all = studentDao.getAll(connection);
        Assertions.assertEquals(STUDENT_COUNT, all.size());
    }

    @Test
    void should_CreateStudent() throws SQLException {
        Student student = new Student(STUDENT_COUNT + 1, 3, "TestName", "TestLastName");
        studentDao.create(connection, student);
        Assertions.assertEquals(student, studentDao.findById(connection, STUDENT_COUNT + 1).get());
    }

    @Test
    void should_UpdateStudent() throws SQLException {
        Student student = new Student(99, 3, "Update", "LastName");
        studentDao.update(connection, student);
        Assertions.assertEquals(student, studentDao.findById(connection, 99).get());
    }

    @Test
    void should_deleteStudent() throws SQLException {
        studentDao.deleteById(connection, 70);
        Assertions.assertTrue(studentDao.findById(connection, 70).isEmpty());
    }

}
