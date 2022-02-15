package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.ICMRDailyCountRepository;
import com.techvg.covid.care.service.ICMRDailyCountQueryService;
import com.techvg.covid.care.service.ICMRDailyCountService;
import com.techvg.covid.care.service.criteria.ICMRDailyCountCriteria;
import com.techvg.covid.care.service.dto.ICMRDailyCountDTO;
import com.techvg.covid.care.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.ICMRDailyCount}.
 */
@RestController
@RequestMapping("/api")
public class ICMRDailyCountResource {

    private final Logger log = LoggerFactory.getLogger(ICMRDailyCountResource.class);

    private static final String ENTITY_NAME = "iCMRDailyCount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ICMRDailyCountService iCMRDailyCountService;

    private final ICMRDailyCountRepository iCMRDailyCountRepository;

    private final ICMRDailyCountQueryService iCMRDailyCountQueryService;

    public ICMRDailyCountResource(
        ICMRDailyCountService iCMRDailyCountService,
        ICMRDailyCountRepository iCMRDailyCountRepository,
        ICMRDailyCountQueryService iCMRDailyCountQueryService
    ) {
        this.iCMRDailyCountService = iCMRDailyCountService;
        this.iCMRDailyCountRepository = iCMRDailyCountRepository;
        this.iCMRDailyCountQueryService = iCMRDailyCountQueryService;
    }

    /**
     * {@code POST  /icmr-daily-counts} : Create a new iCMRDailyCount.
     *
     * @param iCMRDailyCountDTO the iCMRDailyCountDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new iCMRDailyCountDTO, or with status {@code 400 (Bad Request)} if the iCMRDailyCount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/icmr-daily-counts")
    public ResponseEntity<ICMRDailyCountDTO> createICMRDailyCount(@RequestBody ICMRDailyCountDTO iCMRDailyCountDTO)
        throws URISyntaxException {
        log.debug("REST request to save ICMRDailyCount : {}", iCMRDailyCountDTO);
        if (iCMRDailyCountDTO.getId() != null) {
            throw new BadRequestAlertException("A new iCMRDailyCount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ICMRDailyCountDTO result = iCMRDailyCountService.save(iCMRDailyCountDTO);
        return ResponseEntity
            .created(new URI("/api/icmr-daily-counts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /icmr-daily-counts/:id} : Updates an existing iCMRDailyCount.
     *
     * @param id the id of the iCMRDailyCountDTO to save.
     * @param iCMRDailyCountDTO the iCMRDailyCountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated iCMRDailyCountDTO,
     * or with status {@code 400 (Bad Request)} if the iCMRDailyCountDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the iCMRDailyCountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/icmr-daily-counts/{id}")
    public ResponseEntity<ICMRDailyCountDTO> updateICMRDailyCount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ICMRDailyCountDTO iCMRDailyCountDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ICMRDailyCount : {}, {}", id, iCMRDailyCountDTO);
        if (iCMRDailyCountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, iCMRDailyCountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!iCMRDailyCountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ICMRDailyCountDTO result = iCMRDailyCountService.save(iCMRDailyCountDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, iCMRDailyCountDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /icmr-daily-counts/:id} : Partial updates given fields of an existing iCMRDailyCount, field will ignore if it is null
     *
     * @param id the id of the iCMRDailyCountDTO to save.
     * @param iCMRDailyCountDTO the iCMRDailyCountDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated iCMRDailyCountDTO,
     * or with status {@code 400 (Bad Request)} if the iCMRDailyCountDTO is not valid,
     * or with status {@code 404 (Not Found)} if the iCMRDailyCountDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the iCMRDailyCountDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/icmr-daily-counts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ICMRDailyCountDTO> partialUpdateICMRDailyCount(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ICMRDailyCountDTO iCMRDailyCountDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ICMRDailyCount partially : {}, {}", id, iCMRDailyCountDTO);
        if (iCMRDailyCountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, iCMRDailyCountDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!iCMRDailyCountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ICMRDailyCountDTO> result = iCMRDailyCountService.partialUpdate(iCMRDailyCountDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, iCMRDailyCountDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /icmr-daily-counts} : get all the iCMRDailyCounts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of iCMRDailyCounts in body.
     */
    @GetMapping("/icmr-daily-counts")
    public ResponseEntity<List<ICMRDailyCountDTO>> getAllICMRDailyCounts(
        ICMRDailyCountCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ICMRDailyCounts by criteria: {}", criteria);
        Page<ICMRDailyCountDTO> page = iCMRDailyCountQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /icmr-daily-counts/count} : count all the iCMRDailyCounts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/icmr-daily-counts/count")
    public ResponseEntity<Long> countICMRDailyCounts(ICMRDailyCountCriteria criteria) {
        log.debug("REST request to count ICMRDailyCounts by criteria: {}", criteria);
        return ResponseEntity.ok().body(iCMRDailyCountQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /icmr-daily-counts/:id} : get the "id" iCMRDailyCount.
     *
     * @param id the id of the iCMRDailyCountDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the iCMRDailyCountDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/icmr-daily-counts/{id}")
    public ResponseEntity<ICMRDailyCountDTO> getICMRDailyCount(@PathVariable Long id) {
        log.debug("REST request to get ICMRDailyCount : {}", id);
        Optional<ICMRDailyCountDTO> iCMRDailyCountDTO = iCMRDailyCountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(iCMRDailyCountDTO);
    }

    /**
     * {@code DELETE  /icmr-daily-counts/:id} : delete the "id" iCMRDailyCount.
     *
     * @param id the id of the iCMRDailyCountDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/icmr-daily-counts/{id}")
    public ResponseEntity<Void> deleteICMRDailyCount(@PathVariable Long id) {
        log.debug("REST request to delete ICMRDailyCount : {}", id);
        iCMRDailyCountService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
