package com.foxminded.vitaliifedan.task7.misc;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.models.Group;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;

public class GroupsGenerator {

    private final SecureRandom random;

    private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final GroupDao groupDao;

    public GroupsGenerator(GroupDao groupDao) {
        this.groupDao = groupDao;
        this.random = new SecureRandom(new byte[]{1, 2, 3, 4});
    }

    public void generateGroups(Connection connection, int count) throws SQLException {
        for (int i = 0; i < count; i++) {
            String groupName = generateGroupName();
            groupDao.create(connection, new Group(i, groupName));
        }
    }

    private String generateGroupName() {
        return String.format("%s%s-%d", randomChar(alphabet), randomChar(alphabet), random.nextInt(100 - 10) + 10);
    }

    private char randomChar(char[] array) {
        return array[random.nextInt(array.length)];
    }
}
