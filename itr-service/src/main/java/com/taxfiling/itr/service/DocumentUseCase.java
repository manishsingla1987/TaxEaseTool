
package com.taxfiling.itr.service;

import com.taxfiling.itr.model.Document;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface DocumentUseCase {
    Document uploadDocument(MultipartFile file, Long userId, String documentType, String financialYear);
    List<Document> getUserDocuments(Long userId);
    Document getDocument(Long id);
    void deleteDocument(Long id);
    void processDocumentOcr(Long documentId);
}
