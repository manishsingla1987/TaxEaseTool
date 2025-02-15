
package com.taxfiling.itr.service;

import com.taxfiling.itr.model.ItrFiling;
import com.taxfiling.itr.repository.ItrFilingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ItrFilingService implements ItrFilingUseCase {
    
    @Autowired
    private ItrFilingRepository itrFilingRepository;
    
    @Autowired
    private TaxCalculationService taxCalculationService;
    
    @Override
    public ItrFiling submitItr(ItrFiling itrFiling) {
        itrFiling.setTaxAmount(taxCalculationService.calculateTax(itrFiling.getTotalIncome()));
        return itrFilingRepository.save(itrFiling);
    }
    
    @Override
    public ItrFiling updateItr(ItrFiling itrFiling) {
        return itrFilingRepository.save(itrFiling);
    }
    
    @Override
    public void deleteItr(Long id) {
        itrFilingRepository.deleteById(id);
    }
    
    @Override
    public List<ItrFiling> getItrsByUserId(Long userId) {
        return itrFilingRepository.findByUserId(userId);
    }
    
    @Override
    public ItrFiling getItrById(Long id) {
        return itrFilingRepository.findById(id).orElse(null);
    }
    
    @Override
    public BigDecimal calculateTaxAmount(BigDecimal totalIncome) {
        return taxCalculationService.calculateTax(totalIncome);
    }
}
