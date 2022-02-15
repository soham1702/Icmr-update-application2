package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.AuditSystem;
import com.techvg.covid.care.repository.AuditSystemRepository;
import com.techvg.covid.care.service.dto.AuditSystemDTO;
import com.techvg.covid.care.service.mapper.AuditSystemMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuditSystem}.
 */
@Service
@Transactional
public class AuditSystemService {

    private final Logger log = LoggerFactory.getLogger(AuditSystemService.class);

    private final AuditSystemRepository auditSystemRepository;

    private final AuditSystemMapper auditSystemMapper;

    public AuditSystemService(AuditSystemRepository auditSystemRepository, AuditSystemMapper auditSystemMapper) {
        this.auditSystemRepository = auditSystemRepository;
        this.auditSystemMapper = auditSystemMapper;
    }

    /**
     * Save a auditSystem.
     *
     * @param auditSystemDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditSystemDTO save(AuditSystemDTO auditSystemDTO) {
        log.debug("Request to save AuditSystem : {}", auditSystemDTO);
        AuditSystem auditSystem = auditSystemMapper.toEntity(auditSystemDTO);
        auditSystem = auditSystemRepository.save(auditSystem);
        return auditSystemMapper.toDto(auditSystem);
    }

    /**
     * Partially update a auditSystem.
     *
     * @param auditSystemDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditSystemDTO> partialUpdate(AuditSystemDTO auditSystemDTO) {
        log.debug("Request to partially update AuditSystem : {}", auditSystemDTO);

        return auditSystemRepository
            .findById(auditSystemDTO.getId())
            .map(existingAuditSystem -> {
                auditSystemMapper.partialUpdate(existingAuditSystem, auditSystemDTO);

                return existingAuditSystem;
            })
            .map(auditSystemRepository::save)
            .map(auditSystemMapper::toDto);
    }

    /**
     * Get all the auditSystems.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditSystemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditSystems");
        return auditSystemRepository.findAll(pageable).map(auditSystemMapper::toDto);
    }

    /**
     * Get one auditSystem by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditSystemDTO> findOne(Long id) {
        log.debug("Request to get AuditSystem : {}", id);
        return auditSystemRepository.findById(id).map(auditSystemMapper::toDto);
    }

    /**
     * Delete the auditSystem by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditSystem : {}", id);
        auditSystemRepository.deleteById(id);
    }
}
