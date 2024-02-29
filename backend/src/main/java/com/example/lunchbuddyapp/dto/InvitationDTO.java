package com.example.lunchbuddyapp.dto;

import com.example.lunchbuddyapp.model.InvitationId;
import com.example.lunchbuddyapp.util.InvitationStatus;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class InvitationDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9076430797679075898L;

    private InvitationId invitationId;
    private UserDTO user;
    private SessionDTO session;
    private InvitationStatus invitationStatus;
    private String location;
}
