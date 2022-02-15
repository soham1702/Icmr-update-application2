package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.AuditType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AuditType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuditTypeRepository extends JpaRepository<AuditType, Long>, JpaSpecificationExecutor<AuditType> {}
