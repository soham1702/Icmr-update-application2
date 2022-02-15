package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Taluka;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Taluka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalukaRepository extends JpaRepository<Taluka, Long>, JpaSpecificationExecutor<Taluka> {}
