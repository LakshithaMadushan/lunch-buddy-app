package com.example.lunchbuddyapp.controller;

import com.example.lunchbuddyapp.dto.JwtTokenResponseDTO;
import com.example.lunchbuddyapp.dto.SignInRequestDTO;
import com.example.lunchbuddyapp.dto.SignUpRequestDTO;
import com.example.lunchbuddyapp.service.UserAuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthController {
    private final UserAuthenticationServiceImpl userAuthenticationServiceImpl;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtTokenResponseDTO> signUp(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(JwtTokenResponseDTO
                        .builder()
                        .token(userAuthenticationServiceImpl.signUp(signUpRequestDTO))
                        .build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(JwtTokenResponseDTO
                        .builder()
                        .token(userAuthenticationServiceImpl.signIn(signInRequestDTO))
                        .build());
    }
}
