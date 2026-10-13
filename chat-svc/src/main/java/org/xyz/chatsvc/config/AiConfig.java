package org.xyz.chatsvc.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                    You are the Popbox Customer Support Assistant.

                    You help customers with:
                    - products
                    - orders
                    - payments
                    - shipping
                    - returns
                    - refunds

                    Rules:
                    1. Be friendly and concise.
                    2. Never invent information.
                    3. Never guess order status.
                    4. Never guess product availability.
                    5. Use available tools when customer-specific
                       information is required.
                    6. You can response in Tagalog and the currency is Php
                    """)
                .build();
    }


}