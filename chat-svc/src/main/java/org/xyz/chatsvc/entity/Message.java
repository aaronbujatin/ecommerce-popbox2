package org.xyz.chatsvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.xyz.chatsvc.enums.SenderType;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "conversation_id", nullable = false)
    private UUID conversationId;

    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
