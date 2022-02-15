package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.InventoryMaster;
import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.repository.InventoryMasterRepository;
import com.techvg.covid.care.service.criteria.InventoryMasterCriteria;
import com.techvg.covid.care.service.dto.InventoryMasterDTO;
import com.techvg.covid.care.service.mapper.InventoryMasterMapper;
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
 * Integration tests for the {@link InventoryMasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InventoryMasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_VOLUME = 1D;
    private static final Double UPDATED_VOLUME = 2D;
    private static final Double SMALLER_VOLUME = 1D - 1D;

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_CALULATE_VOLUME = 1D;
    private static final Double UPDATED_CALULATE_VOLUME = 2D;
    private static final Double SMALLER_CALULATE_VOLUME = 1D - 1D;

    private static final String DEFAULT_DIMENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_TYPE_IND = "AAAAAAAAAA";
    private static final String UPDATED_SUB_TYPE_IND = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inventory-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InventoryMasterRepository inventoryMasterRepository;

    @Autowired
    private InventoryMasterMapper inventoryMasterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMasterMockMvc;

    private InventoryMaster inventoryMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryMaster createEntity(EntityManager em) {
        InventoryMaster inventoryMaster = new InventoryMaster()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .volume(DEFAULT_VOLUME)
            .unit(DEFAULT_UNIT)
            .calulateVolume(DEFAULT_CALULATE_VOLUME)
            .dimensions(DEFAULT_DIMENSIONS)
            .subTypeInd(DEFAULT_SUB_TYPE_IND)
            .deleted(DEFAULT_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return inventoryMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryMaster createUpdatedEntity(EntityManager em) {
        InventoryMaster inventoryMaster = new InventoryMaster()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .volume(UPDATED_VOLUME)
            .unit(UPDATED_UNIT)
            .calulateVolume(UPDATED_CALULATE_VOLUME)
            .dimensions(UPDATED_DIMENSIONS)
            .subTypeInd(UPDATED_SUB_TYPE_IND)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return inventoryMaster;
    }

    @BeforeEach
    public void initTest() {
        inventoryMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createInventoryMaster() throws Exception {
        int databaseSizeBeforeCreate = inventoryMasterRepository.findAll().size();
        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);
        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryMaster testInventoryMaster = inventoryMasterList.get(inventoryMasterList.size() - 1);
        assertThat(testInventoryMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInventoryMaster.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testInventoryMaster.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testInventoryMaster.getCalulateVolume()).isEqualTo(DEFAULT_CALULATE_VOLUME);
        assertThat(testInventoryMaster.getDimensions()).isEqualTo(DEFAULT_DIMENSIONS);
        assertThat(testInventoryMaster.getSubTypeInd()).isEqualTo(DEFAULT_SUB_TYPE_IND);
        assertThat(testInventoryMaster.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testInventoryMaster.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventoryMaster.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createInventoryMasterWithExistingId() throws Exception {
        // Create the InventoryMaster with an existing ID
        inventoryMaster.setId(1L);
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        int databaseSizeBeforeCreate = inventoryMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryMasterRepository.findAll().size();
        // set the field null
        inventoryMaster.setName(null);

        // Create the InventoryMaster, which fails.
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryMasterRepository.findAll().size();
        // set the field null
        inventoryMaster.setUnit(null);

        // Create the InventoryMaster, which fails.
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryMasterRepository.findAll().size();
        // set the field null
        inventoryMaster.setLastModified(null);

        // Create the InventoryMaster, which fails.
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventoryMasterRepository.findAll().size();
        // set the field null
        inventoryMaster.setLastModifiedBy(null);

        // Create the InventoryMaster, which fails.
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        restInventoryMasterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventoryMasters() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].calulateVolume").value(hasItem(DEFAULT_CALULATE_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].dimensions").value(hasItem(DEFAULT_DIMENSIONS)))
            .andExpect(jsonPath("$.[*].subTypeInd").value(hasItem(DEFAULT_SUB_TYPE_IND)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getInventoryMaster() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get the inventoryMaster
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, inventoryMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryMaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.calulateVolume").value(DEFAULT_CALULATE_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.dimensions").value(DEFAULT_DIMENSIONS))
            .andExpect(jsonPath("$.subTypeInd").value(DEFAULT_SUB_TYPE_IND))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getInventoryMastersByIdFiltering() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        Long id = inventoryMaster.getId();

        defaultInventoryMasterShouldBeFound("id.equals=" + id);
        defaultInventoryMasterShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name equals to DEFAULT_NAME
        defaultInventoryMasterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the inventoryMasterList where name equals to UPDATED_NAME
        defaultInventoryMasterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name not equals to DEFAULT_NAME
        defaultInventoryMasterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the inventoryMasterList where name not equals to UPDATED_NAME
        defaultInventoryMasterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultInventoryMasterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the inventoryMasterList where name equals to UPDATED_NAME
        defaultInventoryMasterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name is not null
        defaultInventoryMasterShouldBeFound("name.specified=true");

        // Get all the inventoryMasterList where name is null
        defaultInventoryMasterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name contains DEFAULT_NAME
        defaultInventoryMasterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the inventoryMasterList where name contains UPDATED_NAME
        defaultInventoryMasterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where name does not contain DEFAULT_NAME
        defaultInventoryMasterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the inventoryMasterList where name does not contain UPDATED_NAME
        defaultInventoryMasterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description equals to DEFAULT_DESCRIPTION
        defaultInventoryMasterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryMasterList where description equals to UPDATED_DESCRIPTION
        defaultInventoryMasterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description not equals to DEFAULT_DESCRIPTION
        defaultInventoryMasterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryMasterList where description not equals to UPDATED_DESCRIPTION
        defaultInventoryMasterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInventoryMasterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the inventoryMasterList where description equals to UPDATED_DESCRIPTION
        defaultInventoryMasterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description is not null
        defaultInventoryMasterShouldBeFound("description.specified=true");

        // Get all the inventoryMasterList where description is null
        defaultInventoryMasterShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description contains DEFAULT_DESCRIPTION
        defaultInventoryMasterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryMasterList where description contains UPDATED_DESCRIPTION
        defaultInventoryMasterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where description does not contain DEFAULT_DESCRIPTION
        defaultInventoryMasterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryMasterList where description does not contain UPDATED_DESCRIPTION
        defaultInventoryMasterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume equals to DEFAULT_VOLUME
        defaultInventoryMasterShouldBeFound("volume.equals=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume equals to UPDATED_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.equals=" + UPDATED_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume not equals to DEFAULT_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.notEquals=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume not equals to UPDATED_VOLUME
        defaultInventoryMasterShouldBeFound("volume.notEquals=" + UPDATED_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume in DEFAULT_VOLUME or UPDATED_VOLUME
        defaultInventoryMasterShouldBeFound("volume.in=" + DEFAULT_VOLUME + "," + UPDATED_VOLUME);

        // Get all the inventoryMasterList where volume equals to UPDATED_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.in=" + UPDATED_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume is not null
        defaultInventoryMasterShouldBeFound("volume.specified=true");

        // Get all the inventoryMasterList where volume is null
        defaultInventoryMasterShouldNotBeFound("volume.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume is greater than or equal to DEFAULT_VOLUME
        defaultInventoryMasterShouldBeFound("volume.greaterThanOrEqual=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume is greater than or equal to UPDATED_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.greaterThanOrEqual=" + UPDATED_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume is less than or equal to DEFAULT_VOLUME
        defaultInventoryMasterShouldBeFound("volume.lessThanOrEqual=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume is less than or equal to SMALLER_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.lessThanOrEqual=" + SMALLER_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume is less than DEFAULT_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.lessThan=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume is less than UPDATED_VOLUME
        defaultInventoryMasterShouldBeFound("volume.lessThan=" + UPDATED_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByVolumeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where volume is greater than DEFAULT_VOLUME
        defaultInventoryMasterShouldNotBeFound("volume.greaterThan=" + DEFAULT_VOLUME);

        // Get all the inventoryMasterList where volume is greater than SMALLER_VOLUME
        defaultInventoryMasterShouldBeFound("volume.greaterThan=" + SMALLER_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit equals to DEFAULT_UNIT
        defaultInventoryMasterShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the inventoryMasterList where unit equals to UPDATED_UNIT
        defaultInventoryMasterShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit not equals to DEFAULT_UNIT
        defaultInventoryMasterShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the inventoryMasterList where unit not equals to UPDATED_UNIT
        defaultInventoryMasterShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultInventoryMasterShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the inventoryMasterList where unit equals to UPDATED_UNIT
        defaultInventoryMasterShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit is not null
        defaultInventoryMasterShouldBeFound("unit.specified=true");

        // Get all the inventoryMasterList where unit is null
        defaultInventoryMasterShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit contains DEFAULT_UNIT
        defaultInventoryMasterShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the inventoryMasterList where unit contains UPDATED_UNIT
        defaultInventoryMasterShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where unit does not contain DEFAULT_UNIT
        defaultInventoryMasterShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the inventoryMasterList where unit does not contain UPDATED_UNIT
        defaultInventoryMasterShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume equals to DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.equals=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume equals to UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.equals=" + UPDATED_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume not equals to DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.notEquals=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume not equals to UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.notEquals=" + UPDATED_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume in DEFAULT_CALULATE_VOLUME or UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.in=" + DEFAULT_CALULATE_VOLUME + "," + UPDATED_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume equals to UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.in=" + UPDATED_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume is not null
        defaultInventoryMasterShouldBeFound("calulateVolume.specified=true");

        // Get all the inventoryMasterList where calulateVolume is null
        defaultInventoryMasterShouldNotBeFound("calulateVolume.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume is greater than or equal to DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.greaterThanOrEqual=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume is greater than or equal to UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.greaterThanOrEqual=" + UPDATED_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume is less than or equal to DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.lessThanOrEqual=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume is less than or equal to SMALLER_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.lessThanOrEqual=" + SMALLER_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume is less than DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.lessThan=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume is less than UPDATED_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.lessThan=" + UPDATED_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByCalulateVolumeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where calulateVolume is greater than DEFAULT_CALULATE_VOLUME
        defaultInventoryMasterShouldNotBeFound("calulateVolume.greaterThan=" + DEFAULT_CALULATE_VOLUME);

        // Get all the inventoryMasterList where calulateVolume is greater than SMALLER_CALULATE_VOLUME
        defaultInventoryMasterShouldBeFound("calulateVolume.greaterThan=" + SMALLER_CALULATE_VOLUME);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions equals to DEFAULT_DIMENSIONS
        defaultInventoryMasterShouldBeFound("dimensions.equals=" + DEFAULT_DIMENSIONS);

        // Get all the inventoryMasterList where dimensions equals to UPDATED_DIMENSIONS
        defaultInventoryMasterShouldNotBeFound("dimensions.equals=" + UPDATED_DIMENSIONS);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions not equals to DEFAULT_DIMENSIONS
        defaultInventoryMasterShouldNotBeFound("dimensions.notEquals=" + DEFAULT_DIMENSIONS);

        // Get all the inventoryMasterList where dimensions not equals to UPDATED_DIMENSIONS
        defaultInventoryMasterShouldBeFound("dimensions.notEquals=" + UPDATED_DIMENSIONS);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions in DEFAULT_DIMENSIONS or UPDATED_DIMENSIONS
        defaultInventoryMasterShouldBeFound("dimensions.in=" + DEFAULT_DIMENSIONS + "," + UPDATED_DIMENSIONS);

        // Get all the inventoryMasterList where dimensions equals to UPDATED_DIMENSIONS
        defaultInventoryMasterShouldNotBeFound("dimensions.in=" + UPDATED_DIMENSIONS);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions is not null
        defaultInventoryMasterShouldBeFound("dimensions.specified=true");

        // Get all the inventoryMasterList where dimensions is null
        defaultInventoryMasterShouldNotBeFound("dimensions.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions contains DEFAULT_DIMENSIONS
        defaultInventoryMasterShouldBeFound("dimensions.contains=" + DEFAULT_DIMENSIONS);

        // Get all the inventoryMasterList where dimensions contains UPDATED_DIMENSIONS
        defaultInventoryMasterShouldNotBeFound("dimensions.contains=" + UPDATED_DIMENSIONS);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDimensionsNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where dimensions does not contain DEFAULT_DIMENSIONS
        defaultInventoryMasterShouldNotBeFound("dimensions.doesNotContain=" + DEFAULT_DIMENSIONS);

        // Get all the inventoryMasterList where dimensions does not contain UPDATED_DIMENSIONS
        defaultInventoryMasterShouldBeFound("dimensions.doesNotContain=" + UPDATED_DIMENSIONS);
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd equals to DEFAULT_SUB_TYPE_IND
        defaultInventoryMasterShouldBeFound("subTypeInd.equals=" + DEFAULT_SUB_TYPE_IND);

        // Get all the inventoryMasterList where subTypeInd equals to UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldNotBeFound("subTypeInd.equals=" + UPDATED_SUB_TYPE_IND);
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd not equals to DEFAULT_SUB_TYPE_IND
        defaultInventoryMasterShouldNotBeFound("subTypeInd.notEquals=" + DEFAULT_SUB_TYPE_IND);

        // Get all the inventoryMasterList where subTypeInd not equals to UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldBeFound("subTypeInd.notEquals=" + UPDATED_SUB_TYPE_IND);
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd in DEFAULT_SUB_TYPE_IND or UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldBeFound("subTypeInd.in=" + DEFAULT_SUB_TYPE_IND + "," + UPDATED_SUB_TYPE_IND);

        // Get all the inventoryMasterList where subTypeInd equals to UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldNotBeFound("subTypeInd.in=" + UPDATED_SUB_TYPE_IND);
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd is not null
        defaultInventoryMasterShouldBeFound("subTypeInd.specified=true");

        // Get all the inventoryMasterList where subTypeInd is null
        defaultInventoryMasterShouldNotBeFound("subTypeInd.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd contains DEFAULT_SUB_TYPE_IND
        defaultInventoryMasterShouldBeFound("subTypeInd.contains=" + DEFAULT_SUB_TYPE_IND);

        // Get all the inventoryMasterList where subTypeInd contains UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldNotBeFound("subTypeInd.contains=" + UPDATED_SUB_TYPE_IND);
    }

    @Test
    @Transactional
    void getAllInventoryMastersBySubTypeIndNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where subTypeInd does not contain DEFAULT_SUB_TYPE_IND
        defaultInventoryMasterShouldNotBeFound("subTypeInd.doesNotContain=" + DEFAULT_SUB_TYPE_IND);

        // Get all the inventoryMasterList where subTypeInd does not contain UPDATED_SUB_TYPE_IND
        defaultInventoryMasterShouldBeFound("subTypeInd.doesNotContain=" + UPDATED_SUB_TYPE_IND);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where deleted equals to DEFAULT_DELETED
        defaultInventoryMasterShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the inventoryMasterList where deleted equals to UPDATED_DELETED
        defaultInventoryMasterShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where deleted not equals to DEFAULT_DELETED
        defaultInventoryMasterShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the inventoryMasterList where deleted not equals to UPDATED_DELETED
        defaultInventoryMasterShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultInventoryMasterShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the inventoryMasterList where deleted equals to UPDATED_DELETED
        defaultInventoryMasterShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where deleted is not null
        defaultInventoryMasterShouldBeFound("deleted.specified=true");

        // Get all the inventoryMasterList where deleted is null
        defaultInventoryMasterShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultInventoryMasterShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryMasterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryMasterShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultInventoryMasterShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the inventoryMasterList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultInventoryMasterShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultInventoryMasterShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the inventoryMasterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInventoryMasterShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModified is not null
        defaultInventoryMasterShouldBeFound("lastModified.specified=true");

        // Get all the inventoryMasterList where lastModified is null
        defaultInventoryMasterShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryMasterShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryMasterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryMasterList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the inventoryMasterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy is not null
        defaultInventoryMasterShouldBeFound("lastModifiedBy.specified=true");

        // Get all the inventoryMasterList where lastModifiedBy is null
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultInventoryMasterShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryMasterList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        // Get all the inventoryMasterList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultInventoryMasterShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the inventoryMasterList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultInventoryMasterShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInventoryMastersByInventoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);
        InventoryType inventoryType;
        if (TestUtil.findAll(em, InventoryType.class).isEmpty()) {
            inventoryType = InventoryTypeResourceIT.createEntity(em);
            em.persist(inventoryType);
            em.flush();
        } else {
            inventoryType = TestUtil.findAll(em, InventoryType.class).get(0);
        }
        em.persist(inventoryType);
        em.flush();
        inventoryMaster.setInventoryType(inventoryType);
        inventoryMasterRepository.saveAndFlush(inventoryMaster);
        Long inventoryTypeId = inventoryType.getId();

        // Get all the inventoryMasterList where inventoryType equals to inventoryTypeId
        defaultInventoryMasterShouldBeFound("inventoryTypeId.equals=" + inventoryTypeId);

        // Get all the inventoryMasterList where inventoryType equals to (inventoryTypeId + 1)
        defaultInventoryMasterShouldNotBeFound("inventoryTypeId.equals=" + (inventoryTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryMasterShouldBeFound(String filter) throws Exception {
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].calulateVolume").value(hasItem(DEFAULT_CALULATE_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].dimensions").value(hasItem(DEFAULT_DIMENSIONS)))
            .andExpect(jsonPath("$.[*].subTypeInd").value(hasItem(DEFAULT_SUB_TYPE_IND)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryMasterShouldNotBeFound(String filter) throws Exception {
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventoryMaster() throws Exception {
        // Get the inventoryMaster
        restInventoryMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInventoryMaster() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();

        // Update the inventoryMaster
        InventoryMaster updatedInventoryMaster = inventoryMasterRepository.findById(inventoryMaster.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryMaster are not directly saved in db
        em.detach(updatedInventoryMaster);
        updatedInventoryMaster
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .volume(UPDATED_VOLUME)
            .unit(UPDATED_UNIT)
            .calulateVolume(UPDATED_CALULATE_VOLUME)
            .dimensions(UPDATED_DIMENSIONS)
            .subTypeInd(UPDATED_SUB_TYPE_IND)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(updatedInventoryMaster);

        restInventoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
        InventoryMaster testInventoryMaster = inventoryMasterList.get(inventoryMasterList.size() - 1);
        assertThat(testInventoryMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInventoryMaster.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testInventoryMaster.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testInventoryMaster.getCalulateVolume()).isEqualTo(UPDATED_CALULATE_VOLUME);
        assertThat(testInventoryMaster.getDimensions()).isEqualTo(UPDATED_DIMENSIONS);
        assertThat(testInventoryMaster.getSubTypeInd()).isEqualTo(UPDATED_SUB_TYPE_IND);
        assertThat(testInventoryMaster.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testInventoryMaster.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventoryMaster.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventoryMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventoryMasterWithPatch() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();

        // Update the inventoryMaster using partial update
        InventoryMaster partialUpdatedInventoryMaster = new InventoryMaster();
        partialUpdatedInventoryMaster.setId(inventoryMaster.getId());

        partialUpdatedInventoryMaster.volume(UPDATED_VOLUME).unit(UPDATED_UNIT);

        restInventoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
        InventoryMaster testInventoryMaster = inventoryMasterList.get(inventoryMasterList.size() - 1);
        assertThat(testInventoryMaster.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInventoryMaster.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testInventoryMaster.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testInventoryMaster.getCalulateVolume()).isEqualTo(DEFAULT_CALULATE_VOLUME);
        assertThat(testInventoryMaster.getDimensions()).isEqualTo(DEFAULT_DIMENSIONS);
        assertThat(testInventoryMaster.getSubTypeInd()).isEqualTo(DEFAULT_SUB_TYPE_IND);
        assertThat(testInventoryMaster.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testInventoryMaster.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInventoryMaster.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateInventoryMasterWithPatch() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();

        // Update the inventoryMaster using partial update
        InventoryMaster partialUpdatedInventoryMaster = new InventoryMaster();
        partialUpdatedInventoryMaster.setId(inventoryMaster.getId());

        partialUpdatedInventoryMaster
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .volume(UPDATED_VOLUME)
            .unit(UPDATED_UNIT)
            .calulateVolume(UPDATED_CALULATE_VOLUME)
            .dimensions(UPDATED_DIMENSIONS)
            .subTypeInd(UPDATED_SUB_TYPE_IND)
            .deleted(UPDATED_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restInventoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventoryMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInventoryMaster))
            )
            .andExpect(status().isOk());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
        InventoryMaster testInventoryMaster = inventoryMasterList.get(inventoryMasterList.size() - 1);
        assertThat(testInventoryMaster.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInventoryMaster.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testInventoryMaster.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testInventoryMaster.getCalulateVolume()).isEqualTo(UPDATED_CALULATE_VOLUME);
        assertThat(testInventoryMaster.getDimensions()).isEqualTo(UPDATED_DIMENSIONS);
        assertThat(testInventoryMaster.getSubTypeInd()).isEqualTo(UPDATED_SUB_TYPE_IND);
        assertThat(testInventoryMaster.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testInventoryMaster.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInventoryMaster.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventoryMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventoryMaster() throws Exception {
        int databaseSizeBeforeUpdate = inventoryMasterRepository.findAll().size();
        inventoryMaster.setId(count.incrementAndGet());

        // Create the InventoryMaster
        InventoryMasterDTO inventoryMasterDTO = inventoryMasterMapper.toDto(inventoryMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventoryMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inventoryMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InventoryMaster in the database
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventoryMaster() throws Exception {
        // Initialize the database
        inventoryMasterRepository.saveAndFlush(inventoryMaster);

        int databaseSizeBeforeDelete = inventoryMasterRepository.findAll().size();

        // Delete the inventoryMaster
        restInventoryMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventoryMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryMaster> inventoryMasterList = inventoryMasterRepository.findAll();
        assertThat(inventoryMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
