package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.GroupDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.GroupsGenerator;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

class GroupDaoImplTest {

    private static GroupDao groupDao;
    private static Connection connection;
    private static final int GROUP_COUNT = 10;

    @BeforeAll
    static void setup() throws SQLException, IOException {
        groupDao = new GroupDaoImpl();
        connection = DataSource.getConnection();
        SqlUtils.executeSqlScriptFile("task7/init_schema.sql");
        new GroupsGenerator(groupDao).generateGroups(connection, GROUP_COUNT);
    }

    @AfterAll
    static void end() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile("task7/drop_schema.sql");
    }

    @Test
    void should_getAllGroups() throws SQLException {
        int size = groupDao.getAll(connection).size();
        Assertions.assertEquals(GROUP_COUNT + 1, size);
    }

    @Test
    void should_CreateGroup() throws SQLException {
        Group expectedResult = new Group(GROUP_COUNT + 1, "test");
        groupDao.create(connection, expectedResult);
        Assertions.assertEquals(expectedResult, groupDao.findById(connection, 11).get());
    }

    @Test
    void should_UpdateGroup() throws SQLException {
        Group expectedResult = new Group(10, "TestGroup");
        groupDao.update(connection, expectedResult);
        Assertions.assertEquals(expectedResult, groupDao.findById(connection, 10).get());
    }

    @Test
    void should_DeleteGroup() throws SQLException {
        groupDao.deleteById(connection, 10);
        Assertions.assertTrue(groupDao.findById(connection, 10).isEmpty());
    }

}
