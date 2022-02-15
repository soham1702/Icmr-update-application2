package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.BedType;
import com.techvg.covid.care.repository.BedTypeRepository;
import com.techvg.covid.care.service.dto.BedTypeDTO;
import com.techvg.covid.care.service.mapper.BedTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BedType}.
 */
@Service
@Transactional
public class BedTypeService {

    private final Logger log = LoggerFactory.getLogger(BedTypeService.class);

    private final BedTypeRepository bedTypeRepository;

    private final BedTypeMapper bedTypeMapper;

    public BedTypeService(BedTypeRepository bedTypeRepository, BedTypeMapper bedTypeMapper) {
        this.bedTypeRepository = bedTypeRepository;
        this.bedTypeMapper = bedTypeMapper;
    }

    /**
     * Save a bedType.
     *
     * @param bedTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public BedTypeDTO save(BedTypeDTO bedTypeDTO) {
        log.debug("Request to save BedType : {}", bedTypeDTO);
        BedType bedType = bedTypeMapper.toEntity(bedTypeDTO);
        bedType = bedTypeRepository.save(bedType);
        return bedTypeMapper.toDto(bedType);
    }

    /**
     * Partially update a bedType.
     *
     * @param bedTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BedTypeDTO> partialUpdate(BedTypeDTO bedTypeDTO) {
        log.debug("Request to partially update BedType : {}", bedTypeDTO);

        return bedTypeRepository
            .findById(bedTypeDTO.getId())
            .map(existingBedType -> {
                bedTypeMapper.partialUpdate(existingBedType, bedTypeDTO);

                return existingBedType;
            })
            .map(bedTypeRepository::save)
            .map(bedTypeMapper::toDto);
    }

    /**
     * Get all the bedTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BedTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BedTypes");
        return bedTypeRepository.findAll(pageable).map(bedTypeMapper::toDto);
    }

    /**
     * Get one bedType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BedTypeDTO> findOne(Long id) {
        log.debug("Request to get BedType : {}", id);
        return bedTypeRepository.findById(id).map(bedTypeMapper::toDto);
    }

    /**
     * Delete the bedType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BedType : {}", id);
        bedTypeRepository.deleteById(id);
    }
}
