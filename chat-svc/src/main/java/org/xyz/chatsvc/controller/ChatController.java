package org.xyz.chatsvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xyz.chatsvc.client.FastekClient;
import org.xyz.chatsvc.dto.ChatRequest;
import org.xyz.chatsvc.dto.ChatResponse;
import org.xyz.chatsvc.service.AiService;
import org.xyz.chatsvc.service.AiServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final AiService aiService;
    private final FastekClient fastekClient;

//    public ChatController(ChatService chatService) {
//        this.chatService = chatService;
//    }

    @PostMapping("/messages")
    public String sendMessage(
            @RequestBody ChatRequest request
    ) {
        return aiService.chat(request.message());
    }




}
