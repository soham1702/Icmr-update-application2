package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.HospitalType;
import com.techvg.covid.care.repository.HospitalTypeRepository;
import com.techvg.covid.care.service.criteria.HospitalTypeCriteria;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
import com.techvg.covid.care.service.mapper.HospitalTypeMapper;
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
 * Service for executing complex queries for {@link HospitalType} entities in the database.
 * The main input is a {@link HospitalTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HospitalTypeDTO} or a {@link Page} of {@link HospitalTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HospitalTypeQueryService extends QueryService<HospitalType> {

    private final Logger log = LoggerFactory.getLogger(HospitalTypeQueryService.class);

    private final HospitalTypeRepository hospitalTypeRepository;

    private final HospitalTypeMapper hospitalTypeMapper;

    public HospitalTypeQueryService(HospitalTypeRepository hospitalTypeRepository, HospitalTypeMapper hospitalTypeMapper) {
        this.hospitalTypeRepository = hospitalTypeRepository;
        this.hospitalTypeMapper = hospitalTypeMapper;
    }

    /**
     * Return a {@link List} of {@link HospitalTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HospitalTypeDTO> findByCriteria(HospitalTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HospitalType> specification = createSpecification(criteria);
        return hospitalTypeMapper.toDto(hospitalTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HospitalTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HospitalTypeDTO> findByCriteria(HospitalTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HospitalType> specification = createSpecification(criteria);
        return hospitalTypeRepository.findAll(specification, page).map(hospitalTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HospitalTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HospitalType> specification = createSpecification(criteria);
        return hospitalTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link HospitalTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<HospitalType> createSpecification(HospitalTypeCriteria criteria) {
        Specification<HospitalType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), HospitalType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), HospitalType_.name));
            }
            if (criteria.getDesciption() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesciption(), HospitalType_.desciption));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), HospitalType_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), HospitalType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), HospitalType_.lastModifiedBy));
            }
        }
        return specification;
    }
}
