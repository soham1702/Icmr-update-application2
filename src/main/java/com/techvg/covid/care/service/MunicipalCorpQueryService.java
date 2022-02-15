package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.MunicipalCorp;
import com.techvg.covid.care.repository.MunicipalCorpRepository;
import com.techvg.covid.care.service.criteria.MunicipalCorpCriteria;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;
import com.techvg.covid.care.service.mapper.MunicipalCorpMapper;
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
 * Service for executing complex queries for {@link MunicipalCorp} entities in the database.
 * The main input is a {@link MunicipalCorpCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MunicipalCorpDTO} or a {@link Page} of {@link MunicipalCorpDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MunicipalCorpQueryService extends QueryService<MunicipalCorp> {

    private final Logger log = LoggerFactory.getLogger(MunicipalCorpQueryService.class);

    private final MunicipalCorpRepository municipalCorpRepository;

    private final MunicipalCorpMapper municipalCorpMapper;

    public MunicipalCorpQueryService(MunicipalCorpRepository municipalCorpRepository, MunicipalCorpMapper municipalCorpMapper) {
        this.municipalCorpRepository = municipalCorpRepository;
        this.municipalCorpMapper = municipalCorpMapper;
    }

    /**
     * Return a {@link List} of {@link MunicipalCorpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MunicipalCorpDTO> findByCriteria(MunicipalCorpCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MunicipalCorp> specification = createSpecification(criteria);
        return municipalCorpMapper.toDto(municipalCorpRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MunicipalCorpDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipalCorpDTO> findByCriteria(MunicipalCorpCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MunicipalCorp> specification = createSpecification(criteria);
        return municipalCorpRepository.findAll(specification, page).map(municipalCorpMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MunicipalCorpCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MunicipalCorp> specification = createSpecification(criteria);
        return municipalCorpRepository.count(specification);
    }

    /**
     * Function to convert {@link MunicipalCorpCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MunicipalCorp> createSpecification(MunicipalCorpCriteria criteria) {
        Specification<MunicipalCorp> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MunicipalCorp_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), MunicipalCorp_.name));
            }
            if (criteria.getDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getDeleted(), MunicipalCorp_.deleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MunicipalCorp_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MunicipalCorp_.lastModifiedBy));
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDistrictId(),
                            root -> root.join(MunicipalCorp_.district, JoinType.LEFT).get(District_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
