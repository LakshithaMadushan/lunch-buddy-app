package com.example.lunchbuddyapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class InvitationId implements Serializable {
    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "SESSION_ID", nullable = false, unique = true)
    private Long sessionId;
}
