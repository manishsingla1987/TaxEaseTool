
package com.taxfiling.itr.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditLogKafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    public void logEvent(Long userId, String action, String details, String ipAddress) {
        try {
            String payload = objectMapper.writeValueAsString(
                Map.of("userId", userId, "action", action, 
                       "details", details, "ipAddress", ipAddress));
            kafkaTemplate.send("audit-logs", payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to log event", e);
        }
    }

    @KafkaListener(topics = "audit-logs")
    public void processAuditLog(String payload) {
        try {
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
            auditLogService.createLog(
                Long.valueOf(data.get("userId").toString()),
                data.get("action").toString(),
                data.get("details").toString(),
                data.get("ipAddress").toString()
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process audit log", e);
        }
    }
}
