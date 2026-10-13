package org.xyz.chatsvc.dto;

import java.util.UUID;

public record ChatRequest(
        UUID conversationId,
        String message
) {
}