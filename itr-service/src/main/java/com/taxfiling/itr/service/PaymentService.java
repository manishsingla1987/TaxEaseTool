
package com.taxfiling.itr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService implements PaymentUseCase {
    @Autowired
    private NotificationService notificationService;

    private Map<String, PaymentStatus> paymentStatuses = new HashMap<>();

    public String initiatePayment(Long userId, BigDecimal amount) {
        String paymentId = "PAYMENT_" + System.currentTimeMillis();
        paymentStatuses.put(paymentId, new PaymentStatus(userId, amount, "PENDING"));
        
        notificationService.createNotification(
            userId,
            "Payment initiated for amount: ₹" + amount,
            "PAYMENT_INITIATED"
        );
        
        return paymentId;
    }

    public boolean verifyPayment(String paymentId) {
        PaymentStatus status = paymentStatuses.get(paymentId);
        if (status != null) {
            status.setStatus("COMPLETED");
            notificationService.createNotification(
                status.getUserId(),
                "Payment completed for amount: ₹" + status.getAmount(),
                "PAYMENT_COMPLETED"
            );
            return true;
        }
        return false;
    }

    public PaymentStatus getPaymentStatus(String paymentId) {
        return paymentStatuses.get(paymentId);
    }

    private static class PaymentStatus {
        private Long userId;
        private BigDecimal amount;
        private String status;

        public PaymentStatus(Long userId, BigDecimal amount, String status) {
            this.userId = userId;
            this.amount = amount;
            this.status = status;
        }

        public Long getUserId() { return userId; }
        public BigDecimal getAmount() { return amount; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}
