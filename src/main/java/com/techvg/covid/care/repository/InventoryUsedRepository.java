package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.InventoryUsed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the InventoryUsed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InventoryUsedRepository extends JpaRepository<InventoryUsed, Long>, JpaSpecificationExecutor<InventoryUsed> {}
