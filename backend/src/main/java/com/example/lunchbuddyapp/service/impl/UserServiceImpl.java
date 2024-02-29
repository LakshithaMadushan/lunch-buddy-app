package com.example.lunchbuddyapp.service.impl;

import com.example.lunchbuddyapp.dto.UserDTO;
import com.example.lunchbuddyapp.mapper.UserMapper;
import com.example.lunchbuddyapp.model.Invitation;
import com.example.lunchbuddyapp.repository.InvitationRepository;
import com.example.lunchbuddyapp.repository.UserRepository;
import com.example.lunchbuddyapp.service.UserService;
import com.example.lunchbuddyapp.util.InvitationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InvitationRepository invitationRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> {
                    UserDTO userDTO = userMapper.toDto(user);
                    List<Invitation> userInvitations = invitationRepository
                            .findAvailableInvitationsByUserId(userDTO.getUserId());
                    boolean notAvailable = userInvitations
                            .stream()
                            .anyMatch(invitation ->
                                    invitation.getInvitationStatus() == InvitationStatus.OWNER ||
                                            invitation.getInvitationStatus() == InvitationStatus.ACCEPTED);
                    userDTO.setAvailable(!notAvailable);
                    return userDTO;
                })
                .toList();
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        return userRepository
                .findById(userId)
                .map(userMapper::toDto)
                .orElse(null);
    }


}
