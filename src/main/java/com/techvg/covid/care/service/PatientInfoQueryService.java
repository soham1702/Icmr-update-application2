package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.*; // for static metamodels
import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.repository.PatientInfoRepository;
import com.techvg.covid.care.service.criteria.PatientInfoCriteria;
import com.techvg.covid.care.service.dto.PatientInfoDTO;
import com.techvg.covid.care.service.mapper.PatientInfoMapper;
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
 * Service for executing complex queries for {@link PatientInfo} entities in the database.
 * The main input is a {@link PatientInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientInfoDTO} or a {@link Page} of {@link PatientInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientInfoQueryService extends QueryService<PatientInfo> {

    private final Logger log = LoggerFactory.getLogger(PatientInfoQueryService.class);

    private final PatientInfoRepository patientInfoRepository;

    private final PatientInfoMapper patientInfoMapper;

    public PatientInfoQueryService(PatientInfoRepository patientInfoRepository, PatientInfoMapper patientInfoMapper) {
        this.patientInfoRepository = patientInfoRepository;
        this.patientInfoMapper = patientInfoMapper;
    }

    /**
     * Return a {@link List} of {@link PatientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientInfoDTO> findByCriteria(PatientInfoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoMapper.toDto(patientInfoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PatientInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientInfoDTO> findByCriteria(PatientInfoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoRepository.findAll(specification, page).map(patientInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientInfoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientInfo> specification = createSpecification(criteria);
        return patientInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientInfo> createSpecification(PatientInfoCriteria criteria) {
        Specification<PatientInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientInfo_.id));
            }
            if (criteria.getIcmrId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIcmrId(), PatientInfo_.icmrId));
            }
            if (criteria.getSrfId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSrfId(), PatientInfo_.srfId));
            }
            if (criteria.getLabName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabName(), PatientInfo_.labName));
            }
            if (criteria.getPatientID() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientID(), PatientInfo_.patientID));
            }
            if (criteria.getPatientName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientName(), PatientInfo_.patientName));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAge(), PatientInfo_.age));
            }
            if (criteria.getAgeIn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgeIn(), PatientInfo_.ageIn));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), PatientInfo_.gender));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), PatientInfo_.nationality));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), PatientInfo_.address));
            }
            if (criteria.getVillageTown() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVillageTown(), PatientInfo_.villageTown));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPincode(), PatientInfo_.pincode));
            }
            if (criteria.getPatientCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPatientCategory(), PatientInfo_.patientCategory));
            }
            if (criteria.getDateOfSampleCollection() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDateOfSampleCollection(), PatientInfo_.dateOfSampleCollection));
            }
            if (criteria.getDateOfSampleReceived() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDateOfSampleReceived(), PatientInfo_.dateOfSampleReceived));
            }
            if (criteria.getSampleType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSampleType(), PatientInfo_.sampleType));
            }
            if (criteria.getSampleId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSampleId(), PatientInfo_.sampleId));
            }
            if (criteria.getUnderlyingMedicalCondition() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getUnderlyingMedicalCondition(), PatientInfo_.underlyingMedicalCondition)
                    );
            }
            if (criteria.getHospitalized() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHospitalized(), PatientInfo_.hospitalized));
            }
            if (criteria.getHospitalName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHospitalName(), PatientInfo_.hospitalName));
            }
            if (criteria.getHospitalizationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getHospitalizationDate(), PatientInfo_.hospitalizationDate));
            }
            if (criteria.getHospitalState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHospitalState(), PatientInfo_.hospitalState));
            }
            if (criteria.getHospitalDistrict() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHospitalDistrict(), PatientInfo_.hospitalDistrict));
            }
            if (criteria.getSymptomsStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymptomsStatus(), PatientInfo_.symptomsStatus));
            }
            if (criteria.getSymptoms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSymptoms(), PatientInfo_.symptoms));
            }
            if (criteria.getTestingKitUsed() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTestingKitUsed(), PatientInfo_.testingKitUsed));
            }
            if (criteria.geteGeneNGene() != null) {
                specification = specification.and(buildStringSpecification(criteria.geteGeneNGene(), PatientInfo_.eGeneNGene));
            }
            if (criteria.getCtValueOfEGeneNGene() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCtValueOfEGeneNGene(), PatientInfo_.ctValueOfEGeneNGene));
            }
            if (criteria.getRdRpSGene() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRdRpSGene(), PatientInfo_.rdRpSGene));
            }
            if (criteria.getCtValueOfRdRpSGene() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getCtValueOfRdRpSGene(), PatientInfo_.ctValueOfRdRpSGene));
            }
            if (criteria.getoRF1aORF1bNN2Gene() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getoRF1aORF1bNN2Gene(), PatientInfo_.oRF1aORF1bNN2Gene));
            }
            if (criteria.getCtValueOfORF1aORF1bNN2Gene() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getCtValueOfORF1aORF1bNN2Gene(), PatientInfo_.ctValueOfORF1aORF1bNN2Gene)
                    );
            }
            if (criteria.getRepeatSample() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepeatSample(), PatientInfo_.repeatSample));
            }
            if (criteria.getDateOfSampleTested() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDateOfSampleTested(), PatientInfo_.dateOfSampleTested));
            }
            if (criteria.getEntryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEntryDate(), PatientInfo_.entryDate));
            }
            if (criteria.getConfirmationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConfirmationDate(), PatientInfo_.confirmationDate));
            }
            if (criteria.getFinalResultSample() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFinalResultSample(), PatientInfo_.finalResultSample));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), PatientInfo_.remarks));
            }
            if (criteria.getEditedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEditedOn(), PatientInfo_.editedOn));
            }
            if (criteria.getCcmsPullDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCcmsPullDate(), PatientInfo_.ccmsPullDate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PatientInfo_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PatientInfo_.lastModifiedBy));
            }
            if (criteria.getStateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStateId(), root -> root.join(PatientInfo_.state, JoinType.LEFT).get(State_.id))
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDistrictId(),
                            root -> root.join(PatientInfo_.district, JoinType.LEFT).get(District_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
