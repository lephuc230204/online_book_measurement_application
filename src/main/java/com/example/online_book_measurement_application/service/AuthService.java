package com.example.online_book_measurement_application.service;

import com.example.online_book_measurement_application.dto.request.SignInRequest;
import com.example.online_book_measurement_application.dto.request.SignUpRequest;
import com.example.online_book_measurement_application.dto.response.AuthDto;
import com.example.online_book_measurement_application.dto.response.ResponseData;

public interface AuthService {
    ResponseData<String> register(SignUpRequest signUpRequest);


    //    ResponseData<String> logout(HttpServletRequest request, HttpServletResponse response);
    ResponseData<String> confirmUser(long userId, String otpCode);
    ResponseData<AuthDto> login(SignInRequest signInRequest);
}
