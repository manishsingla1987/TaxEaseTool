
package com.taxfiling.itr.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class TaxCalculationService implements TaxCalculationUseCase {
    
    @Override
    public BigDecimal calculateTax(BigDecimal totalIncome) {
        if (totalIncome.compareTo(BigDecimal.valueOf(250000)) <= 0) {
            return BigDecimal.ZERO;
        } else if (totalIncome.compareTo(BigDecimal.valueOf(500000)) <= 0) {
            return totalIncome.multiply(BigDecimal.valueOf(0.05));
        } else if (totalIncome.compareTo(BigDecimal.valueOf(1000000)) <= 0) {
            return totalIncome.multiply(BigDecimal.valueOf(0.20));
        } else {
            return totalIncome.multiply(BigDecimal.valueOf(0.30));
        }
    }
}
