
package com.taxfiling.itr.service;

import com.taxfiling.itr.model.ItrFiling;
import java.math.BigDecimal;
import java.util.List;

public interface ItrFilingUseCase {
    ItrFiling submitItr(ItrFiling itrFiling);
    ItrFiling updateItr(ItrFiling itrFiling);
    void deleteItr(Long id);
    List<ItrFiling> getItrsByUserId(Long userId);
    ItrFiling getItrById(Long id);
    BigDecimal calculateTaxAmount(BigDecimal totalIncome);
}
