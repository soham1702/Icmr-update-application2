package com.techvg.covid.care.repository;

import com.techvg.covid.care.domain.SecurityUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SecurityUser entity.
 */
@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long>, JpaSpecificationExecutor<SecurityUser> {
    @Query(
        value = "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles",
        countQuery = "select count(distinct securityUser) from SecurityUser securityUser"
    )
    Page<SecurityUser> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles"
    )
    List<SecurityUser> findAllWithEagerRelationships();

    @Query(
        "select securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles where securityUser.id =:id"
    )
    Optional<SecurityUser> findOneWithEagerRelationships(@Param("id") Long id);
}
