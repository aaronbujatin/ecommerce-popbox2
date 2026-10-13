package org.xyz.chatsvc.service;


import org.xyz.chatsvc.entity.Message;

import java.util.List;

public interface AiService {

    String generateResponse(
            List<Message> messages
    );

    String chat(String message);

    String chat2(String message);
}