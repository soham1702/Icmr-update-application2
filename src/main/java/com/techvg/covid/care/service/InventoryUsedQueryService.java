package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.InventoryUsed;
import com.techvg.covid.care.repository.InventoryUsedRepository;
import com.techvg.covid.care.service.criteria.InventoryUsedCriteria;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
import com.techvg.covid.care.service.mapper.InventoryUsedMapper;
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
 * Service for executing complex queries for {@link InventoryUsed} entities in the database.
 * The main input is a {@link InventoryUsedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryUsedDTO} or a {@link Page} of {@link InventoryUsedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryUsedQueryService extends QueryService<InventoryUsed> {

    private final Logger log = LoggerFactory.getLogger(InventoryUsedQueryService.class);

    private final InventoryUsedRepository inventoryUsedRepository;

    private final InventoryUsedMapper inventoryUsedMapper;

    public InventoryUsedQueryService(InventoryUsedRepository inventoryUsedRepository, InventoryUsedMapper inventoryUsedMapper) {
        this.inventoryUsedRepository = inventoryUsedRepository;
        this.inventoryUsedMapper = inventoryUsedMapper;
    }

    /**
     * Return a {@link List} of {@link InventoryUsedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryUsedDTO> findByCriteria(InventoryUsedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryUsed> specification = createSpecification(criteria);
        return inventoryUsedMapper.toDto(inventoryUsedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoryUsedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryUsedDTO> findByCriteria(InventoryUsedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryUsed> specification = createSpecification(criteria);
        return inventoryUsedRepository.findAll(specification, page).map(inventoryUsedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryUsedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryUsed> specification = createSpecification(criteria);
        return inventoryUsedRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryUsedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryUsed> createSpecification(InventoryUsedCriteria criteria) {
        Specification<InventoryUsed> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryUsed_.id));
            }
            if (criteria.getStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStock(), InventoryUsed_.stock));
            }
            if (criteria.getCapcity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapcity(), InventoryUsed_.capcity));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), InventoryUsed_.comment));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), InventoryUsed_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), InventoryUsed_.lastModifiedBy));
            }
            if (criteria.getInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInventoryId(),
                            root -> root.join(InventoryUsed_.inventory, JoinType.LEFT).get(Inventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
