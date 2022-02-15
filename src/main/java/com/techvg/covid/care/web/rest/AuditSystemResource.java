package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.AuditSystemRepository;
import com.techvg.covid.care.service.AuditSystemQueryService;
import com.techvg.covid.care.service.AuditSystemService;
import com.techvg.covid.care.service.criteria.AuditSystemCriteria;
import com.techvg.covid.care.service.dto.AuditSystemDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.AuditSystem}.
 */
@RestController
@RequestMapping("/api")
public class AuditSystemResource {

    private final Logger log = LoggerFactory.getLogger(AuditSystemResource.class);

    private static final String ENTITY_NAME = "auditSystem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditSystemService auditSystemService;

    private final AuditSystemRepository auditSystemRepository;

    private final AuditSystemQueryService auditSystemQueryService;

    public AuditSystemResource(
        AuditSystemService auditSystemService,
        AuditSystemRepository auditSystemRepository,
        AuditSystemQueryService auditSystemQueryService
    ) {
        this.auditSystemService = auditSystemService;
        this.auditSystemRepository = auditSystemRepository;
        this.auditSystemQueryService = auditSystemQueryService;
    }

    /**
     * {@code POST  /audit-systems} : Create a new auditSystem.
     *
     * @param auditSystemDTO the auditSystemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditSystemDTO, or with status {@code 400 (Bad Request)} if the auditSystem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-systems")
    public ResponseEntity<AuditSystemDTO> createAuditSystem(@Valid @RequestBody AuditSystemDTO auditSystemDTO) throws URISyntaxException {
        log.debug("REST request to save AuditSystem : {}", auditSystemDTO);
        if (auditSystemDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditSystem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditSystemDTO result = auditSystemService.save(auditSystemDTO);
        return ResponseEntity
            .created(new URI("/api/audit-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-systems/:id} : Updates an existing auditSystem.
     *
     * @param id the id of the auditSystemDTO to save.
     * @param auditSystemDTO the auditSystemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditSystemDTO,
     * or with status {@code 400 (Bad Request)} if the auditSystemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditSystemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-systems/{id}")
    public ResponseEntity<AuditSystemDTO> updateAuditSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuditSystemDTO auditSystemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuditSystem : {}, {}", id, auditSystemDTO);
        if (auditSystemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditSystemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditSystemDTO result = auditSystemService.save(auditSystemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditSystemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /audit-systems/:id} : Partial updates given fields of an existing auditSystem, field will ignore if it is null
     *
     * @param id the id of the auditSystemDTO to save.
     * @param auditSystemDTO the auditSystemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditSystemDTO,
     * or with status {@code 400 (Bad Request)} if the auditSystemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the auditSystemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditSystemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/audit-systems/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuditSystemDTO> partialUpdateAuditSystem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuditSystemDTO auditSystemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuditSystem partially : {}, {}", id, auditSystemDTO);
        if (auditSystemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditSystemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditSystemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditSystemDTO> result = auditSystemService.partialUpdate(auditSystemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditSystemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /audit-systems} : get all the auditSystems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditSystems in body.
     */
    @GetMapping("/audit-systems")
    public ResponseEntity<List<AuditSystemDTO>> getAllAuditSystems(
        AuditSystemCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AuditSystems by criteria: {}", criteria);
        Page<AuditSystemDTO> page = auditSystemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-systems/count} : count all the auditSystems.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/audit-systems/count")
    public ResponseEntity<Long> countAuditSystems(AuditSystemCriteria criteria) {
        log.debug("REST request to count AuditSystems by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditSystemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /audit-systems/:id} : get the "id" auditSystem.
     *
     * @param id the id of the auditSystemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditSystemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-systems/{id}")
    public ResponseEntity<AuditSystemDTO> getAuditSystem(@PathVariable Long id) {
        log.debug("REST request to get AuditSystem : {}", id);
        Optional<AuditSystemDTO> auditSystemDTO = auditSystemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditSystemDTO);
    }

    /**
     * {@code DELETE  /audit-systems/:id} : delete the "id" auditSystem.
     *
     * @param id the id of the auditSystemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-systems/{id}")
    public ResponseEntity<Void> deleteAuditSystem(@PathVariable Long id) {
        log.debug("REST request to delete AuditSystem : {}", id);
        auditSystemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
