package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.BedTypeRepository;
import com.techvg.covid.care.service.BedTypeQueryService;
import com.techvg.covid.care.service.BedTypeService;
import com.techvg.covid.care.service.criteria.BedTypeCriteria;
import com.techvg.covid.care.service.dto.BedTypeDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.BedType}.
 */
@RestController
@RequestMapping("/api")
public class BedTypeResource {

    private final Logger log = LoggerFactory.getLogger(BedTypeResource.class);

    private static final String ENTITY_NAME = "bedType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BedTypeService bedTypeService;

    private final BedTypeRepository bedTypeRepository;

    private final BedTypeQueryService bedTypeQueryService;

    public BedTypeResource(BedTypeService bedTypeService, BedTypeRepository bedTypeRepository, BedTypeQueryService bedTypeQueryService) {
        this.bedTypeService = bedTypeService;
        this.bedTypeRepository = bedTypeRepository;
        this.bedTypeQueryService = bedTypeQueryService;
    }

    /**
     * {@code POST  /bed-types} : Create a new bedType.
     *
     * @param bedTypeDTO the bedTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bedTypeDTO, or with status {@code 400 (Bad Request)} if the bedType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bed-types")
    public ResponseEntity<BedTypeDTO> createBedType(@Valid @RequestBody BedTypeDTO bedTypeDTO) throws URISyntaxException {
        log.debug("REST request to save BedType : {}", bedTypeDTO);
        if (bedTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new bedType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BedTypeDTO result = bedTypeService.save(bedTypeDTO);
        return ResponseEntity
            .created(new URI("/api/bed-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bed-types/:id} : Updates an existing bedType.
     *
     * @param id the id of the bedTypeDTO to save.
     * @param bedTypeDTO the bedTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bedTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bedTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bed-types/{id}")
    public ResponseEntity<BedTypeDTO> updateBedType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BedTypeDTO bedTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BedType : {}, {}", id, bedTypeDTO);
        if (bedTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BedTypeDTO result = bedTypeService.save(bedTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bed-types/:id} : Partial updates given fields of an existing bedType, field will ignore if it is null
     *
     * @param id the id of the bedTypeDTO to save.
     * @param bedTypeDTO the bedTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bedTypeDTO,
     * or with status {@code 400 (Bad Request)} if the bedTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bedTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bedTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bed-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BedTypeDTO> partialUpdateBedType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BedTypeDTO bedTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BedType partially : {}, {}", id, bedTypeDTO);
        if (bedTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bedTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bedTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BedTypeDTO> result = bedTypeService.partialUpdate(bedTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bedTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bed-types} : get all the bedTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bedTypes in body.
     */
    @GetMapping("/bed-types")
    public ResponseEntity<List<BedTypeDTO>> getAllBedTypes(
        BedTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get BedTypes by criteria: {}", criteria);
        Page<BedTypeDTO> page = bedTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bed-types/count} : count all the bedTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bed-types/count")
    public ResponseEntity<Long> countBedTypes(BedTypeCriteria criteria) {
        log.debug("REST request to count BedTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(bedTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bed-types/:id} : get the "id" bedType.
     *
     * @param id the id of the bedTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bedTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bed-types/{id}")
    public ResponseEntity<BedTypeDTO> getBedType(@PathVariable Long id) {
        log.debug("REST request to get BedType : {}", id);
        Optional<BedTypeDTO> bedTypeDTO = bedTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bedTypeDTO);
    }

    /**
     * {@code DELETE  /bed-types/:id} : delete the "id" bedType.
     *
     * @param id the id of the bedTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bed-types/{id}")
    public ResponseEntity<Void> deleteBedType(@PathVariable Long id) {
        log.debug("REST request to delete BedType : {}", id);
        bedTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
