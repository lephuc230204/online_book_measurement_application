package com.example.online_book_measurement_application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OtpRequest {
    @NotBlank(message = "otp code must be not blank !")
    private String otpCode;
}
