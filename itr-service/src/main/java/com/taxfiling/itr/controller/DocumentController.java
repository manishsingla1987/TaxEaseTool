
package com.taxfiling.itr.controller;

import com.taxfiling.itr.model.Document;
import com.taxfiling.itr.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    
    @Autowired
    private DocumentService documentService;
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId,
            @RequestParam("documentType") String documentType,
            @RequestParam("financialYear") String financialYear) {
        try {
            Document document = documentService.uploadDocument(file, userId, documentType, financialYear);
            return ResponseEntity.ok(document);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Document>> getUserDocuments(@PathVariable Long userId) {
        return ResponseEntity.ok(documentService.getUserDocuments(userId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<Document> getDocument(@PathVariable Long docId) {
        return ResponseEntity.ok(documentService.getDocument(docId));
    }

    @GetMapping("/download/{docId}")
    public ResponseEntity<?> downloadDocument(@PathVariable Long docId) {
        try {
            byte[] fileContent = documentService.downloadDocument(docId);
            return ResponseEntity.ok()
                .header("Content-Type", "application/octet-stream")
                .body(fileContent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Download failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<?> deleteDocument(@PathVariable Long docId) {
        documentService.deleteDocument(docId);
        return ResponseEntity.ok().build();
    }
}
