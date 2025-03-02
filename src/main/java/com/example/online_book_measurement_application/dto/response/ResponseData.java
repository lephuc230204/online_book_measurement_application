package com.example.online_book_measurement_application.dto.response;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponseData <T> implements Serializable {
    private final int status;
    private final String message;
    private T data;
    /**GET, POST*/
    public ResponseData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    /**PUT, PATCH, DELETE**/
    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }


}
