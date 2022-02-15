package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.ICMRDailyCount;
import com.techvg.covid.care.repository.ICMRDailyCountRepository;
import com.techvg.covid.care.service.dto.ICMRDailyCountDTO;
import com.techvg.covid.care.service.mapper.ICMRDailyCountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ICMRDailyCount}.
 */
@Service
@Transactional
public class ICMRDailyCountService {

    private final Logger log = LoggerFactory.getLogger(ICMRDailyCountService.class);

    private final ICMRDailyCountRepository iCMRDailyCountRepository;

    private final ICMRDailyCountMapper iCMRDailyCountMapper;

    public ICMRDailyCountService(ICMRDailyCountRepository iCMRDailyCountRepository, ICMRDailyCountMapper iCMRDailyCountMapper) {
        this.iCMRDailyCountRepository = iCMRDailyCountRepository;
        this.iCMRDailyCountMapper = iCMRDailyCountMapper;
    }

    /**
     * Save a iCMRDailyCount.
     *
     * @param iCMRDailyCountDTO the entity to save.
     * @return the persisted entity.
     */
    public ICMRDailyCountDTO save(ICMRDailyCountDTO iCMRDailyCountDTO) {
        log.debug("Request to save ICMRDailyCount : {}", iCMRDailyCountDTO);
        ICMRDailyCount iCMRDailyCount = iCMRDailyCountMapper.toEntity(iCMRDailyCountDTO);
        iCMRDailyCount = iCMRDailyCountRepository.save(iCMRDailyCount);
        return iCMRDailyCountMapper.toDto(iCMRDailyCount);
    }

    /**
     * Partially update a iCMRDailyCount.
     *
     * @param iCMRDailyCountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ICMRDailyCountDTO> partialUpdate(ICMRDailyCountDTO iCMRDailyCountDTO) {
        log.debug("Request to partially update ICMRDailyCount : {}", iCMRDailyCountDTO);

        return iCMRDailyCountRepository
            .findById(iCMRDailyCountDTO.getId())
            .map(existingICMRDailyCount -> {
                iCMRDailyCountMapper.partialUpdate(existingICMRDailyCount, iCMRDailyCountDTO);

                return existingICMRDailyCount;
            })
            .map(iCMRDailyCountRepository::save)
            .map(iCMRDailyCountMapper::toDto);
    }

    /**
     * Get all the iCMRDailyCounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ICMRDailyCountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ICMRDailyCounts");
        return iCMRDailyCountRepository.findAll(pageable).map(iCMRDailyCountMapper::toDto);
    }

    /**
     * Get one iCMRDailyCount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ICMRDailyCountDTO> findOne(Long id) {
        log.debug("Request to get ICMRDailyCount : {}", id);
        return iCMRDailyCountRepository.findById(id).map(iCMRDailyCountMapper::toDto);
    }

    /**
     * Delete the iCMRDailyCount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ICMRDailyCount : {}", id);
        iCMRDailyCountRepository.deleteById(id);
    }
}
