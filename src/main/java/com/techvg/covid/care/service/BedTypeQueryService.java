package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.repository.BedTypeRepository;
import com.techvg.covid.care.service.criteria.BedTypeCriteria;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.mapper.BedTypeMapper;
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
 * Service for executing complex queries for {@link BedType} entities in the database.
 * The main input is a {@link BedTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BedTypeDTO} or a {@link Page} of {@link BedTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BedTypeQueryService extends QueryService<BedType> {

    private final Logger log = LoggerFactory.getLogger(BedTypeQueryService.class);

    private final BedTypeRepository bedTypeRepository;

    private final BedTypeMapper bedTypeMapper;

    public BedTypeQueryService(BedTypeRepository bedTypeRepository, BedTypeMapper bedTypeMapper) {
        this.bedTypeRepository = bedTypeRepository;
        this.bedTypeMapper = bedTypeMapper;
    }

    /**
     * Return a {@link List} of {@link BedTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BedTypeDTO> findByCriteria(BedTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BedType> specification = createSpecification(criteria);
        return bedTypeMapper.toDto(bedTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BedTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BedTypeDTO> findByCriteria(BedTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BedType> specification = createSpecification(criteria);
        return bedTypeRepository.findAll(specification, page).map(bedTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BedTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BedType> specification = createSpecification(criteria);
        return bedTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link BedTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BedType> createSpecification(BedTypeCriteria criteria) {
        Specification<BedType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BedType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BedType_.name));
            }
            if (criteria.getPerDayOX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPerDayOX(), BedType_.perDayOX));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), BedType_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), BedType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BedType_.lastModifiedBy));
            }
        }
        return specification;
    }
}
