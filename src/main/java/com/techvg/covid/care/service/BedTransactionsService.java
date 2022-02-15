package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.BedTransactions;
import com.techvg.covid.care.repository.BedTransactionsRepository;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
import com.techvg.covid.care.service.mapper.BedTransactionsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BedTransactions}.
 */
@Service
@Transactional
public class BedTransactionsService {

    private final Logger log = LoggerFactory.getLogger(BedTransactionsService.class);

    private final BedTransactionsRepository bedTransactionsRepository;

    private final BedTransactionsMapper bedTransactionsMapper;

    public BedTransactionsService(BedTransactionsRepository bedTransactionsRepository, BedTransactionsMapper bedTransactionsMapper) {
        this.bedTransactionsRepository = bedTransactionsRepository;
        this.bedTransactionsMapper = bedTransactionsMapper;
    }

    /**
     * Save a bedTransactions.
     *
     * @param bedTransactionsDTO the entity to save.
     * @return the persisted entity.
     */
    public BedTransactionsDTO save(BedTransactionsDTO bedTransactionsDTO) {
        log.debug("Request to save BedTransactions : {}", bedTransactionsDTO);
        BedTransactions bedTransactions = bedTransactionsMapper.toEntity(bedTransactionsDTO);
        bedTransactions = bedTransactionsRepository.save(bedTransactions);
        return bedTransactionsMapper.toDto(bedTransactions);
    }

    /**
     * Partially update a bedTransactions.
     *
     * @param bedTransactionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BedTransactionsDTO> partialUpdate(BedTransactionsDTO bedTransactionsDTO) {
        log.debug("Request to partially update BedTransactions : {}", bedTransactionsDTO);

        return bedTransactionsRepository
            .findById(bedTransactionsDTO.getId())
            .map(existingBedTransactions -> {
                bedTransactionsMapper.partialUpdate(existingBedTransactions, bedTransactionsDTO);

                return existingBedTransactions;
            })
            .map(bedTransactionsRepository::save)
            .map(bedTransactionsMapper::toDto);
    }

    /**
     * Get all the bedTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BedTransactionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BedTransactions");
        return bedTransactionsRepository.findAll(pageable).map(bedTransactionsMapper::toDto);
    }

    /**
     * Get one bedTransactions by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BedTransactionsDTO> findOne(Long id) {
        log.debug("Request to get BedTransactions : {}", id);
        return bedTransactionsRepository.findById(id).map(bedTransactionsMapper::toDto);
    }

    /**
     * Delete the bedTransactions by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BedTransactions : {}", id);
        bedTransactionsRepository.deleteById(id);
    }
}
