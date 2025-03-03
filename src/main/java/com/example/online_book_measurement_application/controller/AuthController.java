package com.example.online_book_measurement_application.controller;

import com.example.online_book_measurement_application.dto.request.OtpRequest;
import com.example.online_book_measurement_application.dto.request.SignInRequest;
import com.example.online_book_measurement_application.dto.request.SignUpRequest;
import com.example.online_book_measurement_application.dto.response.AuthDto;
import com.example.online_book_measurement_application.dto.response.ResponseData;
import com.example.online_book_measurement_application.dto.response.ResponseError;
import com.example.online_book_measurement_application.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Quản lý xác thực người dùng")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Operation(summary = "Đăng nhập", description = "API cho phép người dùng đăng nhập bằng email và mật khẩu")
    @PostMapping("/login")
    public ResponseEntity<ResponseData<AuthDto>> login(@Valid @RequestBody SignInRequest signInRequest) {
        ResponseData<AuthDto> response = authService.login(signInRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @Operation(summary = "Đăng ký tài khoản", description = "API cho phép người dùng đăng ký tài khoản mới bằng email và mật khẩu")
    @PostMapping("/register")
    public ResponseEntity<ResponseData<String>> register(@Valid @RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authService.register(signUpRequest));
    }

    @Operation(summary = "Xác nhận tài khoản", description = "API để xác nhận tài khoản thông qua mã OTP được gửi đến email")
    @PostMapping("/confirm/{userId}")
    public ResponseData<String> confirm(@PathVariable Long userId, @RequestBody OtpRequest otpRequest) {
        log.info("Confirm user, userId={}, otpCode={}", userId, otpRequest.getOtpCode());

        try {
            authService.confirmUser(userId, otpRequest.getOtpCode());
            return new ResponseData<>(200, "User has confirmed successfully");
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(400, "Confirm was failed");
        }
    }
}
