package com.example.lunchbuddyapp.service.impl;

import com.example.lunchbuddyapp.dto.InvitationDTO;
import com.example.lunchbuddyapp.dto.SessionDTO;
import com.example.lunchbuddyapp.mapper.InvitationMapper;
import com.example.lunchbuddyapp.mapper.SessionMapper;
import com.example.lunchbuddyapp.model.Invitation;
import com.example.lunchbuddyapp.model.InvitationId;
import com.example.lunchbuddyapp.repository.InvitationRepository;
import com.example.lunchbuddyapp.repository.SessionRepository;
import com.example.lunchbuddyapp.service.SessionService;
import com.example.lunchbuddyapp.service.UserService;
import com.example.lunchbuddyapp.util.InvitationStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final InvitationMapper invitationMapper;
    private final UserService userService;
    private final InvitationRepository invitationRepository;

    @Override
    public List<SessionDTO> getAllSessions() {
        return sessionRepository
                .findAll()
                .stream()
                .map(sessionMapper::toDto).toList();
    }

    @Override
    public SessionDTO getSessionBySessionID(Long sessionId) {
        return sessionRepository
                .findById(sessionId)
                .map(sessionMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public SessionDTO createSession(SessionDTO sessionDTO) {
        Set<InvitationDTO> invitations = sessionDTO.getInvitations();
        sessionDTO.setInvitations(null);
        sessionDTO.setStartedAt(LocalDateTime.now());
        SessionDTO savedSessionDTO = sessionMapper.toDto(sessionRepository.save(sessionMapper.toEntity(sessionDTO)));
        Long sessionId = savedSessionDTO.getSessionId();

        Set<Invitation> invitationsList = invitations.stream().map(invitationDTO -> {
            invitationDTO.setSession(savedSessionDTO);
            String userId = invitationDTO.getInvitationId().getUserId();
            invitationDTO.setUser(userService.getUserByUserId(userId));
            InvitationId invitationId = new InvitationId();
            invitationId.setSessionId(sessionId);
            invitationId.setUserId(userId);
            invitationDTO.setInvitationId(invitationId);
            return invitationMapper.toEntity(invitationDTO);
        }).collect(Collectors.toSet());

        invitationRepository.saveAll(invitationsList);
        return getSessionBySessionID(sessionId);
    }

    @Transactional
    public SessionDTO addUsersToSession(Long sessionId, SessionDTO sessionDTO) {
        SessionDTO savedSession = getSessionBySessionID(sessionId);
        Set<InvitationDTO> invitations = sessionDTO.getInvitations();
        Set<Invitation> invitationsList = invitations.stream().map(invitationDTO -> {
            invitationDTO.setSession(savedSession);
            String userId = invitationDTO.getInvitationId().getUserId();
            invitationDTO.setUser(userService.getUserByUserId(userId));
            invitationDTO.setInvitationStatus(InvitationStatus.PENDING);
            return invitationMapper.toEntity(invitationDTO);
        }).collect(Collectors.toSet());
        invitationRepository.saveAll(invitationsList);
        return getSessionBySessionID(sessionId);
    }

    @Override
    public SessionDTO endSession(Long sessionId) {
        List<String> locations = invitationRepository.getAllLocationsBySessionId(sessionId);
        int randomIndex = new Random().nextInt(locations != null ? locations.size() : 0);
        SessionDTO savedSessionDTO = getSessionBySessionID(sessionId);
        savedSessionDTO.setInvitations(null);
        savedSessionDTO.setEndedAt(LocalDateTime.now());
        savedSessionDTO.setPickedLocation(locations.get(randomIndex > -1 ? randomIndex : 0));
        return sessionMapper.toDto(sessionRepository.save(sessionMapper.toEntity(savedSessionDTO)));
    }

}
