package com.example.lunchbuddyapp.repository;

import com.example.lunchbuddyapp.model.Invitation;
import com.example.lunchbuddyapp.model.InvitationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, InvitationId> {
    @Query("""
            SELECT i FROM Invitation i 
            INNER JOIN Session s 
            ON i.session.sessionId = s.sessionId 
            WHERE DATE(s.startedAt) = CURRENT_DATE 
            AND i.invitationStatus 
            NOT IN (com.example.lunchbuddyapp.util.InvitationStatus.EXPIRED)
            AND i.user.userId = :userId
                        """)
    List<Invitation> findAvailableInvitationsByUserId(String userId);


    @Query("""
            SELECT i FROM Invitation i 
            INNER JOIN User u 
            ON i.user.userId = u.userId 
            WHERE DATE(i.session.startedAt) = CURRENT_DATE 
            AND i.invitationStatus 
            IN (com.example.lunchbuddyapp.util.InvitationStatus.OWNER,
            com.example.lunchbuddyapp.util.InvitationStatus.ACCEPTED,
            com.example.lunchbuddyapp.util.InvitationStatus.PENDING)
            AND i.session.sessionId = :sessionId
                        """)
    List<Invitation> findAvailableInvitationsBySessionId(Long sessionId);

    @Query("""
            SELECT i.location FROM Invitation i 
            WHERE 
            i.location IS NOT NULL AND 
            i.invitationStatus 
            IN (com.example.lunchbuddyapp.util.InvitationStatus.OWNER,
            com.example.lunchbuddyapp.util.InvitationStatus.ACCEPTED)
            AND i.session.sessionId = :sessionId
                        """)
    List<String> getAllLocationsBySessionId(Long sessionId);

    @Query("""
            SELECT i FROM Invitation i 
            WHERE i.session.sessionId = :sessionId
            AND i.user.userId = :userId
                        """)
    Invitation findInvitationBySessionIdUserId(Long sessionId, String userId);

}
