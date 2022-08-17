package com.foxminded.vitaliifedan.task7.utils;

import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionUtils {
    public static void transaction(DataSource dataSource, ConnectionConsumer consumer) throws SQLException, IOException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                consumer.consume(connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new SQLException("Exception in transaction", e);
            }
        }
    }

    public interface ConnectionConsumer {
        void consume(Connection connection) throws SQLException, IOException;
    }

}
