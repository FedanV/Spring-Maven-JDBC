package com.foxminded.vitaliifedan.task7.dao;

import com.foxminded.vitaliifedan.task7.models.Group;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GroupDao extends CrudDao<Group, Long> {
    Optional<Group> findByGroupName(Connection connection, String name) throws SQLException;

    List<Group> findAllGroupsWithLessOrEqualStudentCount(Connection connection, int count) throws SQLException;
}
