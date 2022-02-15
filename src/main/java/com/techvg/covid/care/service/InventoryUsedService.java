package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.InventoryUsed;
import com.techvg.covid.care.repository.InventoryUsedRepository;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
import com.techvg.covid.care.service.mapper.InventoryUsedMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InventoryUsed}.
 */
@Service
@Transactional
public class InventoryUsedService {

    private final Logger log = LoggerFactory.getLogger(InventoryUsedService.class);

    private final InventoryUsedRepository inventoryUsedRepository;

    private final InventoryUsedMapper inventoryUsedMapper;

    public InventoryUsedService(InventoryUsedRepository inventoryUsedRepository, InventoryUsedMapper inventoryUsedMapper) {
        this.inventoryUsedRepository = inventoryUsedRepository;
        this.inventoryUsedMapper = inventoryUsedMapper;
    }

    /**
     * Save a inventoryUsed.
     *
     * @param inventoryUsedDTO the entity to save.
     * @return the persisted entity.
     */
    public InventoryUsedDTO save(InventoryUsedDTO inventoryUsedDTO) {
        log.debug("Request to save InventoryUsed : {}", inventoryUsedDTO);
        InventoryUsed inventoryUsed = inventoryUsedMapper.toEntity(inventoryUsedDTO);
        inventoryUsed = inventoryUsedRepository.save(inventoryUsed);
        return inventoryUsedMapper.toDto(inventoryUsed);
    }

    /**
     * Partially update a inventoryUsed.
     *
     * @param inventoryUsedDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InventoryUsedDTO> partialUpdate(InventoryUsedDTO inventoryUsedDTO) {
        log.debug("Request to partially update InventoryUsed : {}", inventoryUsedDTO);

        return inventoryUsedRepository
            .findById(inventoryUsedDTO.getId())
            .map(existingInventoryUsed -> {
                inventoryUsedMapper.partialUpdate(existingInventoryUsed, inventoryUsedDTO);

                return existingInventoryUsed;
            })
            .map(inventoryUsedRepository::save)
            .map(inventoryUsedMapper::toDto);
    }

    /**
     * Get all the inventoryUseds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryUsedDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryUseds");
        return inventoryUsedRepository.findAll(pageable).map(inventoryUsedMapper::toDto);
    }

    /**
     * Get one inventoryUsed by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryUsedDTO> findOne(Long id) {
        log.debug("Request to get InventoryUsed : {}", id);
        return inventoryUsedRepository.findById(id).map(inventoryUsedMapper::toDto);
    }

    /**
     * Delete the inventoryUsed by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryUsed : {}", id);
        inventoryUsedRepository.deleteById(id);
    }
}
