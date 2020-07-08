package com.godcheese.tile.database;

import java.util.ArrayList;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-21
 */
public interface DatabaseTable<T extends DatabaseField> {

    String getName();

    void setName(String name);

    ArrayList<T> getFieldList();

    void setFieldList(ArrayList<T> fieldList);

    String getPrimaryKey();

    void setPrimaryKey(String primaryKey);

    T getField(String fieldName);
}
