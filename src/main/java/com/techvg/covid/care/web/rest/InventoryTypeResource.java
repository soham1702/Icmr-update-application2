package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.InventoryTypeRepository;
import com.techvg.covid.care.service.InventoryTypeQueryService;
import com.techvg.covid.care.service.InventoryTypeService;
import com.techvg.covid.care.service.criteria.InventoryTypeCriteria;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.InventoryType}.
 */
@RestController
@RequestMapping("/api")
public class InventoryTypeResource {

    private final Logger log = LoggerFactory.getLogger(InventoryTypeResource.class);

    private static final String ENTITY_NAME = "inventoryType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventoryTypeService inventoryTypeService;

    private final InventoryTypeRepository inventoryTypeRepository;

    private final InventoryTypeQueryService inventoryTypeQueryService;

    public InventoryTypeResource(
        InventoryTypeService inventoryTypeService,
        InventoryTypeRepository inventoryTypeRepository,
        InventoryTypeQueryService inventoryTypeQueryService
    ) {
        this.inventoryTypeService = inventoryTypeService;
        this.inventoryTypeRepository = inventoryTypeRepository;
        this.inventoryTypeQueryService = inventoryTypeQueryService;
    }

    /**
     * {@code POST  /inventory-types} : Create a new inventoryType.
     *
     * @param inventoryTypeDTO the inventoryTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventoryTypeDTO, or with status {@code 400 (Bad Request)} if the inventoryType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inventory-types")
    public ResponseEntity<InventoryTypeDTO> createInventoryType(@Valid @RequestBody InventoryTypeDTO inventoryTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save InventoryType : {}", inventoryTypeDTO);
        if (inventoryTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventoryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InventoryTypeDTO result = inventoryTypeService.save(inventoryTypeDTO);
        return ResponseEntity
            .created(new URI("/api/inventory-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inventory-types/:id} : Updates an existing inventoryType.
     *
     * @param id the id of the inventoryTypeDTO to save.
     * @param inventoryTypeDTO the inventoryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventoryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inventory-types/{id}")
    public ResponseEntity<InventoryTypeDTO> updateInventoryType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InventoryTypeDTO inventoryTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update InventoryType : {}, {}", id, inventoryTypeDTO);
        if (inventoryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InventoryTypeDTO result = inventoryTypeService.save(inventoryTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inventory-types/:id} : Partial updates given fields of an existing inventoryType, field will ignore if it is null
     *
     * @param id the id of the inventoryTypeDTO to save.
     * @param inventoryTypeDTO the inventoryTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventoryTypeDTO,
     * or with status {@code 400 (Bad Request)} if the inventoryTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventoryTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventoryTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inventory-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventoryTypeDTO> partialUpdateInventoryType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InventoryTypeDTO inventoryTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update InventoryType partially : {}, {}", id, inventoryTypeDTO);
        if (inventoryTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventoryTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventoryTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventoryTypeDTO> result = inventoryTypeService.partialUpdate(inventoryTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventoryTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventory-types} : get all the inventoryTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventoryTypes in body.
     */
    @GetMapping("/inventory-types")
    public ResponseEntity<List<InventoryTypeDTO>> getAllInventoryTypes(
        InventoryTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get InventoryTypes by criteria: {}", criteria);
        Page<InventoryTypeDTO> page = inventoryTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventory-types/count} : count all the inventoryTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/inventory-types/count")
    public ResponseEntity<Long> countInventoryTypes(InventoryTypeCriteria criteria) {
        log.debug("REST request to count InventoryTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventoryTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventory-types/:id} : get the "id" inventoryType.
     *
     * @param id the id of the inventoryTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventoryTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inventory-types/{id}")
    public ResponseEntity<InventoryTypeDTO> getInventoryType(@PathVariable Long id) {
        log.debug("REST request to get InventoryType : {}", id);
        Optional<InventoryTypeDTO> inventoryTypeDTO = inventoryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventoryTypeDTO);
    }

    /**
     * {@code DELETE  /inventory-types/:id} : delete the "id" inventoryType.
     *
     * @param id the id of the inventoryTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inventory-types/{id}")
    public ResponseEntity<Void> deleteInventoryType(@PathVariable Long id) {
        log.debug("REST request to delete InventoryType : {}", id);
        inventoryTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
