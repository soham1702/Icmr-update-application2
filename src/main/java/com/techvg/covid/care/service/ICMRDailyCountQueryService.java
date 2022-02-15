package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.ICMRDailyCount;
import com.techvg.covid.care.repository.ICMRDailyCountRepository;
import com.techvg.covid.care.service.criteria.ICMRDailyCountCriteria;
import com.techvg.covid.care.service.dto.ICMRDailyCountDTO;
import com.techvg.covid.care.service.mapper.ICMRDailyCountMapper;
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
 * Service for executing complex queries for {@link ICMRDailyCount} entities in the database.
 * The main input is a {@link ICMRDailyCountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ICMRDailyCountDTO} or a {@link Page} of {@link ICMRDailyCountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ICMRDailyCountQueryService extends QueryService<ICMRDailyCount> {

    private final Logger log = LoggerFactory.getLogger(ICMRDailyCountQueryService.class);

    private final ICMRDailyCountRepository iCMRDailyCountRepository;

    private final ICMRDailyCountMapper iCMRDailyCountMapper;

    public ICMRDailyCountQueryService(ICMRDailyCountRepository iCMRDailyCountRepository, ICMRDailyCountMapper iCMRDailyCountMapper) {
        this.iCMRDailyCountRepository = iCMRDailyCountRepository;
        this.iCMRDailyCountMapper = iCMRDailyCountMapper;
    }

    /**
     * Return a {@link List} of {@link ICMRDailyCountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ICMRDailyCountDTO> findByCriteria(ICMRDailyCountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ICMRDailyCount> specification = createSpecification(criteria);
        return iCMRDailyCountMapper.toDto(iCMRDailyCountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ICMRDailyCountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ICMRDailyCountDTO> findByCriteria(ICMRDailyCountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ICMRDailyCount> specification = createSpecification(criteria);
        return iCMRDailyCountRepository.findAll(specification, page).map(iCMRDailyCountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ICMRDailyCountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ICMRDailyCount> specification = createSpecification(criteria);
        return iCMRDailyCountRepository.count(specification);
    }

    /**
     * Function to convert {@link ICMRDailyCountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ICMRDailyCount> createSpecification(ICMRDailyCountCriteria criteria) {
        Specification<ICMRDailyCount> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ICMRDailyCount_.id));
            }
            if (criteria.getTotalSamplesTested() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalSamplesTested(), ICMRDailyCount_.totalSamplesTested));
            }
            if (criteria.getTotalNegative() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalNegative(), ICMRDailyCount_.totalNegative));
            }
            if (criteria.getTotalPositive() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPositive(), ICMRDailyCount_.totalPositive));
            }
            if (criteria.getCurrentPreviousMonthTest() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getCurrentPreviousMonthTest(), ICMRDailyCount_.currentPreviousMonthTest)
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDistrictId(), ICMRDailyCount_.districtId));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), ICMRDailyCount_.remarks));
            }
            if (criteria.getEditedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditedOn(), ICMRDailyCount_.editedOn));
            }
            if (criteria.getUpdatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedDate(), ICMRDailyCount_.updatedDate));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ICMRDailyCount_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ICMRDailyCount_.freeField2));
            }
        }
        return specification;
    }
}
