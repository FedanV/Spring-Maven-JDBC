package com.foxminded.vitaliifedan.task7.dao.mappers;

import com.foxminded.vitaliifedan.task7.models.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper implements Mapper<Group> {

    @Override
    public Group get(ResultSet rs) throws SQLException {
        return new Group(
                rs.getInt("group_id"),
                rs.getString("group_name"));
    }
}
