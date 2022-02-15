package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.InventoryMasterRepository;
import com.techvg.covid.care.service.InventoryMasterQueryService;
import com.techvg.covid.care.service.InventoryMasterService;
import com.techvg.covid.care.service.criteria.InventoryMasterCriteria;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.InventoryMaster}.
 */
@RestController
@RequestMapping("/api")
public class InventoryMasterResource {

    private final Logger log = LoggerFactory.getLogger(InventoryMasterResource.class);

    private static final String ENTITY_NAME = "inventoryMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryMasterService inventoryMasterService;

    private final InventoryMasterRepository inventoryMasterRepository;

    private final InventoryMasterQueryService inventoryMasterQueryService;

    public InventoryMasterResource(
        InventoryMasterService inventoryMasterService,
        InventoryMasterRepository inventoryMasterRepository,
        InventoryMasterQueryService inventoryMasterQueryService
    ) {
        this.inventoryMasterService = inventoryMasterService;
        this.inventoryMasterRepository = inventoryMasterRepository;
        this.inventoryMasterQueryService = inventoryMasterQueryService;
    }

    /**
     * {@code POST  /inventory-masters} : Create a new inventoryMaster.
     *
     * @param inventoryMasterDTO the inventoryMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryMasterDTO, or with status {@code 400 (Bad Request)} if the inventoryMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-masters")
    public ResponseEntity<InventoryMasterDTO> createInventoryMaster(@Valid @RequestBody InventoryMasterDTO inventoryMasterDTO)
        throws URISyntaxException {
        log.debug("REST request to save InventoryMaster : {}", inventoryMasterDTO);
        if (inventoryMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryMasterDTO result = inventoryMasterService.save(inventoryMasterDTO);
        return ResponseEntity
            .created(new URI("/api/inventory-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-masters/:id} : Updates an existing inventoryMaster.
     *
     * @param id the id of the inventoryMasterDTO to save.
     * @param inventoryMasterDTO the inventoryMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryMasterDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-masters/{id}")
    public ResponseEntity<InventoryMasterDTO> updateInventoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryMasterDTO inventoryMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InventoryMaster : {}, {}", id, inventoryMasterDTO);
        if (inventoryMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InventoryMasterDTO result = inventoryMasterService.save(inventoryMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inventory-masters/:id} : Partial updates given fields of an existing inventoryMaster, field will ignore if it is null
     *
     * @param id the id of the inventoryMasterDTO to save.
     * @param inventoryMasterDTO the inventoryMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryMasterDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inventory-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryMasterDTO> partialUpdateInventoryMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryMasterDTO inventoryMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InventoryMaster partially : {}, {}", id, inventoryMasterDTO);
        if (inventoryMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryMasterDTO> result = inventoryMasterService.partialUpdate(inventoryMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-masters} : get all the inventoryMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryMasters in body.
     */
    @GetMapping("/inventory-masters")
    public ResponseEntity<List<InventoryMasterDTO>> getAllInventoryMasters(
        InventoryMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InventoryMasters by criteria: {}", criteria);
        Page<InventoryMasterDTO> page = inventoryMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-masters/count} : count all the inventoryMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-masters/count")
    public ResponseEntity<Long> countInventoryMasters(InventoryMasterCriteria criteria) {
        log.debug("REST request to count InventoryMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-masters/:id} : get the "id" inventoryMaster.
     *
     * @param id the id of the inventoryMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-masters/{id}")
    public ResponseEntity<InventoryMasterDTO> getInventoryMaster(@PathVariable Long id) {
        log.debug("REST request to get InventoryMaster : {}", id);
        Optional<InventoryMasterDTO> inventoryMasterDTO = inventoryMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryMasterDTO);
    }

    /**
     * {@code DELETE  /inventory-masters/:id} : delete the "id" inventoryMaster.
     *
     * @param id the id of the inventoryMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-masters/{id}")
    public ResponseEntity<Void> deleteInventoryMaster(@PathVariable Long id) {
        log.debug("REST request to delete InventoryMaster : {}", id);
        inventoryMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
