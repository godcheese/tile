package com.godcheese.tile.database;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-22
 */
public interface DatabaseField {

    String getName();

    void setName(String name);

    SqlGenerateProperties.MySqlFieldType getType();

    void setType(SqlGenerateProperties.MySqlFieldType type);

    int getMaxLength();

    void setMaxLength(int maxLength);

    int getMinLength();

    void setMinLength(int minLength);

    String getComment();

    void setComment(String comment);
}
