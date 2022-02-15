package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.InventoryMaster;
import com.techvg.covid.care.repository.InventoryMasterRepository;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
import com.techvg.covid.care.service.mapper.InventoryMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InventoryMaster}.
 */
@Service
@Transactional
public class InventoryMasterService {

    private final Logger log = LoggerFactory.getLogger(InventoryMasterService.class);

    private final InventoryMasterRepository inventoryMasterRepository;

    private final InventoryMasterMapper inventoryMasterMapper;

    public InventoryMasterService(InventoryMasterRepository inventoryMasterRepository, InventoryMasterMapper inventoryMasterMapper) {
        this.inventoryMasterRepository = inventoryMasterRepository;
        this.inventoryMasterMapper = inventoryMasterMapper;
    }

    /**
     * Save a inventoryMaster.
     *
     * @param inventoryMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public InventoryMasterDTO save(InventoryMasterDTO inventoryMasterDTO) {
        log.debug("Request to save InventoryMaster : {}", inventoryMasterDTO);
        InventoryMaster inventoryMaster = inventoryMasterMapper.toEntity(inventoryMasterDTO);
        inventoryMaster = inventoryMasterRepository.save(inventoryMaster);
        return inventoryMasterMapper.toDto(inventoryMaster);
    }

    /**
     * Partially update a inventoryMaster.
     *
     * @param inventoryMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InventoryMasterDTO> partialUpdate(InventoryMasterDTO inventoryMasterDTO) {
        log.debug("Request to partially update InventoryMaster : {}", inventoryMasterDTO);

        return inventoryMasterRepository
            .findById(inventoryMasterDTO.getId())
            .map(existingInventoryMaster -> {
                inventoryMasterMapper.partialUpdate(existingInventoryMaster, inventoryMasterDTO);

                return existingInventoryMaster;
            })
            .map(inventoryMasterRepository::save)
            .map(inventoryMasterMapper::toDto);
    }

    /**
     * Get all the inventoryMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryMasters");
        return inventoryMasterRepository.findAll(pageable).map(inventoryMasterMapper::toDto);
    }

    /**
     * Get one inventoryMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryMasterDTO> findOne(Long id) {
        log.debug("Request to get InventoryMaster : {}", id);
        return inventoryMasterRepository.findById(id).map(inventoryMasterMapper::toDto);
    }

    /**
     * Delete the inventoryMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryMaster : {}", id);
        inventoryMasterRepository.deleteById(id);
    }
}
