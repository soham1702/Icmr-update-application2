package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.TripDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TripDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TripDetailsRepository extends JpaRepository<TripDetails, Long>, JpaSpecificationExecutor<TripDetails> {}
