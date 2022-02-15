package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.HospitalType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HospitalType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HospitalTypeRepository extends JpaRepository<HospitalType, Long>, JpaSpecificationExecutor<HospitalType> {}
