package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.TripDetails;
import com.techvg.covid.care.repository.TripDetailsRepository;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import com.techvg.covid.care.service.mapper.TripDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TripDetails}.
 */
@Service
@Transactional
public class TripDetailsService {

    private final Logger log = LoggerFactory.getLogger(TripDetailsService.class);

    private final TripDetailsRepository tripDetailsRepository;

    private final TripDetailsMapper tripDetailsMapper;

    public TripDetailsService(TripDetailsRepository tripDetailsRepository, TripDetailsMapper tripDetailsMapper) {
        this.tripDetailsRepository = tripDetailsRepository;
        this.tripDetailsMapper = tripDetailsMapper;
    }

    /**
     * Save a tripDetails.
     *
     * @param tripDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public TripDetailsDTO save(TripDetailsDTO tripDetailsDTO) {
        log.debug("Request to save TripDetails : {}", tripDetailsDTO);
        TripDetails tripDetails = tripDetailsMapper.toEntity(tripDetailsDTO);
        tripDetails = tripDetailsRepository.save(tripDetails);
        return tripDetailsMapper.toDto(tripDetails);
    }

    /**
     * Partially update a tripDetails.
     *
     * @param tripDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TripDetailsDTO> partialUpdate(TripDetailsDTO tripDetailsDTO) {
        log.debug("Request to partially update TripDetails : {}", tripDetailsDTO);

        return tripDetailsRepository
            .findById(tripDetailsDTO.getId())
            .map(existingTripDetails -> {
                tripDetailsMapper.partialUpdate(existingTripDetails, tripDetailsDTO);

                return existingTripDetails;
            })
            .map(tripDetailsRepository::save)
            .map(tripDetailsMapper::toDto);
    }

    /**
     * Get all the tripDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TripDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TripDetails");
        return tripDetailsRepository.findAll(pageable).map(tripDetailsMapper::toDto);
    }

    /**
     * Get one tripDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TripDetailsDTO> findOne(Long id) {
        log.debug("Request to get TripDetails : {}", id);
        return tripDetailsRepository.findById(id).map(tripDetailsMapper::toDto);
    }

    /**
     * Delete the tripDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TripDetails : {}", id);
        tripDetailsRepository.deleteById(id);
    }
}
