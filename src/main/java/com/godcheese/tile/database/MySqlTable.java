package com.godcheese.tile.database;

import java.util.ArrayList;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-21
 */
public class MySqlTable implements DatabaseTable<MySqlField> {

    private String name;
    private ArrayList<MySqlField> fieldList;
    private String primaryKey;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArrayList<MySqlField> getFieldList() {
        return fieldList;
    }

    @Override
    public void setFieldList(ArrayList<MySqlField> fieldList) {
        this.fieldList = fieldList;
    }

    @Override
    public String getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Override
    public MySqlField getField(String fieldName) {
        if (!fieldList.isEmpty()) {
            for (MySqlField field : fieldList) {
                if (field.getName().equals(fieldName)) {
                    return field;
                }
            }
        }
        return null;
    }
}
