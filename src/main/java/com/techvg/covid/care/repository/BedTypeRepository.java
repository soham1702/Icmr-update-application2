package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.BedType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BedType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BedTypeRepository extends JpaRepository<BedType, Long>, JpaSpecificationExecutor<BedType> {}
