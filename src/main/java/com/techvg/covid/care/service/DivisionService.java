package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.Division;
import com.techvg.covid.care.repository.DivisionRepository;
import com.techvg.covid.care.service.dto.DivisionDTO;
import com.techvg.covid.care.service.mapper.DivisionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Division}.
 */
@Service
@Transactional
public class DivisionService {

    private final Logger log = LoggerFactory.getLogger(DivisionService.class);

    private final DivisionRepository divisionRepository;

    private final DivisionMapper divisionMapper;

    public DivisionService(DivisionRepository divisionRepository, DivisionMapper divisionMapper) {
        this.divisionRepository = divisionRepository;
        this.divisionMapper = divisionMapper;
    }

    /**
     * Save a division.
     *
     * @param divisionDTO the entity to save.
     * @return the persisted entity.
     */
    public DivisionDTO save(DivisionDTO divisionDTO) {
        log.debug("Request to save Division : {}", divisionDTO);
        Division division = divisionMapper.toEntity(divisionDTO);
        division = divisionRepository.save(division);
        return divisionMapper.toDto(division);
    }

    /**
     * Partially update a division.
     *
     * @param divisionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DivisionDTO> partialUpdate(DivisionDTO divisionDTO) {
        log.debug("Request to partially update Division : {}", divisionDTO);

        return divisionRepository
            .findById(divisionDTO.getId())
            .map(existingDivision -> {
                divisionMapper.partialUpdate(existingDivision, divisionDTO);

                return existingDivision;
            })
            .map(divisionRepository::save)
            .map(divisionMapper::toDto);
    }

    /**
     * Get all the divisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DivisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Divisions");
        return divisionRepository.findAll(pageable).map(divisionMapper::toDto);
    }

    /**
     * Get one division by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DivisionDTO> findOne(Long id) {
        log.debug("Request to get Division : {}", id);
        return divisionRepository.findById(id).map(divisionMapper::toDto);
    }

    /**
     * Delete the division by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Division : {}", id);
        divisionRepository.deleteById(id);
    }
}
