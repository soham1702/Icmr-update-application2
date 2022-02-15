package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.InventoryUsedRepository;
import com.techvg.covid.care.service.InventoryUsedQueryService;
import com.techvg.covid.care.service.InventoryUsedService;
import com.techvg.covid.care.service.criteria.InventoryUsedCriteria;
import com.techvg.covid.care.service.dto.InventoryUsedDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.InventoryUsed}.
 */
@RestController
@RequestMapping("/api")
public class InventoryUsedResource {

    private final Logger log = LoggerFactory.getLogger(InventoryUsedResource.class);

    private static final String ENTITY_NAME = "inventoryUsed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryUsedService inventoryUsedService;

    private final InventoryUsedRepository inventoryUsedRepository;

    private final InventoryUsedQueryService inventoryUsedQueryService;

    public InventoryUsedResource(
        InventoryUsedService inventoryUsedService,
        InventoryUsedRepository inventoryUsedRepository,
        InventoryUsedQueryService inventoryUsedQueryService
    ) {
        this.inventoryUsedService = inventoryUsedService;
        this.inventoryUsedRepository = inventoryUsedRepository;
        this.inventoryUsedQueryService = inventoryUsedQueryService;
    }

    /**
     * {@code POST  /inventory-useds} : Create a new inventoryUsed.
     *
     * @param inventoryUsedDTO the inventoryUsedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryUsedDTO, or with status {@code 400 (Bad Request)} if the inventoryUsed has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-useds")
    public ResponseEntity<InventoryUsedDTO> createInventoryUsed(@Valid @RequestBody InventoryUsedDTO inventoryUsedDTO)
        throws URISyntaxException {
        log.debug("REST request to save InventoryUsed : {}", inventoryUsedDTO);
        if (inventoryUsedDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryUsed cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryUsedDTO result = inventoryUsedService.save(inventoryUsedDTO);
        return ResponseEntity
            .created(new URI("/api/inventory-useds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-useds/:id} : Updates an existing inventoryUsed.
     *
     * @param id the id of the inventoryUsedDTO to save.
     * @param inventoryUsedDTO the inventoryUsedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryUsedDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryUsedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryUsedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-useds/{id}")
    public ResponseEntity<InventoryUsedDTO> updateInventoryUsed(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryUsedDTO inventoryUsedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InventoryUsed : {}, {}", id, inventoryUsedDTO);
        if (inventoryUsedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryUsedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryUsedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InventoryUsedDTO result = inventoryUsedService.save(inventoryUsedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryUsedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inventory-useds/:id} : Partial updates given fields of an existing inventoryUsed, field will ignore if it is null
     *
     * @param id the id of the inventoryUsedDTO to save.
     * @param inventoryUsedDTO the inventoryUsedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryUsedDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryUsedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryUsedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryUsedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inventory-useds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryUsedDTO> partialUpdateInventoryUsed(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryUsedDTO inventoryUsedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InventoryUsed partially : {}, {}", id, inventoryUsedDTO);
        if (inventoryUsedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryUsedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryUsedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryUsedDTO> result = inventoryUsedService.partialUpdate(inventoryUsedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryUsedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-useds} : get all the inventoryUseds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryUseds in body.
     */
    @GetMapping("/inventory-useds")
    public ResponseEntity<List<InventoryUsedDTO>> getAllInventoryUseds(
        InventoryUsedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InventoryUseds by criteria: {}", criteria);
        Page<InventoryUsedDTO> page = inventoryUsedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-useds/count} : count all the inventoryUseds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-useds/count")
    public ResponseEntity<Long> countInventoryUseds(InventoryUsedCriteria criteria) {
        log.debug("REST request to count InventoryUseds by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryUsedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-useds/:id} : get the "id" inventoryUsed.
     *
     * @param id the id of the inventoryUsedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryUsedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-useds/{id}")
    public ResponseEntity<InventoryUsedDTO> getInventoryUsed(@PathVariable Long id) {
        log.debug("REST request to get InventoryUsed : {}", id);
        Optional<InventoryUsedDTO> inventoryUsedDTO = inventoryUsedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryUsedDTO);
    }

    /**
     * {@code DELETE  /inventory-useds/:id} : delete the "id" inventoryUsed.
     *
     * @param id the id of the inventoryUsedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-useds/{id}")
    public ResponseEntity<Void> deleteInventoryUsed(@PathVariable Long id) {
        log.debug("REST request to delete InventoryUsed : {}", id);
        inventoryUsedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
