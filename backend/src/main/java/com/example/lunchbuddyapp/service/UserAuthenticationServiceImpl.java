package com.example.lunchbuddyapp.service;

import com.example.lunchbuddyapp.dto.SignInRequestDTO;
import com.example.lunchbuddyapp.dto.SignUpRequestDTO;
import com.example.lunchbuddyapp.exception.UnAuthorizeException;
import com.example.lunchbuddyapp.exception.UserIdAlreadyExistsException;
import com.example.lunchbuddyapp.model.User;
import com.example.lunchbuddyapp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Value("${token.expirations}")
    Long tokenExpiration;

    @Value("${token.secret}")
    String tokenSecret;

    public String signUp(SignUpRequestDTO signUpRequestDTO) {
        if (!userRepository.existsById(signUpRequestDTO.getUserId())) {
            User user = User.builder()
                    .userId(signUpRequestDTO.getUserId())
                    .firstName(signUpRequestDTO.getFirstName())
                    .lastName(signUpRequestDTO.getLastName())
                    .password(passwordEncoder.encode(signUpRequestDTO.getPassword()))
                    .build();
            user = userRepository.save(user);
            return Jwts.builder()
                    .setSubject(user.getUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(SignatureAlgorithm.HS256, tokenSecret)
                    .compact();
        }
        throw new UserIdAlreadyExistsException("User already exists !");
    }

    public String signIn(SignInRequestDTO signInRequestDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(signInRequestDTO.getUserId());
        Map<String, Object> claims = new HashMap<>();
        userRepository.findById(signInRequestDTO.getUserId()).map(user -> {
            claims.put("firstName", user.getFirstName());
            claims.put("lastName", user.getLastName());
            claims.put("avatarUrl", user.getAvatarUrl());
            return user;
        });

        if (Objects.nonNull(userDetails)
                && passwordEncoder.matches(signInRequestDTO.getPassword(), userDetails.getPassword())) {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                    .signWith(SignatureAlgorithm.HS256, tokenSecret)
                    .compact();
        }
        throw new UnAuthorizeException("Invalid userId or password !");
    }
}
