package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.BedTransactions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BedTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedTransactionsRepository extends JpaRepository<BedTransactions, Long>, JpaSpecificationExecutor<BedTransactions> {}
