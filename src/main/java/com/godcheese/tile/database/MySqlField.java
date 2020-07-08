package com.godcheese.tile.database;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-21
 */
public class MySqlField implements DatabaseField {

    private String name;
    private SqlGenerateProperties.MySqlFieldType type;
    private int maxLength;
    private int minLength;
    private String comment;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public SqlGenerateProperties.MySqlFieldType getType() {
        return type;
    }

    @Override
    public void setType(SqlGenerateProperties.MySqlFieldType type) {
        this.type = type;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int getMinLength() {
        return minLength;
    }

    @Override
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public String getComment() {
        return comment;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "MySqlField{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", maxLength=" + maxLength +
                ", minLength=" + minLength +
                ", comment='" + comment + '\'' +
                '}';
    }
}
