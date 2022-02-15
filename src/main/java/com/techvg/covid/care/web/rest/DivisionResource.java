package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.DivisionRepository;
import com.techvg.covid.care.service.DivisionQueryService;
import com.techvg.covid.care.service.DivisionService;
import com.techvg.covid.care.service.criteria.DivisionCriteria;
import com.techvg.covid.care.service.dto.DivisionDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.Division}.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    private static final String ENTITY_NAME = "division";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DivisionService divisionService;

    private final DivisionRepository divisionRepository;

    private final DivisionQueryService divisionQueryService;

    public DivisionResource(
        DivisionService divisionService,
        DivisionRepository divisionRepository,
        DivisionQueryService divisionQueryService
    ) {
        this.divisionService = divisionService;
        this.divisionRepository = divisionRepository;
        this.divisionQueryService = divisionQueryService;
    }

    /**
     * {@code POST  /divisions} : Create a new division.
     *
     * @param divisionDTO the divisionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new divisionDTO, or with status {@code 400 (Bad Request)} if the division has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/divisions")
    public ResponseEntity<DivisionDTO> createDivision(@Valid @RequestBody DivisionDTO divisionDTO) throws URISyntaxException {
        log.debug("REST request to save Division : {}", divisionDTO);
        if (divisionDTO.getId() != null) {
            throw new BadRequestAlertException("A new division cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DivisionDTO result = divisionService.save(divisionDTO);
        return ResponseEntity
            .created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /divisions/:id} : Updates an existing division.
     *
     * @param id the id of the divisionDTO to save.
     * @param divisionDTO the divisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisionDTO,
     * or with status {@code 400 (Bad Request)} if the divisionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the divisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/divisions/{id}")
    public ResponseEntity<DivisionDTO> updateDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DivisionDTO divisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Division : {}, {}", id, divisionDTO);
        if (divisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DivisionDTO result = divisionService.save(divisionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /divisions/:id} : Partial updates given fields of an existing division, field will ignore if it is null
     *
     * @param id the id of the divisionDTO to save.
     * @param divisionDTO the divisionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated divisionDTO,
     * or with status {@code 400 (Bad Request)} if the divisionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the divisionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the divisionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/divisions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DivisionDTO> partialUpdateDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DivisionDTO divisionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Division partially : {}, {}", id, divisionDTO);
        if (divisionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, divisionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!divisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DivisionDTO> result = divisionService.partialUpdate(divisionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, divisionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /divisions} : get all the divisions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of divisions in body.
     */
    @GetMapping("/divisions")
    public ResponseEntity<List<DivisionDTO>> getAllDivisions(
        DivisionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Divisions by criteria: {}", criteria);
        Page<DivisionDTO> page = divisionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /divisions/count} : count all the divisions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/divisions/count")
    public ResponseEntity<Long> countDivisions(DivisionCriteria criteria) {
        log.debug("REST request to count Divisions by criteria: {}", criteria);
        return ResponseEntity.ok().body(divisionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /divisions/:id} : get the "id" division.
     *
     * @param id the id of the divisionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the divisionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/divisions/{id}")
    public ResponseEntity<DivisionDTO> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        Optional<DivisionDTO> divisionDTO = divisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(divisionDTO);
    }

    /**
     * {@code DELETE  /divisions/:id} : delete the "id" division.
     *
     * @param id the id of the divisionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/divisions/{id}")
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
