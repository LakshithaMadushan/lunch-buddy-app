package com.example.lunchbuddyapp.controller;

import com.example.lunchbuddyapp.dto.SessionDTO;
import com.example.lunchbuddyapp.exception.UnAuthorizeException;
import com.example.lunchbuddyapp.model.InvitationId;
import com.example.lunchbuddyapp.service.SessionService;
import com.example.lunchbuddyapp.util.InvitationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/session")
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok().body(sessionService.getAllSessions());
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> getSessionBySessionID(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(sessionService.getSessionBySessionID(sessionId));
    }

    @PostMapping
    public ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        sessionDTO.setStartedBy(userId);
        sessionDTO.getInvitations()
                .forEach(invitationDTO -> {
                    if (invitationDTO.getInvitationStatus() == InvitationStatus.OWNER) {
                        InvitationId invitationId = new InvitationId();
                        invitationId.setUserId(userId);
                        invitationDTO.setInvitationId(invitationId);
                    }
                });

        SessionDTO session = sessionService.createSession(sessionDTO);
        Long sessionId = session.getSessionId();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessionId)
                .toUri();

        return ResponseEntity.created(location).body(session);
    }

    @PutMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> endSession(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(sessionService.endSession(sessionId));
    }

    @PostMapping("/addUsers/{sessionId}")
    public ResponseEntity<SessionDTO> addUsersToSession(@PathVariable Long sessionId, @RequestBody SessionDTO sessionDTO) {
        return ResponseEntity.ok().body(sessionService.addUsersToSession(sessionId, sessionDTO));
    }
}
