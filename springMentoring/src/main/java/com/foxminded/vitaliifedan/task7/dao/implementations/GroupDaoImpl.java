package com.foxminded.vitaliifedan.task7.dao.implementations;

import com.foxminded.vitaliifedan.task7.dao.GroupDao;
import com.foxminded.vitaliifedan.task7.dao.mappers.GroupMapper;
import com.foxminded.vitaliifedan.task7.models.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupDaoImpl implements GroupDao {

    private final GroupMapper groupMapper;

    public GroupDaoImpl() {
        this.groupMapper = new GroupMapper();
    }

    @Override
    public void create(Connection connection, Group entity) throws SQLException {
        String create = "INSERT INTO groups(group_name) VALUES(?)";
        try (PreparedStatement statement = connection.prepareStatement(create)) {
            statement.setString(1, entity.getGroupName());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(Connection connection, Group entity) throws SQLException {
        String update = "UPDATE groups SET group_name=? WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(update)) {
            statement.setString(1, entity.getGroupName());
            statement.setInt(2, entity.getGroupId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Group> getAll(Connection connection) throws SQLException {
        String findAll = "SELECT * FROM groups";
        List<Group> groups = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(findAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(groupMapper.get(resultSet));
            }
        }
        return groups;
    }

    @Override
    public Optional<Group> findById(Connection connection, Integer id) throws SQLException {
        String findById = "SELECT * FROM groups WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(findById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(groupMapper.get(resultSet));
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Connection connection, Integer id) throws SQLException {
        String deleteById = "DELETE FROM groups WHERE group_id=?";
        try (PreparedStatement statement = connection.prepareStatement(deleteById)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public Optional<Group> findByGroupName(Connection connection, String name) throws SQLException {
        String findByGroupName = "SELECT * FROM groups WHERE group_name=?";
        try (PreparedStatement statement = connection.prepareStatement(findByGroupName)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(groupMapper.get(resultSet));
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
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                groups.add(groupMapper.get(resultSet));
            }
            return groups;
        }
    }
}
