package com.techvg.covid.care.service;

import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.repository.SecurityUserRepository;
import com.techvg.covid.care.service.dto.SecurityUserDTO;
import com.techvg.covid.care.service.mapper.SecurityUserMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SecurityUser}.
 */
@Service
@Transactional
public class SecurityUserService {

    private final Logger log = LoggerFactory.getLogger(SecurityUserService.class);

    private final SecurityUserRepository securityUserRepository;

    private final SecurityUserMapper securityUserMapper;

    public SecurityUserService(SecurityUserRepository securityUserRepository, SecurityUserMapper securityUserMapper) {
        this.securityUserRepository = securityUserRepository;
        this.securityUserMapper = securityUserMapper;
    }

    /**
     * Save a securityUser.
     *
     * @param securityUserDTO the entity to save.
     * @return the persisted entity.
     */
    public SecurityUserDTO save(SecurityUserDTO securityUserDTO) {
        log.debug("Request to save SecurityUser : {}", securityUserDTO);
        SecurityUser securityUser = securityUserMapper.toEntity(securityUserDTO);
        securityUser = securityUserRepository.save(securityUser);
        return securityUserMapper.toDto(securityUser);
    }

    /**
     * Partially update a securityUser.
     *
     * @param securityUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SecurityUserDTO> partialUpdate(SecurityUserDTO securityUserDTO) {
        log.debug("Request to partially update SecurityUser : {}", securityUserDTO);

        return securityUserRepository
            .findById(securityUserDTO.getId())
            .map(existingSecurityUser -> {
                securityUserMapper.partialUpdate(existingSecurityUser, securityUserDTO);

                return existingSecurityUser;
            })
            .map(securityUserRepository::save)
            .map(securityUserMapper::toDto);
    }

    /**
     * Get all the securityUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SecurityUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SecurityUsers");
        return securityUserRepository.findAll(pageable).map(securityUserMapper::toDto);
    }

    /**
     * Get all the securityUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SecurityUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return securityUserRepository.findAllWithEagerRelationships(pageable).map(securityUserMapper::toDto);
    }

    /**
     * Get one securityUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SecurityUserDTO> findOne(Long id) {
        log.debug("Request to get SecurityUser : {}", id);
        return securityUserRepository.findOneWithEagerRelationships(id).map(securityUserMapper::toDto);
    }

    /**
     * Delete the securityUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SecurityUser : {}", id);
        securityUserRepository.deleteById(id);
    }
}
