package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.BedTransactionsRepository;
import com.techvg.covid.care.service.BedTransactionsQueryService;
import com.techvg.covid.care.service.BedTransactionsService;
import com.techvg.covid.care.service.criteria.BedTransactionsCriteria;
import com.techvg.covid.care.service.dto.BedTransactionsDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.BedTransactions}.
 */
@RestController
@RequestMapping("/api")
public class BedTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(BedTransactionsResource.class);

    private static final String ENTITY_NAME = "bedTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedTransactionsService bedTransactionsService;

    private final BedTransactionsRepository bedTransactionsRepository;

    private final BedTransactionsQueryService bedTransactionsQueryService;

    public BedTransactionsResource(
        BedTransactionsService bedTransactionsService,
        BedTransactionsRepository bedTransactionsRepository,
        BedTransactionsQueryService bedTransactionsQueryService
    ) {
        this.bedTransactionsService = bedTransactionsService;
        this.bedTransactionsRepository = bedTransactionsRepository;
        this.bedTransactionsQueryService = bedTransactionsQueryService;
    }

    /**
     * {@code POST  /bed-transactions} : Create a new bedTransactions.
     *
     * @param bedTransactionsDTO the bedTransactionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedTransactionsDTO, or with status {@code 400 (Bad Request)} if the bedTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bed-transactions")
    public ResponseEntity<BedTransactionsDTO> createBedTransactions(@Valid @RequestBody BedTransactionsDTO bedTransactionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save BedTransactions : {}", bedTransactionsDTO);
        if (bedTransactionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new bedTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BedTransactionsDTO result = bedTransactionsService.save(bedTransactionsDTO);
        return ResponseEntity
            .created(new URI("/api/bed-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bed-transactions/:id} : Updates an existing bedTransactions.
     *
     * @param id the id of the bedTransactionsDTO to save.
     * @param bedTransactionsDTO the bedTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the bedTransactionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bed-transactions/{id}")
    public ResponseEntity<BedTransactionsDTO> updateBedTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BedTransactionsDTO bedTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BedTransactions : {}, {}", id, bedTransactionsDTO);
        if (bedTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BedTransactionsDTO result = bedTransactionsService.save(bedTransactionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedTransactionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bed-transactions/:id} : Partial updates given fields of an existing bedTransactions, field will ignore if it is null
     *
     * @param id the id of the bedTransactionsDTO to save.
     * @param bedTransactionsDTO the bedTransactionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTransactionsDTO,
     * or with status {@code 400 (Bad Request)} if the bedTransactionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bedTransactionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedTransactionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bed-transactions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BedTransactionsDTO> partialUpdateBedTransactions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BedTransactionsDTO bedTransactionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BedTransactions partially : {}, {}", id, bedTransactionsDTO);
        if (bedTransactionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedTransactionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedTransactionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BedTransactionsDTO> result = bedTransactionsService.partialUpdate(bedTransactionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedTransactionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bed-transactions} : get all the bedTransactions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedTransactions in body.
     */
    @GetMapping("/bed-transactions")
    public ResponseEntity<List<BedTransactionsDTO>> getAllBedTransactions(
        BedTransactionsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BedTransactions by criteria: {}", criteria);
        Page<BedTransactionsDTO> page = bedTransactionsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bed-transactions/count} : count all the bedTransactions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bed-transactions/count")
    public ResponseEntity<Long> countBedTransactions(BedTransactionsCriteria criteria) {
        log.debug("REST request to count BedTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(bedTransactionsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bed-transactions/:id} : get the "id" bedTransactions.
     *
     * @param id the id of the bedTransactionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedTransactionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bed-transactions/{id}")
    public ResponseEntity<BedTransactionsDTO> getBedTransactions(@PathVariable Long id) {
        log.debug("REST request to get BedTransactions : {}", id);
        Optional<BedTransactionsDTO> bedTransactionsDTO = bedTransactionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedTransactionsDTO);
    }

    /**
     * {@code DELETE  /bed-transactions/:id} : delete the "id" bedTransactions.
     *
     * @param id the id of the bedTransactionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bed-transactions/{id}")
    public ResponseEntity<Void> deleteBedTransactions(@PathVariable Long id) {
        log.debug("REST request to delete BedTransactions : {}", id);
        bedTransactionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
