package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.repository.InventoryTypeRepository;
import com.techvg.covid.care.service.criteria.InventoryTypeCriteria;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;
import com.techvg.covid.care.service.mapper.InventoryTypeMapper;
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
 * Service for executing complex queries for {@link InventoryType} entities in the database.
 * The main input is a {@link InventoryTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryTypeDTO} or a {@link Page} of {@link InventoryTypeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryTypeQueryService extends QueryService<InventoryType> {

    private final Logger log = LoggerFactory.getLogger(InventoryTypeQueryService.class);

    private final InventoryTypeRepository inventoryTypeRepository;

    private final InventoryTypeMapper inventoryTypeMapper;

    public InventoryTypeQueryService(InventoryTypeRepository inventoryTypeRepository, InventoryTypeMapper inventoryTypeMapper) {
        this.inventoryTypeRepository = inventoryTypeRepository;
        this.inventoryTypeMapper = inventoryTypeMapper;
    }

    /**
     * Return a {@link List} of {@link InventoryTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryTypeDTO> findByCriteria(InventoryTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryType> specification = createSpecification(criteria);
        return inventoryTypeMapper.toDto(inventoryTypeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoryTypeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryTypeDTO> findByCriteria(InventoryTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryType> specification = createSpecification(criteria);
        return inventoryTypeRepository.findAll(specification, page).map(inventoryTypeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryType> specification = createSpecification(criteria);
        return inventoryTypeRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryTypeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryType> createSpecification(InventoryTypeCriteria criteria) {
        Specification<InventoryType> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryType_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InventoryType_.name));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), InventoryType_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), InventoryType_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), InventoryType_.lastModifiedBy));
            }
        }
        return specification;
    }
}
