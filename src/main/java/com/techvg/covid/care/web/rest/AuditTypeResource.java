package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.AuditTypeRepository;
import com.techvg.covid.care.service.AuditTypeQueryService;
import com.techvg.covid.care.service.AuditTypeService;
import com.techvg.covid.care.service.criteria.AuditTypeCriteria;
import com.techvg.covid.care.service.dto.AuditTypeDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.AuditType}.
 */
@RestController
@RequestMapping("/api")
public class AuditTypeResource {

    private final Logger log = LoggerFactory.getLogger(AuditTypeResource.class);

    private static final String ENTITY_NAME = "auditType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuditTypeService auditTypeService;

    private final AuditTypeRepository auditTypeRepository;

    private final AuditTypeQueryService auditTypeQueryService;

    public AuditTypeResource(
        AuditTypeService auditTypeService,
        AuditTypeRepository auditTypeRepository,
        AuditTypeQueryService auditTypeQueryService
    ) {
        this.auditTypeService = auditTypeService;
        this.auditTypeRepository = auditTypeRepository;
        this.auditTypeQueryService = auditTypeQueryService;
    }

    /**
     * {@code POST  /audit-types} : Create a new auditType.
     *
     * @param auditTypeDTO the auditTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auditTypeDTO, or with status {@code 400 (Bad Request)} if the auditType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/audit-types")
    public ResponseEntity<AuditTypeDTO> createAuditType(@Valid @RequestBody AuditTypeDTO auditTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AuditType : {}", auditTypeDTO);
        if (auditTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new auditType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AuditTypeDTO result = auditTypeService.save(auditTypeDTO);
        return ResponseEntity
            .created(new URI("/api/audit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /audit-types/:id} : Updates an existing auditType.
     *
     * @param id the id of the auditTypeDTO to save.
     * @param auditTypeDTO the auditTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditTypeDTO,
     * or with status {@code 400 (Bad Request)} if the auditTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auditTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/audit-types/{id}")
    public ResponseEntity<AuditTypeDTO> updateAuditType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AuditTypeDTO auditTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AuditType : {}, {}", id, auditTypeDTO);
        if (auditTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AuditTypeDTO result = auditTypeService.save(auditTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /audit-types/:id} : Partial updates given fields of an existing auditType, field will ignore if it is null
     *
     * @param id the id of the auditTypeDTO to save.
     * @param auditTypeDTO the auditTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auditTypeDTO,
     * or with status {@code 400 (Bad Request)} if the auditTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the auditTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the auditTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/audit-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AuditTypeDTO> partialUpdateAuditType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AuditTypeDTO auditTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AuditType partially : {}, {}", id, auditTypeDTO);
        if (auditTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, auditTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!auditTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AuditTypeDTO> result = auditTypeService.partialUpdate(auditTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auditTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /audit-types} : get all the auditTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auditTypes in body.
     */
    @GetMapping("/audit-types")
    public ResponseEntity<List<AuditTypeDTO>> getAllAuditTypes(
        AuditTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AuditTypes by criteria: {}", criteria);
        Page<AuditTypeDTO> page = auditTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /audit-types/count} : count all the auditTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/audit-types/count")
    public ResponseEntity<Long> countAuditTypes(AuditTypeCriteria criteria) {
        log.debug("REST request to count AuditTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(auditTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /audit-types/:id} : get the "id" auditType.
     *
     * @param id the id of the auditTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auditTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/audit-types/{id}")
    public ResponseEntity<AuditTypeDTO> getAuditType(@PathVariable Long id) {
        log.debug("REST request to get AuditType : {}", id);
        Optional<AuditTypeDTO> auditTypeDTO = auditTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auditTypeDTO);
    }

    /**
     * {@code DELETE  /audit-types/:id} : delete the "id" auditType.
     *
     * @param id the id of the auditTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/audit-types/{id}")
    public ResponseEntity<Void> deleteAuditType(@PathVariable Long id) {
        log.debug("REST request to delete AuditType : {}", id);
        auditTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
