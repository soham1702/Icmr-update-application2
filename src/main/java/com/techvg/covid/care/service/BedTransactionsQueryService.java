package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.BedTransactions;
import com.techvg.covid.care.repository.BedTransactionsRepository;
import com.techvg.covid.care.service.criteria.BedTransactionsCriteria;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
import com.techvg.covid.care.service.mapper.BedTransactionsMapper;
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
 * Service for executing complex queries for {@link BedTransactions} entities in the database.
 * The main input is a {@link BedTransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BedTransactionsDTO} or a {@link Page} of {@link BedTransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BedTransactionsQueryService extends QueryService<BedTransactions> {

    private final Logger log = LoggerFactory.getLogger(BedTransactionsQueryService.class);

    private final BedTransactionsRepository bedTransactionsRepository;

    private final BedTransactionsMapper bedTransactionsMapper;

    public BedTransactionsQueryService(BedTransactionsRepository bedTransactionsRepository, BedTransactionsMapper bedTransactionsMapper) {
        this.bedTransactionsRepository = bedTransactionsRepository;
        this.bedTransactionsMapper = bedTransactionsMapper;
    }

    /**
     * Return a {@link List} of {@link BedTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BedTransactionsDTO> findByCriteria(BedTransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BedTransactions> specification = createSpecification(criteria);
        return bedTransactionsMapper.toDto(bedTransactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BedTransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BedTransactionsDTO> findByCriteria(BedTransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BedTransactions> specification = createSpecification(criteria);
        return bedTransactionsRepository.findAll(specification, page).map(bedTransactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BedTransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BedTransactions> specification = createSpecification(criteria);
        return bedTransactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link BedTransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BedTransactions> createSpecification(BedTransactionsCriteria criteria) {
        Specification<BedTransactions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BedTransactions_.id));
            }
            if (criteria.getOccupied() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOccupied(), BedTransactions_.occupied));
            }
            if (criteria.getOnCylinder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnCylinder(), BedTransactions_.onCylinder));
            }
            if (criteria.getOnLMO() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnLMO(), BedTransactions_.onLMO));
            }
            if (criteria.getOnConcentrators() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOnConcentrators(), BedTransactions_.onConcentrators));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), BedTransactions_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), BedTransactions_.lastModifiedBy));
            }
            if (criteria.getBedTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBedTypeId(),
                            root -> root.join(BedTransactions_.bedType, JoinType.LEFT).get(BedType_.id)
                        )
                    );
            }
            if (criteria.getHospitalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalId(),
                            root -> root.join(BedTransactions_.hospital, JoinType.LEFT).get(Hospital_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
