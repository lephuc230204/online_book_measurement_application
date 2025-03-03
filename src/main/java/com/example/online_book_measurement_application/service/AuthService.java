package com.example.online_book_measurement_application.service;

import com.example.online_book_measurement_application.dto.request.SignInRequest;
import com.example.online_book_measurement_application.dto.response.AuthDto;
import com.example.online_book_measurement_application.dto.response.ResponseData;

public interface AuthService {
    ResponseData<AuthDto> logib(SignInRequest signInRequest);
}
