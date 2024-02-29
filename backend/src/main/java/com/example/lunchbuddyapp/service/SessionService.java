package com.example.lunchbuddyapp.service;

import com.example.lunchbuddyapp.dto.SessionDTO;

import java.util.List;

public interface SessionService {
    List<SessionDTO> getAllSessions();

    SessionDTO getSessionBySessionID(Long sessionId);

    SessionDTO createSession(SessionDTO sessionDTO);

    SessionDTO endSession(Long sessionId);
    SessionDTO addUsersToSession(Long sessionId, SessionDTO sessionDTO);
}
