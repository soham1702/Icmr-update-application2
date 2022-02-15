package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.repository.InventoryTypeRepository;
import com.techvg.covid.care.service.criteria.InventoryTypeCriteria;
import com.techvg.covid.care.service.dto.InventoryTypeDTO;
import com.techvg.covid.care.service.mapper.InventoryTypeMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InventoryTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inventory-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InventoryTypeRepository inventoryTypeRepository;

    @Autowired
    private InventoryTypeMapper inventoryTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryTypeMockMvc;

    private InventoryType inventoryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryType createEntity(EntityManager em) {
        InventoryType inventoryType = new InventoryType()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return inventoryType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryType createUpdatedEntity(EntityManager em) {
        InventoryType inventoryType = new InventoryType()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return inventoryType;
    }

    @BeforeEach
    public void initTest() {
        inventoryType = createEntity(em);
    }

    @Test
    @Transactional
    void createInventoryType() throws Exception {
        int databaseSizeBeforeCreate = inventoryTypeRepository.findAll().size();
        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);
        restInventoryTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryType testInventoryType = inventoryTypeList.get(inventoryTypeList.size() - 1);
        assertThat(testInventoryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryType.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testInventoryType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventoryType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createInventoryTypeWithExistingId() throws Exception {
        // Create the InventoryType with an existing ID
        inventoryType.setId(1L);
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        int databaseSizeBeforeCreate = inventoryTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryTypeRepository.findAll().size();
        // set the field null
        inventoryType.setName(null);

        // Create the InventoryType, which fails.
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        restInventoryTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryTypeRepository.findAll().size();
        // set the field null
        inventoryType.setLastModified(null);

        // Create the InventoryType, which fails.
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        restInventoryTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryTypeRepository.findAll().size();
        // set the field null
        inventoryType.setLastModifiedBy(null);

        // Create the InventoryType, which fails.
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        restInventoryTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventoryTypes() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getInventoryType() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get the inventoryType
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, inventoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getInventoryTypesByIdFiltering() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        Long id = inventoryType.getId();

        defaultInventoryTypeShouldBeFound("id.equals=" + id);
        defaultInventoryTypeShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name equals to DEFAULT_NAME
        defaultInventoryTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the inventoryTypeList where name equals to UPDATED_NAME
        defaultInventoryTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name not equals to DEFAULT_NAME
        defaultInventoryTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the inventoryTypeList where name not equals to UPDATED_NAME
        defaultInventoryTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInventoryTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the inventoryTypeList where name equals to UPDATED_NAME
        defaultInventoryTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name is not null
        defaultInventoryTypeShouldBeFound("name.specified=true");

        // Get all the inventoryTypeList where name is null
        defaultInventoryTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name contains DEFAULT_NAME
        defaultInventoryTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the inventoryTypeList where name contains UPDATED_NAME
        defaultInventoryTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where name does not contain DEFAULT_NAME
        defaultInventoryTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the inventoryTypeList where name does not contain UPDATED_NAME
        defaultInventoryTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where deleted equals to DEFAULT_DELETED
        defaultInventoryTypeShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the inventoryTypeList where deleted equals to UPDATED_DELETED
        defaultInventoryTypeShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where deleted not equals to DEFAULT_DELETED
        defaultInventoryTypeShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the inventoryTypeList where deleted not equals to UPDATED_DELETED
        defaultInventoryTypeShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultInventoryTypeShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the inventoryTypeList where deleted equals to UPDATED_DELETED
        defaultInventoryTypeShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where deleted is not null
        defaultInventoryTypeShouldBeFound("deleted.specified=true");

        // Get all the inventoryTypeList where deleted is null
        defaultInventoryTypeShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultInventoryTypeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryTypeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultInventoryTypeShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryTypeList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultInventoryTypeShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultInventoryTypeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the inventoryTypeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryTypeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModified is not null
        defaultInventoryTypeShouldBeFound("lastModified.specified=true");

        // Get all the inventoryTypeList where lastModified is null
        defaultInventoryTypeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryTypeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryTypeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the inventoryTypeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy is not null
        defaultInventoryTypeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the inventoryTypeList where lastModifiedBy is null
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultInventoryTypeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryTypeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryTypesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        // Get all the inventoryTypeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultInventoryTypeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryTypeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultInventoryTypeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryTypeShouldBeFound(String filter) throws Exception {
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryTypeShouldNotBeFound(String filter) throws Exception {
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventoryType() throws Exception {
        // Get the inventoryType
        restInventoryTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInventoryType() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();

        // Update the inventoryType
        InventoryType updatedInventoryType = inventoryTypeRepository.findById(inventoryType.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryType are not directly saved in db
        em.detach(updatedInventoryType);
        updatedInventoryType
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(updatedInventoryType);

        restInventoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
        InventoryType testInventoryType = inventoryTypeList.get(inventoryTypeList.size() - 1);
        assertThat(testInventoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryType.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testInventoryType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventoryType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryTypeWithPatch() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();

        // Update the inventoryType using partial update
        InventoryType partialUpdatedInventoryType = new InventoryType();
        partialUpdatedInventoryType.setId(inventoryType.getId());

        partialUpdatedInventoryType.name(UPDATED_NAME).deleted(UPDATED_DELETED);

        restInventoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventoryType))
            )
            .andExpect(status().isOk());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
        InventoryType testInventoryType = inventoryTypeList.get(inventoryTypeList.size() - 1);
        assertThat(testInventoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryType.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testInventoryType.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventoryType.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateInventoryTypeWithPatch() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();

        // Update the inventoryType using partial update
        InventoryType partialUpdatedInventoryType = new InventoryType();
        partialUpdatedInventoryType.setId(inventoryType.getId());

        partialUpdatedInventoryType
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInventoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventoryType))
            )
            .andExpect(status().isOk());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
        InventoryType testInventoryType = inventoryTypeList.get(inventoryTypeList.size() - 1);
        assertThat(testInventoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryType.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testInventoryType.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventoryType.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventoryType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTypeRepository.findAll().size();
        inventoryType.setId(count.incrementAndGet());

        // Create the InventoryType
        InventoryTypeDTO inventoryTypeDTO = inventoryTypeMapper.toDto(inventoryType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryTypeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryType in the database
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventoryType() throws Exception {
        // Initialize the database
        inventoryTypeRepository.saveAndFlush(inventoryType);

        int databaseSizeBeforeDelete = inventoryTypeRepository.findAll().size();

        // Delete the inventoryType
        restInventoryTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventoryType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryType> inventoryTypeList = inventoryTypeRepository.findAll();
        assertThat(inventoryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
