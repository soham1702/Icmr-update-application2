package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.InventoryMaster;
import com.techvg.covid.care.repository.InventoryMasterRepository;
import com.techvg.covid.care.service.criteria.InventoryMasterCriteria;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
import com.techvg.covid.care.service.mapper.InventoryMasterMapper;
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
 * Service for executing complex queries for {@link InventoryMaster} entities in the database.
 * The main input is a {@link InventoryMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryMasterDTO} or a {@link Page} of {@link InventoryMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryMasterQueryService extends QueryService<InventoryMaster> {

    private final Logger log = LoggerFactory.getLogger(InventoryMasterQueryService.class);

    private final InventoryMasterRepository inventoryMasterRepository;

    private final InventoryMasterMapper inventoryMasterMapper;

    public InventoryMasterQueryService(InventoryMasterRepository inventoryMasterRepository, InventoryMasterMapper inventoryMasterMapper) {
        this.inventoryMasterRepository = inventoryMasterRepository;
        this.inventoryMasterMapper = inventoryMasterMapper;
    }

    /**
     * Return a {@link List} of {@link InventoryMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryMasterDTO> findByCriteria(InventoryMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InventoryMaster> specification = createSpecification(criteria);
        return inventoryMasterMapper.toDto(inventoryMasterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoryMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryMasterDTO> findByCriteria(InventoryMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InventoryMaster> specification = createSpecification(criteria);
        return inventoryMasterRepository.findAll(specification, page).map(inventoryMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InventoryMaster> specification = createSpecification(criteria);
        return inventoryMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InventoryMaster> createSpecification(InventoryMasterCriteria criteria) {
        Specification<InventoryMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InventoryMaster_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), InventoryMaster_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), InventoryMaster_.description));
            }
            if (criteria.getVolume() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVolume(), InventoryMaster_.volume));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), InventoryMaster_.unit));
            }
            if (criteria.getCalulateVolume() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCalulateVolume(), InventoryMaster_.calulateVolume));
            }
            if (criteria.getDimensions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimensions(), InventoryMaster_.dimensions));
            }
            if (criteria.getSubTypeInd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubTypeInd(), InventoryMaster_.subTypeInd));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), InventoryMaster_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), InventoryMaster_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), InventoryMaster_.lastModifiedBy));
            }
            if (criteria.getInventoryTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInventoryTypeId(),
                            root -> root.join(InventoryMaster_.inventoryType, JoinType.LEFT).get(InventoryType_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
