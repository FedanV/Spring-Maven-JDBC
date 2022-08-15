package com.foxminded.vitaliifedan.task7.utils;

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

    public static void executeSqlScriptFile(String fileName) throws SQLException, IOException {
        transaction(connection ->
                SqlUtils.executeSqlScript(connection, ResourcesUtils.loadTextFileFromResources(fileName)));
    }


}
