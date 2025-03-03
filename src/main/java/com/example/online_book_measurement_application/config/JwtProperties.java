package com.example.online_book_measurement_application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("app.security.jwt")
public class JwtProperties {
    private String privateKey;
    private String prefix;
    private String authHeader;
    private long keyExpiresAt;
}
