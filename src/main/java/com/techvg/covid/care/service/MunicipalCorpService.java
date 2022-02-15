package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.MunicipalCorp;
import com.techvg.covid.care.repository.MunicipalCorpRepository;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;
import com.techvg.covid.care.service.mapper.MunicipalCorpMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MunicipalCorp}.
 */
@Service
@Transactional
public class MunicipalCorpService {

    private final Logger log = LoggerFactory.getLogger(MunicipalCorpService.class);

    private final MunicipalCorpRepository municipalCorpRepository;

    private final MunicipalCorpMapper municipalCorpMapper;

    public MunicipalCorpService(MunicipalCorpRepository municipalCorpRepository, MunicipalCorpMapper municipalCorpMapper) {
        this.municipalCorpRepository = municipalCorpRepository;
        this.municipalCorpMapper = municipalCorpMapper;
    }

    /**
     * Save a municipalCorp.
     *
     * @param municipalCorpDTO the entity to save.
     * @return the persisted entity.
     */
    public MunicipalCorpDTO save(MunicipalCorpDTO municipalCorpDTO) {
        log.debug("Request to save MunicipalCorp : {}", municipalCorpDTO);
        MunicipalCorp municipalCorp = municipalCorpMapper.toEntity(municipalCorpDTO);
        municipalCorp = municipalCorpRepository.save(municipalCorp);
        return municipalCorpMapper.toDto(municipalCorp);
    }

    /**
     * Partially update a municipalCorp.
     *
     * @param municipalCorpDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MunicipalCorpDTO> partialUpdate(MunicipalCorpDTO municipalCorpDTO) {
        log.debug("Request to partially update MunicipalCorp : {}", municipalCorpDTO);

        return municipalCorpRepository
            .findById(municipalCorpDTO.getId())
            .map(existingMunicipalCorp -> {
                municipalCorpMapper.partialUpdate(existingMunicipalCorp, municipalCorpDTO);

                return existingMunicipalCorp;
            })
            .map(municipalCorpRepository::save)
            .map(municipalCorpMapper::toDto);
    }

    /**
     * Get all the municipalCorps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MunicipalCorpDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MunicipalCorps");
        return municipalCorpRepository.findAll(pageable).map(municipalCorpMapper::toDto);
    }

    /**
     * Get one municipalCorp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MunicipalCorpDTO> findOne(Long id) {
        log.debug("Request to get MunicipalCorp : {}", id);
        return municipalCorpRepository.findById(id).map(municipalCorpMapper::toDto);
    }

    /**
     * Delete the municipalCorp by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MunicipalCorp : {}", id);
        municipalCorpRepository.deleteById(id);
    }
}
