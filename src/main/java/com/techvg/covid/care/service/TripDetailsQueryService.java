package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.TripDetails;
import com.techvg.covid.care.repository.TripDetailsRepository;
import com.techvg.covid.care.service.criteria.TripDetailsCriteria;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import com.techvg.covid.care.service.mapper.TripDetailsMapper;
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
 * Service for executing complex queries for {@link TripDetails} entities in the database.
 * The main input is a {@link TripDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TripDetailsDTO} or a {@link Page} of {@link TripDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TripDetailsQueryService extends QueryService<TripDetails> {

    private final Logger log = LoggerFactory.getLogger(TripDetailsQueryService.class);

    private final TripDetailsRepository tripDetailsRepository;

    private final TripDetailsMapper tripDetailsMapper;

    public TripDetailsQueryService(TripDetailsRepository tripDetailsRepository, TripDetailsMapper tripDetailsMapper) {
        this.tripDetailsRepository = tripDetailsRepository;
        this.tripDetailsMapper = tripDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link TripDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TripDetailsDTO> findByCriteria(TripDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TripDetails> specification = createSpecification(criteria);
        return tripDetailsMapper.toDto(tripDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TripDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TripDetailsDTO> findByCriteria(TripDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TripDetails> specification = createSpecification(criteria);
        return tripDetailsRepository.findAll(specification, page).map(tripDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TripDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TripDetails> specification = createSpecification(criteria);
        return tripDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link TripDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TripDetails> createSpecification(TripDetailsCriteria criteria) {
        Specification<TripDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TripDetails_.id));
            }
            if (criteria.getStockSent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockSent(), TripDetails_.stockSent));
            }
            if (criteria.getStockRec() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStockRec(), TripDetails_.stockRec));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TripDetails_.comment));
            }
            if (criteria.getReceiverComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiverComment(), TripDetails_.receiverComment));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), TripDetails_.status));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), TripDetails_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), TripDetails_.createdBy));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TripDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TripDetails_.lastModifiedBy));
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(TripDetails_.supplier, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
            if (criteria.getHospitalId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalId(),
                            root -> root.join(TripDetails_.hospital, JoinType.LEFT).get(Hospital_.id)
                        )
                    );
            }
            if (criteria.getTransactionsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransactionsId(),
                            root -> root.join(TripDetails_.transactions, JoinType.LEFT).get(Transactions_.id)
                        )
                    );
            }
            if (criteria.getTripId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTripId(), root -> root.join(TripDetails_.trip, JoinType.LEFT).get(Trip_.id))
                    );
            }
        }
        return specification;
    }
}
