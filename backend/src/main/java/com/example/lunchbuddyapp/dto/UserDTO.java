package com.example.lunchbuddyapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6676430797567075898L;

    private String userId;
    private String firstName;
    private String lastName;
    private String password;
    private String avatarUrl;
    private Set<InvitationDTO> invitations;
    private Boolean available = true;
}
