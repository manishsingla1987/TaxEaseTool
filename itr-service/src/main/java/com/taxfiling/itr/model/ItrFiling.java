
package com.taxfiling.itr.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "itr_filings")
public class ItrFiling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String financialYear;
    private String itrType;
    private BigDecimal totalIncome;
    private BigDecimal taxPayable;
    private String status;
    private LocalDateTime submissionDate;
    private LocalDateTime lastModified;
}
