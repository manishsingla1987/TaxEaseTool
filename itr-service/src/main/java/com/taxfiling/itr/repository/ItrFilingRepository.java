
package com.taxfiling.itr.repository;

import com.taxfiling.itr.model.ItrFiling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItrFilingRepository extends JpaRepository<ItrFiling, Long> {
    List<ItrFiling> findByUserId(Long userId);
    Optional<ItrFiling> findByUserIdAndFinancialYear(Long userId, String financialYear);
}
