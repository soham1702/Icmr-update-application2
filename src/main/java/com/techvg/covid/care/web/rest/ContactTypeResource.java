package com.techvg.covid.care.web.rest;

import com.techvg.covid.care.repository.ContactTypeRepository;
import com.techvg.covid.care.service.ContactTypeQueryService;
import com.techvg.covid.care.service.ContactTypeService;
import com.techvg.covid.care.service.criteria.ContactTypeCriteria;
import com.techvg.covid.care.service.dto.ContactTypeDTO;
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
 * REST controller for managing {@link com.techvg.covid.care.domain.ContactType}.
 */
@RestController
@RequestMapping("/api")
public class ContactTypeResource {

    private final Logger log = LoggerFactory.getLogger(ContactTypeResource.class);

    private static final String ENTITY_NAME = "contactType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactTypeService contactTypeService;

    private final ContactTypeRepository contactTypeRepository;

    private final ContactTypeQueryService contactTypeQueryService;

    public ContactTypeResource(
        ContactTypeService contactTypeService,
        ContactTypeRepository contactTypeRepository,
        ContactTypeQueryService contactTypeQueryService
    ) {
        this.contactTypeService = contactTypeService;
        this.contactTypeRepository = contactTypeRepository;
        this.contactTypeQueryService = contactTypeQueryService;
    }

    /**
     * {@code POST  /contact-types} : Create a new contactType.
     *
     * @param contactTypeDTO the contactTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactTypeDTO, or with status {@code 400 (Bad Request)} if the contactType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-types")
    public ResponseEntity<ContactTypeDTO> createContactType(@Valid @RequestBody ContactTypeDTO contactTypeDTO) throws URISyntaxException {
        log.debug("REST request to save ContactType : {}", contactTypeDTO);
        if (contactTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactTypeDTO result = contactTypeService.save(contactTypeDTO);
        return ResponseEntity
            .created(new URI("/api/contact-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-types/:id} : Updates an existing contactType.
     *
     * @param id the id of the contactTypeDTO to save.
     * @param contactTypeDTO the contactTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactTypeDTO,
     * or with status {@code 400 (Bad Request)} if the contactTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-types/{id}")
    public ResponseEntity<ContactTypeDTO> updateContactType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactTypeDTO contactTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactType : {}, {}", id, contactTypeDTO);
        if (contactTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactTypeDTO result = contactTypeService.save(contactTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-types/:id} : Partial updates given fields of an existing contactType, field will ignore if it is null
     *
     * @param id the id of the contactTypeDTO to save.
     * @param contactTypeDTO the contactTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactTypeDTO,
     * or with status {@code 400 (Bad Request)} if the contactTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactTypeDTO> partialUpdateContactType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactTypeDTO contactTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactType partially : {}, {}", id, contactTypeDTO);
        if (contactTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactTypeDTO> result = contactTypeService.partialUpdate(contactTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-types} : get all the contactTypes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactTypes in body.
     */
    @GetMapping("/contact-types")
    public ResponseEntity<List<ContactTypeDTO>> getAllContactTypes(
        ContactTypeCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ContactTypes by criteria: {}", criteria);
        Page<ContactTypeDTO> page = contactTypeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contact-types/count} : count all the contactTypes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/contact-types/count")
    public ResponseEntity<Long> countContactTypes(ContactTypeCriteria criteria) {
        log.debug("REST request to count ContactTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(contactTypeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /contact-types/:id} : get the "id" contactType.
     *
     * @param id the id of the contactTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-types/{id}")
    public ResponseEntity<ContactTypeDTO> getContactType(@PathVariable Long id) {
        log.debug("REST request to get ContactType : {}", id);
        Optional<ContactTypeDTO> contactTypeDTO = contactTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactTypeDTO);
    }

    /**
     * {@code DELETE  /contact-types/:id} : delete the "id" contactType.
     *
     * @param id the id of the contactTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-types/{id}")
    public ResponseEntity<Void> deleteContactType(@PathVariable Long id) {
        log.debug("REST request to delete ContactType : {}", id);
        contactTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
