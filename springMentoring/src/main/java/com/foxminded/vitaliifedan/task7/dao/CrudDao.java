package com.foxminded.vitaliifedan.task7.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T, K> {

    T create(Connection connection, T entity) throws SQLException;

    T update(Connection connection, T entity) throws SQLException;

    List<T> getAll(Connection connection) throws SQLException;

    Optional<T> findById(Connection connection, K id) throws SQLException;

    void deleteById(Connection connection, K id) throws SQLException;

}
