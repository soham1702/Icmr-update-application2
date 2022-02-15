package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.Contact;
import com.techvg.covid.care.domain.ContactType;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.repository.ContactRepository;
import com.techvg.covid.care.service.criteria.ContactCriteria;
import com.techvg.covid.care.service.dto.ContactDTO;
import com.techvg.covid.care.service.mapper.ContactMapper;
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
 * Integration tests for the {@link ContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .name(DEFAULT_NAME)
            .contactNo(DEFAULT_CONTACT_NO)
            .email(DEFAULT_EMAIL)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return contact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .name(UPDATED_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .email(UPDATED_EMAIL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContact.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testContact.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContact.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createContactWithExistingId() throws Exception {
        // Create the Contact with an existing ID
        contact.setId(1L);
        ContactDTO contactDTO = contactMapper.toDto(contact);

        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setName(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setLastModified(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setLastModifiedBy(null);

        // Create the Contact, which fails.
        ContactDTO contactDTO = contactMapper.toDto(contact);

        restContactMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isBadRequest());

        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc
            .perform(get(ENTITY_API_URL_ID, contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getContactsByIdFiltering() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        Long id = contact.getId();

        defaultContactShouldBeFound("id.equals=" + id);
        defaultContactShouldNotBeFound("id.notEquals=" + id);

        defaultContactShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContactShouldNotBeFound("id.greaterThan=" + id);

        defaultContactShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContactShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name equals to DEFAULT_NAME
        defaultContactShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the contactList where name equals to UPDATED_NAME
        defaultContactShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name not equals to DEFAULT_NAME
        defaultContactShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the contactList where name not equals to UPDATED_NAME
        defaultContactShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name in DEFAULT_NAME or UPDATED_NAME
        defaultContactShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the contactList where name equals to UPDATED_NAME
        defaultContactShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name is not null
        defaultContactShouldBeFound("name.specified=true");

        // Get all the contactList where name is null
        defaultContactShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByNameContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name contains DEFAULT_NAME
        defaultContactShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the contactList where name contains UPDATED_NAME
        defaultContactShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where name does not contain DEFAULT_NAME
        defaultContactShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the contactList where name does not contain UPDATED_NAME
        defaultContactShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllContactsByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo equals to DEFAULT_CONTACT_NO
        defaultContactShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the contactList where contactNo equals to UPDATED_CONTACT_NO
        defaultContactShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllContactsByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultContactShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the contactList where contactNo not equals to UPDATED_CONTACT_NO
        defaultContactShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllContactsByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultContactShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the contactList where contactNo equals to UPDATED_CONTACT_NO
        defaultContactShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllContactsByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo is not null
        defaultContactShouldBeFound("contactNo.specified=true");

        // Get all the contactList where contactNo is null
        defaultContactShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByContactNoContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo contains DEFAULT_CONTACT_NO
        defaultContactShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the contactList where contactNo contains UPDATED_CONTACT_NO
        defaultContactShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllContactsByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultContactShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the contactList where contactNo does not contain UPDATED_CONTACT_NO
        defaultContactShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email equals to DEFAULT_EMAIL
        defaultContactShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the contactList where email equals to UPDATED_EMAIL
        defaultContactShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email not equals to DEFAULT_EMAIL
        defaultContactShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the contactList where email not equals to UPDATED_EMAIL
        defaultContactShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultContactShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the contactList where email equals to UPDATED_EMAIL
        defaultContactShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email is not null
        defaultContactShouldBeFound("email.specified=true");

        // Get all the contactList where email is null
        defaultContactShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByEmailContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email contains DEFAULT_EMAIL
        defaultContactShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the contactList where email contains UPDATED_EMAIL
        defaultContactShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where email does not contain DEFAULT_EMAIL
        defaultContactShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the contactList where email does not contain UPDATED_EMAIL
        defaultContactShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultContactShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the contactList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultContactShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultContactShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the contactList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultContactShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultContactShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the contactList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultContactShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModified is not null
        defaultContactShouldBeFound("lastModified.specified=true");

        // Get all the contactList where lastModified is null
        defaultContactShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultContactShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultContactShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultContactShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultContactShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultContactShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the contactList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultContactShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy is not null
        defaultContactShouldBeFound("lastModifiedBy.specified=true");

        // Get all the contactList where lastModifiedBy is null
        defaultContactShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultContactShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultContactShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultContactShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the contactList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultContactShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllContactsByContactTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
        ContactType contactType;
        if (TestUtil.findAll(em, ContactType.class).isEmpty()) {
            contactType = ContactTypeResourceIT.createEntity(em);
            em.persist(contactType);
            em.flush();
        } else {
            contactType = TestUtil.findAll(em, ContactType.class).get(0);
        }
        em.persist(contactType);
        em.flush();
        contact.setContactType(contactType);
        contactRepository.saveAndFlush(contact);
        Long contactTypeId = contactType.getId();

        // Get all the contactList where contactType equals to contactTypeId
        defaultContactShouldBeFound("contactTypeId.equals=" + contactTypeId);

        // Get all the contactList where contactType equals to (contactTypeId + 1)
        defaultContactShouldNotBeFound("contactTypeId.equals=" + (contactTypeId + 1));
    }

    @Test
    @Transactional
    void getAllContactsByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
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
        contact.setHospital(hospital);
        contactRepository.saveAndFlush(contact);
        Long hospitalId = hospital.getId();

        // Get all the contactList where hospital equals to hospitalId
        defaultContactShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the contactList where hospital equals to (hospitalId + 1)
        defaultContactShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    @Test
    @Transactional
    void getAllContactsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
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
        contact.setSupplier(supplier);
        contactRepository.saveAndFlush(contact);
        Long supplierId = supplier.getId();

        // Get all the contactList where supplier equals to supplierId
        defaultContactShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the contactList where supplier equals to (supplierId + 1)
        defaultContactShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContactShouldBeFound(String filter) throws Exception {
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContactShouldNotBeFound(String filter) throws Exception {
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContactMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .name(UPDATED_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .email(UPDATED_EMAIL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ContactDTO contactDTO = contactMapper.toDto(updatedContact);

        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContact.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContact.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact.name(UPDATED_NAME).email(UPDATED_EMAIL).lastModified(UPDATED_LAST_MODIFIED);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContact.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContact.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateContactWithPatch() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact using partial update
        Contact partialUpdatedContact = new Contact();
        partialUpdatedContact.setId(contact.getId());

        partialUpdatedContact
            .name(UPDATED_NAME)
            .contactNo(UPDATED_CONTACT_NO)
            .email(UPDATED_EMAIL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContact.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testContact.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContact.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testContact.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();
        contact.setId(count.incrementAndGet());

        // Create the Contact
        ContactDTO contactDTO = contactMapper.toDto(contact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, contact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
