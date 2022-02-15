package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.AuditType;
import com.techvg.covid.care.repository.AuditTypeRepository;
import com.techvg.covid.care.service.criteria.AuditTypeCriteria;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
import com.techvg.covid.care.service.mapper.AuditTypeMapper;
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
 * Service for executing complex queries for {@link AuditType} entities in the database.
 * The main input is a {@link AuditTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AuditTypeDTO} or a {@link Page} of {@link AuditTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AuditTypeQueryService extends QueryService<AuditType> {

    private final Logger log = LoggerFactory.getLogger(AuditTypeQueryService.class);

    private final AuditTypeRepository auditTypeRepository;

    private final AuditTypeMapper auditTypeMapper;

    public AuditTypeQueryService(AuditTypeRepository auditTypeRepository, AuditTypeMapper auditTypeMapper) {
        this.auditTypeRepository = auditTypeRepository;
        this.auditTypeMapper = auditTypeMapper;
    }

    /**
     * Return a {@link List} of {@link AuditTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AuditTypeDTO> findByCriteria(AuditTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AuditType> specification = createSpecification(criteria);
        return auditTypeMapper.toDto(auditTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AuditTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditTypeDTO> findByCriteria(AuditTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AuditType> specification = createSpecification(criteria);
        return auditTypeRepository.findAll(specification, page).map(auditTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AuditTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AuditType> specification = createSpecification(criteria);
        return auditTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link AuditTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AuditType> createSpecification(AuditTypeCriteria criteria) {
        Specification<AuditType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AuditType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), AuditType_.name));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), AuditType_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AuditType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AuditType_.lastModifiedBy));
            }
        }
        return specification;
    }
}
