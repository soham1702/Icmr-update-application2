package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.domain.Trip;
import com.techvg.covid.care.domain.TripDetails;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.TripRepository;
import com.techvg.covid.care.service.criteria.TripCriteria;
import com.techvg.covid.care.service.dto.TripDTO;
import com.techvg.covid.care.service.mapper.TripMapper;
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
 * Integration tests for the {@link TripResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TripResourceIT {

    private static final String DEFAULT_TRACKING_NO = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_MOBA_ID = 1L;
    private static final Long UPDATED_MOBA_ID = 2L;
    private static final Long SMALLER_MOBA_ID = 1L - 1L;

    private static final String DEFAULT_NUMBER_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_PLATE = "BBBBBBBBBB";

    private static final Long DEFAULT_STOCK = 1L;
    private static final Long UPDATED_STOCK = 2L;
    private static final Long SMALLER_STOCK = 1L - 1L;

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.OPEN;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.TRANSIT;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trips";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTripMockMvc;

    private Trip trip;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createEntity(EntityManager em) {
        Trip trip = new Trip()
            .trackingNo(DEFAULT_TRACKING_NO)
            .mobaId(DEFAULT_MOBA_ID)
            .numberPlate(DEFAULT_NUMBER_PLATE)
            .stock(DEFAULT_STOCK)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .comment(DEFAULT_COMMENT)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return trip;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createUpdatedEntity(EntityManager em) {
        Trip trip = new Trip()
            .trackingNo(UPDATED_TRACKING_NO)
            .mobaId(UPDATED_MOBA_ID)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .comment(UPDATED_COMMENT)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return trip;
    }

    @BeforeEach
    public void initTest() {
        trip = createEntity(em);
    }

    @Test
    @Transactional
    void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();
        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);
        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTrackingNo()).isEqualTo(DEFAULT_TRACKING_NO);
        assertThat(testTrip.getMobaId()).isEqualTo(DEFAULT_MOBA_ID);
        assertThat(testTrip.getNumberPlate()).isEqualTo(DEFAULT_NUMBER_PLATE);
        assertThat(testTrip.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testTrip.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrip.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTrip.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTrip.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrip.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTrip.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTripWithExistingId() throws Exception {
        // Create the Trip with an existing ID
        trip.setId(1L);
        TripDTO tripDTO = tripMapper.toDto(trip);

        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrackingNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setTrackingNo(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMobaIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setMobaId(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberPlateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setNumberPlate(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setStock(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setStatus(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setCreatedDate(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripRepository.findAll().size();
        // set the field null
        trip.setCreatedBy(null);

        // Create the Trip, which fails.
        TripDTO tripDTO = tripMapper.toDto(trip);

        restTripMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList
        restTripMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingNo").value(hasItem(DEFAULT_TRACKING_NO)))
            .andExpect(jsonPath("$.[*].mobaId").value(hasItem(DEFAULT_MOBA_ID.intValue())))
            .andExpect(jsonPath("$.[*].numberPlate").value(hasItem(DEFAULT_NUMBER_PLATE)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc
            .perform(get(ENTITY_API_URL_ID, trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.trackingNo").value(DEFAULT_TRACKING_NO))
            .andExpect(jsonPath("$.mobaId").value(DEFAULT_MOBA_ID.intValue()))
            .andExpect(jsonPath("$.numberPlate").value(DEFAULT_NUMBER_PLATE))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTripsByIdFiltering() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        Long id = trip.getId();

        defaultTripShouldBeFound("id.equals=" + id);
        defaultTripShouldNotBeFound("id.notEquals=" + id);

        defaultTripShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTripShouldNotBeFound("id.greaterThan=" + id);

        defaultTripShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTripShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo equals to DEFAULT_TRACKING_NO
        defaultTripShouldBeFound("trackingNo.equals=" + DEFAULT_TRACKING_NO);

        // Get all the tripList where trackingNo equals to UPDATED_TRACKING_NO
        defaultTripShouldNotBeFound("trackingNo.equals=" + UPDATED_TRACKING_NO);
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo not equals to DEFAULT_TRACKING_NO
        defaultTripShouldNotBeFound("trackingNo.notEquals=" + DEFAULT_TRACKING_NO);

        // Get all the tripList where trackingNo not equals to UPDATED_TRACKING_NO
        defaultTripShouldBeFound("trackingNo.notEquals=" + UPDATED_TRACKING_NO);
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo in DEFAULT_TRACKING_NO or UPDATED_TRACKING_NO
        defaultTripShouldBeFound("trackingNo.in=" + DEFAULT_TRACKING_NO + "," + UPDATED_TRACKING_NO);

        // Get all the tripList where trackingNo equals to UPDATED_TRACKING_NO
        defaultTripShouldNotBeFound("trackingNo.in=" + UPDATED_TRACKING_NO);
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo is not null
        defaultTripShouldBeFound("trackingNo.specified=true");

        // Get all the tripList where trackingNo is null
        defaultTripShouldNotBeFound("trackingNo.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo contains DEFAULT_TRACKING_NO
        defaultTripShouldBeFound("trackingNo.contains=" + DEFAULT_TRACKING_NO);

        // Get all the tripList where trackingNo contains UPDATED_TRACKING_NO
        defaultTripShouldNotBeFound("trackingNo.contains=" + UPDATED_TRACKING_NO);
    }

    @Test
    @Transactional
    void getAllTripsByTrackingNoNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where trackingNo does not contain DEFAULT_TRACKING_NO
        defaultTripShouldNotBeFound("trackingNo.doesNotContain=" + DEFAULT_TRACKING_NO);

        // Get all the tripList where trackingNo does not contain UPDATED_TRACKING_NO
        defaultTripShouldBeFound("trackingNo.doesNotContain=" + UPDATED_TRACKING_NO);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId equals to DEFAULT_MOBA_ID
        defaultTripShouldBeFound("mobaId.equals=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId equals to UPDATED_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.equals=" + UPDATED_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId not equals to DEFAULT_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.notEquals=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId not equals to UPDATED_MOBA_ID
        defaultTripShouldBeFound("mobaId.notEquals=" + UPDATED_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId in DEFAULT_MOBA_ID or UPDATED_MOBA_ID
        defaultTripShouldBeFound("mobaId.in=" + DEFAULT_MOBA_ID + "," + UPDATED_MOBA_ID);

        // Get all the tripList where mobaId equals to UPDATED_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.in=" + UPDATED_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId is not null
        defaultTripShouldBeFound("mobaId.specified=true");

        // Get all the tripList where mobaId is null
        defaultTripShouldNotBeFound("mobaId.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId is greater than or equal to DEFAULT_MOBA_ID
        defaultTripShouldBeFound("mobaId.greaterThanOrEqual=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId is greater than or equal to UPDATED_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.greaterThanOrEqual=" + UPDATED_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId is less than or equal to DEFAULT_MOBA_ID
        defaultTripShouldBeFound("mobaId.lessThanOrEqual=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId is less than or equal to SMALLER_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.lessThanOrEqual=" + SMALLER_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId is less than DEFAULT_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.lessThan=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId is less than UPDATED_MOBA_ID
        defaultTripShouldBeFound("mobaId.lessThan=" + UPDATED_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByMobaIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where mobaId is greater than DEFAULT_MOBA_ID
        defaultTripShouldNotBeFound("mobaId.greaterThan=" + DEFAULT_MOBA_ID);

        // Get all the tripList where mobaId is greater than SMALLER_MOBA_ID
        defaultTripShouldBeFound("mobaId.greaterThan=" + SMALLER_MOBA_ID);
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate equals to DEFAULT_NUMBER_PLATE
        defaultTripShouldBeFound("numberPlate.equals=" + DEFAULT_NUMBER_PLATE);

        // Get all the tripList where numberPlate equals to UPDATED_NUMBER_PLATE
        defaultTripShouldNotBeFound("numberPlate.equals=" + UPDATED_NUMBER_PLATE);
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate not equals to DEFAULT_NUMBER_PLATE
        defaultTripShouldNotBeFound("numberPlate.notEquals=" + DEFAULT_NUMBER_PLATE);

        // Get all the tripList where numberPlate not equals to UPDATED_NUMBER_PLATE
        defaultTripShouldBeFound("numberPlate.notEquals=" + UPDATED_NUMBER_PLATE);
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate in DEFAULT_NUMBER_PLATE or UPDATED_NUMBER_PLATE
        defaultTripShouldBeFound("numberPlate.in=" + DEFAULT_NUMBER_PLATE + "," + UPDATED_NUMBER_PLATE);

        // Get all the tripList where numberPlate equals to UPDATED_NUMBER_PLATE
        defaultTripShouldNotBeFound("numberPlate.in=" + UPDATED_NUMBER_PLATE);
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate is not null
        defaultTripShouldBeFound("numberPlate.specified=true");

        // Get all the tripList where numberPlate is null
        defaultTripShouldNotBeFound("numberPlate.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate contains DEFAULT_NUMBER_PLATE
        defaultTripShouldBeFound("numberPlate.contains=" + DEFAULT_NUMBER_PLATE);

        // Get all the tripList where numberPlate contains UPDATED_NUMBER_PLATE
        defaultTripShouldNotBeFound("numberPlate.contains=" + UPDATED_NUMBER_PLATE);
    }

    @Test
    @Transactional
    void getAllTripsByNumberPlateNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where numberPlate does not contain DEFAULT_NUMBER_PLATE
        defaultTripShouldNotBeFound("numberPlate.doesNotContain=" + DEFAULT_NUMBER_PLATE);

        // Get all the tripList where numberPlate does not contain UPDATED_NUMBER_PLATE
        defaultTripShouldBeFound("numberPlate.doesNotContain=" + UPDATED_NUMBER_PLATE);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock equals to DEFAULT_STOCK
        defaultTripShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the tripList where stock equals to UPDATED_STOCK
        defaultTripShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock not equals to DEFAULT_STOCK
        defaultTripShouldNotBeFound("stock.notEquals=" + DEFAULT_STOCK);

        // Get all the tripList where stock not equals to UPDATED_STOCK
        defaultTripShouldBeFound("stock.notEquals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultTripShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the tripList where stock equals to UPDATED_STOCK
        defaultTripShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock is not null
        defaultTripShouldBeFound("stock.specified=true");

        // Get all the tripList where stock is null
        defaultTripShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock is greater than or equal to DEFAULT_STOCK
        defaultTripShouldBeFound("stock.greaterThanOrEqual=" + DEFAULT_STOCK);

        // Get all the tripList where stock is greater than or equal to UPDATED_STOCK
        defaultTripShouldNotBeFound("stock.greaterThanOrEqual=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock is less than or equal to DEFAULT_STOCK
        defaultTripShouldBeFound("stock.lessThanOrEqual=" + DEFAULT_STOCK);

        // Get all the tripList where stock is less than or equal to SMALLER_STOCK
        defaultTripShouldNotBeFound("stock.lessThanOrEqual=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock is less than DEFAULT_STOCK
        defaultTripShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the tripList where stock is less than UPDATED_STOCK
        defaultTripShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where stock is greater than DEFAULT_STOCK
        defaultTripShouldNotBeFound("stock.greaterThan=" + DEFAULT_STOCK);

        // Get all the tripList where stock is greater than SMALLER_STOCK
        defaultTripShouldBeFound("stock.greaterThan=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllTripsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where status equals to DEFAULT_STATUS
        defaultTripShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the tripList where status equals to UPDATED_STATUS
        defaultTripShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where status not equals to DEFAULT_STATUS
        defaultTripShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the tripList where status not equals to UPDATED_STATUS
        defaultTripShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTripShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the tripList where status equals to UPDATED_STATUS
        defaultTripShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where status is not null
        defaultTripShouldBeFound("status.specified=true");

        // Get all the tripList where status is null
        defaultTripShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTripShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the tripList where createdDate equals to UPDATED_CREATED_DATE
        defaultTripShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultTripShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the tripList where createdDate not equals to UPDATED_CREATED_DATE
        defaultTripShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTripShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the tripList where createdDate equals to UPDATED_CREATED_DATE
        defaultTripShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdDate is not null
        defaultTripShouldBeFound("createdDate.specified=true");

        // Get all the tripList where createdDate is null
        defaultTripShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy equals to DEFAULT_CREATED_BY
        defaultTripShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the tripList where createdBy equals to UPDATED_CREATED_BY
        defaultTripShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy not equals to DEFAULT_CREATED_BY
        defaultTripShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the tripList where createdBy not equals to UPDATED_CREATED_BY
        defaultTripShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultTripShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the tripList where createdBy equals to UPDATED_CREATED_BY
        defaultTripShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy is not null
        defaultTripShouldBeFound("createdBy.specified=true");

        // Get all the tripList where createdBy is null
        defaultTripShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy contains DEFAULT_CREATED_BY
        defaultTripShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the tripList where createdBy contains UPDATED_CREATED_BY
        defaultTripShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where createdBy does not contain DEFAULT_CREATED_BY
        defaultTripShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the tripList where createdBy does not contain UPDATED_CREATED_BY
        defaultTripShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTripShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tripList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTripShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTripShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tripList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTripShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTripShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the tripList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTripShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModified is not null
        defaultTripShouldBeFound("lastModified.specified=true");

        // Get all the tripList where lastModified is null
        defaultTripShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment equals to DEFAULT_COMMENT
        defaultTripShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the tripList where comment equals to UPDATED_COMMENT
        defaultTripShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment not equals to DEFAULT_COMMENT
        defaultTripShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the tripList where comment not equals to UPDATED_COMMENT
        defaultTripShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTripShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the tripList where comment equals to UPDATED_COMMENT
        defaultTripShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment is not null
        defaultTripShouldBeFound("comment.specified=true");

        // Get all the tripList where comment is null
        defaultTripShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByCommentContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment contains DEFAULT_COMMENT
        defaultTripShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the tripList where comment contains UPDATED_COMMENT
        defaultTripShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where comment does not contain DEFAULT_COMMENT
        defaultTripShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the tripList where comment does not contain UPDATED_COMMENT
        defaultTripShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTripShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTripShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTripShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTripShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTripShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the tripList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTripShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy is not null
        defaultTripShouldBeFound("lastModifiedBy.specified=true");

        // Get all the tripList where lastModifiedBy is null
        defaultTripShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTripShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTripShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTripShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTripShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripsByTripDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        TripDetails tripDetails;
        if (TestUtil.findAll(em, TripDetails.class).isEmpty()) {
            tripDetails = TripDetailsResourceIT.createEntity(em);
            em.persist(tripDetails);
            em.flush();
        } else {
            tripDetails = TestUtil.findAll(em, TripDetails.class).get(0);
        }
        em.persist(tripDetails);
        em.flush();
        trip.addTripDetails(tripDetails);
        tripRepository.saveAndFlush(trip);
        Long tripDetailsId = tripDetails.getId();

        // Get all the tripList where tripDetails equals to tripDetailsId
        defaultTripShouldBeFound("tripDetailsId.equals=" + tripDetailsId);

        // Get all the tripList where tripDetails equals to (tripDetailsId + 1)
        defaultTripShouldNotBeFound("tripDetailsId.equals=" + (tripDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllTripsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
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
        trip.setSupplier(supplier);
        tripRepository.saveAndFlush(trip);
        Long supplierId = supplier.getId();

        // Get all the tripList where supplier equals to supplierId
        defaultTripShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the tripList where supplier equals to (supplierId + 1)
        defaultTripShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTripShouldBeFound(String filter) throws Exception {
        restTripMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingNo").value(hasItem(DEFAULT_TRACKING_NO)))
            .andExpect(jsonPath("$.[*].mobaId").value(hasItem(DEFAULT_MOBA_ID.intValue())))
            .andExpect(jsonPath("$.[*].numberPlate").value(hasItem(DEFAULT_NUMBER_PLATE)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTripMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTripShouldNotBeFound(String filter) throws Exception {
        restTripMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTripMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findById(trip.getId()).get();
        // Disconnect from session so that the updates on updatedTrip are not directly saved in db
        em.detach(updatedTrip);
        updatedTrip
            .trackingNo(UPDATED_TRACKING_NO)
            .mobaId(UPDATED_MOBA_ID)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .comment(UPDATED_COMMENT)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TripDTO tripDTO = tripMapper.toDto(updatedTrip);

        restTripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tripDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDTO))
            )
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTrackingNo()).isEqualTo(UPDATED_TRACKING_NO);
        assertThat(testTrip.getMobaId()).isEqualTo(UPDATED_MOBA_ID);
        assertThat(testTrip.getNumberPlate()).isEqualTo(UPDATED_NUMBER_PLATE);
        assertThat(testTrip.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testTrip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrip.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTrip.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTrip.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrip.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTrip.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tripDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTripWithPatch() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip using partial update
        Trip partialUpdatedTrip = new Trip();
        partialUpdatedTrip.setId(trip.getId());

        partialUpdatedTrip
            .trackingNo(UPDATED_TRACKING_NO)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrip))
            )
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTrackingNo()).isEqualTo(UPDATED_TRACKING_NO);
        assertThat(testTrip.getMobaId()).isEqualTo(DEFAULT_MOBA_ID);
        assertThat(testTrip.getNumberPlate()).isEqualTo(DEFAULT_NUMBER_PLATE);
        assertThat(testTrip.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testTrip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrip.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTrip.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTrip.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTrip.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTrip.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTripWithPatch() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip using partial update
        Trip partialUpdatedTrip = new Trip();
        partialUpdatedTrip.setId(trip.getId());

        partialUpdatedTrip
            .trackingNo(UPDATED_TRACKING_NO)
            .mobaId(UPDATED_MOBA_ID)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .comment(UPDATED_COMMENT)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrip.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrip))
            )
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTrackingNo()).isEqualTo(UPDATED_TRACKING_NO);
        assertThat(testTrip.getMobaId()).isEqualTo(UPDATED_MOBA_ID);
        assertThat(testTrip.getNumberPlate()).isEqualTo(UPDATED_NUMBER_PLATE);
        assertThat(testTrip.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testTrip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrip.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTrip.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTrip.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTrip.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTrip.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tripDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();
        trip.setId(count.incrementAndGet());

        // Create the Trip
        TripDTO tripDTO = tripMapper.toDto(trip);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Delete the trip
        restTripMockMvc
            .perform(delete(ENTITY_API_URL_ID, trip.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
