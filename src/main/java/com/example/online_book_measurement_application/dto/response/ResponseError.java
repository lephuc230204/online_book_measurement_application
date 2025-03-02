package com.example.online_book_measurement_application.dto.response;

import lombok.Getter;

@Getter
public class ResponseError<T> extends ResponseData<T> {
    public ResponseError(int status, String message) {
        super(status, message);
    }
}