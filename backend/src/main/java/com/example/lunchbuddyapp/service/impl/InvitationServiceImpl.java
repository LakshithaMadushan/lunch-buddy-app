package com.example.lunchbuddyapp.service.impl;

import com.example.lunchbuddyapp.dto.InvitationDTO;
import com.example.lunchbuddyapp.dto.SessionDTO;
import com.example.lunchbuddyapp.dto.UserDTO;
import com.example.lunchbuddyapp.mapper.InvitationMapper;
import com.example.lunchbuddyapp.mapper.SessionMapper;
import com.example.lunchbuddyapp.mapper.UserMapper;
import com.example.lunchbuddyapp.model.InvitationId;
import com.example.lunchbuddyapp.repository.InvitationRepository;
import com.example.lunchbuddyapp.repository.SessionRepository;
import com.example.lunchbuddyapp.repository.UserRepository;
import com.example.lunchbuddyapp.service.InvitationService;
import com.example.lunchbuddyapp.service.UserService;
import com.example.lunchbuddyapp.util.InvitationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InvitationServiceImpl implements InvitationService {
    private final InvitationRepository invitationRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final InvitationMapper invitationMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final SessionMapper sessionMapper;

    @Override
    public List<InvitationDTO> getAllInvitations() {
        return invitationRepository
                .findAll()
                .stream()
                .map(invitationMapper::toDto)
                .toList();
    }

    @Override
    public List<InvitationDTO> getUserInvitations(String userId) {
        return invitationRepository.findAvailableInvitationsByUserId(userId)
                .stream()
                .map(invitation -> {
                    InvitationDTO invitationDto = invitationMapper.toDto(invitation);
                    UserDTO userByUserId = userService.getUserByUserId(invitationDto.getSession().getStartedBy());
                    invitationDto.getSession().setStartedUser(userByUserId);
                    return invitationDto;
                })
                .toList();
    }

    public List<InvitationDTO> getSessionInvitations(Long sessionId) {
        return invitationRepository.findAvailableInvitationsBySessionId(sessionId)
                .stream()
                .map(invitation -> {
                    InvitationDTO invitationDto = invitationMapper.toDto(invitation);
                    invitationDto.setUser(userMapper.toDto(invitation.getUser()));
                    return invitationDto;
                })
                .toList();
    }


    @Override
    public InvitationDTO createInvitation(Long sessionId, String userId, InvitationDTO invitationDTO) {
        SessionDTO sessionDTO = sessionRepository.findById(sessionId).map(sessionMapper::toDto).orElse(null);
        UserDTO userDTO = userRepository.findById(userId).map(userMapper::toDto).orElse(null);
        invitationDTO.setSession(sessionDTO);
        invitationDTO.setUser(userDTO);
        InvitationId invitationId = new InvitationId();
        invitationId.setSessionId(sessionId);
        invitationId.setUserId(userId);
        invitationDTO.setInvitationId(invitationId);
        return invitationMapper.toDto(invitationRepository.save(invitationMapper.toEntity(invitationDTO)));
    }

    @Override
    public InvitationDTO updateLocation(Long sessionId, String userId, String location) {
        InvitationDTO invitationDTO = invitationMapper.toDto(invitationRepository.findInvitationBySessionIdUserId(sessionId, userId));
        invitationDTO.setLocation(location);
        return invitationMapper.toDto(invitationRepository.save(invitationMapper.toEntity(invitationDTO)));
    }

    @Override
    public InvitationDTO acceptInvitation(Long sessionId, String userId) {
        InvitationDTO invitationDTO = invitationMapper.toDto(invitationRepository.findInvitationBySessionIdUserId(sessionId, userId));
        invitationDTO.setInvitationStatus(InvitationStatus.ACCEPTED);
        invitationDTO.setLocation(null);
        return invitationMapper.toDto(invitationRepository.save(invitationMapper.toEntity(invitationDTO)));
    }

    @Override
    public InvitationDTO rejectInvitation(Long sessionId, String userId) {
        InvitationDTO invitationDTO = invitationMapper.toDto(invitationRepository.findInvitationBySessionIdUserId(sessionId, userId));
        invitationDTO.setInvitationStatus(InvitationStatus.REJECTED);
        invitationDTO.setLocation(null);
        return invitationMapper.toDto(invitationRepository.save(invitationMapper.toEntity(invitationDTO)));
    }

    @Override
    public InvitationDTO leaveInvitation(Long sessionId, String userId) {
        InvitationDTO invitationDTO = invitationMapper.toDto(invitationRepository.findInvitationBySessionIdUserId(sessionId, userId));
        invitationDTO.setInvitationStatus(InvitationStatus.LEAVE);
        invitationDTO.setLocation(null);
        return invitationMapper.toDto(invitationRepository.save(invitationMapper.toEntity(invitationDTO)));
    }
}
