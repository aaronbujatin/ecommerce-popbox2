package org.xyz.chatsvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.xyz.chatsvc.component.ProductTools;
import org.xyz.chatsvc.entity.Message;
import java.util.List;

@Service
public class AiServiceImpl implements AiService {
//
    private ChatClient chatClient;
    private ProductTools productTools;

    public AiServiceImpl(ChatClient chatClient, ProductTools productTools) {
        this.chatClient = chatClient;
        this.productTools = productTools;
    }
//    private final String systemPrompt;

//    public AiServiceImpl(
//            @Value("src/main/resources/prompts/customer-support.txt")
//            Resource promptResource
//    ) throws IOException {
//
//        this.systemPrompt = new String(
//                                promptResource.getInputStream().readAllBytes(),
//                                StandardCharsets.UTF_8
//                            );
//    }

    @Override
    public String generateResponse(
            List<Message> messages
    ) {
        // Call LLM here
        return "Hello! How can I help you today?";
    }

    public String chat(String message) {
        return chatClient.prompt()
//                .user(message)
//                .tools(productTools)
                .call()
                .content();
    }

    @Override
    public String chat2(String message) {





        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }


}