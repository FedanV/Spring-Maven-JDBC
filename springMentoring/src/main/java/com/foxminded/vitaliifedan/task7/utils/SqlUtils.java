package com.foxminded.vitaliifedan.task7.utils;

import com.foxminded.vitaliifedan.task7.dao.datasource.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.foxminded.vitaliifedan.task7.utils.TransactionUtils.transaction;

public class SqlUtils {

    private SqlUtils() {
    }

    public static void executeSqlScript(Connection connection, String sqlScript) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sqlScript);
        }
    }

    public static void executeSqlScriptFile(DataSource dataSource, String fileName) throws SQLException, IOException {
        transaction(dataSource, connection ->
                SqlUtils.executeSqlScript(connection, ResourcesUtils.loadTextFileFromResources(fileName)));
    }


}
