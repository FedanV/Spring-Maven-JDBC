package com.foxminded.vitaliifedan.task7.dao.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource implements AutoCloseable {

    public static final String DRIVER_CLASS_NAME = "dataSource.driver";
    public static final String JDBC_URL = "dataSource.jdbcUrl";
    public static final String USERNAME = "dataSource.username";
    public static final String PASSWORD = "dataSource.password";
    private final HikariDataSource ds;

    public DataSource(Properties properties) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(properties.getProperty(DRIVER_CLASS_NAME));
        config.setJdbcUrl(properties.getProperty(JDBC_URL));
        config.setUsername(properties.getProperty(USERNAME));
        config.setPassword(properties.getProperty(PASSWORD));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(20);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    @Override
    public void close() {
        ds.close();
    }
}
