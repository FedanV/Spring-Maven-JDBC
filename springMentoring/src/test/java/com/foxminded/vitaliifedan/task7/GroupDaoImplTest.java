package com.foxminded.vitaliifedan.task7;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.StudentDao;
import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;
import com.foxminded.vitaliifedan.task7.dao.implementations.GroupDaoImpl;
import com.foxminded.vitaliifedan.task7.dao.implementations.StudentDaoImpl;
import com.foxminded.vitaliifedan.task7.misc.GroupsGenerator;
import com.foxminded.vitaliifedan.task7.misc.StudentsGenerator;
import com.foxminded.vitaliifedan.task7.models.Group;
import com.foxminded.vitaliifedan.task7.utils.ResourcesUtils;
import com.foxminded.vitaliifedan.task7.utils.SqlUtils;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.transaction;

class GroupDaoImplTest {

    private static GroupDao groupDao;
    private static final int GROUP_COUNT = 10;
    private static DataSource dataSource;
    private static StudentDao studentDao;

    @BeforeAll
    static void setup() throws IOException {
        Properties properties = ResourcesUtils.loadPropertiesFromResources("task7/database_test.properties");
        dataSource = new DataSource(properties);
        groupDao = new GroupDaoImpl();
        studentDao = new StudentDaoImpl();
    }

    @BeforeEach
    void initEach() throws SQLException, IOException {
        SqlUtils.executeSqlScriptFile(dataSource, "task7/init_schema.sql");
        transaction(dataSource, connection -> {
            new GroupsGenerator(groupDao).generateGroups(dataSource.getConnection(), GROUP_COUNT);
            new StudentsGenerator(studentDao, groupDao).generateStudents(dataSource.getConnection(), 200);
        });
    }

    @AfterAll
    static void end() {
        dataSource.close();
    }

    @Test
    void should_getAllGroups() throws SQLException {
        int size = groupDao.getAll(dataSource.getConnection()).size();
        Assertions.assertEquals(GROUP_COUNT, size);
    }

    @Test
    void should_CreateGroup() throws SQLException {
        Group testGroup = new Group("test");
        Group createdGroup = groupDao.create(dataSource.getConnection(), testGroup);
        Assertions.assertNotNull(createdGroup);
    }

    @Test
    void should_UpdateGroup() throws SQLException {
        Group expectedResult = new Group(9, "TestGroup");
        Group updatedGroup = groupDao.update(dataSource.getConnection(), expectedResult);
        Assertions.assertEquals(expectedResult, updatedGroup);
    }

    @Test
    void should_DeleteGroup() throws SQLException {
        groupDao.deleteById(dataSource.getConnection(), 9L);
        Assertions.assertTrue(groupDao.findById(dataSource.getConnection(), 9L).isEmpty());
    }

    @Test
    void should_findAllGroupsWithLessOrEqualStudentCount() throws SQLException {
        List<Group> allGroupsWithLessOrEqualStudentCount = groupDao.findAllGroupsWithLessOrEqualStudentCount(dataSource.getConnection(), 20);
        Assertions.assertEquals(7, allGroupsWithLessOrEqualStudentCount.size());
    }

}
