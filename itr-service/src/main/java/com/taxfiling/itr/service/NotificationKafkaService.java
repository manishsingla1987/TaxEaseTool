
package com.taxfiling.itr.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationKafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public void sendNotification(Long userId, String message, String type) {
        try {
            String payload = objectMapper.writeValueAsString(
                Map.of("userId", userId, "message", message, "type", type));
            kafkaTemplate.send("notifications", payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send notification", e);
        }
    }

    @KafkaListener(topics = "notifications")
    public void processNotification(String payload) {
        try {
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
            notificationService.createNotification(
                Long.valueOf(data.get("userId").toString()),
                data.get("message").toString(),
                data.get("type").toString()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process notification", e);
        }
    }
}
