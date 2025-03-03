package com.example.online_book_measurement_application.service.impl;

import com.example.online_book_measurement_application.config.JwtProvider;
import com.example.online_book_measurement_application.dto.request.SignInRequest;
import com.example.online_book_measurement_application.dto.request.SignUpRequest;
import com.example.online_book_measurement_application.dto.response.AuthDto;
import com.example.online_book_measurement_application.dto.response.ResponseData;
import com.example.online_book_measurement_application.dto.response.ResponseError;
import com.example.online_book_measurement_application.entity.Role;
import com.example.online_book_measurement_application.entity.User;
import com.example.online_book_measurement_application.enums.Status;
import com.example.online_book_measurement_application.repository.RoleRepository;
import com.example.online_book_measurement_application.repository.UserRepository;
import com.example.online_book_measurement_application.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public ResponseData<String> register(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseError<>(400, "Email address already in use");
        }
        Role role = roleRepository.findByName("ROLE_USER").orElse(null);
        if (role == null) {
            log.error("Role not found");
            return new ResponseError<>(400, "Role not found");
        }
        String otpCode = String.format("%06d", new Random().nextInt(99999));
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .role(role)
                .otp(otpCode)
                .dob(signUpRequest.getDob())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .status(Status.NONACTIVE)
                .build();
        userRepository.save(user);

        kafkaTemplate.send("confirm-account-topic", String.format("email=%s,id=%s,otpCode=%s", user.getEmail(), user.getUserId(), otpCode));
        log.info("User {} registered successfully with ID {}, pls check email to confirm OTP. Thanks!", user.getEmail(), user.getUserId());
        return new ResponseData<>(200, "Success register new user. Please check your email for confirmation", "Id: " + user.getUserId());
    }
    @Override
    public ResponseData<String> confirmUser(long userId, String otpCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Check if the OTP matches
        if (!otpCode.equals(user.getOtp())) {
            log.error("OTP does not match for userId={}", userId);
            throw new IllegalArgumentException("OTP is incorrect");
        }
        user.setStatus(Status.ACTIVE);
        user.setSetCreatedDate(LocalDate.now());
        userRepository.save(user);
        return new ResponseData<>(200, "confirm successfully");
    }

    @Override
    public ResponseData<AuthDto> login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email :"+ signInRequest.getEmail()));

        if (!user.isEnabled()){
            throw new IllegalArgumentException("Account is not active !");
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
        } catch (AuthenticationException e){
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accesToken = jwtProvider.generateToken(authentication);
        log.info("User {} logged in successfully with ", user.getEmail());

        return new ResponseData<>(200,"Login success", AuthDto.from(user,accesToken));
    }
}
