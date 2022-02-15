package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.TripDetailsRepository;
import com.techvg.covid.care.service.TripDetailsQueryService;
import com.techvg.covid.care.service.TripDetailsService;
import com.techvg.covid.care.service.criteria.TripDetailsCriteria;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.TripDetails}.
 */
@RestController
@RequestMapping("/api")
public class TripDetailsResource {

    private final Logger log = LoggerFactory.getLogger(TripDetailsResource.class);

    private static final String ENTITY_NAME = "tripDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TripDetailsService tripDetailsService;

    private final TripDetailsRepository tripDetailsRepository;

    private final TripDetailsQueryService tripDetailsQueryService;

    public TripDetailsResource(
        TripDetailsService tripDetailsService,
        TripDetailsRepository tripDetailsRepository,
        TripDetailsQueryService tripDetailsQueryService
    ) {
        this.tripDetailsService = tripDetailsService;
        this.tripDetailsRepository = tripDetailsRepository;
        this.tripDetailsQueryService = tripDetailsQueryService;
    }

    /**
     * {@code POST  /trip-details} : Create a new tripDetails.
     *
     * @param tripDetailsDTO the tripDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tripDetailsDTO, or with status {@code 400 (Bad Request)} if the tripDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trip-details")
    public ResponseEntity<TripDetailsDTO> createTripDetails(@Valid @RequestBody TripDetailsDTO tripDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save TripDetails : {}", tripDetailsDTO);
        if (tripDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tripDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TripDetailsDTO result = tripDetailsService.save(tripDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/trip-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trip-details/:id} : Updates an existing tripDetails.
     *
     * @param id the id of the tripDetailsDTO to save.
     * @param tripDetailsDTO the tripDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the tripDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tripDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trip-details/{id}")
    public ResponseEntity<TripDetailsDTO> updateTripDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TripDetailsDTO tripDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TripDetails : {}, {}", id, tripDetailsDTO);
        if (tripDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TripDetailsDTO result = tripDetailsService.save(tripDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tripDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trip-details/:id} : Partial updates given fields of an existing tripDetails, field will ignore if it is null
     *
     * @param id the id of the tripDetailsDTO to save.
     * @param tripDetailsDTO the tripDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tripDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the tripDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tripDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tripDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trip-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TripDetailsDTO> partialUpdateTripDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TripDetailsDTO tripDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TripDetails partially : {}, {}", id, tripDetailsDTO);
        if (tripDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tripDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tripDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TripDetailsDTO> result = tripDetailsService.partialUpdate(tripDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tripDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trip-details} : get all the tripDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tripDetails in body.
     */
    @GetMapping("/trip-details")
    public ResponseEntity<List<TripDetailsDTO>> getAllTripDetails(
        TripDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TripDetails by criteria: {}", criteria);
        Page<TripDetailsDTO> page = tripDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trip-details/count} : count all the tripDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trip-details/count")
    public ResponseEntity<Long> countTripDetails(TripDetailsCriteria criteria) {
        log.debug("REST request to count TripDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(tripDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trip-details/:id} : get the "id" tripDetails.
     *
     * @param id the id of the tripDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tripDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trip-details/{id}")
    public ResponseEntity<TripDetailsDTO> getTripDetails(@PathVariable Long id) {
        log.debug("REST request to get TripDetails : {}", id);
        Optional<TripDetailsDTO> tripDetailsDTO = tripDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tripDetailsDTO);
    }

    /**
     * {@code DELETE  /trip-details/:id} : delete the "id" tripDetails.
     *
     * @param id the id of the tripDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trip-details/{id}")
    public ResponseEntity<Void> deleteTripDetails(@PathVariable Long id) {
        log.debug("REST request to delete TripDetails : {}", id);
        tripDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
