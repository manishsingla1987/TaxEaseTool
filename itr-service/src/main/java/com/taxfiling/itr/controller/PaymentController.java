
package com.taxfiling.itr.controller;

import com.taxfiling.itr.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequest request) {
        String paymentId = paymentService.initiatePayment(request.getUserId(), request.getAmount());
        return ResponseEntity.ok(new PaymentResponse(paymentId));
    }

    @PostMapping("/verify/{paymentId}")
    public ResponseEntity<?> verifyPayment(@PathVariable String paymentId) {
        boolean verified = paymentService.verifyPayment(paymentId);
        return ResponseEntity.ok(new VerificationResponse(verified));
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentStatus(paymentId));
    }

    public static class PaymentRequest {
        private Long userId;
        private BigDecimal amount;

        public Long getUserId() { return userId; }
        public BigDecimal getAmount() { return amount; }
    }

    public static class PaymentResponse {
        private String paymentId;

        public PaymentResponse(String paymentId) {
            this.paymentId = paymentId;
        }

        public String getPaymentId() { return paymentId; }
    }

    public static class VerificationResponse {
        private boolean verified;

        public VerificationResponse(boolean verified) {
            this.verified = verified;
        }

        public boolean isVerified() { return verified; }
    }
}
