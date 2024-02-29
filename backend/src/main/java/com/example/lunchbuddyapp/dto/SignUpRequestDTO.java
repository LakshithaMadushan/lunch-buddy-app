package com.example.lunchbuddyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private String password;
}
