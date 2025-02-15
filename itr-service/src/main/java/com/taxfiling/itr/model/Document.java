
package com.taxfiling.itr.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String documentType;
    private String fileName;
    private String fileUrl;
    private LocalDateTime uploadDate;
    private String financialYear;
}
