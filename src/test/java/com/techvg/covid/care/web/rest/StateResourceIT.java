package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.State;
import com.techvg.covid.care.repository.StateRepository;
import com.techvg.covid.care.service.criteria.StateCriteria;
import com.techvg.covid.care.service.dto.StateDTO;
import com.techvg.covid.care.service.mapper.StateMapper;
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
 * Integration tests for the {@link StateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final Long DEFAULT_LGD_CODE = 1L;
    private static final Long UPDATED_LGD_CODE = 2L;
    private static final Long SMALLER_LGD_CODE = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/states";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateMapper stateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateMockMvc;

    private State state;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createEntity(EntityManager em) {
        State state = new State()
            .name(DEFAULT_NAME)
            .deleted(DEFAULT_DELETED)
            .lgdCode(DEFAULT_LGD_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return state;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createUpdatedEntity(EntityManager em) {
        State state = new State()
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return state;
    }

    @BeforeEach
    public void initTest() {
        state = createEntity(em);
    }

    @Test
    @Transactional
    void createState() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();
        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);
        restStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isCreated());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate + 1);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testState.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testState.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testState.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testState.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createStateWithExistingId() throws Exception {
        // Create the State with an existing ID
        state.setId(1L);
        StateDTO stateDTO = stateMapper.toDto(state);

        int databaseSizeBeforeCreate = stateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setName(null);

        // Create the State, which fails.
        StateDTO stateDTO = stateMapper.toDto(state);

        restStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setLastModified(null);

        // Create the State, which fails.
        StateDTO stateDTO = stateMapper.toDto(state);

        restStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setLastModifiedBy(null);

        // Create the State, which fails.
        StateDTO stateDTO = stateMapper.toDto(state);

        restStateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllStates() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList
        restStateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get the state
        restStateMockMvc
            .perform(get(ENTITY_API_URL_ID, state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lgdCode").value(DEFAULT_LGD_CODE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getStatesByIdFiltering() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        Long id = state.getId();

        defaultStateShouldBeFound("id.equals=" + id);
        defaultStateShouldNotBeFound("id.notEquals=" + id);

        defaultStateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStateShouldNotBeFound("id.greaterThan=" + id);

        defaultStateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name equals to DEFAULT_NAME
        defaultStateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the stateList where name equals to UPDATED_NAME
        defaultStateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name not equals to DEFAULT_NAME
        defaultStateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the stateList where name not equals to UPDATED_NAME
        defaultStateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the stateList where name equals to UPDATED_NAME
        defaultStateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name is not null
        defaultStateShouldBeFound("name.specified=true");

        // Get all the stateList where name is null
        defaultStateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStatesByNameContainsSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name contains DEFAULT_NAME
        defaultStateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the stateList where name contains UPDATED_NAME
        defaultStateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where name does not contain DEFAULT_NAME
        defaultStateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the stateList where name does not contain UPDATED_NAME
        defaultStateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStatesByDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where deleted equals to DEFAULT_DELETED
        defaultStateShouldBeFound("deleted.equals=" + DEFAULT_DELETED);

        // Get all the stateList where deleted equals to UPDATED_DELETED
        defaultStateShouldNotBeFound("deleted.equals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllStatesByDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where deleted not equals to DEFAULT_DELETED
        defaultStateShouldNotBeFound("deleted.notEquals=" + DEFAULT_DELETED);

        // Get all the stateList where deleted not equals to UPDATED_DELETED
        defaultStateShouldBeFound("deleted.notEquals=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllStatesByDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where deleted in DEFAULT_DELETED or UPDATED_DELETED
        defaultStateShouldBeFound("deleted.in=" + DEFAULT_DELETED + "," + UPDATED_DELETED);

        // Get all the stateList where deleted equals to UPDATED_DELETED
        defaultStateShouldNotBeFound("deleted.in=" + UPDATED_DELETED);
    }

    @Test
    @Transactional
    void getAllStatesByDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where deleted is not null
        defaultStateShouldBeFound("deleted.specified=true");

        // Get all the stateList where deleted is null
        defaultStateShouldNotBeFound("deleted.specified=false");
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode equals to DEFAULT_LGD_CODE
        defaultStateShouldBeFound("lgdCode.equals=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode equals to UPDATED_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.equals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode not equals to DEFAULT_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.notEquals=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode not equals to UPDATED_LGD_CODE
        defaultStateShouldBeFound("lgdCode.notEquals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode in DEFAULT_LGD_CODE or UPDATED_LGD_CODE
        defaultStateShouldBeFound("lgdCode.in=" + DEFAULT_LGD_CODE + "," + UPDATED_LGD_CODE);

        // Get all the stateList where lgdCode equals to UPDATED_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.in=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode is not null
        defaultStateShouldBeFound("lgdCode.specified=true");

        // Get all the stateList where lgdCode is null
        defaultStateShouldNotBeFound("lgdCode.specified=false");
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode is greater than or equal to DEFAULT_LGD_CODE
        defaultStateShouldBeFound("lgdCode.greaterThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode is greater than or equal to UPDATED_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.greaterThanOrEqual=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode is less than or equal to DEFAULT_LGD_CODE
        defaultStateShouldBeFound("lgdCode.lessThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode is less than or equal to SMALLER_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.lessThanOrEqual=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode is less than DEFAULT_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.lessThan=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode is less than UPDATED_LGD_CODE
        defaultStateShouldBeFound("lgdCode.lessThan=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLgdCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lgdCode is greater than DEFAULT_LGD_CODE
        defaultStateShouldNotBeFound("lgdCode.greaterThan=" + DEFAULT_LGD_CODE);

        // Get all the stateList where lgdCode is greater than SMALLER_LGD_CODE
        defaultStateShouldBeFound("lgdCode.greaterThan=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultStateShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the stateList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultStateShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultStateShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the stateList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultStateShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultStateShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the stateList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultStateShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModified is not null
        defaultStateShouldBeFound("lastModified.specified=true");

        // Get all the stateList where lastModified is null
        defaultStateShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultStateShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the stateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStateShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultStateShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the stateList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultStateShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultStateShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the stateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStateShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy is not null
        defaultStateShouldBeFound("lastModifiedBy.specified=true");

        // Get all the stateList where lastModifiedBy is null
        defaultStateShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultStateShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the stateList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultStateShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStatesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultStateShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the stateList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultStateShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStateShouldBeFound(String filter) throws Exception {
        restStateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restStateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStateShouldNotBeFound(String filter) throws Exception {
        restStateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingState() throws Exception {
        // Get the state
        restStateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state
        State updatedState = stateRepository.findById(state.getId()).get();
        // Disconnect from session so that the updates on updatedState are not directly saved in db
        em.detach(updatedState);
        updatedState
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        StateDTO stateDTO = stateMapper.toDto(updatedState);

        restStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateDTO))
            )
            .andExpect(status().isOk());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testState.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testState.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testState.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testState.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, stateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(stateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStateWithPatch() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state using partial update
        State partialUpdatedState = new State();
        partialUpdatedState.setId(state.getId());

        partialUpdatedState.name(UPDATED_NAME).deleted(UPDATED_DELETED);

        restStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedState))
            )
            .andExpect(status().isOk());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testState.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testState.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testState.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testState.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateStateWithPatch() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state using partial update
        State partialUpdatedState = new State();
        partialUpdatedState.setId(state.getId());

        partialUpdatedState
            .name(UPDATED_NAME)
            .deleted(UPDATED_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedState.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedState))
            )
            .andExpect(status().isOk());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testState.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testState.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testState.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testState.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, stateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(stateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();
        state.setId(count.incrementAndGet());

        // Create the State
        StateDTO stateDTO = stateMapper.toDto(state);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(stateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        int databaseSizeBeforeDelete = stateRepository.findAll().size();

        // Delete the state
        restStateMockMvc
            .perform(delete(ENTITY_API_URL_ID, state.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
