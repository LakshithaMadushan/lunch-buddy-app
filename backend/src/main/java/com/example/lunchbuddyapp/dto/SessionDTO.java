package com.example.lunchbuddyapp.dto;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SessionDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6676430797679075898L;

    private Long sessionId;
    private String startedBy;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String pickedLocation;
    private Set<InvitationDTO> invitations;
    private UserDTO startedUser;
}
