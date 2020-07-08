package com.godcheese.tile.mybatis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class Sort {

    private static final Direction DEFAULT_DIRECTION = Sort.Direction.DESC;
    private String field;
    private Direction direction;

    public Sort(Direction direction, String field) {
        this.field = "`" + field + "`";
        this.direction = direction;
    }

    public Sort(Direction direction, String... fields) {
        List<String> fieldsList = fields == null ? new ArrayList<>() : Arrays.asList(fields);
        try {
            if (fieldsList.isEmpty()) {
                throw new Exception("empty fields");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String separator = ", ";
        StringBuilder fieldsStringBuilder = new StringBuilder();
        String fieldsString = "";
        if (!fieldsList.isEmpty()) {
            for (String field : fieldsList) {
                fieldsStringBuilder.append("`").append(field).append("`").append(separator);
            }
            fieldsString = fieldsStringBuilder.toString();
            if (fieldsStringBuilder.lastIndexOf(separator) > -1) {
                fieldsString = fieldsStringBuilder.substring(0, fieldsStringBuilder.lastIndexOf(separator));
            }
        }

        this.field = fieldsString;
        this.direction = direction;
    }

    public Sort(String field) {
        this.field = "`" + field + "`";
        this.direction = DEFAULT_DIRECTION;
    }

    public String getField() {
        return field;
    }

    public Direction getDirection() {
        return direction;
    }

    public static enum Direction {

        /**
         * 按倒序排序，降序排列(即：从大到小排序)
         */
        DESC("DESC"),

        /**
         * 按正序排序，升序排列(即：从小到大排序)
         */
        ASC("ASC");

        private String value;

        Direction(String value) {
            this.value = value;
        }
    }
}
