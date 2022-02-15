package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.SecurityUserRepository;
import com.techvg.covid.care.service.SecurityUserQueryService;
import com.techvg.covid.care.service.SecurityUserService;
import com.techvg.covid.care.service.criteria.SecurityUserCriteria;
import com.techvg.covid.care.service.dto.SecurityUserDTO;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.techvg.covid.care.domain.SecurityUser}.
 */
@RestController
@RequestMapping("/api")
public class SecurityUserResource {

    private final Logger log = LoggerFactory.getLogger(SecurityUserResource.class);

    private static final String ENTITY_NAME = "securityUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecurityUserService securityUserService;

    private final SecurityUserRepository securityUserRepository;

    private final SecurityUserQueryService securityUserQueryService;

    public SecurityUserResource(
        SecurityUserService securityUserService,
        SecurityUserRepository securityUserRepository,
        SecurityUserQueryService securityUserQueryService
    ) {
        this.securityUserService = securityUserService;
        this.securityUserRepository = securityUserRepository;
        this.securityUserQueryService = securityUserQueryService;
    }

    /**
     * {@code POST  /security-users} : Create a new securityUser.
     *
     * @param securityUserDTO the securityUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new securityUserDTO, or with status {@code 400 (Bad Request)} if the securityUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/security-users")
    public ResponseEntity<SecurityUserDTO> createSecurityUser(@Valid @RequestBody SecurityUserDTO securityUserDTO)
        throws URISyntaxException {
        log.debug("REST request to save SecurityUser : {}", securityUserDTO);
        if (securityUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new securityUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SecurityUserDTO result = securityUserService.save(securityUserDTO);
        return ResponseEntity
            .created(new URI("/api/security-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /security-users/:id} : Updates an existing securityUser.
     *
     * @param id the id of the securityUserDTO to save.
     * @param securityUserDTO the securityUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityUserDTO,
     * or with status {@code 400 (Bad Request)} if the securityUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the securityUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/security-users/{id}")
    public ResponseEntity<SecurityUserDTO> updateSecurityUser(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SecurityUserDTO securityUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SecurityUser : {}, {}", id, securityUserDTO);
        if (securityUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SecurityUserDTO result = securityUserService.save(securityUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /security-users/:id} : Partial updates given fields of an existing securityUser, field will ignore if it is null
     *
     * @param id the id of the securityUserDTO to save.
     * @param securityUserDTO the securityUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated securityUserDTO,
     * or with status {@code 400 (Bad Request)} if the securityUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the securityUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the securityUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/security-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecurityUserDTO> partialUpdateSecurityUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SecurityUserDTO securityUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecurityUser partially : {}, {}", id, securityUserDTO);
        if (securityUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, securityUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!securityUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecurityUserDTO> result = securityUserService.partialUpdate(securityUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, securityUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /security-users} : get all the securityUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of securityUsers in body.
     */
    @GetMapping("/security-users")
    public ResponseEntity<List<SecurityUserDTO>> getAllSecurityUsers(
        SecurityUserCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SecurityUsers by criteria: {}", criteria);
        Page<SecurityUserDTO> page = securityUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /security-users/count} : count all the securityUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/security-users/count")
    public ResponseEntity<Long> countSecurityUsers(SecurityUserCriteria criteria) {
        log.debug("REST request to count SecurityUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(securityUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /security-users/:id} : get the "id" securityUser.
     *
     * @param id the id of the securityUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the securityUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/security-users/{id}")
    public ResponseEntity<SecurityUserDTO> getSecurityUser(@PathVariable Long id) {
        log.debug("REST request to get SecurityUser : {}", id);
        Optional<SecurityUserDTO> securityUserDTO = securityUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(securityUserDTO);
    }

    /**
     * {@code DELETE  /security-users/:id} : delete the "id" securityUser.
     *
     * @param id the id of the securityUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/security-users/{id}")
    public ResponseEntity<Void> deleteSecurityUser(@PathVariable Long id) {
        log.debug("REST request to delete SecurityUser : {}", id);
        securityUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
