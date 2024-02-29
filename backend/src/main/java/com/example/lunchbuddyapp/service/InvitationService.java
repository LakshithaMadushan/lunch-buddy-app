package com.example.lunchbuddyapp.service;

import com.example.lunchbuddyapp.dto.InvitationDTO;

import java.util.List;

public interface InvitationService {
    List<InvitationDTO> getAllInvitations();

    List<InvitationDTO> getUserInvitations(String userId);

    List<InvitationDTO> getSessionInvitations(Long sessionId);

    InvitationDTO createInvitation(Long sessionId, String userId, InvitationDTO invitationDTO);


    InvitationDTO updateLocation(Long sessionId, String userId, String location);


    InvitationDTO acceptInvitation(Long sessionId, String userId);
    InvitationDTO rejectInvitation(Long sessionId, String userId);
    InvitationDTO leaveInvitation(Long sessionId, String userId);
}
