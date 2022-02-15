package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.SecurityUser;
import com.techvg.covid.care.domain.UserAccess;
import com.techvg.covid.care.domain.enumeration.AccessLevel;
import com.techvg.covid.care.repository.UserAccessRepository;
import com.techvg.covid.care.service.criteria.UserAccessCriteria;
import com.techvg.covid.care.service.dto.UserAccessDTO;
import com.techvg.covid.care.service.mapper.UserAccessMapper;
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
 * Integration tests for the {@link UserAccessResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAccessResourceIT {

    private static final AccessLevel DEFAULT_LEVEL = AccessLevel.HOSPITAL;
    private static final AccessLevel UPDATED_LEVEL = AccessLevel.SUPPLIER;

    private static final Long DEFAULT_ACCESS_ID = 1L;
    private static final Long UPDATED_ACCESS_ID = 2L;
    private static final Long SMALLER_ACCESS_ID = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/user-accesses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserAccessRepository userAccessRepository;

    @Autowired
    private UserAccessMapper userAccessMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAccessMockMvc;

    private UserAccess userAccess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccess createEntity(EntityManager em) {
        UserAccess userAccess = new UserAccess()
            .level(DEFAULT_LEVEL)
            .accessId(DEFAULT_ACCESS_ID)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return userAccess;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAccess createUpdatedEntity(EntityManager em) {
        UserAccess userAccess = new UserAccess()
            .level(UPDATED_LEVEL)
            .accessId(UPDATED_ACCESS_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return userAccess;
    }

    @BeforeEach
    public void initTest() {
        userAccess = createEntity(em);
    }

    @Test
    @Transactional
    void createUserAccess() throws Exception {
        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();
        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);
        restUserAccessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate + 1);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testUserAccess.getAccessId()).isEqualTo(DEFAULT_ACCESS_ID);
        assertThat(testUserAccess.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testUserAccess.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createUserAccessWithExistingId() throws Exception {
        // Create the UserAccess with an existing ID
        userAccess.setId(1L);
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        int databaseSizeBeforeCreate = userAccessRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAccessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccessRepository.findAll().size();
        // set the field null
        userAccess.setLastModified(null);

        // Create the UserAccess, which fails.
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        restUserAccessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAccessRepository.findAll().size();
        // set the field null
        userAccess.setLastModifiedBy(null);

        // Create the UserAccess, which fails.
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        restUserAccessMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isBadRequest());

        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserAccesses() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].accessId").value(hasItem(DEFAULT_ACCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get the userAccess
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL_ID, userAccess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAccess.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.accessId").value(DEFAULT_ACCESS_ID.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getUserAccessesByIdFiltering() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        Long id = userAccess.getId();

        defaultUserAccessShouldBeFound("id.equals=" + id);
        defaultUserAccessShouldNotBeFound("id.notEquals=" + id);

        defaultUserAccessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserAccessShouldNotBeFound("id.greaterThan=" + id);

        defaultUserAccessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserAccessShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where level equals to DEFAULT_LEVEL
        defaultUserAccessShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the userAccessList where level equals to UPDATED_LEVEL
        defaultUserAccessShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where level not equals to DEFAULT_LEVEL
        defaultUserAccessShouldNotBeFound("level.notEquals=" + DEFAULT_LEVEL);

        // Get all the userAccessList where level not equals to UPDATED_LEVEL
        defaultUserAccessShouldBeFound("level.notEquals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultUserAccessShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the userAccessList where level equals to UPDATED_LEVEL
        defaultUserAccessShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where level is not null
        defaultUserAccessShouldBeFound("level.specified=true");

        // Get all the userAccessList where level is null
        defaultUserAccessShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId equals to DEFAULT_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.equals=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId equals to UPDATED_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.equals=" + UPDATED_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId not equals to DEFAULT_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.notEquals=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId not equals to UPDATED_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.notEquals=" + UPDATED_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsInShouldWork() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId in DEFAULT_ACCESS_ID or UPDATED_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.in=" + DEFAULT_ACCESS_ID + "," + UPDATED_ACCESS_ID);

        // Get all the userAccessList where accessId equals to UPDATED_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.in=" + UPDATED_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId is not null
        defaultUserAccessShouldBeFound("accessId.specified=true");

        // Get all the userAccessList where accessId is null
        defaultUserAccessShouldNotBeFound("accessId.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId is greater than or equal to DEFAULT_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.greaterThanOrEqual=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId is greater than or equal to UPDATED_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.greaterThanOrEqual=" + UPDATED_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId is less than or equal to DEFAULT_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.lessThanOrEqual=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId is less than or equal to SMALLER_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.lessThanOrEqual=" + SMALLER_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsLessThanSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId is less than DEFAULT_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.lessThan=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId is less than UPDATED_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.lessThan=" + UPDATED_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByAccessIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where accessId is greater than DEFAULT_ACCESS_ID
        defaultUserAccessShouldNotBeFound("accessId.greaterThan=" + DEFAULT_ACCESS_ID);

        // Get all the userAccessList where accessId is greater than SMALLER_ACCESS_ID
        defaultUserAccessShouldBeFound("accessId.greaterThan=" + SMALLER_ACCESS_ID);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultUserAccessShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the userAccessList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultUserAccessShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultUserAccessShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the userAccessList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultUserAccessShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultUserAccessShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the userAccessList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultUserAccessShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModified is not null
        defaultUserAccessShouldBeFound("lastModified.specified=true");

        // Get all the userAccessList where lastModified is null
        defaultUserAccessShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultUserAccessShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the userAccessList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultUserAccessShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the userAccessList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the userAccessList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy is not null
        defaultUserAccessShouldBeFound("lastModifiedBy.specified=true");

        // Get all the userAccessList where lastModifiedBy is null
        defaultUserAccessShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultUserAccessShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the userAccessList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUserAccessesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        // Get all the userAccessList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultUserAccessShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the userAccessList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultUserAccessShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllUserAccessesBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            securityUser = SecurityUserResourceIT.createEntity(em);
            em.persist(securityUser);
            em.flush();
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        userAccess.setSecurityUser(securityUser);
        userAccessRepository.saveAndFlush(userAccess);
        Long securityUserId = securityUser.getId();

        // Get all the userAccessList where securityUser equals to securityUserId
        defaultUserAccessShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the userAccessList where securityUser equals to (securityUserId + 1)
        defaultUserAccessShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserAccessShouldBeFound(String filter) throws Exception {
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAccess.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].accessId").value(hasItem(DEFAULT_ACCESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserAccessShouldNotBeFound(String filter) throws Exception {
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserAccessMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserAccess() throws Exception {
        // Get the userAccess
        restUserAccessMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Update the userAccess
        UserAccess updatedUserAccess = userAccessRepository.findById(userAccess.getId()).get();
        // Disconnect from session so that the updates on updatedUserAccess are not directly saved in db
        em.detach(updatedUserAccess);
        updatedUserAccess
            .level(UPDATED_LEVEL)
            .accessId(UPDATED_ACCESS_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(updatedUserAccess);

        restUserAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAccessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testUserAccess.getAccessId()).isEqualTo(UPDATED_ACCESS_ID);
        assertThat(testUserAccess.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testUserAccess.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAccessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAccessDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserAccessWithPatch() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Update the userAccess using partial update
        UserAccess partialUpdatedUserAccess = new UserAccess();
        partialUpdatedUserAccess.setId(userAccess.getId());

        partialUpdatedUserAccess.accessId(UPDATED_ACCESS_ID).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restUserAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccess))
            )
            .andExpect(status().isOk());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testUserAccess.getAccessId()).isEqualTo(UPDATED_ACCESS_ID);
        assertThat(testUserAccess.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testUserAccess.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateUserAccessWithPatch() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();

        // Update the userAccess using partial update
        UserAccess partialUpdatedUserAccess = new UserAccess();
        partialUpdatedUserAccess.setId(userAccess.getId());

        partialUpdatedUserAccess
            .level(UPDATED_LEVEL)
            .accessId(UPDATED_ACCESS_ID)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restUserAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAccess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAccess))
            )
            .andExpect(status().isOk());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
        UserAccess testUserAccess = userAccessList.get(userAccessList.size() - 1);
        assertThat(testUserAccess.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testUserAccess.getAccessId()).isEqualTo(UPDATED_ACCESS_ID);
        assertThat(testUserAccess.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testUserAccess.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAccessDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserAccess() throws Exception {
        int databaseSizeBeforeUpdate = userAccessRepository.findAll().size();
        userAccess.setId(count.incrementAndGet());

        // Create the UserAccess
        UserAccessDTO userAccessDTO = userAccessMapper.toDto(userAccess);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAccessMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userAccessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAccess in the database
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserAccess() throws Exception {
        // Initialize the database
        userAccessRepository.saveAndFlush(userAccess);

        int databaseSizeBeforeDelete = userAccessRepository.findAll().size();

        // Delete the userAccess
        restUserAccessMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAccess.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAccess> userAccessList = userAccessRepository.findAll();
        assertThat(userAccessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
