package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.AuditSystem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuditSystem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditSystemRepository extends JpaRepository<AuditSystem, Long>, JpaSpecificationExecutor<AuditSystem> {}
