package com.example.lunchbuddyapp.controller;

import com.example.lunchbuddyapp.dto.InvitationDTO;
import com.example.lunchbuddyapp.exception.UnAuthorizeException;
import com.example.lunchbuddyapp.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/invitation")
@CrossOrigin(origins = {"http://localhost:4200"})
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
        return ResponseEntity.ok().body(invitationService.getAllInvitations());
    }

    @GetMapping("/user")
    public ResponseEntity<List<InvitationDTO>> getUserInvitations() {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }
        return ResponseEntity.ok().body(invitationService.getUserInvitations(userId));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<InvitationDTO>> getSessionInvitations(@PathVariable Long sessionId) {
        return ResponseEntity.ok().body(invitationService.getSessionInvitations(sessionId));
    }

    @PostMapping("/add/{sessionId}/{userId}")
    public ResponseEntity<InvitationDTO> addInvitation(@PathVariable Long sessionId,
                                                       @PathVariable String userId, @RequestBody InvitationDTO invitationDTO) {
        return ResponseEntity.ok().body(invitationService.createInvitation(sessionId, userId, invitationDTO));
    }

    @PatchMapping("/updateLocation/{sessionId}/{location}")
    public ResponseEntity<InvitationDTO> updateLocation(@PathVariable Long sessionId, @PathVariable String location) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        return ResponseEntity.ok().body(invitationService.updateLocation(sessionId, userId, location));
    }

    @PatchMapping("/accept/{sessionId}")
    public ResponseEntity<InvitationDTO> acceptInvitation(@PathVariable Long sessionId) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        return ResponseEntity.ok().body(invitationService.acceptInvitation(sessionId, userId));
    }

    @PatchMapping("/reject/{sessionId}")
    public ResponseEntity<InvitationDTO> rejectInvitation(@PathVariable Long sessionId) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        return ResponseEntity.ok().body(invitationService.rejectInvitation(sessionId, userId));
    }

    @PatchMapping("/leave/{sessionId}")
    public ResponseEntity<InvitationDTO> leaveInvitation(@PathVariable Long sessionId) {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        return ResponseEntity.ok().body(invitationService.leaveInvitation(sessionId, userId));
    }
}
