package com.example.online_book_measurement_application.dto.response;

import com.example.online_book_measurement_application.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDto {
    private Long id;
    private String accessToken;
    private String email;

    public static AuthDto from(User user, String accessToken){
        return AuthDto.builder()
                .id(user.getUserId())
                .accessToken(accessToken)
                .email(user.getEmail())
                .build();
    }
}
