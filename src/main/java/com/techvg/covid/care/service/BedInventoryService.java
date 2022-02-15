package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.BedInventory;
import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
import com.techvg.covid.care.service.mapper.BedInventoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BedInventory}.
 */
@Service
@Transactional
public class BedInventoryService {

    private final Logger log = LoggerFactory.getLogger(BedInventoryService.class);

    private final BedInventoryRepository bedInventoryRepository;

    private final BedInventoryMapper bedInventoryMapper;

    public BedInventoryService(BedInventoryRepository bedInventoryRepository, BedInventoryMapper bedInventoryMapper) {
        this.bedInventoryRepository = bedInventoryRepository;
        this.bedInventoryMapper = bedInventoryMapper;
    }

    /**
     * Save a bedInventory.
     *
     * @param bedInventoryDTO the entity to save.
     * @return the persisted entity.
     */
    public BedInventoryDTO save(BedInventoryDTO bedInventoryDTO) {
        log.debug("Request to save BedInventory : {}", bedInventoryDTO);
        BedInventory bedInventory = bedInventoryMapper.toEntity(bedInventoryDTO);
        bedInventory = bedInventoryRepository.save(bedInventory);
        return bedInventoryMapper.toDto(bedInventory);
    }

    /**
     * Partially update a bedInventory.
     *
     * @param bedInventoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BedInventoryDTO> partialUpdate(BedInventoryDTO bedInventoryDTO) {
        log.debug("Request to partially update BedInventory : {}", bedInventoryDTO);

        return bedInventoryRepository
            .findById(bedInventoryDTO.getId())
            .map(existingBedInventory -> {
                bedInventoryMapper.partialUpdate(existingBedInventory, bedInventoryDTO);

                return existingBedInventory;
            })
            .map(bedInventoryRepository::save)
            .map(bedInventoryMapper::toDto);
    }

    /**
     * Get all the bedInventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BedInventoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BedInventories");
        return bedInventoryRepository.findAll(pageable).map(bedInventoryMapper::toDto);
    }

    /**
     * Get one bedInventory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BedInventoryDTO> findOne(Long id) {
        log.debug("Request to get BedInventory : {}", id);
        return bedInventoryRepository.findById(id).map(bedInventoryMapper::toDto);
    }

    /**
     * Delete the bedInventory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BedInventory : {}", id);
        bedInventoryRepository.deleteById(id);
    }
}
