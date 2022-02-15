package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.MunicipalCorp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MunicipalCorp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MunicipalCorpRepository extends JpaRepository<MunicipalCorp, Long>, JpaSpecificationExecutor<MunicipalCorp> {}
