package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.repository.InventoryRepository;
import com.techvg.covid.care.service.criteria.InventoryCriteria;
import com.techvg.covid.care.service.dto.InventoryDTO;
import com.techvg.covid.care.service.mapper.InventoryMapper;
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
 * Service for executing complex queries for {@link Inventory} entities in the database.
 * The main input is a {@link InventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InventoryDTO} or a {@link Page} of {@link InventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryQueryService extends QueryService<Inventory> {

    private final Logger log = LoggerFactory.getLogger(InventoryQueryService.class);

    private final InventoryRepository inventoryRepository;

    private final InventoryMapper inventoryMapper;

    public InventoryQueryService(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Return a {@link List} of {@link InventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InventoryDTO> findByCriteria(InventoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryMapper.toDto(inventoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryDTO> findByCriteria(InventoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.findAll(specification, page).map(inventoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventory> createSpecification(InventoryCriteria criteria) {
        Specification<Inventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inventory_.id));
            }
            if (criteria.getStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStock(), Inventory_.stock));
            }
            if (criteria.getCapcity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapcity(), Inventory_.capcity));
            }
            if (criteria.getInstalledCapcity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstalledCapcity(), Inventory_.installedCapcity));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Inventory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Inventory_.lastModifiedBy));
            }
            if (criteria.getInventoryMasterId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInventoryMasterId(),
                            root -> root.join(Inventory_.inventoryMaster, JoinType.LEFT).get(InventoryMaster_.id)
                        )
                    );
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(Inventory_.supplier, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
            if (criteria.getHospitalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalId(),
                            root -> root.join(Inventory_.hospital, JoinType.LEFT).get(Hospital_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
