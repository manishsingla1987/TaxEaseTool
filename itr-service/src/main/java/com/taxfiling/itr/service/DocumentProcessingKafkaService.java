
package com.taxfiling.itr.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentProcessingKafkaService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final DocumentService documentService;
    private final ObjectMapper objectMapper;

    public void processDocument(Long documentId, String documentType) {
        try {
            String payload = objectMapper.writeValueAsString(
                Map.of("documentId", documentId, "documentType", documentType));
            kafkaTemplate.send("document-processing", payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process document", e);
        }
    }

    @KafkaListener(topics = "document-processing")
    public void handleDocumentProcessing(String payload) {
        try {
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
            // Implement OCR processing logic here
            documentService.updateDocumentStatus(
                Long.valueOf(data.get("documentId").toString()),
                "PROCESSED"
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to process document", e);
        }
    }
}
