package com.godcheese.tile.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-17
 */
public class JdbcUtil {

    private static Connection instance = null;

    public static Connection getInstance(String driver, String url, String username, String password) {

        if (instance == null) {
            JdbcUtil jdbcUtil = new JdbcUtil();
            instance = jdbcUtil.connect(driver, url, username, password);
        }
        return instance;
    }

    private Connection connect(String driver, String url, String username, String password) {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
