
package com.taxfiling.itr.service;

import java.math.BigDecimal;

public interface TaxCalculationUseCase {
    BigDecimal calculateTax(BigDecimal totalIncome);
    BigDecimal calculateSurcharge(BigDecimal taxAmount, BigDecimal totalIncome);
    BigDecimal calculateCess(BigDecimal taxAmount);
    BigDecimal getTaxableIncome(BigDecimal grossIncome, BigDecimal deductions);
    BigDecimal calculateTaxSavings(BigDecimal investments);
}
