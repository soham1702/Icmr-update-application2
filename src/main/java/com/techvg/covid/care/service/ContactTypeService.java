package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.ContactType;
import com.techvg.covid.care.repository.ContactTypeRepository;
import com.techvg.covid.care.service.dto.ContactTypeDTO;
import com.techvg.covid.care.service.mapper.ContactTypeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactType}.
 */
@Service
@Transactional
public class ContactTypeService {

    private final Logger log = LoggerFactory.getLogger(ContactTypeService.class);

    private final ContactTypeRepository contactTypeRepository;

    private final ContactTypeMapper contactTypeMapper;

    public ContactTypeService(ContactTypeRepository contactTypeRepository, ContactTypeMapper contactTypeMapper) {
        this.contactTypeRepository = contactTypeRepository;
        this.contactTypeMapper = contactTypeMapper;
    }

    /**
     * Save a contactType.
     *
     * @param contactTypeDTO the entity to save.
     * @return the persisted entity.
     */
    public ContactTypeDTO save(ContactTypeDTO contactTypeDTO) {
        log.debug("Request to save ContactType : {}", contactTypeDTO);
        ContactType contactType = contactTypeMapper.toEntity(contactTypeDTO);
        contactType = contactTypeRepository.save(contactType);
        return contactTypeMapper.toDto(contactType);
    }

    /**
     * Partially update a contactType.
     *
     * @param contactTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContactTypeDTO> partialUpdate(ContactTypeDTO contactTypeDTO) {
        log.debug("Request to partially update ContactType : {}", contactTypeDTO);

        return contactTypeRepository
            .findById(contactTypeDTO.getId())
            .map(existingContactType -> {
                contactTypeMapper.partialUpdate(existingContactType, contactTypeDTO);

                return existingContactType;
            })
            .map(contactTypeRepository::save)
            .map(contactTypeMapper::toDto);
    }

    /**
     * Get all the contactTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContactTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContactTypes");
        return contactTypeRepository.findAll(pageable).map(contactTypeMapper::toDto);
    }

    /**
     * Get one contactType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContactTypeDTO> findOne(Long id) {
        log.debug("Request to get ContactType : {}", id);
        return contactTypeRepository.findById(id).map(contactTypeMapper::toDto);
    }

    /**
     * Delete the contactType by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactType : {}", id);
        contactTypeRepository.deleteById(id);
    }
}
