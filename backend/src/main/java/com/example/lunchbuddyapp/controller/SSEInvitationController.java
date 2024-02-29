package com.example.lunchbuddyapp.controller;

import com.example.lunchbuddyapp.dto.InvitationDTO;
import com.example.lunchbuddyapp.exception.UnAuthorizeException;
import com.example.lunchbuddyapp.service.InvitationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/sse/invitation")
public class SSEInvitationController {
    @Value("${sse.frequency.per-second: 1}")
    private int sseFrequency;

    private final InvitationService invitationService;
    private final Map<Long, Flux<ServerSentEvent<List<InvitationDTO>>>> sseStreamsOne = new ConcurrentHashMap<>();
    private final Map<String, Flux<ServerSentEvent<List<InvitationDTO>>>> sseStreams = new ConcurrentHashMap<>();

    public SSEInvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping(value = "/session/{sessionId}", produces = "text/event-stream")
    public Flux<ServerSentEvent<List<InvitationDTO>>> getInvitationsInSession(@PathVariable Long sessionId) {
        Flux<ServerSentEvent<List<InvitationDTO>>> sseStream = Flux.interval(Duration.ofSeconds(sseFrequency))
                .map(sequence ->
                        ServerSentEvent.<List<InvitationDTO>>builder()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .data(invitationService.getSessionInvitations(sessionId))
                                .build()
                )
                .doOnCancel(() -> sseStreamsOne.remove(sessionId)); // Remove the stream when cancelled

        sseStreamsOne.put(sessionId, sseStream);
        return sseStream;
    }

    @GetMapping(value = "/user", produces = "text/event-stream")
    public Flux<ServerSentEvent<List<InvitationDTO>>> getUserInvitations() {
        String userId;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && (authentication.getPrincipal() instanceof String principal)) {
            userId = principal;
        } else {
            throw new UnAuthorizeException();
        }

        Flux<ServerSentEvent<List<InvitationDTO>>> sseStream = Flux.interval(Duration.ofSeconds(sseFrequency))
                .map(sequence ->
                        ServerSentEvent.<List<InvitationDTO>>builder()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .data(invitationService.getUserInvitations(userId))
                                .build()
                )
                .doOnCancel(() -> sseStreams.remove(userId)); // Remove the stream when cancelled

        sseStreams.put(userId, sseStream);
        return sseStream;
    }
}
