package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.BedInventoryRepository;
import com.techvg.covid.care.service.BedInventoryQueryService;
import com.techvg.covid.care.service.BedInventoryService;
import com.techvg.covid.care.service.criteria.BedInventoryCriteria;
import com.techvg.covid.care.service.dto.BedInventoryDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.BedInventory}.
 */
@RestController
@RequestMapping("/api")
public class BedInventoryResource {

    private final Logger log = LoggerFactory.getLogger(BedInventoryResource.class);

    private static final String ENTITY_NAME = "bedInventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedInventoryService bedInventoryService;

    private final BedInventoryRepository bedInventoryRepository;

    private final BedInventoryQueryService bedInventoryQueryService;

    public BedInventoryResource(
        BedInventoryService bedInventoryService,
        BedInventoryRepository bedInventoryRepository,
        BedInventoryQueryService bedInventoryQueryService
    ) {
        this.bedInventoryService = bedInventoryService;
        this.bedInventoryRepository = bedInventoryRepository;
        this.bedInventoryQueryService = bedInventoryQueryService;
    }

    /**
     * {@code POST  /bed-inventories} : Create a new bedInventory.
     *
     * @param bedInventoryDTO the bedInventoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedInventoryDTO, or with status {@code 400 (Bad Request)} if the bedInventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bed-inventories")
    public ResponseEntity<BedInventoryDTO> createBedInventory(@Valid @RequestBody BedInventoryDTO bedInventoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save BedInventory : {}", bedInventoryDTO);
        if (bedInventoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new bedInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BedInventoryDTO result = bedInventoryService.save(bedInventoryDTO);
        return ResponseEntity
            .created(new URI("/api/bed-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bed-inventories/:id} : Updates an existing bedInventory.
     *
     * @param id the id of the bedInventoryDTO to save.
     * @param bedInventoryDTO the bedInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the bedInventoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bed-inventories/{id}")
    public ResponseEntity<BedInventoryDTO> updateBedInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BedInventoryDTO bedInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BedInventory : {}, {}", id, bedInventoryDTO);
        if (bedInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BedInventoryDTO result = bedInventoryService.save(bedInventoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedInventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bed-inventories/:id} : Partial updates given fields of an existing bedInventory, field will ignore if it is null
     *
     * @param id the id of the bedInventoryDTO to save.
     * @param bedInventoryDTO the bedInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the bedInventoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bedInventoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bed-inventories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BedInventoryDTO> partialUpdateBedInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BedInventoryDTO bedInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BedInventory partially : {}, {}", id, bedInventoryDTO);
        if (bedInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BedInventoryDTO> result = bedInventoryService.partialUpdate(bedInventoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedInventoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bed-inventories} : get all the bedInventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedInventories in body.
     */
    @GetMapping("/bed-inventories")
    public ResponseEntity<List<BedInventoryDTO>> getAllBedInventories(
        BedInventoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BedInventories by criteria: {}", criteria);
        Page<BedInventoryDTO> page = bedInventoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bed-inventories/count} : count all the bedInventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bed-inventories/count")
    public ResponseEntity<Long> countBedInventories(BedInventoryCriteria criteria) {
        log.debug("REST request to count BedInventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(bedInventoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bed-inventories/:id} : get the "id" bedInventory.
     *
     * @param id the id of the bedInventoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedInventoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bed-inventories/{id}")
    public ResponseEntity<BedInventoryDTO> getBedInventory(@PathVariable Long id) {
        log.debug("REST request to get BedInventory : {}", id);
        Optional<BedInventoryDTO> bedInventoryDTO = bedInventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedInventoryDTO);
    }

    /**
     * {@code DELETE  /bed-inventories/:id} : delete the "id" bedInventory.
     *
     * @param id the id of the bedInventoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bed-inventories/{id}")
    public ResponseEntity<Void> deleteBedInventory(@PathVariable Long id) {
        log.debug("REST request to delete BedInventory : {}", id);
        bedInventoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
