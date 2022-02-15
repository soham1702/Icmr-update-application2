package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.ICMRDailyCount;
import com.techvg.covid.care.repository.ICMRDailyCountRepository;
import com.techvg.covid.care.service.criteria.ICMRDailyCountCriteria;
import com.techvg.covid.care.service.dto.ICMRDailyCountDTO;
import com.techvg.covid.care.service.mapper.ICMRDailyCountMapper;
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
 * Integration tests for the {@link ICMRDailyCountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ICMRDailyCountResourceIT {

    private static final Long DEFAULT_TOTAL_SAMPLES_TESTED = 1L;
    private static final Long UPDATED_TOTAL_SAMPLES_TESTED = 2L;
    private static final Long SMALLER_TOTAL_SAMPLES_TESTED = 1L - 1L;

    private static final Long DEFAULT_TOTAL_NEGATIVE = 1L;
    private static final Long UPDATED_TOTAL_NEGATIVE = 2L;
    private static final Long SMALLER_TOTAL_NEGATIVE = 1L - 1L;

    private static final Long DEFAULT_TOTAL_POSITIVE = 1L;
    private static final Long UPDATED_TOTAL_POSITIVE = 2L;
    private static final Long SMALLER_TOTAL_POSITIVE = 1L - 1L;

    private static final Long DEFAULT_CURRENT_PREVIOUS_MONTH_TEST = 1L;
    private static final Long UPDATED_CURRENT_PREVIOUS_MONTH_TEST = 2L;
    private static final Long SMALLER_CURRENT_PREVIOUS_MONTH_TEST = 1L - 1L;

    private static final Long DEFAULT_DISTRICT_ID = 1L;
    private static final Long UPDATED_DISTRICT_ID = 2L;
    private static final Long SMALLER_DISTRICT_ID = 1L - 1L;

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_EDITED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EDITED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/icmr-daily-counts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ICMRDailyCountRepository iCMRDailyCountRepository;

    @Autowired
    private ICMRDailyCountMapper iCMRDailyCountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restICMRDailyCountMockMvc;

    private ICMRDailyCount iCMRDailyCount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ICMRDailyCount createEntity(EntityManager em) {
        ICMRDailyCount iCMRDailyCount = new ICMRDailyCount()
            .totalSamplesTested(DEFAULT_TOTAL_SAMPLES_TESTED)
            .totalNegative(DEFAULT_TOTAL_NEGATIVE)
            .totalPositive(DEFAULT_TOTAL_POSITIVE)
            .currentPreviousMonthTest(DEFAULT_CURRENT_PREVIOUS_MONTH_TEST)
            .districtId(DEFAULT_DISTRICT_ID)
            .remarks(DEFAULT_REMARKS)
            .editedOn(DEFAULT_EDITED_ON)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2);
        return iCMRDailyCount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ICMRDailyCount createUpdatedEntity(EntityManager em) {
        ICMRDailyCount iCMRDailyCount = new ICMRDailyCount()
            .totalSamplesTested(UPDATED_TOTAL_SAMPLES_TESTED)
            .totalNegative(UPDATED_TOTAL_NEGATIVE)
            .totalPositive(UPDATED_TOTAL_POSITIVE)
            .currentPreviousMonthTest(UPDATED_CURRENT_PREVIOUS_MONTH_TEST)
            .districtId(UPDATED_DISTRICT_ID)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .updatedDate(UPDATED_UPDATED_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);
        return iCMRDailyCount;
    }

    @BeforeEach
    public void initTest() {
        iCMRDailyCount = createEntity(em);
    }

    @Test
    @Transactional
    void createICMRDailyCount() throws Exception {
        int databaseSizeBeforeCreate = iCMRDailyCountRepository.findAll().size();
        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);
        restICMRDailyCountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeCreate + 1);
        ICMRDailyCount testICMRDailyCount = iCMRDailyCountList.get(iCMRDailyCountList.size() - 1);
        assertThat(testICMRDailyCount.getTotalSamplesTested()).isEqualTo(DEFAULT_TOTAL_SAMPLES_TESTED);
        assertThat(testICMRDailyCount.getTotalNegative()).isEqualTo(DEFAULT_TOTAL_NEGATIVE);
        assertThat(testICMRDailyCount.getTotalPositive()).isEqualTo(DEFAULT_TOTAL_POSITIVE);
        assertThat(testICMRDailyCount.getCurrentPreviousMonthTest()).isEqualTo(DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);
        assertThat(testICMRDailyCount.getDistrictId()).isEqualTo(DEFAULT_DISTRICT_ID);
        assertThat(testICMRDailyCount.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testICMRDailyCount.getEditedOn()).isEqualTo(DEFAULT_EDITED_ON);
        assertThat(testICMRDailyCount.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testICMRDailyCount.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testICMRDailyCount.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void createICMRDailyCountWithExistingId() throws Exception {
        // Create the ICMRDailyCount with an existing ID
        iCMRDailyCount.setId(1L);
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        int databaseSizeBeforeCreate = iCMRDailyCountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restICMRDailyCountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllICMRDailyCounts() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iCMRDailyCount.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalSamplesTested").value(hasItem(DEFAULT_TOTAL_SAMPLES_TESTED.intValue())))
            .andExpect(jsonPath("$.[*].totalNegative").value(hasItem(DEFAULT_TOTAL_NEGATIVE.intValue())))
            .andExpect(jsonPath("$.[*].totalPositive").value(hasItem(DEFAULT_TOTAL_POSITIVE.intValue())))
            .andExpect(jsonPath("$.[*].currentPreviousMonthTest").value(hasItem(DEFAULT_CURRENT_PREVIOUS_MONTH_TEST.intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].editedOn").value(hasItem(DEFAULT_EDITED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)));
    }

    @Test
    @Transactional
    void getICMRDailyCount() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get the iCMRDailyCount
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL_ID, iCMRDailyCount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(iCMRDailyCount.getId().intValue()))
            .andExpect(jsonPath("$.totalSamplesTested").value(DEFAULT_TOTAL_SAMPLES_TESTED.intValue()))
            .andExpect(jsonPath("$.totalNegative").value(DEFAULT_TOTAL_NEGATIVE.intValue()))
            .andExpect(jsonPath("$.totalPositive").value(DEFAULT_TOTAL_POSITIVE.intValue()))
            .andExpect(jsonPath("$.currentPreviousMonthTest").value(DEFAULT_CURRENT_PREVIOUS_MONTH_TEST.intValue()))
            .andExpect(jsonPath("$.districtId").value(DEFAULT_DISTRICT_ID.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.editedOn").value(DEFAULT_EDITED_ON.toString()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2));
    }

    @Test
    @Transactional
    void getICMRDailyCountsByIdFiltering() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        Long id = iCMRDailyCount.getId();

        defaultICMRDailyCountShouldBeFound("id.equals=" + id);
        defaultICMRDailyCountShouldNotBeFound("id.notEquals=" + id);

        defaultICMRDailyCountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultICMRDailyCountShouldNotBeFound("id.greaterThan=" + id);

        defaultICMRDailyCountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultICMRDailyCountShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested equals to DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.equals=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested equals to UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.equals=" + UPDATED_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested not equals to DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.notEquals=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested not equals to UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.notEquals=" + UPDATED_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested in DEFAULT_TOTAL_SAMPLES_TESTED or UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.in=" + DEFAULT_TOTAL_SAMPLES_TESTED + "," + UPDATED_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested equals to UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.in=" + UPDATED_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested is not null
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.specified=true");

        // Get all the iCMRDailyCountList where totalSamplesTested is null
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested is greater than or equal to DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.greaterThanOrEqual=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested is greater than or equal to UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.greaterThanOrEqual=" + UPDATED_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested is less than or equal to DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.lessThanOrEqual=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested is less than or equal to SMALLER_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.lessThanOrEqual=" + SMALLER_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsLessThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested is less than DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.lessThan=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested is less than UPDATED_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.lessThan=" + UPDATED_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalSamplesTestedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalSamplesTested is greater than DEFAULT_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldNotBeFound("totalSamplesTested.greaterThan=" + DEFAULT_TOTAL_SAMPLES_TESTED);

        // Get all the iCMRDailyCountList where totalSamplesTested is greater than SMALLER_TOTAL_SAMPLES_TESTED
        defaultICMRDailyCountShouldBeFound("totalSamplesTested.greaterThan=" + SMALLER_TOTAL_SAMPLES_TESTED);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative equals to DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.equals=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative equals to UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.equals=" + UPDATED_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative not equals to DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.notEquals=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative not equals to UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.notEquals=" + UPDATED_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative in DEFAULT_TOTAL_NEGATIVE or UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.in=" + DEFAULT_TOTAL_NEGATIVE + "," + UPDATED_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative equals to UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.in=" + UPDATED_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative is not null
        defaultICMRDailyCountShouldBeFound("totalNegative.specified=true");

        // Get all the iCMRDailyCountList where totalNegative is null
        defaultICMRDailyCountShouldNotBeFound("totalNegative.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative is greater than or equal to DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.greaterThanOrEqual=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative is greater than or equal to UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.greaterThanOrEqual=" + UPDATED_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative is less than or equal to DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.lessThanOrEqual=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative is less than or equal to SMALLER_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.lessThanOrEqual=" + SMALLER_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsLessThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative is less than DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.lessThan=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative is less than UPDATED_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.lessThan=" + UPDATED_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalNegativeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalNegative is greater than DEFAULT_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldNotBeFound("totalNegative.greaterThan=" + DEFAULT_TOTAL_NEGATIVE);

        // Get all the iCMRDailyCountList where totalNegative is greater than SMALLER_TOTAL_NEGATIVE
        defaultICMRDailyCountShouldBeFound("totalNegative.greaterThan=" + SMALLER_TOTAL_NEGATIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive equals to DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.equals=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive equals to UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.equals=" + UPDATED_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive not equals to DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.notEquals=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive not equals to UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.notEquals=" + UPDATED_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive in DEFAULT_TOTAL_POSITIVE or UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.in=" + DEFAULT_TOTAL_POSITIVE + "," + UPDATED_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive equals to UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.in=" + UPDATED_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive is not null
        defaultICMRDailyCountShouldBeFound("totalPositive.specified=true");

        // Get all the iCMRDailyCountList where totalPositive is null
        defaultICMRDailyCountShouldNotBeFound("totalPositive.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive is greater than or equal to DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.greaterThanOrEqual=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive is greater than or equal to UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.greaterThanOrEqual=" + UPDATED_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive is less than or equal to DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.lessThanOrEqual=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive is less than or equal to SMALLER_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.lessThanOrEqual=" + SMALLER_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsLessThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive is less than DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.lessThan=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive is less than UPDATED_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.lessThan=" + UPDATED_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByTotalPositiveIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where totalPositive is greater than DEFAULT_TOTAL_POSITIVE
        defaultICMRDailyCountShouldNotBeFound("totalPositive.greaterThan=" + DEFAULT_TOTAL_POSITIVE);

        // Get all the iCMRDailyCountList where totalPositive is greater than SMALLER_TOTAL_POSITIVE
        defaultICMRDailyCountShouldBeFound("totalPositive.greaterThan=" + SMALLER_TOTAL_POSITIVE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest equals to DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.equals=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest equals to UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.equals=" + UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest not equals to DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.notEquals=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest not equals to UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.notEquals=" + UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest in DEFAULT_CURRENT_PREVIOUS_MONTH_TEST or UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound(
            "currentPreviousMonthTest.in=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST + "," + UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        );

        // Get all the iCMRDailyCountList where currentPreviousMonthTest equals to UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.in=" + UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is not null
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.specified=true");

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is null
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is greater than or equal to DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.greaterThanOrEqual=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is greater than or equal to UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.greaterThanOrEqual=" + UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is less than or equal to DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.lessThanOrEqual=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is less than or equal to SMALLER_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.lessThanOrEqual=" + SMALLER_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsLessThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is less than DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.lessThan=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is less than UPDATED_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.lessThan=" + UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByCurrentPreviousMonthTestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is greater than DEFAULT_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldNotBeFound("currentPreviousMonthTest.greaterThan=" + DEFAULT_CURRENT_PREVIOUS_MONTH_TEST);

        // Get all the iCMRDailyCountList where currentPreviousMonthTest is greater than SMALLER_CURRENT_PREVIOUS_MONTH_TEST
        defaultICMRDailyCountShouldBeFound("currentPreviousMonthTest.greaterThan=" + SMALLER_CURRENT_PREVIOUS_MONTH_TEST);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId equals to DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.equals=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId equals to UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.equals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId not equals to DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.notEquals=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId not equals to UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.notEquals=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId in DEFAULT_DISTRICT_ID or UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.in=" + DEFAULT_DISTRICT_ID + "," + UPDATED_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId equals to UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.in=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId is not null
        defaultICMRDailyCountShouldBeFound("districtId.specified=true");

        // Get all the iCMRDailyCountList where districtId is null
        defaultICMRDailyCountShouldNotBeFound("districtId.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId is greater than or equal to DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.greaterThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId is greater than or equal to UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.greaterThanOrEqual=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId is less than or equal to DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.lessThanOrEqual=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId is less than or equal to SMALLER_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.lessThanOrEqual=" + SMALLER_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsLessThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId is less than DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.lessThan=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId is less than UPDATED_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.lessThan=" + UPDATED_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByDistrictIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where districtId is greater than DEFAULT_DISTRICT_ID
        defaultICMRDailyCountShouldNotBeFound("districtId.greaterThan=" + DEFAULT_DISTRICT_ID);

        // Get all the iCMRDailyCountList where districtId is greater than SMALLER_DISTRICT_ID
        defaultICMRDailyCountShouldBeFound("districtId.greaterThan=" + SMALLER_DISTRICT_ID);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks equals to DEFAULT_REMARKS
        defaultICMRDailyCountShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the iCMRDailyCountList where remarks equals to UPDATED_REMARKS
        defaultICMRDailyCountShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks not equals to DEFAULT_REMARKS
        defaultICMRDailyCountShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the iCMRDailyCountList where remarks not equals to UPDATED_REMARKS
        defaultICMRDailyCountShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultICMRDailyCountShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the iCMRDailyCountList where remarks equals to UPDATED_REMARKS
        defaultICMRDailyCountShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks is not null
        defaultICMRDailyCountShouldBeFound("remarks.specified=true");

        // Get all the iCMRDailyCountList where remarks is null
        defaultICMRDailyCountShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks contains DEFAULT_REMARKS
        defaultICMRDailyCountShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the iCMRDailyCountList where remarks contains UPDATED_REMARKS
        defaultICMRDailyCountShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where remarks does not contain DEFAULT_REMARKS
        defaultICMRDailyCountShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the iCMRDailyCountList where remarks does not contain UPDATED_REMARKS
        defaultICMRDailyCountShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByEditedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where editedOn equals to DEFAULT_EDITED_ON
        defaultICMRDailyCountShouldBeFound("editedOn.equals=" + DEFAULT_EDITED_ON);

        // Get all the iCMRDailyCountList where editedOn equals to UPDATED_EDITED_ON
        defaultICMRDailyCountShouldNotBeFound("editedOn.equals=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByEditedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where editedOn not equals to DEFAULT_EDITED_ON
        defaultICMRDailyCountShouldNotBeFound("editedOn.notEquals=" + DEFAULT_EDITED_ON);

        // Get all the iCMRDailyCountList where editedOn not equals to UPDATED_EDITED_ON
        defaultICMRDailyCountShouldBeFound("editedOn.notEquals=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByEditedOnIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where editedOn in DEFAULT_EDITED_ON or UPDATED_EDITED_ON
        defaultICMRDailyCountShouldBeFound("editedOn.in=" + DEFAULT_EDITED_ON + "," + UPDATED_EDITED_ON);

        // Get all the iCMRDailyCountList where editedOn equals to UPDATED_EDITED_ON
        defaultICMRDailyCountShouldNotBeFound("editedOn.in=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByEditedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where editedOn is not null
        defaultICMRDailyCountShouldBeFound("editedOn.specified=true");

        // Get all the iCMRDailyCountList where editedOn is null
        defaultICMRDailyCountShouldNotBeFound("editedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByUpdatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where updatedDate equals to DEFAULT_UPDATED_DATE
        defaultICMRDailyCountShouldBeFound("updatedDate.equals=" + DEFAULT_UPDATED_DATE);

        // Get all the iCMRDailyCountList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultICMRDailyCountShouldNotBeFound("updatedDate.equals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByUpdatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where updatedDate not equals to DEFAULT_UPDATED_DATE
        defaultICMRDailyCountShouldNotBeFound("updatedDate.notEquals=" + DEFAULT_UPDATED_DATE);

        // Get all the iCMRDailyCountList where updatedDate not equals to UPDATED_UPDATED_DATE
        defaultICMRDailyCountShouldBeFound("updatedDate.notEquals=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByUpdatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where updatedDate in DEFAULT_UPDATED_DATE or UPDATED_UPDATED_DATE
        defaultICMRDailyCountShouldBeFound("updatedDate.in=" + DEFAULT_UPDATED_DATE + "," + UPDATED_UPDATED_DATE);

        // Get all the iCMRDailyCountList where updatedDate equals to UPDATED_UPDATED_DATE
        defaultICMRDailyCountShouldNotBeFound("updatedDate.in=" + UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByUpdatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where updatedDate is not null
        defaultICMRDailyCountShouldBeFound("updatedDate.specified=true");

        // Get all the iCMRDailyCountList where updatedDate is null
        defaultICMRDailyCountShouldNotBeFound("updatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultICMRDailyCountShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the iCMRDailyCountList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultICMRDailyCountShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the iCMRDailyCountList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the iCMRDailyCountList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 is not null
        defaultICMRDailyCountShouldBeFound("freeField1.specified=true");

        // Get all the iCMRDailyCountList where freeField1 is null
        defaultICMRDailyCountShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultICMRDailyCountShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the iCMRDailyCountList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultICMRDailyCountShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the iCMRDailyCountList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultICMRDailyCountShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultICMRDailyCountShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the iCMRDailyCountList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultICMRDailyCountShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the iCMRDailyCountList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the iCMRDailyCountList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 is not null
        defaultICMRDailyCountShouldBeFound("freeField2.specified=true");

        // Get all the iCMRDailyCountList where freeField2 is null
        defaultICMRDailyCountShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultICMRDailyCountShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the iCMRDailyCountList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllICMRDailyCountsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        // Get all the iCMRDailyCountList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultICMRDailyCountShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the iCMRDailyCountList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultICMRDailyCountShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultICMRDailyCountShouldBeFound(String filter) throws Exception {
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(iCMRDailyCount.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalSamplesTested").value(hasItem(DEFAULT_TOTAL_SAMPLES_TESTED.intValue())))
            .andExpect(jsonPath("$.[*].totalNegative").value(hasItem(DEFAULT_TOTAL_NEGATIVE.intValue())))
            .andExpect(jsonPath("$.[*].totalPositive").value(hasItem(DEFAULT_TOTAL_POSITIVE.intValue())))
            .andExpect(jsonPath("$.[*].currentPreviousMonthTest").value(hasItem(DEFAULT_CURRENT_PREVIOUS_MONTH_TEST.intValue())))
            .andExpect(jsonPath("$.[*].districtId").value(hasItem(DEFAULT_DISTRICT_ID.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].editedOn").value(hasItem(DEFAULT_EDITED_ON.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)));

        // Check, that the count call also returns 1
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultICMRDailyCountShouldNotBeFound(String filter) throws Exception {
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restICMRDailyCountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingICMRDailyCount() throws Exception {
        // Get the iCMRDailyCount
        restICMRDailyCountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewICMRDailyCount() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();

        // Update the iCMRDailyCount
        ICMRDailyCount updatedICMRDailyCount = iCMRDailyCountRepository.findById(iCMRDailyCount.getId()).get();
        // Disconnect from session so that the updates on updatedICMRDailyCount are not directly saved in db
        em.detach(updatedICMRDailyCount);
        updatedICMRDailyCount
            .totalSamplesTested(UPDATED_TOTAL_SAMPLES_TESTED)
            .totalNegative(UPDATED_TOTAL_NEGATIVE)
            .totalPositive(UPDATED_TOTAL_POSITIVE)
            .currentPreviousMonthTest(UPDATED_CURRENT_PREVIOUS_MONTH_TEST)
            .districtId(UPDATED_DISTRICT_ID)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .updatedDate(UPDATED_UPDATED_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(updatedICMRDailyCount);

        restICMRDailyCountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, iCMRDailyCountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
        ICMRDailyCount testICMRDailyCount = iCMRDailyCountList.get(iCMRDailyCountList.size() - 1);
        assertThat(testICMRDailyCount.getTotalSamplesTested()).isEqualTo(UPDATED_TOTAL_SAMPLES_TESTED);
        assertThat(testICMRDailyCount.getTotalNegative()).isEqualTo(UPDATED_TOTAL_NEGATIVE);
        assertThat(testICMRDailyCount.getTotalPositive()).isEqualTo(UPDATED_TOTAL_POSITIVE);
        assertThat(testICMRDailyCount.getCurrentPreviousMonthTest()).isEqualTo(UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
        assertThat(testICMRDailyCount.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testICMRDailyCount.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testICMRDailyCount.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testICMRDailyCount.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testICMRDailyCount.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testICMRDailyCount.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void putNonExistingICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, iCMRDailyCountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateICMRDailyCountWithPatch() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();

        // Update the iCMRDailyCount using partial update
        ICMRDailyCount partialUpdatedICMRDailyCount = new ICMRDailyCount();
        partialUpdatedICMRDailyCount.setId(iCMRDailyCount.getId());

        partialUpdatedICMRDailyCount
            .totalSamplesTested(UPDATED_TOTAL_SAMPLES_TESTED)
            .totalNegative(UPDATED_TOTAL_NEGATIVE)
            .totalPositive(UPDATED_TOTAL_POSITIVE)
            .currentPreviousMonthTest(UPDATED_CURRENT_PREVIOUS_MONTH_TEST)
            .districtId(UPDATED_DISTRICT_ID)
            .updatedDate(UPDATED_UPDATED_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);

        restICMRDailyCountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedICMRDailyCount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedICMRDailyCount))
            )
            .andExpect(status().isOk());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
        ICMRDailyCount testICMRDailyCount = iCMRDailyCountList.get(iCMRDailyCountList.size() - 1);
        assertThat(testICMRDailyCount.getTotalSamplesTested()).isEqualTo(UPDATED_TOTAL_SAMPLES_TESTED);
        assertThat(testICMRDailyCount.getTotalNegative()).isEqualTo(UPDATED_TOTAL_NEGATIVE);
        assertThat(testICMRDailyCount.getTotalPositive()).isEqualTo(UPDATED_TOTAL_POSITIVE);
        assertThat(testICMRDailyCount.getCurrentPreviousMonthTest()).isEqualTo(UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
        assertThat(testICMRDailyCount.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testICMRDailyCount.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testICMRDailyCount.getEditedOn()).isEqualTo(DEFAULT_EDITED_ON);
        assertThat(testICMRDailyCount.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testICMRDailyCount.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testICMRDailyCount.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void fullUpdateICMRDailyCountWithPatch() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();

        // Update the iCMRDailyCount using partial update
        ICMRDailyCount partialUpdatedICMRDailyCount = new ICMRDailyCount();
        partialUpdatedICMRDailyCount.setId(iCMRDailyCount.getId());

        partialUpdatedICMRDailyCount
            .totalSamplesTested(UPDATED_TOTAL_SAMPLES_TESTED)
            .totalNegative(UPDATED_TOTAL_NEGATIVE)
            .totalPositive(UPDATED_TOTAL_POSITIVE)
            .currentPreviousMonthTest(UPDATED_CURRENT_PREVIOUS_MONTH_TEST)
            .districtId(UPDATED_DISTRICT_ID)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .updatedDate(UPDATED_UPDATED_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2);

        restICMRDailyCountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedICMRDailyCount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedICMRDailyCount))
            )
            .andExpect(status().isOk());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
        ICMRDailyCount testICMRDailyCount = iCMRDailyCountList.get(iCMRDailyCountList.size() - 1);
        assertThat(testICMRDailyCount.getTotalSamplesTested()).isEqualTo(UPDATED_TOTAL_SAMPLES_TESTED);
        assertThat(testICMRDailyCount.getTotalNegative()).isEqualTo(UPDATED_TOTAL_NEGATIVE);
        assertThat(testICMRDailyCount.getTotalPositive()).isEqualTo(UPDATED_TOTAL_POSITIVE);
        assertThat(testICMRDailyCount.getCurrentPreviousMonthTest()).isEqualTo(UPDATED_CURRENT_PREVIOUS_MONTH_TEST);
        assertThat(testICMRDailyCount.getDistrictId()).isEqualTo(UPDATED_DISTRICT_ID);
        assertThat(testICMRDailyCount.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testICMRDailyCount.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testICMRDailyCount.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testICMRDailyCount.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testICMRDailyCount.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void patchNonExistingICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, iCMRDailyCountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamICMRDailyCount() throws Exception {
        int databaseSizeBeforeUpdate = iCMRDailyCountRepository.findAll().size();
        iCMRDailyCount.setId(count.incrementAndGet());

        // Create the ICMRDailyCount
        ICMRDailyCountDTO iCMRDailyCountDTO = iCMRDailyCountMapper.toDto(iCMRDailyCount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restICMRDailyCountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(iCMRDailyCountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ICMRDailyCount in the database
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteICMRDailyCount() throws Exception {
        // Initialize the database
        iCMRDailyCountRepository.saveAndFlush(iCMRDailyCount);

        int databaseSizeBeforeDelete = iCMRDailyCountRepository.findAll().size();

        // Delete the iCMRDailyCount
        restICMRDailyCountMockMvc
            .perform(delete(ENTITY_API_URL_ID, iCMRDailyCount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ICMRDailyCount> iCMRDailyCountList = iCMRDailyCountRepository.findAll();
        assertThat(iCMRDailyCountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
