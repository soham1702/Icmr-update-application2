package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.ContactType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ContactType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, Long>, JpaSpecificationExecutor<ContactType> {}
