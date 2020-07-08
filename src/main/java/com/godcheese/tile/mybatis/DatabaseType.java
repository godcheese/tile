package com.godcheese.tile.mybatis;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2019-03-05
 */
public enum DatabaseType {

    /**
     * MySQL
     */
    MYSQL("MYSQL"),

    /**
     * Oracle
     */
    ORACLE("ORACLE"),

    /**
     * SQL Server
     */
    SQL_SERVER("SQL_SERVER");

    private String value;

    DatabaseType(String value) {
        this.value = value;
    }
}