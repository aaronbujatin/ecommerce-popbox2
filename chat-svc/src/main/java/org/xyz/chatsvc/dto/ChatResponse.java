package org.xyz.chatsvc.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatResponse(
        UUID conversationId,
        String message,
        boolean escalated
) {
}