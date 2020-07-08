package com.godcheese.tile.web.exception;


import com.godcheese.tile.web.http.FailureEntity;
import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-05-15
 */
public class BaseResponseException extends Exception {

    private Throwable cause;
    private String message;
    private int code = 0;
    private long timestamp = Instant.now().toEpochMilli();

    public BaseResponseException() {
    }

    public BaseResponseException(FailureEntity failureEntity) {
        this.message = failureEntity.getMessage();
        this.code = failureEntity.getCode();
        this.timestamp = failureEntity.getTimestamp();
    }

    public BaseResponseException(HttpStatus httpStatus) {
        this.message = httpStatus.getReasonPhrase();
        this.code = httpStatus.value();
    }

    public BaseResponseException(int code) {
        this.code = code;
    }

    public BaseResponseException(String message) {
        this.message = message;
    }

    public BaseResponseException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BaseResponseException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public BaseResponseException(String message, int code, Throwable cause) {
        this.message = message;
        this.code = code;
        this.cause = cause;
    }

    public BaseResponseException(Throwable cause) {
        super(cause);
        this.cause = cause;
    }

    public BaseResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
