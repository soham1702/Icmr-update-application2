package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.HospitalType;
import com.techvg.covid.care.repository.HospitalTypeRepository;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
import com.techvg.covid.care.service.mapper.HospitalTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link HospitalType}.
 */
@Service
@Transactional
public class HospitalTypeService {

    private final Logger log = LoggerFactory.getLogger(HospitalTypeService.class);

    private final HospitalTypeRepository hospitalTypeRepository;

    private final HospitalTypeMapper hospitalTypeMapper;

    public HospitalTypeService(HospitalTypeRepository hospitalTypeRepository, HospitalTypeMapper hospitalTypeMapper) {
        this.hospitalTypeRepository = hospitalTypeRepository;
        this.hospitalTypeMapper = hospitalTypeMapper;
    }

    /**
     * Save a hospitalType.
     *
     * @param hospitalTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public HospitalTypeDTO save(HospitalTypeDTO hospitalTypeDTO) {
        log.debug("Request to save HospitalType : {}", hospitalTypeDTO);
        HospitalType hospitalType = hospitalTypeMapper.toEntity(hospitalTypeDTO);
        hospitalType = hospitalTypeRepository.save(hospitalType);
        return hospitalTypeMapper.toDto(hospitalType);
    }

    /**
     * Partially update a hospitalType.
     *
     * @param hospitalTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HospitalTypeDTO> partialUpdate(HospitalTypeDTO hospitalTypeDTO) {
        log.debug("Request to partially update HospitalType : {}", hospitalTypeDTO);

        return hospitalTypeRepository
            .findById(hospitalTypeDTO.getId())
            .map(existingHospitalType -> {
                hospitalTypeMapper.partialUpdate(existingHospitalType, hospitalTypeDTO);

                return existingHospitalType;
            })
            .map(hospitalTypeRepository::save)
            .map(hospitalTypeMapper::toDto);
    }

    /**
     * Get all the hospitalTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<HospitalTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HospitalTypes");
        return hospitalTypeRepository.findAll(pageable).map(hospitalTypeMapper::toDto);
    }

    /**
     * Get one hospitalType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HospitalTypeDTO> findOne(Long id) {
        log.debug("Request to get HospitalType : {}", id);
        return hospitalTypeRepository.findById(id).map(hospitalTypeMapper::toDto);
    }

    /**
     * Delete the hospitalType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete HospitalType : {}", id);
        hospitalTypeRepository.deleteById(id);
    }
}
