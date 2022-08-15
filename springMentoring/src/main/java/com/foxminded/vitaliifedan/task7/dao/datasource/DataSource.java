package com.foxminded.vitaliifedan.task7.dao.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final HikariConfig config;

    static {
        try {
            config = new HikariConfig(
                    Path.of(DataSource.class.getClassLoader().getResource("task7/database.properties").toURI()).toString()
            );
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private static final HikariDataSource ds;

    static {
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//        config.setAutoCommit(false);
        ds = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
