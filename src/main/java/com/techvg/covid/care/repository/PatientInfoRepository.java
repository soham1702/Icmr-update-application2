package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.PatientInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PatientInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientInfoRepository extends JpaRepository<PatientInfo, Long>, JpaSpecificationExecutor<PatientInfo> {}
