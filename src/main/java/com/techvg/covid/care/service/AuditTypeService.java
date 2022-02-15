package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.AuditType;
import com.techvg.covid.care.repository.AuditTypeRepository;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
import com.techvg.covid.care.service.mapper.AuditTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AuditType}.
 */
@Service
@Transactional
public class AuditTypeService {

    private final Logger log = LoggerFactory.getLogger(AuditTypeService.class);

    private final AuditTypeRepository auditTypeRepository;

    private final AuditTypeMapper auditTypeMapper;

    public AuditTypeService(AuditTypeRepository auditTypeRepository, AuditTypeMapper auditTypeMapper) {
        this.auditTypeRepository = auditTypeRepository;
        this.auditTypeMapper = auditTypeMapper;
    }

    /**
     * Save a auditType.
     *
     * @param auditTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditTypeDTO save(AuditTypeDTO auditTypeDTO) {
        log.debug("Request to save AuditType : {}", auditTypeDTO);
        AuditType auditType = auditTypeMapper.toEntity(auditTypeDTO);
        auditType = auditTypeRepository.save(auditType);
        return auditTypeMapper.toDto(auditType);
    }

    /**
     * Partially update a auditType.
     *
     * @param auditTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditTypeDTO> partialUpdate(AuditTypeDTO auditTypeDTO) {
        log.debug("Request to partially update AuditType : {}", auditTypeDTO);

        return auditTypeRepository
            .findById(auditTypeDTO.getId())
            .map(existingAuditType -> {
                auditTypeMapper.partialUpdate(existingAuditType, auditTypeDTO);

                return existingAuditType;
            })
            .map(auditTypeRepository::save)
            .map(auditTypeMapper::toDto);
    }

    /**
     * Get all the auditTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AuditTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditTypes");
        return auditTypeRepository.findAll(pageable).map(auditTypeMapper::toDto);
    }

    /**
     * Get one auditType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AuditTypeDTO> findOne(Long id) {
        log.debug("Request to get AuditType : {}", id);
        return auditTypeRepository.findById(id).map(auditTypeMapper::toDto);
    }

    /**
     * Delete the auditType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditType : {}", id);
        auditTypeRepository.deleteById(id);
    }
}
