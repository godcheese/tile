package com.godcheese.tile.mybatis;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-25
 */
public class OrderBy {

    private static final Sort.Direction DEFAULT_DIRECTION = Sort.Direction.DESC;
    private String fields;
    private Sort.Direction direction;

    public OrderBy(String fields) {
        this.fields = fields;
    }

    public OrderBy(String fields, Sort.Direction direction) {
        this.fields = fields;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }
}
