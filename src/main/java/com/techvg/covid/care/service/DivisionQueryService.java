package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.Division;
import com.techvg.covid.care.repository.DivisionRepository;
import com.techvg.covid.care.service.criteria.DivisionCriteria;
import com.techvg.covid.care.service.dto.DivisionDTO;
import com.techvg.covid.care.service.mapper.DivisionMapper;
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
 * Service for executing complex queries for {@link Division} entities in the database.
 * The main input is a {@link DivisionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DivisionDTO} or a {@link Page} of {@link DivisionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DivisionQueryService extends QueryService<Division> {

    private final Logger log = LoggerFactory.getLogger(DivisionQueryService.class);

    private final DivisionRepository divisionRepository;

    private final DivisionMapper divisionMapper;

    public DivisionQueryService(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    /**
     * Return a {@link List} of {@link DivisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DivisionDTO> findByCriteria(DivisionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Division> specification = createSpecification(criteria);
        return divisionMapper.toDto(divisionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DivisionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DivisionDTO> findByCriteria(DivisionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Division> specification = createSpecification(criteria);
        return divisionRepository.findAll(specification, page).map(divisionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DivisionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Division> specification = createSpecification(criteria);
        return divisionRepository.count(specification);
    }

    /**
     * Function to convert {@link DivisionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Division> createSpecification(DivisionCriteria criteria) {
        Specification<Division> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Division_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Division_.name));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), Division_.deleted));
            }
            if (criteria.getLgdCode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLgdCode(), Division_.lgdCode));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Division_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Division_.lastModifiedBy));
            }
        }
        return specification;
    }
}
