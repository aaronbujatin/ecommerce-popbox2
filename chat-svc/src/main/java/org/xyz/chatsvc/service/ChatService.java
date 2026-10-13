//package org.xyz.chatsvc.service;
//
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//import org.xyz.chatsvc.dto.ChatRequest;
//import org.xyz.chatsvc.dto.ChatResponse;
//import org.xyz.chatsvc.entity.Conversation;
//import org.xyz.chatsvc.entity.Message;
//import org.xyz.chatsvc.enums.ConversationStatus;
//import org.xyz.chatsvc.enums.SenderType;
//import org.xyz.chatsvc.repository.ConversationRepository;
//import org.xyz.chatsvc.repository.MessageRepository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class ChatService {
//
//    private final ConversationRepository conversationRepository;
//    private final MessageRepository messageRepository;
//    private final AiService aiService;
//
//    public ChatService(
//            ConversationRepository conversationRepository,
//            MessageRepository messageRepository,
//            AiService aiService
//    ) {
//        this.conversationRepository = conversationRepository;
//        this.messageRepository = messageRepository;
//        this.aiService = aiService;
//    }
//
//    @Transactional
//    public ChatResponse sendMessage(ChatRequest request) {
//
//        Conversation conversation;
//
//        if (request.conversationId() == null) {
//
//            conversation = new Conversation();
//
//            conversation.setUserId(
//                    getCurrentUserId()
//            );
//
//            conversation.setStatus(
//                    ConversationStatus.ACTIVE
//            );
//
//            conversation =
//                    conversationRepository.save(conversation);
//
//        } else {
//
//            conversation =
//                    conversationRepository
//                            .findById(request.conversationId())
//                            .orElseThrow();
//        }
//
//        // Save user message
//        Message userMessage = new Message();
//
//        userMessage.setConversationId(
//                conversation.getId()
//        );
//
//        userMessage.setSenderType(
//                SenderType.USER
//        );
//
//        userMessage.setContent(
//                request.message()
//        );
//
//        messageRepository.save(userMessage);
//
//        // Get conversation history
//        List<Message> history =
//                messageRepository
//                        .findByConversationIdOrderByCreatedAtAsc(
//                                conversation.getId()
//                        );
//
//        // Ask AI
//        String aiResponse =
//                aiService.generateResponse(history);
//
//        // Save AI message
//        Message aiMessage = new Message();
//
//        aiMessage.setConversationId(
//                conversation.getId()
//        );
//
//        aiMessage.setSenderType(
//                SenderType.AI
//        );
//
//        aiMessage.setContent(
//                aiResponse
//        );
//
//        messageRepository.save(aiMessage);
//
//        return ChatResponse.builder()
//                .conversationId(conversation.getId())
//                .message(aiResponse)
//                .build();
//    }
//
//    private UUID getCurrentUserId() {
//        // temporary
//        return UUID.randomUUID();
//    }
//}
