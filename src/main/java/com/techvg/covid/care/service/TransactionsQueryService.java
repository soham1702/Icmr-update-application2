package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.Transactions;
import com.techvg.covid.care.repository.TransactionsRepository;
import com.techvg.covid.care.service.criteria.TransactionsCriteria;
import com.techvg.covid.care.service.dto.TransactionsDTO;
import com.techvg.covid.care.service.mapper.TransactionsMapper;
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
 * Service for executing complex queries for {@link Transactions} entities in the database.
 * The main input is a {@link TransactionsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionsDTO} or a {@link Page} of {@link TransactionsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionsQueryService extends QueryService<Transactions> {

    private final Logger log = LoggerFactory.getLogger(TransactionsQueryService.class);

    private final TransactionsRepository transactionsRepository;

    private final TransactionsMapper transactionsMapper;

    public TransactionsQueryService(TransactionsRepository transactionsRepository, TransactionsMapper transactionsMapper) {
        this.transactionsRepository = transactionsRepository;
        this.transactionsMapper = transactionsMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionsDTO> findByCriteria(TransactionsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transactions> specification = createSpecification(criteria);
        return transactionsMapper.toDto(transactionsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionsDTO> findByCriteria(TransactionsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transactions> specification = createSpecification(criteria);
        return transactionsRepository.findAll(specification, page).map(transactionsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transactions> specification = createSpecification(criteria);
        return transactionsRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transactions> createSpecification(TransactionsCriteria criteria) {
        Specification<Transactions> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transactions_.id));
            }
            if (criteria.getStockReq() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockReq(), Transactions_.stockReq));
            }
            if (criteria.getStockProvided() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockProvided(), Transactions_.stockProvided));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Transactions_.status));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Transactions_.comment));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Transactions_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Transactions_.lastModifiedBy));
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(Transactions_.supplier, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
            if (criteria.getInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInventoryId(),
                            root -> root.join(Transactions_.inventory, JoinType.LEFT).get(Inventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
