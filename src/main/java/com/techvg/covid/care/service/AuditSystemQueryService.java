package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.AuditSystem;
import com.techvg.covid.care.repository.AuditSystemRepository;
import com.techvg.covid.care.service.criteria.AuditSystemCriteria;
import com.techvg.covid.care.service.dto.AuditSystemDTO;
import com.techvg.covid.care.service.mapper.AuditSystemMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AuditSystem} entities in the database.
 * The main input is a {@link AuditSystemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuditSystemDTO} or a {@link Page} of {@link AuditSystemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuditSystemQueryService extends QueryService<AuditSystem> {

    private final Logger log = LoggerFactory.getLogger(AuditSystemQueryService.class);

    private final AuditSystemRepository auditSystemRepository;

    private final AuditSystemMapper auditSystemMapper;

    public AuditSystemQueryService(AuditSystemRepository auditSystemRepository, AuditSystemMapper auditSystemMapper) {
        this.auditSystemRepository = auditSystemRepository;
        this.auditSystemMapper = auditSystemMapper;
    }

    /**
     * Return a {@link List} of {@link AuditSystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuditSystemDTO> findByCriteria(AuditSystemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuditSystem> specification = createSpecification(criteria);
        return auditSystemMapper.toDto(auditSystemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuditSystemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditSystemDTO> findByCriteria(AuditSystemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuditSystem> specification = createSpecification(criteria);
        return auditSystemRepository.findAll(specification, page).map(auditSystemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuditSystemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuditSystem> specification = createSpecification(criteria);
        return auditSystemRepository.count(specification);
    }

    /**
     * Function to convert {@link AuditSystemCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuditSystem> createSpecification(AuditSystemCriteria criteria) {
        Specification<AuditSystem> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuditSystem_.id));
            }
            if (criteria.getAuditorName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuditorName(), AuditSystem_.auditorName));
            }
            if (criteria.getDefectCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDefectCount(), AuditSystem_.defectCount));
            }
            if (criteria.getDefectFixCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDefectFixCount(), AuditSystem_.defectFixCount));
            }
            if (criteria.getInspectionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInspectionDate(), AuditSystem_.inspectionDate));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), AuditSystem_.remark));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), AuditSystem_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AuditSystem_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AuditSystem_.lastModifiedBy));
            }
            if (criteria.getAuditTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAuditTypeId(),
                            root -> root.join(AuditSystem_.auditType, JoinType.LEFT).get(AuditType_.id)
                        )
                    );
            }
            if (criteria.getHospitalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalId(),
                            root -> root.join(AuditSystem_.hospital, JoinType.LEFT).get(Hospital_.id)
                        )
                    );
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(AuditSystem_.supplier, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
