
package com.taxfiling.itr.service;

import java.math.BigDecimal;

public interface PaymentUseCase {
    String initiatePayment(Long userId, Long itrId, BigDecimal amount);
    String verifyPayment(String paymentId);
    PaymentStatus getPaymentStatus(String paymentId);
    void trackPaymentUpdates(String paymentId);
    
    enum PaymentStatus {
        PENDING, SUCCESS, FAILED
    }
}
