package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.domain.Transactions;
import com.techvg.covid.care.domain.Trip;
import com.techvg.covid.care.domain.TripDetails;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.TripDetailsRepository;
import com.techvg.covid.care.service.criteria.TripDetailsCriteria;
import com.techvg.covid.care.service.dto.TripDetailsDTO;
import com.techvg.covid.care.service.mapper.TripDetailsMapper;
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
 * Integration tests for the {@link TripDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TripDetailsResourceIT {

    private static final Long DEFAULT_STOCK_SENT = 1L;
    private static final Long UPDATED_STOCK_SENT = 2L;
    private static final Long SMALLER_STOCK_SENT = 1L - 1L;

    private static final Long DEFAULT_STOCK_REC = 1L;
    private static final Long UPDATED_STOCK_REC = 2L;
    private static final Long SMALLER_STOCK_REC = 1L - 1L;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER_COMMENT = "BBBBBBBBBB";

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.OPEN;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.TRANSIT;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trip-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TripDetailsRepository tripDetailsRepository;

    @Autowired
    private TripDetailsMapper tripDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTripDetailsMockMvc;

    private TripDetails tripDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TripDetails createEntity(EntityManager em) {
        TripDetails tripDetails = new TripDetails()
            .stockSent(DEFAULT_STOCK_SENT)
            .stockRec(DEFAULT_STOCK_REC)
            .comment(DEFAULT_COMMENT)
            .receiverComment(DEFAULT_RECEIVER_COMMENT)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return tripDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TripDetails createUpdatedEntity(EntityManager em) {
        TripDetails tripDetails = new TripDetails()
            .stockSent(UPDATED_STOCK_SENT)
            .stockRec(UPDATED_STOCK_REC)
            .comment(UPDATED_COMMENT)
            .receiverComment(UPDATED_RECEIVER_COMMENT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return tripDetails;
    }

    @BeforeEach
    public void initTest() {
        tripDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createTripDetails() throws Exception {
        int databaseSizeBeforeCreate = tripDetailsRepository.findAll().size();
        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);
        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        TripDetails testTripDetails = tripDetailsList.get(tripDetailsList.size() - 1);
        assertThat(testTripDetails.getStockSent()).isEqualTo(DEFAULT_STOCK_SENT);
        assertThat(testTripDetails.getStockRec()).isEqualTo(DEFAULT_STOCK_REC);
        assertThat(testTripDetails.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTripDetails.getReceiverComment()).isEqualTo(DEFAULT_RECEIVER_COMMENT);
        assertThat(testTripDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTripDetails.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTripDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTripDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTripDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTripDetailsWithExistingId() throws Exception {
        // Create the TripDetails with an existing ID
        tripDetails.setId(1L);
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        int databaseSizeBeforeCreate = tripDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStockSentIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDetailsRepository.findAll().size();
        // set the field null
        tripDetails.setStockSent(null);

        // Create the TripDetails, which fails.
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDetailsRepository.findAll().size();
        // set the field null
        tripDetails.setStatus(null);

        // Create the TripDetails, which fails.
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDetailsRepository.findAll().size();
        // set the field null
        tripDetails.setCreatedDate(null);

        // Create the TripDetails, which fails.
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = tripDetailsRepository.findAll().size();
        // set the field null
        tripDetails.setCreatedBy(null);

        // Create the TripDetails, which fails.
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        restTripDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTripDetails() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockSent").value(hasItem(DEFAULT_STOCK_SENT.intValue())))
            .andExpect(jsonPath("$.[*].stockRec").value(hasItem(DEFAULT_STOCK_REC.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].receiverComment").value(hasItem(DEFAULT_RECEIVER_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTripDetails() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get the tripDetails
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, tripDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tripDetails.getId().intValue()))
            .andExpect(jsonPath("$.stockSent").value(DEFAULT_STOCK_SENT.intValue()))
            .andExpect(jsonPath("$.stockRec").value(DEFAULT_STOCK_REC.intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.receiverComment").value(DEFAULT_RECEIVER_COMMENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTripDetailsByIdFiltering() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        Long id = tripDetails.getId();

        defaultTripDetailsShouldBeFound("id.equals=" + id);
        defaultTripDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultTripDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTripDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultTripDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTripDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent equals to DEFAULT_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.equals=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent equals to UPDATED_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.equals=" + UPDATED_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent not equals to DEFAULT_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.notEquals=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent not equals to UPDATED_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.notEquals=" + UPDATED_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent in DEFAULT_STOCK_SENT or UPDATED_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.in=" + DEFAULT_STOCK_SENT + "," + UPDATED_STOCK_SENT);

        // Get all the tripDetailsList where stockSent equals to UPDATED_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.in=" + UPDATED_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent is not null
        defaultTripDetailsShouldBeFound("stockSent.specified=true");

        // Get all the tripDetailsList where stockSent is null
        defaultTripDetailsShouldNotBeFound("stockSent.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent is greater than or equal to DEFAULT_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.greaterThanOrEqual=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent is greater than or equal to UPDATED_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.greaterThanOrEqual=" + UPDATED_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent is less than or equal to DEFAULT_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.lessThanOrEqual=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent is less than or equal to SMALLER_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.lessThanOrEqual=" + SMALLER_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsLessThanSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent is less than DEFAULT_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.lessThan=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent is less than UPDATED_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.lessThan=" + UPDATED_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockSentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockSent is greater than DEFAULT_STOCK_SENT
        defaultTripDetailsShouldNotBeFound("stockSent.greaterThan=" + DEFAULT_STOCK_SENT);

        // Get all the tripDetailsList where stockSent is greater than SMALLER_STOCK_SENT
        defaultTripDetailsShouldBeFound("stockSent.greaterThan=" + SMALLER_STOCK_SENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec equals to DEFAULT_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.equals=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec equals to UPDATED_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.equals=" + UPDATED_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec not equals to DEFAULT_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.notEquals=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec not equals to UPDATED_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.notEquals=" + UPDATED_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec in DEFAULT_STOCK_REC or UPDATED_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.in=" + DEFAULT_STOCK_REC + "," + UPDATED_STOCK_REC);

        // Get all the tripDetailsList where stockRec equals to UPDATED_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.in=" + UPDATED_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec is not null
        defaultTripDetailsShouldBeFound("stockRec.specified=true");

        // Get all the tripDetailsList where stockRec is null
        defaultTripDetailsShouldNotBeFound("stockRec.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec is greater than or equal to DEFAULT_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.greaterThanOrEqual=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec is greater than or equal to UPDATED_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.greaterThanOrEqual=" + UPDATED_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec is less than or equal to DEFAULT_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.lessThanOrEqual=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec is less than or equal to SMALLER_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.lessThanOrEqual=" + SMALLER_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsLessThanSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec is less than DEFAULT_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.lessThan=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec is less than UPDATED_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.lessThan=" + UPDATED_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStockRecIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where stockRec is greater than DEFAULT_STOCK_REC
        defaultTripDetailsShouldNotBeFound("stockRec.greaterThan=" + DEFAULT_STOCK_REC);

        // Get all the tripDetailsList where stockRec is greater than SMALLER_STOCK_REC
        defaultTripDetailsShouldBeFound("stockRec.greaterThan=" + SMALLER_STOCK_REC);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment equals to DEFAULT_COMMENT
        defaultTripDetailsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the tripDetailsList where comment equals to UPDATED_COMMENT
        defaultTripDetailsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment not equals to DEFAULT_COMMENT
        defaultTripDetailsShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the tripDetailsList where comment not equals to UPDATED_COMMENT
        defaultTripDetailsShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTripDetailsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the tripDetailsList where comment equals to UPDATED_COMMENT
        defaultTripDetailsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment is not null
        defaultTripDetailsShouldBeFound("comment.specified=true");

        // Get all the tripDetailsList where comment is null
        defaultTripDetailsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment contains DEFAULT_COMMENT
        defaultTripDetailsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the tripDetailsList where comment contains UPDATED_COMMENT
        defaultTripDetailsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where comment does not contain DEFAULT_COMMENT
        defaultTripDetailsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the tripDetailsList where comment does not contain UPDATED_COMMENT
        defaultTripDetailsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment equals to DEFAULT_RECEIVER_COMMENT
        defaultTripDetailsShouldBeFound("receiverComment.equals=" + DEFAULT_RECEIVER_COMMENT);

        // Get all the tripDetailsList where receiverComment equals to UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldNotBeFound("receiverComment.equals=" + UPDATED_RECEIVER_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment not equals to DEFAULT_RECEIVER_COMMENT
        defaultTripDetailsShouldNotBeFound("receiverComment.notEquals=" + DEFAULT_RECEIVER_COMMENT);

        // Get all the tripDetailsList where receiverComment not equals to UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldBeFound("receiverComment.notEquals=" + UPDATED_RECEIVER_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment in DEFAULT_RECEIVER_COMMENT or UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldBeFound("receiverComment.in=" + DEFAULT_RECEIVER_COMMENT + "," + UPDATED_RECEIVER_COMMENT);

        // Get all the tripDetailsList where receiverComment equals to UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldNotBeFound("receiverComment.in=" + UPDATED_RECEIVER_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment is not null
        defaultTripDetailsShouldBeFound("receiverComment.specified=true");

        // Get all the tripDetailsList where receiverComment is null
        defaultTripDetailsShouldNotBeFound("receiverComment.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment contains DEFAULT_RECEIVER_COMMENT
        defaultTripDetailsShouldBeFound("receiverComment.contains=" + DEFAULT_RECEIVER_COMMENT);

        // Get all the tripDetailsList where receiverComment contains UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldNotBeFound("receiverComment.contains=" + UPDATED_RECEIVER_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByReceiverCommentNotContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where receiverComment does not contain DEFAULT_RECEIVER_COMMENT
        defaultTripDetailsShouldNotBeFound("receiverComment.doesNotContain=" + DEFAULT_RECEIVER_COMMENT);

        // Get all the tripDetailsList where receiverComment does not contain UPDATED_RECEIVER_COMMENT
        defaultTripDetailsShouldBeFound("receiverComment.doesNotContain=" + UPDATED_RECEIVER_COMMENT);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where status equals to DEFAULT_STATUS
        defaultTripDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the tripDetailsList where status equals to UPDATED_STATUS
        defaultTripDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where status not equals to DEFAULT_STATUS
        defaultTripDetailsShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the tripDetailsList where status not equals to UPDATED_STATUS
        defaultTripDetailsShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTripDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the tripDetailsList where status equals to UPDATED_STATUS
        defaultTripDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTripDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where status is not null
        defaultTripDetailsShouldBeFound("status.specified=true");

        // Get all the tripDetailsList where status is null
        defaultTripDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdDate equals to DEFAULT_CREATED_DATE
        defaultTripDetailsShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the tripDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultTripDetailsShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultTripDetailsShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the tripDetailsList where createdDate not equals to UPDATED_CREATED_DATE
        defaultTripDetailsShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultTripDetailsShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the tripDetailsList where createdDate equals to UPDATED_CREATED_DATE
        defaultTripDetailsShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdDate is not null
        defaultTripDetailsShouldBeFound("createdDate.specified=true");

        // Get all the tripDetailsList where createdDate is null
        defaultTripDetailsShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultTripDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the tripDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultTripDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy not equals to DEFAULT_CREATED_BY
        defaultTripDetailsShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the tripDetailsList where createdBy not equals to UPDATED_CREATED_BY
        defaultTripDetailsShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultTripDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the tripDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultTripDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy is not null
        defaultTripDetailsShouldBeFound("createdBy.specified=true");

        // Get all the tripDetailsList where createdBy is null
        defaultTripDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultTripDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the tripDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultTripDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultTripDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the tripDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultTripDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTripDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tripDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTripDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTripDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tripDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTripDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTripDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the tripDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTripDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModified is not null
        defaultTripDetailsShouldBeFound("lastModified.specified=true");

        // Get all the tripDetailsList where lastModified is null
        defaultTripDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTripDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the tripDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy is not null
        defaultTripDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the tripDetailsList where lastModifiedBy is null
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTripDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        // Get all the tripDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTripDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tripDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTripDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTripDetailsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);
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
        tripDetails.setSupplier(supplier);
        tripDetailsRepository.saveAndFlush(tripDetails);
        Long supplierId = supplier.getId();

        // Get all the tripDetailsList where supplier equals to supplierId
        defaultTripDetailsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the tripDetailsList where supplier equals to (supplierId + 1)
        defaultTripDetailsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    @Test
    @Transactional
    void getAllTripDetailsByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);
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
        tripDetails.setHospital(hospital);
        tripDetailsRepository.saveAndFlush(tripDetails);
        Long hospitalId = hospital.getId();

        // Get all the tripDetailsList where hospital equals to hospitalId
        defaultTripDetailsShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the tripDetailsList where hospital equals to (hospitalId + 1)
        defaultTripDetailsShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    @Test
    @Transactional
    void getAllTripDetailsByTransactionsIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);
        Transactions transactions;
        if (TestUtil.findAll(em, Transactions.class).isEmpty()) {
            transactions = TransactionsResourceIT.createEntity(em);
            em.persist(transactions);
            em.flush();
        } else {
            transactions = TestUtil.findAll(em, Transactions.class).get(0);
        }
        em.persist(transactions);
        em.flush();
        tripDetails.setTransactions(transactions);
        tripDetailsRepository.saveAndFlush(tripDetails);
        Long transactionsId = transactions.getId();

        // Get all the tripDetailsList where transactions equals to transactionsId
        defaultTripDetailsShouldBeFound("transactionsId.equals=" + transactionsId);

        // Get all the tripDetailsList where transactions equals to (transactionsId + 1)
        defaultTripDetailsShouldNotBeFound("transactionsId.equals=" + (transactionsId + 1));
    }

    @Test
    @Transactional
    void getAllTripDetailsByTripIsEqualToSomething() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);
        Trip trip;
        if (TestUtil.findAll(em, Trip.class).isEmpty()) {
            trip = TripResourceIT.createEntity(em);
            em.persist(trip);
            em.flush();
        } else {
            trip = TestUtil.findAll(em, Trip.class).get(0);
        }
        em.persist(trip);
        em.flush();
        tripDetails.setTrip(trip);
        tripDetailsRepository.saveAndFlush(tripDetails);
        Long tripId = trip.getId();

        // Get all the tripDetailsList where trip equals to tripId
        defaultTripDetailsShouldBeFound("tripId.equals=" + tripId);

        // Get all the tripDetailsList where trip equals to (tripId + 1)
        defaultTripDetailsShouldNotBeFound("tripId.equals=" + (tripId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTripDetailsShouldBeFound(String filter) throws Exception {
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockSent").value(hasItem(DEFAULT_STOCK_SENT.intValue())))
            .andExpect(jsonPath("$.[*].stockRec").value(hasItem(DEFAULT_STOCK_REC.intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].receiverComment").value(hasItem(DEFAULT_RECEIVER_COMMENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTripDetailsShouldNotBeFound(String filter) throws Exception {
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTripDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTripDetails() throws Exception {
        // Get the tripDetails
        restTripDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTripDetails() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();

        // Update the tripDetails
        TripDetails updatedTripDetails = tripDetailsRepository.findById(tripDetails.getId()).get();
        // Disconnect from session so that the updates on updatedTripDetails are not directly saved in db
        em.detach(updatedTripDetails);
        updatedTripDetails
            .stockSent(UPDATED_STOCK_SENT)
            .stockRec(UPDATED_STOCK_REC)
            .comment(UPDATED_COMMENT)
            .receiverComment(UPDATED_RECEIVER_COMMENT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(updatedTripDetails);

        restTripDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tripDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
        TripDetails testTripDetails = tripDetailsList.get(tripDetailsList.size() - 1);
        assertThat(testTripDetails.getStockSent()).isEqualTo(UPDATED_STOCK_SENT);
        assertThat(testTripDetails.getStockRec()).isEqualTo(UPDATED_STOCK_REC);
        assertThat(testTripDetails.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTripDetails.getReceiverComment()).isEqualTo(UPDATED_RECEIVER_COMMENT);
        assertThat(testTripDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTripDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTripDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTripDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTripDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tripDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTripDetailsWithPatch() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();

        // Update the tripDetails using partial update
        TripDetails partialUpdatedTripDetails = new TripDetails();
        partialUpdatedTripDetails.setId(tripDetails.getId());

        partialUpdatedTripDetails.stockSent(UPDATED_STOCK_SENT).stockRec(UPDATED_STOCK_REC).createdDate(UPDATED_CREATED_DATE);

        restTripDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTripDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTripDetails))
            )
            .andExpect(status().isOk());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
        TripDetails testTripDetails = tripDetailsList.get(tripDetailsList.size() - 1);
        assertThat(testTripDetails.getStockSent()).isEqualTo(UPDATED_STOCK_SENT);
        assertThat(testTripDetails.getStockRec()).isEqualTo(UPDATED_STOCK_REC);
        assertThat(testTripDetails.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTripDetails.getReceiverComment()).isEqualTo(DEFAULT_RECEIVER_COMMENT);
        assertThat(testTripDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTripDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTripDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTripDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTripDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTripDetailsWithPatch() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();

        // Update the tripDetails using partial update
        TripDetails partialUpdatedTripDetails = new TripDetails();
        partialUpdatedTripDetails.setId(tripDetails.getId());

        partialUpdatedTripDetails
            .stockSent(UPDATED_STOCK_SENT)
            .stockRec(UPDATED_STOCK_REC)
            .comment(UPDATED_COMMENT)
            .receiverComment(UPDATED_RECEIVER_COMMENT)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTripDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTripDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTripDetails))
            )
            .andExpect(status().isOk());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
        TripDetails testTripDetails = tripDetailsList.get(tripDetailsList.size() - 1);
        assertThat(testTripDetails.getStockSent()).isEqualTo(UPDATED_STOCK_SENT);
        assertThat(testTripDetails.getStockRec()).isEqualTo(UPDATED_STOCK_REC);
        assertThat(testTripDetails.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTripDetails.getReceiverComment()).isEqualTo(UPDATED_RECEIVER_COMMENT);
        assertThat(testTripDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTripDetails.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTripDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTripDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTripDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tripDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTripDetails() throws Exception {
        int databaseSizeBeforeUpdate = tripDetailsRepository.findAll().size();
        tripDetails.setId(count.incrementAndGet());

        // Create the TripDetails
        TripDetailsDTO tripDetailsDTO = tripDetailsMapper.toDto(tripDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTripDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tripDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TripDetails in the database
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTripDetails() throws Exception {
        // Initialize the database
        tripDetailsRepository.saveAndFlush(tripDetails);

        int databaseSizeBeforeDelete = tripDetailsRepository.findAll().size();

        // Delete the tripDetails
        restTripDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tripDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TripDetails> tripDetailsList = tripDetailsRepository.findAll();
        assertThat(tripDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
