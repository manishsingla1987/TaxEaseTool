
package com.taxfiling.itr.controller;

import com.taxfiling.itr.model.ItrFiling;
import com.taxfiling.itr.service.ItrFilingService;
import com.taxfiling.itr.service.NotificationService;
import com.taxfiling.itr.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/itr")
public class ItrFilingController {

    @Autowired
    private ItrFilingService itrFilingService;

    @Autowired
    private TaxCalculationService taxCalculationService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/submit")
    public ResponseEntity<?> submitItr(@RequestBody ItrFiling itrFiling) {
        itrFiling.setTaxPayable(taxCalculationService.calculateTax(itrFiling.getTotalIncome()));
        itrFiling.setStatus("FILED");
        itrFiling.setSubmissionDate(LocalDateTime.now());
        itrFiling.setLastModified(LocalDateTime.now());
        
        ItrFiling saved = itrFilingService.submitItr(itrFiling);
        notificationService.createNotification(
            saved.getUserId(),
            "Your ITR for FY " + saved.getFinancialYear() + " has been filed successfully.",
            "ITR_FILING"
        );
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<?> getItrStatus(@PathVariable Long userId) {
        return ResponseEntity.ok(itrFilingService.getItrsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItrById(@PathVariable Long id) {
        return ResponseEntity.ok(itrFilingService.getItrById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItr(@PathVariable Long id, @RequestBody ItrFiling itrFiling) {
        itrFiling.setId(id);
        itrFiling.setLastModified(LocalDateTime.now());
        return ResponseEntity.ok(itrFilingService.updateItr(itrFiling));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteItr(@PathVariable Long id) {
        itrFilingService.deleteItr(id);
        return ResponseEntity.ok().build();
    }
}
