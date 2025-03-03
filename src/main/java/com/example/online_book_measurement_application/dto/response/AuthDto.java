package com.example.online_book_measurement_application.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private Long id;
    private String accessToken;
    private String userName;
}
