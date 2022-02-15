package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.HospitalTypeRepository;
import com.techvg.covid.care.service.HospitalTypeQueryService;
import com.techvg.covid.care.service.HospitalTypeService;
import com.techvg.covid.care.service.criteria.HospitalTypeCriteria;
import com.techvg.covid.care.service.dto.HospitalTypeDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.HospitalType}.
 */
@RestController
@RequestMapping("/api")
public class HospitalTypeResource {

    private final Logger log = LoggerFactory.getLogger(HospitalTypeResource.class);

    private static final String ENTITY_NAME = "hospitalType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HospitalTypeService hospitalTypeService;

    private final HospitalTypeRepository hospitalTypeRepository;

    private final HospitalTypeQueryService hospitalTypeQueryService;

    public HospitalTypeResource(
        HospitalTypeService hospitalTypeService,
        HospitalTypeRepository hospitalTypeRepository,
        HospitalTypeQueryService hospitalTypeQueryService
    ) {
        this.hospitalTypeService = hospitalTypeService;
        this.hospitalTypeRepository = hospitalTypeRepository;
        this.hospitalTypeQueryService = hospitalTypeQueryService;
    }

    /**
     * {@code POST  /hospital-types} : Create a new hospitalType.
     *
     * @param hospitalTypeDTO the hospitalTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hospitalTypeDTO, or with status {@code 400 (Bad Request)} if the hospitalType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hospital-types")
    public ResponseEntity<HospitalTypeDTO> createHospitalType(@Valid @RequestBody HospitalTypeDTO hospitalTypeDTO)
        throws URISyntaxException {
        log.debug("REST request to save HospitalType : {}", hospitalTypeDTO);
        if (hospitalTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new hospitalType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HospitalTypeDTO result = hospitalTypeService.save(hospitalTypeDTO);
        return ResponseEntity
            .created(new URI("/api/hospital-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hospital-types/:id} : Updates an existing hospitalType.
     *
     * @param id the id of the hospitalTypeDTO to save.
     * @param hospitalTypeDTO the hospitalTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalTypeDTO,
     * or with status {@code 400 (Bad Request)} if the hospitalTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hospitalTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hospital-types/{id}")
    public ResponseEntity<HospitalTypeDTO> updateHospitalType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HospitalTypeDTO hospitalTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update HospitalType : {}, {}", id, hospitalTypeDTO);
        if (hospitalTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HospitalTypeDTO result = hospitalTypeService.save(hospitalTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospitalTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hospital-types/:id} : Partial updates given fields of an existing hospitalType, field will ignore if it is null
     *
     * @param id the id of the hospitalTypeDTO to save.
     * @param hospitalTypeDTO the hospitalTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hospitalTypeDTO,
     * or with status {@code 400 (Bad Request)} if the hospitalTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hospitalTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hospitalTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hospital-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HospitalTypeDTO> partialUpdateHospitalType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HospitalTypeDTO hospitalTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update HospitalType partially : {}, {}", id, hospitalTypeDTO);
        if (hospitalTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hospitalTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hospitalTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HospitalTypeDTO> result = hospitalTypeService.partialUpdate(hospitalTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hospitalTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hospital-types} : get all the hospitalTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hospitalTypes in body.
     */
    @GetMapping("/hospital-types")
    public ResponseEntity<List<HospitalTypeDTO>> getAllHospitalTypes(
        HospitalTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get HospitalTypes by criteria: {}", criteria);
        Page<HospitalTypeDTO> page = hospitalTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hospital-types/count} : count all the hospitalTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/hospital-types/count")
    public ResponseEntity<Long> countHospitalTypes(HospitalTypeCriteria criteria) {
        log.debug("REST request to count HospitalTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(hospitalTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /hospital-types/:id} : get the "id" hospitalType.
     *
     * @param id the id of the hospitalTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hospitalTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hospital-types/{id}")
    public ResponseEntity<HospitalTypeDTO> getHospitalType(@PathVariable Long id) {
        log.debug("REST request to get HospitalType : {}", id);
        Optional<HospitalTypeDTO> hospitalTypeDTO = hospitalTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hospitalTypeDTO);
    }

    /**
     * {@code DELETE  /hospital-types/:id} : delete the "id" hospitalType.
     *
     * @param id the id of the hospitalTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hospital-types/{id}")
    public ResponseEntity<Void> deleteHospitalType(@PathVariable Long id) {
        log.debug("REST request to delete HospitalType : {}", id);
        hospitalTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
