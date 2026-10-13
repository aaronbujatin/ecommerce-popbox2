package org.xyz.chatsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xyz.chatsvc.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository
        extends JpaRepository<Message, UUID> {

    List<Message> findByConversationIdOrderByCreatedAtAsc(
            UUID conversationId
    );
}
