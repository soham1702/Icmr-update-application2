package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.State;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the State entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {}
