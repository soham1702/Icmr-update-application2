package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.MunicipalCorpRepository;
import com.techvg.covid.care.service.MunicipalCorpQueryService;
import com.techvg.covid.care.service.MunicipalCorpService;
import com.techvg.covid.care.service.criteria.MunicipalCorpCriteria;
import com.techvg.covid.care.service.dto.MunicipalCorpDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.MunicipalCorp}.
 */
@RestController
@RequestMapping("/api")
public class MunicipalCorpResource {

    private final Logger log = LoggerFactory.getLogger(MunicipalCorpResource.class);

    private static final String ENTITY_NAME = "municipalCorp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MunicipalCorpService municipalCorpService;

    private final MunicipalCorpRepository municipalCorpRepository;

    private final MunicipalCorpQueryService municipalCorpQueryService;

    public MunicipalCorpResource(
        MunicipalCorpService municipalCorpService,
        MunicipalCorpRepository municipalCorpRepository,
        MunicipalCorpQueryService municipalCorpQueryService
    ) {
        this.municipalCorpService = municipalCorpService;
        this.municipalCorpRepository = municipalCorpRepository;
        this.municipalCorpQueryService = municipalCorpQueryService;
    }

    /**
     * {@code POST  /municipal-corps} : Create a new municipalCorp.
     *
     * @param municipalCorpDTO the municipalCorpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new municipalCorpDTO, or with status {@code 400 (Bad Request)} if the municipalCorp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/municipal-corps")
    public ResponseEntity<MunicipalCorpDTO> createMunicipalCorp(@Valid @RequestBody MunicipalCorpDTO municipalCorpDTO)
        throws URISyntaxException {
        log.debug("REST request to save MunicipalCorp : {}", municipalCorpDTO);
        if (municipalCorpDTO.getId() != null) {
            throw new BadRequestAlertException("A new municipalCorp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MunicipalCorpDTO result = municipalCorpService.save(municipalCorpDTO);
        return ResponseEntity
            .created(new URI("/api/municipal-corps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /municipal-corps/:id} : Updates an existing municipalCorp.
     *
     * @param id the id of the municipalCorpDTO to save.
     * @param municipalCorpDTO the municipalCorpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated municipalCorpDTO,
     * or with status {@code 400 (Bad Request)} if the municipalCorpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the municipalCorpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/municipal-corps/{id}")
    public ResponseEntity<MunicipalCorpDTO> updateMunicipalCorp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MunicipalCorpDTO municipalCorpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MunicipalCorp : {}, {}", id, municipalCorpDTO);
        if (municipalCorpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, municipalCorpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!municipalCorpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MunicipalCorpDTO result = municipalCorpService.save(municipalCorpDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, municipalCorpDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /municipal-corps/:id} : Partial updates given fields of an existing municipalCorp, field will ignore if it is null
     *
     * @param id the id of the municipalCorpDTO to save.
     * @param municipalCorpDTO the municipalCorpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated municipalCorpDTO,
     * or with status {@code 400 (Bad Request)} if the municipalCorpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the municipalCorpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the municipalCorpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/municipal-corps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MunicipalCorpDTO> partialUpdateMunicipalCorp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MunicipalCorpDTO municipalCorpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MunicipalCorp partially : {}, {}", id, municipalCorpDTO);
        if (municipalCorpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, municipalCorpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!municipalCorpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MunicipalCorpDTO> result = municipalCorpService.partialUpdate(municipalCorpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, municipalCorpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /municipal-corps} : get all the municipalCorps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of municipalCorps in body.
     */
    @GetMapping("/municipal-corps")
    public ResponseEntity<List<MunicipalCorpDTO>> getAllMunicipalCorps(
        MunicipalCorpCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MunicipalCorps by criteria: {}", criteria);
        Page<MunicipalCorpDTO> page = municipalCorpQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /municipal-corps/count} : count all the municipalCorps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/municipal-corps/count")
    public ResponseEntity<Long> countMunicipalCorps(MunicipalCorpCriteria criteria) {
        log.debug("REST request to count MunicipalCorps by criteria: {}", criteria);
        return ResponseEntity.ok().body(municipalCorpQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /municipal-corps/:id} : get the "id" municipalCorp.
     *
     * @param id the id of the municipalCorpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the municipalCorpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/municipal-corps/{id}")
    public ResponseEntity<MunicipalCorpDTO> getMunicipalCorp(@PathVariable Long id) {
        log.debug("REST request to get MunicipalCorp : {}", id);
        Optional<MunicipalCorpDTO> municipalCorpDTO = municipalCorpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(municipalCorpDTO);
    }

    /**
     * {@code DELETE  /municipal-corps/:id} : delete the "id" municipalCorp.
     *
     * @param id the id of the municipalCorpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/municipal-corps/{id}")
    public ResponseEntity<Void> deleteMunicipalCorp(@PathVariable Long id) {
        log.debug("REST request to delete MunicipalCorp : {}", id);
        municipalCorpService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
