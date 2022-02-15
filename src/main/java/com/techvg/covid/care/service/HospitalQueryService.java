package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.repository.HospitalRepository;
import com.techvg.covid.care.service.criteria.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.mapper.HospitalMapper;
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
 * Service for executing complex queries for {@link Hospital} entities in the database.
 * The main input is a {@link HospitalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HospitalDTO} or a {@link Page} of {@link HospitalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HospitalQueryService extends QueryService<Hospital> {

    private final Logger log = LoggerFactory.getLogger(HospitalQueryService.class);

    private final HospitalRepository hospitalRepository;

    private final HospitalMapper hospitalMapper;

    public HospitalQueryService(HospitalRepository hospitalRepository, HospitalMapper hospitalMapper) {
        this.hospitalRepository = hospitalRepository;
        this.hospitalMapper = hospitalMapper;
    }

    /**
     * Return a {@link List} of {@link HospitalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HospitalDTO> findByCriteria(HospitalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Hospital> specification = createSpecification(criteria);
        return hospitalMapper.toDto(hospitalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link HospitalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HospitalDTO> findByCriteria(HospitalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Hospital> specification = createSpecification(criteria);
        return hospitalRepository.findAll(specification, page).map(hospitalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HospitalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Hospital> specification = createSpecification(criteria);
        return hospitalRepository.count(specification);
    }

    /**
     * Function to convert {@link HospitalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Hospital> createSpecification(HospitalCriteria criteria) {
        Specification<Hospital> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Hospital_.id));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getCategory(), Hospital_.category));
            }
            if (criteria.getSubCategory() != null) {
                specification = specification.and(buildSpecification(criteria.getSubCategory(), Hospital_.subCategory));
            }
            if (criteria.getContactNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactNo(), Hospital_.contactNo));
            }
            if (criteria.getLatitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLatitude(), Hospital_.latitude));
            }
            if (criteria.getLongitude() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLongitude(), Hospital_.longitude));
            }
            if (criteria.getDocCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDocCount(), Hospital_.docCount));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Hospital_.email));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Hospital_.name));
            }
            if (criteria.getRegistrationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegistrationNo(), Hospital_.registrationNo));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), Hospital_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), Hospital_.address2));
            }
            if (criteria.getArea() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArea(), Hospital_.area));
            }
            if (criteria.getPinCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPinCode(), Hospital_.pinCode));
            }
            if (criteria.getHospitalId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHospitalId(), Hospital_.hospitalId));
            }
            if (criteria.getOdasFacilityId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOdasFacilityId(), Hospital_.odasFacilityId));
            }
            if (criteria.getReferenceNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceNumber(), Hospital_.referenceNumber));
            }
            if (criteria.getStatusInd() != null) {
                specification = specification.and(buildSpecification(criteria.getStatusInd(), Hospital_.statusInd));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Hospital_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Hospital_.lastModifiedBy));
            }
            if (criteria.getStateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStateId(), root -> root.join(Hospital_.state, JoinType.LEFT).get(State_.id))
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getDistrictId(), root -> root.join(Hospital_.district, JoinType.LEFT).get(District_.id))
                    );
            }
            if (criteria.getTalukaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTalukaId(), root -> root.join(Hospital_.taluka, JoinType.LEFT).get(Taluka_.id))
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(Hospital_.city, JoinType.LEFT).get(City_.id))
                    );
            }
            if (criteria.getMunicipalCorpId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMunicipalCorpId(),
                            root -> root.join(Hospital_.municipalCorp, JoinType.LEFT).get(MunicipalCorp_.id)
                        )
                    );
            }
            if (criteria.getHospitalTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getHospitalTypeId(),
                            root -> root.join(Hospital_.hospitalType, JoinType.LEFT).get(HospitalType_.id)
                        )
                    );
            }
            if (criteria.getSupplierId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSupplierId(),
                            root -> root.join(Hospital_.suppliers, JoinType.LEFT).get(Supplier_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
