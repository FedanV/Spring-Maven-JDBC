package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.GroupDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.StudentDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.GroupsGenerator;
import com.foxminded.vitaliifedan.task7.misc.StudentsGenerator;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

class GroupDaoImplTest {

    private static GroupDao groupDao;
    private static final int GROUP_COUNT = 10;
    private static DataSource dataSource;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.setProperty(DataSource.DRIVER_CLASS_NAME, "org.h2.Driver");
        properties.setProperty(DataSource.JDBC_URL, "jdbc:h2:mem:db1;MODE=PostgreSQL");
        properties.setProperty(DataSource.USERNAME, "sa");
        properties.setProperty(DataSource.PASSWORD, "");
        dataSource = new DataSource(properties);
        groupDao = new GroupDaoImpl();
        StudentDao studentDao = new StudentDaoImpl();
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        new GroupsGenerator(groupDao).generateGroups(dataSource.getConnection(), GROUP_COUNT);
        new StudentsGenerator(studentDao, groupDao).generateStudents(dataSource.getConnection(), 200);
    }

    @AfterAll
    static void end() {
        dataSource.close();
    }

    @Test
    void should_getAllGroups() throws SQLException {
        int size = groupDao.getAll(dataSource.getConnection()).size();
        Assertions.assertEquals(GROUP_COUNT + 1, size);
    }

    @Test
    void should_CreateGroup() throws SQLException {
        Group expectedResult = new Group(GROUP_COUNT + 1, "test");
        Group createdGroup = groupDao.create(dataSource.getConnection(), expectedResult);
        Assertions.assertEquals(expectedResult, createdGroup);
    }

    @Test
    void should_UpdateGroup() throws SQLException {
        Group expectedResult = new Group(10, "TestGroup");
        Group updatedGroup = groupDao.update(dataSource.getConnection(), expectedResult);
        Assertions.assertEquals(expectedResult, updatedGroup);
    }

    @Test
    void should_DeleteGroup() throws SQLException {
        groupDao.deleteById(dataSource.getConnection(), 10L);
        Assertions.assertTrue(groupDao.findById(dataSource.getConnection(), 10L).isEmpty());
    }

    @Test
    void should_findAllGroupsWithLessOrEqualStudentCount() throws SQLException {
        List<Group> allGroupsWithLessOrEqualStudentCount = groupDao.findAllGroupsWithLessOrEqualStudentCount(dataSource.getConnection(), 20);
        Assertions.assertEquals(4, allGroupsWithLessOrEqualStudentCount.size());
    }

}
