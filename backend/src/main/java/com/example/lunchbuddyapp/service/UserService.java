package com.example.lunchbuddyapp.service;

import com.example.lunchbuddyapp.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    public UserDTO getUserByUserId(String userId);
}
