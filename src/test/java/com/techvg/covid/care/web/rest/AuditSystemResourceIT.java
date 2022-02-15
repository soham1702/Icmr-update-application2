package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.AuditSystem;
import com.techvg.covid.care.domain.AuditType;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.repository.AuditSystemRepository;
import com.techvg.covid.care.service.criteria.AuditSystemCriteria;
import com.techvg.covid.care.service.dto.AuditSystemDTO;
import com.techvg.covid.care.service.mapper.AuditSystemMapper;
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
 * Integration tests for the {@link AuditSystemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditSystemResourceIT {

    private static final String DEFAULT_AUDITOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUDITOR_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DEFECT_COUNT = 1L;
    private static final Long UPDATED_DEFECT_COUNT = 2L;
    private static final Long SMALLER_DEFECT_COUNT = 1L - 1L;

    private static final Long DEFAULT_DEFECT_FIX_COUNT = 1L;
    private static final Long UPDATED_DEFECT_FIX_COUNT = 2L;
    private static final Long SMALLER_DEFECT_FIX_COUNT = 1L - 1L;

    private static final Instant DEFAULT_INSPECTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSPECTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/audit-systems";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditSystemRepository auditSystemRepository;

    @Autowired
    private AuditSystemMapper auditSystemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditSystemMockMvc;

    private AuditSystem auditSystem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditSystem createEntity(EntityManager em) {
        AuditSystem auditSystem = new AuditSystem()
            .auditorName(DEFAULT_AUDITOR_NAME)
            .defectCount(DEFAULT_DEFECT_COUNT)
            .defectFixCount(DEFAULT_DEFECT_FIX_COUNT)
            .inspectionDate(DEFAULT_INSPECTION_DATE)
            .remark(DEFAULT_REMARK)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return auditSystem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditSystem createUpdatedEntity(EntityManager em) {
        AuditSystem auditSystem = new AuditSystem()
            .auditorName(UPDATED_AUDITOR_NAME)
            .defectCount(UPDATED_DEFECT_COUNT)
            .defectFixCount(UPDATED_DEFECT_FIX_COUNT)
            .inspectionDate(UPDATED_INSPECTION_DATE)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return auditSystem;
    }

    @BeforeEach
    public void initTest() {
        auditSystem = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditSystem() throws Exception {
        int databaseSizeBeforeCreate = auditSystemRepository.findAll().size();
        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);
        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeCreate + 1);
        AuditSystem testAuditSystem = auditSystemList.get(auditSystemList.size() - 1);
        assertThat(testAuditSystem.getAuditorName()).isEqualTo(DEFAULT_AUDITOR_NAME);
        assertThat(testAuditSystem.getDefectCount()).isEqualTo(DEFAULT_DEFECT_COUNT);
        assertThat(testAuditSystem.getDefectFixCount()).isEqualTo(DEFAULT_DEFECT_FIX_COUNT);
        assertThat(testAuditSystem.getInspectionDate()).isEqualTo(DEFAULT_INSPECTION_DATE);
        assertThat(testAuditSystem.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testAuditSystem.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAuditSystem.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAuditSystem.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createAuditSystemWithExistingId() throws Exception {
        // Create the AuditSystem with an existing ID
        auditSystem.setId(1L);
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        int databaseSizeBeforeCreate = auditSystemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAuditorNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditSystemRepository.findAll().size();
        // set the field null
        auditSystem.setAuditorName(null);

        // Create the AuditSystem, which fails.
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInspectionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditSystemRepository.findAll().size();
        // set the field null
        auditSystem.setInspectionDate(null);

        // Create the AuditSystem, which fails.
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditSystemRepository.findAll().size();
        // set the field null
        auditSystem.setLastModified(null);

        // Create the AuditSystem, which fails.
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditSystemRepository.findAll().size();
        // set the field null
        auditSystem.setLastModifiedBy(null);

        // Create the AuditSystem, which fails.
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        restAuditSystemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditSystems() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditorName").value(hasItem(DEFAULT_AUDITOR_NAME)))
            .andExpect(jsonPath("$.[*].defectCount").value(hasItem(DEFAULT_DEFECT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].defectFixCount").value(hasItem(DEFAULT_DEFECT_FIX_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].inspectionDate").value(hasItem(DEFAULT_INSPECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getAuditSystem() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get the auditSystem
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL_ID, auditSystem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditSystem.getId().intValue()))
            .andExpect(jsonPath("$.auditorName").value(DEFAULT_AUDITOR_NAME))
            .andExpect(jsonPath("$.defectCount").value(DEFAULT_DEFECT_COUNT.intValue()))
            .andExpect(jsonPath("$.defectFixCount").value(DEFAULT_DEFECT_FIX_COUNT.intValue()))
            .andExpect(jsonPath("$.inspectionDate").value(DEFAULT_INSPECTION_DATE.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getAuditSystemsByIdFiltering() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        Long id = auditSystem.getId();

        defaultAuditSystemShouldBeFound("id.equals=" + id);
        defaultAuditSystemShouldNotBeFound("id.notEquals=" + id);

        defaultAuditSystemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditSystemShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditSystemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditSystemShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName equals to DEFAULT_AUDITOR_NAME
        defaultAuditSystemShouldBeFound("auditorName.equals=" + DEFAULT_AUDITOR_NAME);

        // Get all the auditSystemList where auditorName equals to UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldNotBeFound("auditorName.equals=" + UPDATED_AUDITOR_NAME);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName not equals to DEFAULT_AUDITOR_NAME
        defaultAuditSystemShouldNotBeFound("auditorName.notEquals=" + DEFAULT_AUDITOR_NAME);

        // Get all the auditSystemList where auditorName not equals to UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldBeFound("auditorName.notEquals=" + UPDATED_AUDITOR_NAME);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName in DEFAULT_AUDITOR_NAME or UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldBeFound("auditorName.in=" + DEFAULT_AUDITOR_NAME + "," + UPDATED_AUDITOR_NAME);

        // Get all the auditSystemList where auditorName equals to UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldNotBeFound("auditorName.in=" + UPDATED_AUDITOR_NAME);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName is not null
        defaultAuditSystemShouldBeFound("auditorName.specified=true");

        // Get all the auditSystemList where auditorName is null
        defaultAuditSystemShouldNotBeFound("auditorName.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName contains DEFAULT_AUDITOR_NAME
        defaultAuditSystemShouldBeFound("auditorName.contains=" + DEFAULT_AUDITOR_NAME);

        // Get all the auditSystemList where auditorName contains UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldNotBeFound("auditorName.contains=" + UPDATED_AUDITOR_NAME);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditorNameNotContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where auditorName does not contain DEFAULT_AUDITOR_NAME
        defaultAuditSystemShouldNotBeFound("auditorName.doesNotContain=" + DEFAULT_AUDITOR_NAME);

        // Get all the auditSystemList where auditorName does not contain UPDATED_AUDITOR_NAME
        defaultAuditSystemShouldBeFound("auditorName.doesNotContain=" + UPDATED_AUDITOR_NAME);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount equals to DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.equals=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount equals to UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.equals=" + UPDATED_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount not equals to DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.notEquals=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount not equals to UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.notEquals=" + UPDATED_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount in DEFAULT_DEFECT_COUNT or UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.in=" + DEFAULT_DEFECT_COUNT + "," + UPDATED_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount equals to UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.in=" + UPDATED_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount is not null
        defaultAuditSystemShouldBeFound("defectCount.specified=true");

        // Get all the auditSystemList where defectCount is null
        defaultAuditSystemShouldNotBeFound("defectCount.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount is greater than or equal to DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.greaterThanOrEqual=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount is greater than or equal to UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.greaterThanOrEqual=" + UPDATED_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount is less than or equal to DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.lessThanOrEqual=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount is less than or equal to SMALLER_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.lessThanOrEqual=" + SMALLER_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsLessThanSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount is less than DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.lessThan=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount is less than UPDATED_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.lessThan=" + UPDATED_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectCount is greater than DEFAULT_DEFECT_COUNT
        defaultAuditSystemShouldNotBeFound("defectCount.greaterThan=" + DEFAULT_DEFECT_COUNT);

        // Get all the auditSystemList where defectCount is greater than SMALLER_DEFECT_COUNT
        defaultAuditSystemShouldBeFound("defectCount.greaterThan=" + SMALLER_DEFECT_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount equals to DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.equals=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount equals to UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.equals=" + UPDATED_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount not equals to DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.notEquals=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount not equals to UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.notEquals=" + UPDATED_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount in DEFAULT_DEFECT_FIX_COUNT or UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.in=" + DEFAULT_DEFECT_FIX_COUNT + "," + UPDATED_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount equals to UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.in=" + UPDATED_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount is not null
        defaultAuditSystemShouldBeFound("defectFixCount.specified=true");

        // Get all the auditSystemList where defectFixCount is null
        defaultAuditSystemShouldNotBeFound("defectFixCount.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount is greater than or equal to DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.greaterThanOrEqual=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount is greater than or equal to UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.greaterThanOrEqual=" + UPDATED_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount is less than or equal to DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.lessThanOrEqual=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount is less than or equal to SMALLER_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.lessThanOrEqual=" + SMALLER_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsLessThanSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount is less than DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.lessThan=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount is less than UPDATED_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.lessThan=" + UPDATED_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByDefectFixCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where defectFixCount is greater than DEFAULT_DEFECT_FIX_COUNT
        defaultAuditSystemShouldNotBeFound("defectFixCount.greaterThan=" + DEFAULT_DEFECT_FIX_COUNT);

        // Get all the auditSystemList where defectFixCount is greater than SMALLER_DEFECT_FIX_COUNT
        defaultAuditSystemShouldBeFound("defectFixCount.greaterThan=" + SMALLER_DEFECT_FIX_COUNT);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByInspectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where inspectionDate equals to DEFAULT_INSPECTION_DATE
        defaultAuditSystemShouldBeFound("inspectionDate.equals=" + DEFAULT_INSPECTION_DATE);

        // Get all the auditSystemList where inspectionDate equals to UPDATED_INSPECTION_DATE
        defaultAuditSystemShouldNotBeFound("inspectionDate.equals=" + UPDATED_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByInspectionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where inspectionDate not equals to DEFAULT_INSPECTION_DATE
        defaultAuditSystemShouldNotBeFound("inspectionDate.notEquals=" + DEFAULT_INSPECTION_DATE);

        // Get all the auditSystemList where inspectionDate not equals to UPDATED_INSPECTION_DATE
        defaultAuditSystemShouldBeFound("inspectionDate.notEquals=" + UPDATED_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByInspectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where inspectionDate in DEFAULT_INSPECTION_DATE or UPDATED_INSPECTION_DATE
        defaultAuditSystemShouldBeFound("inspectionDate.in=" + DEFAULT_INSPECTION_DATE + "," + UPDATED_INSPECTION_DATE);

        // Get all the auditSystemList where inspectionDate equals to UPDATED_INSPECTION_DATE
        defaultAuditSystemShouldNotBeFound("inspectionDate.in=" + UPDATED_INSPECTION_DATE);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByInspectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where inspectionDate is not null
        defaultAuditSystemShouldBeFound("inspectionDate.specified=true");

        // Get all the auditSystemList where inspectionDate is null
        defaultAuditSystemShouldNotBeFound("inspectionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark equals to DEFAULT_REMARK
        defaultAuditSystemShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the auditSystemList where remark equals to UPDATED_REMARK
        defaultAuditSystemShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark not equals to DEFAULT_REMARK
        defaultAuditSystemShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the auditSystemList where remark not equals to UPDATED_REMARK
        defaultAuditSystemShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultAuditSystemShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the auditSystemList where remark equals to UPDATED_REMARK
        defaultAuditSystemShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark is not null
        defaultAuditSystemShouldBeFound("remark.specified=true");

        // Get all the auditSystemList where remark is null
        defaultAuditSystemShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark contains DEFAULT_REMARK
        defaultAuditSystemShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the auditSystemList where remark contains UPDATED_REMARK
        defaultAuditSystemShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where remark does not contain DEFAULT_REMARK
        defaultAuditSystemShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the auditSystemList where remark does not contain UPDATED_REMARK
        defaultAuditSystemShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status equals to DEFAULT_STATUS
        defaultAuditSystemShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the auditSystemList where status equals to UPDATED_STATUS
        defaultAuditSystemShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status not equals to DEFAULT_STATUS
        defaultAuditSystemShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the auditSystemList where status not equals to UPDATED_STATUS
        defaultAuditSystemShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAuditSystemShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the auditSystemList where status equals to UPDATED_STATUS
        defaultAuditSystemShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status is not null
        defaultAuditSystemShouldBeFound("status.specified=true");

        // Get all the auditSystemList where status is null
        defaultAuditSystemShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status contains DEFAULT_STATUS
        defaultAuditSystemShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the auditSystemList where status contains UPDATED_STATUS
        defaultAuditSystemShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where status does not contain DEFAULT_STATUS
        defaultAuditSystemShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the auditSystemList where status does not contain UPDATED_STATUS
        defaultAuditSystemShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAuditSystemShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the auditSystemList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAuditSystemShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultAuditSystemShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the auditSystemList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultAuditSystemShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAuditSystemShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the auditSystemList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAuditSystemShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModified is not null
        defaultAuditSystemShouldBeFound("lastModified.specified=true");

        // Get all the auditSystemList where lastModified is null
        defaultAuditSystemShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAuditSystemShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditSystemList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditSystemList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the auditSystemList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy is not null
        defaultAuditSystemShouldBeFound("lastModifiedBy.specified=true");

        // Get all the auditSystemList where lastModifiedBy is null
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAuditSystemShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditSystemList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        // Get all the auditSystemList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAuditSystemShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the auditSystemList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAuditSystemShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAuditSystemsByAuditTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);
        AuditType auditType;
        if (TestUtil.findAll(em, AuditType.class).isEmpty()) {
            auditType = AuditTypeResourceIT.createEntity(em);
            em.persist(auditType);
            em.flush();
        } else {
            auditType = TestUtil.findAll(em, AuditType.class).get(0);
        }
        em.persist(auditType);
        em.flush();
        auditSystem.setAuditType(auditType);
        auditSystemRepository.saveAndFlush(auditSystem);
        Long auditTypeId = auditType.getId();

        // Get all the auditSystemList where auditType equals to auditTypeId
        defaultAuditSystemShouldBeFound("auditTypeId.equals=" + auditTypeId);

        // Get all the auditSystemList where auditType equals to (auditTypeId + 1)
        defaultAuditSystemShouldNotBeFound("auditTypeId.equals=" + (auditTypeId + 1));
    }

    @Test
    @Transactional
    void getAllAuditSystemsByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);
        Hospital hospital;
        if (TestUtil.findAll(em, Hospital.class).isEmpty()) {
            hospital = HospitalResourceIT.createEntity(em);
            em.persist(hospital);
            em.flush();
        } else {
            hospital = TestUtil.findAll(em, Hospital.class).get(0);
        }
        em.persist(hospital);
        em.flush();
        auditSystem.setHospital(hospital);
        auditSystemRepository.saveAndFlush(auditSystem);
        Long hospitalId = hospital.getId();

        // Get all the auditSystemList where hospital equals to hospitalId
        defaultAuditSystemShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the auditSystemList where hospital equals to (hospitalId + 1)
        defaultAuditSystemShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    @Test
    @Transactional
    void getAllAuditSystemsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);
        Supplier supplier;
        if (TestUtil.findAll(em, Supplier.class).isEmpty()) {
            supplier = SupplierResourceIT.createEntity(em);
            em.persist(supplier);
            em.flush();
        } else {
            supplier = TestUtil.findAll(em, Supplier.class).get(0);
        }
        em.persist(supplier);
        em.flush();
        auditSystem.setSupplier(supplier);
        auditSystemRepository.saveAndFlush(auditSystem);
        Long supplierId = supplier.getId();

        // Get all the auditSystemList where supplier equals to supplierId
        defaultAuditSystemShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the auditSystemList where supplier equals to (supplierId + 1)
        defaultAuditSystemShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditSystemShouldBeFound(String filter) throws Exception {
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditSystem.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditorName").value(hasItem(DEFAULT_AUDITOR_NAME)))
            .andExpect(jsonPath("$.[*].defectCount").value(hasItem(DEFAULT_DEFECT_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].defectFixCount").value(hasItem(DEFAULT_DEFECT_FIX_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].inspectionDate").value(hasItem(DEFAULT_INSPECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditSystemShouldNotBeFound(String filter) throws Exception {
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditSystemMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuditSystem() throws Exception {
        // Get the auditSystem
        restAuditSystemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuditSystem() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();

        // Update the auditSystem
        AuditSystem updatedAuditSystem = auditSystemRepository.findById(auditSystem.getId()).get();
        // Disconnect from session so that the updates on updatedAuditSystem are not directly saved in db
        em.detach(updatedAuditSystem);
        updatedAuditSystem
            .auditorName(UPDATED_AUDITOR_NAME)
            .defectCount(UPDATED_DEFECT_COUNT)
            .defectFixCount(UPDATED_DEFECT_FIX_COUNT)
            .inspectionDate(UPDATED_INSPECTION_DATE)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(updatedAuditSystem);

        restAuditSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditSystemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
        AuditSystem testAuditSystem = auditSystemList.get(auditSystemList.size() - 1);
        assertThat(testAuditSystem.getAuditorName()).isEqualTo(UPDATED_AUDITOR_NAME);
        assertThat(testAuditSystem.getDefectCount()).isEqualTo(UPDATED_DEFECT_COUNT);
        assertThat(testAuditSystem.getDefectFixCount()).isEqualTo(UPDATED_DEFECT_FIX_COUNT);
        assertThat(testAuditSystem.getInspectionDate()).isEqualTo(UPDATED_INSPECTION_DATE);
        assertThat(testAuditSystem.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAuditSystem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditSystem.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAuditSystem.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditSystemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditSystemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuditSystemWithPatch() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();

        // Update the auditSystem using partial update
        AuditSystem partialUpdatedAuditSystem = new AuditSystem();
        partialUpdatedAuditSystem.setId(auditSystem.getId());

        partialUpdatedAuditSystem.status(UPDATED_STATUS);

        restAuditSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditSystem))
            )
            .andExpect(status().isOk());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
        AuditSystem testAuditSystem = auditSystemList.get(auditSystemList.size() - 1);
        assertThat(testAuditSystem.getAuditorName()).isEqualTo(DEFAULT_AUDITOR_NAME);
        assertThat(testAuditSystem.getDefectCount()).isEqualTo(DEFAULT_DEFECT_COUNT);
        assertThat(testAuditSystem.getDefectFixCount()).isEqualTo(DEFAULT_DEFECT_FIX_COUNT);
        assertThat(testAuditSystem.getInspectionDate()).isEqualTo(DEFAULT_INSPECTION_DATE);
        assertThat(testAuditSystem.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testAuditSystem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditSystem.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAuditSystem.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateAuditSystemWithPatch() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();

        // Update the auditSystem using partial update
        AuditSystem partialUpdatedAuditSystem = new AuditSystem();
        partialUpdatedAuditSystem.setId(auditSystem.getId());

        partialUpdatedAuditSystem
            .auditorName(UPDATED_AUDITOR_NAME)
            .defectCount(UPDATED_DEFECT_COUNT)
            .defectFixCount(UPDATED_DEFECT_FIX_COUNT)
            .inspectionDate(UPDATED_INSPECTION_DATE)
            .remark(UPDATED_REMARK)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAuditSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditSystem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditSystem))
            )
            .andExpect(status().isOk());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
        AuditSystem testAuditSystem = auditSystemList.get(auditSystemList.size() - 1);
        assertThat(testAuditSystem.getAuditorName()).isEqualTo(UPDATED_AUDITOR_NAME);
        assertThat(testAuditSystem.getDefectCount()).isEqualTo(UPDATED_DEFECT_COUNT);
        assertThat(testAuditSystem.getDefectFixCount()).isEqualTo(UPDATED_DEFECT_FIX_COUNT);
        assertThat(testAuditSystem.getInspectionDate()).isEqualTo(UPDATED_INSPECTION_DATE);
        assertThat(testAuditSystem.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testAuditSystem.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAuditSystem.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAuditSystem.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditSystemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditSystem() throws Exception {
        int databaseSizeBeforeUpdate = auditSystemRepository.findAll().size();
        auditSystem.setId(count.incrementAndGet());

        // Create the AuditSystem
        AuditSystemDTO auditSystemDTO = auditSystemMapper.toDto(auditSystem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditSystemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditSystemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditSystem in the database
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuditSystem() throws Exception {
        // Initialize the database
        auditSystemRepository.saveAndFlush(auditSystem);

        int databaseSizeBeforeDelete = auditSystemRepository.findAll().size();

        // Delete the auditSystem
        restAuditSystemMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditSystem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditSystem> auditSystemList = auditSystemRepository.findAll();
        assertThat(auditSystemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
