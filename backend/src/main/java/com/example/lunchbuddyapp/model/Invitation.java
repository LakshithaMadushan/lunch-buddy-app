package com.example.lunchbuddyapp.model;

import com.example.lunchbuddyapp.util.InvitationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "INVITATION")
public class Invitation  {
    @EmbeddedId
    private InvitationId invitationId;

    @JsonBackReference
    @ManyToOne
    @MapsId("userId") // Refers to the userId property of InvitationId
    @JoinColumn(name = "USER_ID")
    private User user;

    @JsonBackReference
    @ManyToOne
    @MapsId("sessionId") // Refers to the sessionId property of InvitationId
    @JoinColumn(name = "SESSION_ID")
    private Session session;

    @Enumerated(EnumType.STRING)
    @Column(name = "INVITATION_STATUS")
    private InvitationStatus invitationStatus;

    @Column(name = "LOCATION")
    private String location;
}
