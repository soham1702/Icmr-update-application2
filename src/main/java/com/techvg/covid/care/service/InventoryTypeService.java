package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.repository.InventoryTypeRepository;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;
import com.techvg.covid.care.service.mapper.InventoryTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InventoryType}.
 */
@Service
@Transactional
public class InventoryTypeService {

    private final Logger log = LoggerFactory.getLogger(InventoryTypeService.class);

    private final InventoryTypeRepository inventoryTypeRepository;

    private final InventoryTypeMapper inventoryTypeMapper;

    public InventoryTypeService(InventoryTypeRepository inventoryTypeRepository, InventoryTypeMapper inventoryTypeMapper) {
        this.inventoryTypeRepository = inventoryTypeRepository;
        this.inventoryTypeMapper = inventoryTypeMapper;
    }

    /**
     * Save a inventoryType.
     *
     * @param inventoryTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public InventoryTypeDTO save(InventoryTypeDTO inventoryTypeDTO) {
        log.debug("Request to save InventoryType : {}", inventoryTypeDTO);
        InventoryType inventoryType = inventoryTypeMapper.toEntity(inventoryTypeDTO);
        inventoryType = inventoryTypeRepository.save(inventoryType);
        return inventoryTypeMapper.toDto(inventoryType);
    }

    /**
     * Partially update a inventoryType.
     *
     * @param inventoryTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InventoryTypeDTO> partialUpdate(InventoryTypeDTO inventoryTypeDTO) {
        log.debug("Request to partially update InventoryType : {}", inventoryTypeDTO);

        return inventoryTypeRepository
            .findById(inventoryTypeDTO.getId())
            .map(existingInventoryType -> {
                inventoryTypeMapper.partialUpdate(existingInventoryType, inventoryTypeDTO);

                return existingInventoryType;
            })
            .map(inventoryTypeRepository::save)
            .map(inventoryTypeMapper::toDto);
    }

    /**
     * Get all the inventoryTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InventoryTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InventoryTypes");
        return inventoryTypeRepository.findAll(pageable).map(inventoryTypeMapper::toDto);
    }

    /**
     * Get one inventoryType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventoryTypeDTO> findOne(Long id) {
        log.debug("Request to get InventoryType : {}", id);
        return inventoryTypeRepository.findById(id).map(inventoryTypeMapper::toDto);
    }

    /**
     * Delete the inventoryType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InventoryType : {}", id);
        inventoryTypeRepository.deleteById(id);
    }
}
