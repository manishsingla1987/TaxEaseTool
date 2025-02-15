
package com.taxfiling.itr.service;

import com.taxfiling.itr.model.Document;
import com.taxfiling.itr.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private NotificationService notificationService;

    private final String uploadDir = "uploads";

    public Document uploadDocument(MultipartFile file, Long userId, String documentType, String financialYear) throws IOException {
        // Create uploads directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Generate unique filename
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        
        // Save file to disk
        Files.write(filePath, file.getBytes());

        // Create document record
        Document document = new Document();
        document.setUserId(userId);
        document.setDocumentType(documentType);
        document.setFileName(file.getOriginalFilename());
        document.setFileUrl("/uploads/" + fileName);
        document.setUploadDate(LocalDateTime.now());
        document.setFinancialYear(financialYear);
        
        Document savedDocument = documentRepository.save(document);
        
        notificationService.createNotification(
            userId,
            "Document " + documentType + " for FY " + financialYear + " uploaded successfully.",
            "DOCUMENT_UPLOAD"
        );
        
        return savedDocument;
    }

    public List<Document> getUserDocuments(Long userId) {
        return documentRepository.findByUserId(userId);
    }

    public Document getDocument(Long documentId) {
        return documentRepository.findById(documentId)
            .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    public void deleteDocument(Long documentId) throws IOException {
        Document document = getDocument(documentId);
        
        // Delete file from disk
        Path filePath = Paths.get(document.getFileUrl().replace("/uploads/", uploadDir + "/"));
        Files.deleteIfExists(filePath);
        
        // Delete record from database
        documentRepository.delete(document);
        
        notificationService.createNotification(
            document.getUserId(),
            "Document " + document.getDocumentType() + " deleted successfully.",
            "DOCUMENT_DELETE"
        );
    }
}
