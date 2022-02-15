package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.SecurityRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityRole entity.
 */
@Repository
public interface SecurityRoleRepository extends JpaRepository<SecurityRole, Long>, JpaSpecificationExecutor<SecurityRole> {
    @Query(
        value = "select distinct securityRole from SecurityRole securityRole left join fetch securityRole.securityPermissions",
        countQuery = "select count(distinct securityRole) from SecurityRole securityRole"
    )
    Page<SecurityRole> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct securityRole from SecurityRole securityRole left join fetch securityRole.securityPermissions")
    List<SecurityRole> findAllWithEagerRelationships();

    @Query("select securityRole from SecurityRole securityRole left join fetch securityRole.securityPermissions where securityRole.id =:id")
    Optional<SecurityRole> findOneWithEagerRelationships(@Param("id") Long id);
}
