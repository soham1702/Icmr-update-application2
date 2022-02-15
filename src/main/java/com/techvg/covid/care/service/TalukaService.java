package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.Taluka;
import com.techvg.covid.care.repository.TalukaRepository;
import com.techvg.covid.care.service.dto.TalukaDTO;
import com.techvg.covid.care.service.mapper.TalukaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Taluka}.
 */
@Service
@Transactional
public class TalukaService {

    private final Logger log = LoggerFactory.getLogger(TalukaService.class);

    private final TalukaRepository talukaRepository;

    private final TalukaMapper talukaMapper;

    public TalukaService(TalukaRepository talukaRepository, TalukaMapper talukaMapper) {
        this.talukaRepository = talukaRepository;
        this.talukaMapper = talukaMapper;
    }

    /**
     * Save a taluka.
     *
     * @param talukaDTO the entity to save.
     * @return the persisted entity.
     */
    public TalukaDTO save(TalukaDTO talukaDTO) {
        log.debug("Request to save Taluka : {}", talukaDTO);
        Taluka taluka = talukaMapper.toEntity(talukaDTO);
        taluka = talukaRepository.save(taluka);
        return talukaMapper.toDto(taluka);
    }

    /**
     * Partially update a taluka.
     *
     * @param talukaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TalukaDTO> partialUpdate(TalukaDTO talukaDTO) {
        log.debug("Request to partially update Taluka : {}", talukaDTO);

        return talukaRepository
            .findById(talukaDTO.getId())
            .map(existingTaluka -> {
                talukaMapper.partialUpdate(existingTaluka, talukaDTO);

                return existingTaluka;
            })
            .map(talukaRepository::save)
            .map(talukaMapper::toDto);
    }

    /**
     * Get all the talukas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TalukaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Talukas");
        return talukaRepository.findAll(pageable).map(talukaMapper::toDto);
    }

    /**
     * Get one taluka by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TalukaDTO> findOne(Long id) {
        log.debug("Request to get Taluka : {}", id);
        return talukaRepository.findById(id).map(talukaMapper::toDto);
    }

    /**
     * Delete the taluka by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Taluka : {}", id);
        talukaRepository.deleteById(id);
    }
}
