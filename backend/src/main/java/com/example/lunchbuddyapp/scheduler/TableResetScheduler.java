package com.example.lunchbuddyapp.scheduler;

import com.example.lunchbuddyapp.repository.InvitationRepository;
import com.example.lunchbuddyapp.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TableResetScheduler {
    private final InvitationRepository invitationRepository;
    private final SessionRepository sessionRepository;

    // Scheduled task to run at midnight every day
    // Because this app is targeting lunch sessions
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearTableAtMidnight() {
        // Delete all records from the invitation table
        invitationRepository.deleteAll();

        // Delete all records from the session table
        sessionRepository.deleteAll();
    }
}
