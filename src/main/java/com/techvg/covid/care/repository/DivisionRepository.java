package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.Division;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Division entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DivisionRepository extends JpaRepository<Division, Long>, JpaSpecificationExecutor<Division> {}
