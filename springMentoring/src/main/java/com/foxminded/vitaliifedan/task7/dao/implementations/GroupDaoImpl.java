package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.GroupMapper;
import com.foxminded.vitaliifedan.task7.models.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDaoImpl implements GroupDao {

    private final GroupMapper groupMapper;

    public GroupDaoImpl() {
        this.groupMapper = new GroupMapper();
    }

    @Override
    public Group create(Connection connection, Group entity) throws SQLException {
        String create = "INSERT INTO groups(group_name) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(create, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getGroupName());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Creation group " + entity.getGroupName() + " failed");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("Unable to retrieve group id");
                }
                return new Group(generatedKeys.getLong("group_id"), entity.getGroupName());
            }
        }
    }

    @Override
    public Group update(Connection connection, Group entity) throws SQLException {
        String update = "UPDATE groups SET group_name=? WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, entity.getGroupName());
            statement.setLong(2, entity.getGroupId());
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Unable to update " + entity.getGroupName());
            }
            return new Group(entity.getGroupId(), entity.getGroupName());
        }
    }

    @Override
    public List<Group> getAll(Connection connection) throws SQLException {
        String findAll = "SELECT * FROM groups";
        List<Group> groups = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(groupMapper.get(resultSet));
                }
            }
        }
        return groups;
    }

    @Override
    public Optional<Group> findById(Connection connection, Long id) throws SQLException {
        String findById = "SELECT * FROM groups WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(findById)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(groupMapper.get(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Connection connection, Long id) throws SQLException {
        String deleteById = "DELETE FROM groups WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setLong(1, id);
            int affectedRow = statement.executeUpdate();
            if (affectedRow == 0) {
                throw new SQLException("Unable to deleted group with id " + id);
            }
        }
    }

    @Override
    public Optional<Group> findByGroupName(Connection connection, String name) throws SQLException {
        String findByGroupName = "SELECT * FROM groups WHERE group_name=?";
        try (PreparedStatement statement = connection.prepareStatement(findByGroupName)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(groupMapper.get(resultSet));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Group> findAllGroupsWithLessOrEqualStudentCount(Connection connection, int count) throws SQLException {
        List<Group> groups = new ArrayList<>();
        String findAllGroups = "SELECT st.group_id, gr.group_name " +
                "FROM students AS st " +
                "JOIN groups AS gr ON st.group_id = gr.group_id " +
                "GROUP BY st.group_id, gr.group_name " +
                "HAVING COUNT(student_id) <= ?";
        try (PreparedStatement statement = connection.prepareStatement(findAllGroups)) {
            statement.setInt(1, count);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groups.add(groupMapper.get(resultSet));
                }
            }
            return groups;
        }
    }
}
