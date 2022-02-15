package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.BedInventory;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.criteria.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.mapper.BedInventoryMapper;
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
 * Service for executing complex queries for {@link BedInventory} entities in the database.
 * The main input is a {@link BedInventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BedInventoryDTO} or a {@link Page} of {@link BedInventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BedInventoryQueryService extends QueryService<BedInventory> {

    private final Logger log = LoggerFactory.getLogger(BedInventoryQueryService.class);

    private final BedInventoryRepository bedInventoryRepository;

    private final BedInventoryMapper bedInventoryMapper;

    public BedInventoryQueryService(BedInventoryRepository bedInventoryRepository, BedInventoryMapper bedInventoryMapper) {
        this.bedInventoryRepository = bedInventoryRepository;
        this.bedInventoryMapper = bedInventoryMapper;
    }

    /**
     * Return a {@link List} of {@link BedInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BedInventoryDTO> findByCriteria(BedInventoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BedInventory> specification = createSpecification(criteria);
        return bedInventoryMapper.toDto(bedInventoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BedInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BedInventoryDTO> findByCriteria(BedInventoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BedInventory> specification = createSpecification(criteria);
        return bedInventoryRepository.findAll(specification, page).map(bedInventoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BedInventoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BedInventory> specification = createSpecification(criteria);
        return bedInventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link BedInventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BedInventory> createSpecification(BedInventoryCriteria criteria) {
        Specification<BedInventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BedInventory_.id));
            }
            if (criteria.getBedCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBedCount(), BedInventory_.bedCount));
            }
            if (criteria.getOccupied() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOccupied(), BedInventory_.occupied));
            }
            if (criteria.getOnCylinder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnCylinder(), BedInventory_.onCylinder));
            }
            if (criteria.getOnLMO() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnLMO(), BedInventory_.onLMO));
            }
            if (criteria.getOnConcentrators() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnConcentrators(), BedInventory_.onConcentrators));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), BedInventory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BedInventory_.lastModifiedBy));
            }
            if (criteria.getBedTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBedTypeId(),
                            root -> root.join(BedInventory_.bedType, JoinType.LEFT).get(BedType_.id)
                        )
                    );
            }
            if (criteria.getHospitalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalId(),
                            root -> root.join(BedInventory_.hospital, JoinType.LEFT).get(Hospital_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
