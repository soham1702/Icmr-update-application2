package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.Inventory;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.domain.Transactions;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import com.techvg.covid.care.repository.TransactionsRepository;
import com.techvg.covid.care.service.criteria.TransactionsCriteria;
import com.techvg.covid.care.service.dto.TransactionsDTO;
import com.techvg.covid.care.service.mapper.TransactionsMapper;
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
 * Integration tests for the {@link TransactionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransactionsResourceIT {

    private static final Long DEFAULT_STOCK_REQ = 1L;
    private static final Long UPDATED_STOCK_REQ = 2L;
    private static final Long SMALLER_STOCK_REQ = 1L - 1L;

    private static final Long DEFAULT_STOCK_PROVIDED = 1L;
    private static final Long UPDATED_STOCK_PROVIDED = 2L;
    private static final Long SMALLER_STOCK_PROVIDED = 1L - 1L;

    private static final TransactionStatus DEFAULT_STATUS = TransactionStatus.OPEN;
    private static final TransactionStatus UPDATED_STATUS = TransactionStatus.TRANSIT;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transactions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private TransactionsMapper transactionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionsMockMvc;

    private Transactions transactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createEntity(EntityManager em) {
        Transactions transactions = new Transactions()
            .stockReq(DEFAULT_STOCK_REQ)
            .stockProvided(DEFAULT_STOCK_PROVIDED)
            .status(DEFAULT_STATUS)
            .comment(DEFAULT_COMMENT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return transactions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transactions createUpdatedEntity(EntityManager em) {
        Transactions transactions = new Transactions()
            .stockReq(UPDATED_STOCK_REQ)
            .stockProvided(UPDATED_STOCK_PROVIDED)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return transactions;
    }

    @BeforeEach
    public void initTest() {
        transactions = createEntity(em);
    }

    @Test
    @Transactional
    void createTransactions() throws Exception {
        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();
        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);
        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate + 1);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getStockReq()).isEqualTo(DEFAULT_STOCK_REQ);
        assertThat(testTransactions.getStockProvided()).isEqualTo(DEFAULT_STOCK_PROVIDED);
        assertThat(testTransactions.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransactions.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransactions.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransactions.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTransactionsWithExistingId() throws Exception {
        // Create the Transactions with an existing ID
        transactions.setId(1L);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        int databaseSizeBeforeCreate = transactionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStockReqIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setStockReq(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setStatus(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setLastModified(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionsRepository.findAll().size();
        // set the field null
        transactions.setLastModifiedBy(null);

        // Create the Transactions, which fails.
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        restTransactionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockReq").value(hasItem(DEFAULT_STOCK_REQ.intValue())))
            .andExpect(jsonPath("$.[*].stockProvided").value(hasItem(DEFAULT_STOCK_PROVIDED.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get the transactions
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL_ID, transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactions.getId().intValue()))
            .andExpect(jsonPath("$.stockReq").value(DEFAULT_STOCK_REQ.intValue()))
            .andExpect(jsonPath("$.stockProvided").value(DEFAULT_STOCK_PROVIDED.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTransactionsByIdFiltering() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        Long id = transactions.getId();

        defaultTransactionsShouldBeFound("id.equals=" + id);
        defaultTransactionsShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionsShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq equals to DEFAULT_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.equals=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq equals to UPDATED_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.equals=" + UPDATED_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq not equals to DEFAULT_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.notEquals=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq not equals to UPDATED_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.notEquals=" + UPDATED_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq in DEFAULT_STOCK_REQ or UPDATED_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.in=" + DEFAULT_STOCK_REQ + "," + UPDATED_STOCK_REQ);

        // Get all the transactionsList where stockReq equals to UPDATED_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.in=" + UPDATED_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq is not null
        defaultTransactionsShouldBeFound("stockReq.specified=true");

        // Get all the transactionsList where stockReq is null
        defaultTransactionsShouldNotBeFound("stockReq.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq is greater than or equal to DEFAULT_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.greaterThanOrEqual=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq is greater than or equal to UPDATED_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.greaterThanOrEqual=" + UPDATED_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq is less than or equal to DEFAULT_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.lessThanOrEqual=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq is less than or equal to SMALLER_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.lessThanOrEqual=" + SMALLER_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq is less than DEFAULT_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.lessThan=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq is less than UPDATED_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.lessThan=" + UPDATED_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockReqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockReq is greater than DEFAULT_STOCK_REQ
        defaultTransactionsShouldNotBeFound("stockReq.greaterThan=" + DEFAULT_STOCK_REQ);

        // Get all the transactionsList where stockReq is greater than SMALLER_STOCK_REQ
        defaultTransactionsShouldBeFound("stockReq.greaterThan=" + SMALLER_STOCK_REQ);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided equals to DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.equals=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided equals to UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.equals=" + UPDATED_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided not equals to DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.notEquals=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided not equals to UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.notEquals=" + UPDATED_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided in DEFAULT_STOCK_PROVIDED or UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.in=" + DEFAULT_STOCK_PROVIDED + "," + UPDATED_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided equals to UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.in=" + UPDATED_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided is not null
        defaultTransactionsShouldBeFound("stockProvided.specified=true");

        // Get all the transactionsList where stockProvided is null
        defaultTransactionsShouldNotBeFound("stockProvided.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided is greater than or equal to DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.greaterThanOrEqual=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided is greater than or equal to UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.greaterThanOrEqual=" + UPDATED_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided is less than or equal to DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.lessThanOrEqual=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided is less than or equal to SMALLER_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.lessThanOrEqual=" + SMALLER_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided is less than DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.lessThan=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided is less than UPDATED_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.lessThan=" + UPDATED_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStockProvidedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where stockProvided is greater than DEFAULT_STOCK_PROVIDED
        defaultTransactionsShouldNotBeFound("stockProvided.greaterThan=" + DEFAULT_STOCK_PROVIDED);

        // Get all the transactionsList where stockProvided is greater than SMALLER_STOCK_PROVIDED
        defaultTransactionsShouldBeFound("stockProvided.greaterThan=" + SMALLER_STOCK_PROVIDED);
    }

    @Test
    @Transactional
    void getAllTransactionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where status equals to DEFAULT_STATUS
        defaultTransactionsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transactionsList where status equals to UPDATED_STATUS
        defaultTransactionsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where status not equals to DEFAULT_STATUS
        defaultTransactionsShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the transactionsList where status not equals to UPDATED_STATUS
        defaultTransactionsShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransactionsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transactionsList where status equals to UPDATED_STATUS
        defaultTransactionsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransactionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where status is not null
        defaultTransactionsShouldBeFound("status.specified=true");

        // Get all the transactionsList where status is null
        defaultTransactionsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment equals to DEFAULT_COMMENT
        defaultTransactionsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transactionsList where comment equals to UPDATED_COMMENT
        defaultTransactionsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment not equals to DEFAULT_COMMENT
        defaultTransactionsShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transactionsList where comment not equals to UPDATED_COMMENT
        defaultTransactionsShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransactionsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transactionsList where comment equals to UPDATED_COMMENT
        defaultTransactionsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment is not null
        defaultTransactionsShouldBeFound("comment.specified=true");

        // Get all the transactionsList where comment is null
        defaultTransactionsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentContainsSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment contains DEFAULT_COMMENT
        defaultTransactionsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transactionsList where comment contains UPDATED_COMMENT
        defaultTransactionsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransactionsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where comment does not contain DEFAULT_COMMENT
        defaultTransactionsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transactionsList where comment does not contain UPDATED_COMMENT
        defaultTransactionsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransactionsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transactionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransactionsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransactionsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transactionsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransactionsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransactionsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transactionsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransactionsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModified is not null
        defaultTransactionsShouldBeFound("lastModified.specified=true");

        // Get all the transactionsList where lastModified is null
        defaultTransactionsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransactionsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transactionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransactionsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transactionsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transactionsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy is not null
        defaultTransactionsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transactionsList where lastModifiedBy is null
        defaultTransactionsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransactionsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transactionsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransactionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        // Get all the transactionsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransactionsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transactionsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransactionsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransactionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);
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
        transactions.setSupplier(supplier);
        transactionsRepository.saveAndFlush(transactions);
        Long supplierId = supplier.getId();

        // Get all the transactionsList where supplier equals to supplierId
        defaultTransactionsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the transactionsList where supplier equals to (supplierId + 1)
        defaultTransactionsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    @Test
    @Transactional
    void getAllTransactionsByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);
        Inventory inventory;
        if (TestUtil.findAll(em, Inventory.class).isEmpty()) {
            inventory = InventoryResourceIT.createEntity(em);
            em.persist(inventory);
            em.flush();
        } else {
            inventory = TestUtil.findAll(em, Inventory.class).get(0);
        }
        em.persist(inventory);
        em.flush();
        transactions.setInventory(inventory);
        transactionsRepository.saveAndFlush(transactions);
        Long inventoryId = inventory.getId();

        // Get all the transactionsList where inventory equals to inventoryId
        defaultTransactionsShouldBeFound("inventoryId.equals=" + inventoryId);

        // Get all the transactionsList where inventory equals to (inventoryId + 1)
        defaultTransactionsShouldNotBeFound("inventoryId.equals=" + (inventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionsShouldBeFound(String filter) throws Exception {
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].stockReq").value(hasItem(DEFAULT_STOCK_REQ.intValue())))
            .andExpect(jsonPath("$.[*].stockProvided").value(hasItem(DEFAULT_STOCK_PROVIDED.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionsShouldNotBeFound(String filter) throws Exception {
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransactions() throws Exception {
        // Get the transactions
        restTransactionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions
        Transactions updatedTransactions = transactionsRepository.findById(transactions.getId()).get();
        // Disconnect from session so that the updates on updatedTransactions are not directly saved in db
        em.detach(updatedTransactions);
        updatedTransactions
            .stockReq(UPDATED_STOCK_REQ)
            .stockProvided(UPDATED_STOCK_PROVIDED)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(updatedTransactions);

        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getStockReq()).isEqualTo(UPDATED_STOCK_REQ);
        assertThat(testTransactions.getStockProvided()).isEqualTo(UPDATED_STOCK_PROVIDED);
        assertThat(testTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransactions.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransactions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransactions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions
            .stockReq(UPDATED_STOCK_REQ)
            .stockProvided(UPDATED_STOCK_PROVIDED)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getStockReq()).isEqualTo(UPDATED_STOCK_REQ);
        assertThat(testTransactions.getStockProvided()).isEqualTo(UPDATED_STOCK_PROVIDED);
        assertThat(testTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransactions.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransactions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransactions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTransactionsWithPatch() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();

        // Update the transactions using partial update
        Transactions partialUpdatedTransactions = new Transactions();
        partialUpdatedTransactions.setId(transactions.getId());

        partialUpdatedTransactions
            .stockReq(UPDATED_STOCK_REQ)
            .stockProvided(UPDATED_STOCK_PROVIDED)
            .status(UPDATED_STATUS)
            .comment(UPDATED_COMMENT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransactions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransactions))
            )
            .andExpect(status().isOk());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
        Transactions testTransactions = transactionsList.get(transactionsList.size() - 1);
        assertThat(testTransactions.getStockReq()).isEqualTo(UPDATED_STOCK_REQ);
        assertThat(testTransactions.getStockProvided()).isEqualTo(UPDATED_STOCK_PROVIDED);
        assertThat(testTransactions.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransactions.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransactions.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransactions.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transactionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransactions() throws Exception {
        int databaseSizeBeforeUpdate = transactionsRepository.findAll().size();
        transactions.setId(count.incrementAndGet());

        // Create the Transactions
        TransactionsDTO transactionsDTO = transactionsMapper.toDto(transactions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransactionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transactionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transactions in the database
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransactions() throws Exception {
        // Initialize the database
        transactionsRepository.saveAndFlush(transactions);

        int databaseSizeBeforeDelete = transactionsRepository.findAll().size();

        // Delete the transactions
        restTransactionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, transactions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transactions> transactionsList = transactionsRepository.findAll();
        assertThat(transactionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
