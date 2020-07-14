package com.godcheese.tile.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-17
 */
public class DatabaseUtil {

    private static Connection instance = null;

    public static Connection getInstance(String driverClassName, String url, String username, String password) {

        if (instance == null) {
            DatabaseUtil databaseUtil = new DatabaseUtil();
            instance = databaseUtil.connect(driverClassName, url, username, password);
        }
        return instance;
    }

    private Connection connect(String driverClassName, String url, String username, String password) {
        Connection connection = null;
        try {
            Class.forName(driverClassName);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
