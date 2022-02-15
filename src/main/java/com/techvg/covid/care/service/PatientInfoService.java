package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.repository.PatientInfoRepository;
import com.techvg.covid.care.service.dto.PatientInfoDTO;
import com.techvg.covid.care.service.mapper.PatientInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PatientInfo}.
 */
@Service
@Transactional
public class PatientInfoService {

    private final Logger log = LoggerFactory.getLogger(PatientInfoService.class);

    private final PatientInfoRepository patientInfoRepository;

    private final PatientInfoMapper patientInfoMapper;

    public PatientInfoService(PatientInfoRepository patientInfoRepository, PatientInfoMapper patientInfoMapper) {
        this.patientInfoRepository = patientInfoRepository;
        this.patientInfoMapper = patientInfoMapper;
    }

    /**
     * Save a patientInfo.
     *
     * @param patientInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public PatientInfoDTO save(PatientInfoDTO patientInfoDTO) {
        log.debug("Request to save PatientInfo : {}", patientInfoDTO);
        PatientInfo patientInfo = patientInfoMapper.toEntity(patientInfoDTO);
        patientInfo = patientInfoRepository.save(patientInfo);
        return patientInfoMapper.toDto(patientInfo);
    }

    /**
     * Partially update a patientInfo.
     *
     * @param patientInfoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PatientInfoDTO> partialUpdate(PatientInfoDTO patientInfoDTO) {
        log.debug("Request to partially update PatientInfo : {}", patientInfoDTO);

        return patientInfoRepository
            .findById(patientInfoDTO.getId())
            .map(existingPatientInfo -> {
                patientInfoMapper.partialUpdate(existingPatientInfo, patientInfoDTO);

                return existingPatientInfo;
            })
            .map(patientInfoRepository::save)
            .map(patientInfoMapper::toDto);
    }

    /**
     * Get all the patientInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PatientInfos");
        return patientInfoRepository.findAll(pageable).map(patientInfoMapper::toDto);
    }

    /**
     * Get one patientInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PatientInfoDTO> findOne(Long id) {
        log.debug("Request to get PatientInfo : {}", id);
        return patientInfoRepository.findById(id).map(patientInfoMapper::toDto);
    }

    /**
     * Delete the patientInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PatientInfo : {}", id);
        patientInfoRepository.deleteById(id);
    }
}
