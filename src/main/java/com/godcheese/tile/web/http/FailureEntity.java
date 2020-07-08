package com.godcheese.tile.web.http;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-05-29
 */
public interface FailureEntity {

    /**
     * message
     *
     * @return String
     */
    String getMessage();

    /**
     * code
     *
     * @return int
     */
    int getCode();

    /**
     * @return long
     */
    long getTimestamp();
}