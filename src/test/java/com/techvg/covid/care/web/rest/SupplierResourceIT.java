package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.City;
import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.InventoryType;
import com.techvg.covid.care.domain.State;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.domain.Taluka;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import com.techvg.covid.care.domain.enumeration.SupplierType;
import com.techvg.covid.care.repository.SupplierRepository;
import com.techvg.covid.care.service.criteria.SupplierCriteria;
import com.techvg.covid.care.service.dto.SupplierDTO;
import com.techvg.covid.care.service.mapper.SupplierMapper;
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
 * Integration tests for the {@link SupplierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplierResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final SupplierType DEFAULT_SUPPLIER_TYPE = SupplierType.REFILLER;
    private static final SupplierType UPDATED_SUPPLIER_TYPE = SupplierType.MANUFACTURER;

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final StatusInd DEFAULT_STATUS_IND = StatusInd.A;
    private static final StatusInd UPDATED_STATUS_IND = StatusInd.I;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/suppliers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierMockMvc;

    private Supplier supplier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .name(DEFAULT_NAME)
            .supplierType(DEFAULT_SUPPLIER_TYPE)
            .contactNo(DEFAULT_CONTACT_NO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .email(DEFAULT_EMAIL)
            .registrationNo(DEFAULT_REGISTRATION_NO)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .area(DEFAULT_AREA)
            .pinCode(DEFAULT_PIN_CODE)
            .statusInd(DEFAULT_STATUS_IND)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return supplier;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supplier createUpdatedEntity(EntityManager em) {
        Supplier supplier = new Supplier()
            .name(UPDATED_NAME)
            .supplierType(UPDATED_SUPPLIER_TYPE)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return supplier;
    }

    @BeforeEach
    public void initTest() {
        supplier = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplier() throws Exception {
        int databaseSizeBeforeCreate = supplierRepository.findAll().size();
        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);
        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isCreated());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate + 1);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplier.getSupplierType()).isEqualTo(DEFAULT_SUPPLIER_TYPE);
        assertThat(testSupplier.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testSupplier.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testSupplier.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testSupplier.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupplier.getRegistrationNo()).isEqualTo(DEFAULT_REGISTRATION_NO);
        assertThat(testSupplier.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testSupplier.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testSupplier.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testSupplier.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testSupplier.getStatusInd()).isEqualTo(DEFAULT_STATUS_IND);
        assertThat(testSupplier.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSupplier.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createSupplierWithExistingId() throws Exception {
        // Create the Supplier with an existing ID
        supplier.setId(1L);
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        int databaseSizeBeforeCreate = supplierRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setName(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSupplierTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setSupplierType(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPinCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setPinCode(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setLastModified(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierRepository.findAll().size();
        // set the field null
        supplier.setLastModifiedBy(null);

        // Create the Supplier, which fails.
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        restSupplierMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isBadRequest());

        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSuppliers() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierType").value(hasItem(DEFAULT_SUPPLIER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registrationNo").value(hasItem(DEFAULT_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].statusInd").value(hasItem(DEFAULT_STATUS_IND.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get the supplier
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL_ID, supplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplier.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.supplierType").value(DEFAULT_SUPPLIER_TYPE.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.registrationNo").value(DEFAULT_REGISTRATION_NO))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.statusInd").value(DEFAULT_STATUS_IND.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getSuppliersByIdFiltering() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        Long id = supplier.getId();

        defaultSupplierShouldBeFound("id.equals=" + id);
        defaultSupplierShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSuppliersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name equals to DEFAULT_NAME
        defaultSupplierShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the supplierList where name equals to UPDATED_NAME
        defaultSupplierShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name not equals to DEFAULT_NAME
        defaultSupplierShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the supplierList where name not equals to UPDATED_NAME
        defaultSupplierShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSupplierShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the supplierList where name equals to UPDATED_NAME
        defaultSupplierShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name is not null
        defaultSupplierShouldBeFound("name.specified=true");

        // Get all the supplierList where name is null
        defaultSupplierShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByNameContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name contains DEFAULT_NAME
        defaultSupplierShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the supplierList where name contains UPDATED_NAME
        defaultSupplierShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where name does not contain DEFAULT_NAME
        defaultSupplierShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the supplierList where name does not contain UPDATED_NAME
        defaultSupplierShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where supplierType equals to DEFAULT_SUPPLIER_TYPE
        defaultSupplierShouldBeFound("supplierType.equals=" + DEFAULT_SUPPLIER_TYPE);

        // Get all the supplierList where supplierType equals to UPDATED_SUPPLIER_TYPE
        defaultSupplierShouldNotBeFound("supplierType.equals=" + UPDATED_SUPPLIER_TYPE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where supplierType not equals to DEFAULT_SUPPLIER_TYPE
        defaultSupplierShouldNotBeFound("supplierType.notEquals=" + DEFAULT_SUPPLIER_TYPE);

        // Get all the supplierList where supplierType not equals to UPDATED_SUPPLIER_TYPE
        defaultSupplierShouldBeFound("supplierType.notEquals=" + UPDATED_SUPPLIER_TYPE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTypeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where supplierType in DEFAULT_SUPPLIER_TYPE or UPDATED_SUPPLIER_TYPE
        defaultSupplierShouldBeFound("supplierType.in=" + DEFAULT_SUPPLIER_TYPE + "," + UPDATED_SUPPLIER_TYPE);

        // Get all the supplierList where supplierType equals to UPDATED_SUPPLIER_TYPE
        defaultSupplierShouldNotBeFound("supplierType.in=" + UPDATED_SUPPLIER_TYPE);
    }

    @Test
    @Transactional
    void getAllSuppliersBySupplierTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where supplierType is not null
        defaultSupplierShouldBeFound("supplierType.specified=true");

        // Get all the supplierList where supplierType is null
        defaultSupplierShouldNotBeFound("supplierType.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo equals to DEFAULT_CONTACT_NO
        defaultSupplierShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the supplierList where contactNo equals to UPDATED_CONTACT_NO
        defaultSupplierShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultSupplierShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the supplierList where contactNo not equals to UPDATED_CONTACT_NO
        defaultSupplierShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultSupplierShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the supplierList where contactNo equals to UPDATED_CONTACT_NO
        defaultSupplierShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo is not null
        defaultSupplierShouldBeFound("contactNo.specified=true");

        // Get all the supplierList where contactNo is null
        defaultSupplierShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo contains DEFAULT_CONTACT_NO
        defaultSupplierShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the supplierList where contactNo contains UPDATED_CONTACT_NO
        defaultSupplierShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultSupplierShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the supplierList where contactNo does not contain UPDATED_CONTACT_NO
        defaultSupplierShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude equals to DEFAULT_LATITUDE
        defaultSupplierShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the supplierList where latitude equals to UPDATED_LATITUDE
        defaultSupplierShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude not equals to DEFAULT_LATITUDE
        defaultSupplierShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the supplierList where latitude not equals to UPDATED_LATITUDE
        defaultSupplierShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultSupplierShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the supplierList where latitude equals to UPDATED_LATITUDE
        defaultSupplierShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude is not null
        defaultSupplierShouldBeFound("latitude.specified=true");

        // Get all the supplierList where latitude is null
        defaultSupplierShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude contains DEFAULT_LATITUDE
        defaultSupplierShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the supplierList where latitude contains UPDATED_LATITUDE
        defaultSupplierShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where latitude does not contain DEFAULT_LATITUDE
        defaultSupplierShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the supplierList where latitude does not contain UPDATED_LATITUDE
        defaultSupplierShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude equals to DEFAULT_LONGITUDE
        defaultSupplierShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the supplierList where longitude equals to UPDATED_LONGITUDE
        defaultSupplierShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude not equals to DEFAULT_LONGITUDE
        defaultSupplierShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the supplierList where longitude not equals to UPDATED_LONGITUDE
        defaultSupplierShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultSupplierShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the supplierList where longitude equals to UPDATED_LONGITUDE
        defaultSupplierShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude is not null
        defaultSupplierShouldBeFound("longitude.specified=true");

        // Get all the supplierList where longitude is null
        defaultSupplierShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude contains DEFAULT_LONGITUDE
        defaultSupplierShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the supplierList where longitude contains UPDATED_LONGITUDE
        defaultSupplierShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where longitude does not contain DEFAULT_LONGITUDE
        defaultSupplierShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the supplierList where longitude does not contain UPDATED_LONGITUDE
        defaultSupplierShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email equals to DEFAULT_EMAIL
        defaultSupplierShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the supplierList where email equals to UPDATED_EMAIL
        defaultSupplierShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email not equals to DEFAULT_EMAIL
        defaultSupplierShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the supplierList where email not equals to UPDATED_EMAIL
        defaultSupplierShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultSupplierShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the supplierList where email equals to UPDATED_EMAIL
        defaultSupplierShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email is not null
        defaultSupplierShouldBeFound("email.specified=true");

        // Get all the supplierList where email is null
        defaultSupplierShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email contains DEFAULT_EMAIL
        defaultSupplierShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the supplierList where email contains UPDATED_EMAIL
        defaultSupplierShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where email does not contain DEFAULT_EMAIL
        defaultSupplierShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the supplierList where email does not contain UPDATED_EMAIL
        defaultSupplierShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo equals to DEFAULT_REGISTRATION_NO
        defaultSupplierShouldBeFound("registrationNo.equals=" + DEFAULT_REGISTRATION_NO);

        // Get all the supplierList where registrationNo equals to UPDATED_REGISTRATION_NO
        defaultSupplierShouldNotBeFound("registrationNo.equals=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo not equals to DEFAULT_REGISTRATION_NO
        defaultSupplierShouldNotBeFound("registrationNo.notEquals=" + DEFAULT_REGISTRATION_NO);

        // Get all the supplierList where registrationNo not equals to UPDATED_REGISTRATION_NO
        defaultSupplierShouldBeFound("registrationNo.notEquals=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo in DEFAULT_REGISTRATION_NO or UPDATED_REGISTRATION_NO
        defaultSupplierShouldBeFound("registrationNo.in=" + DEFAULT_REGISTRATION_NO + "," + UPDATED_REGISTRATION_NO);

        // Get all the supplierList where registrationNo equals to UPDATED_REGISTRATION_NO
        defaultSupplierShouldNotBeFound("registrationNo.in=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo is not null
        defaultSupplierShouldBeFound("registrationNo.specified=true");

        // Get all the supplierList where registrationNo is null
        defaultSupplierShouldNotBeFound("registrationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo contains DEFAULT_REGISTRATION_NO
        defaultSupplierShouldBeFound("registrationNo.contains=" + DEFAULT_REGISTRATION_NO);

        // Get all the supplierList where registrationNo contains UPDATED_REGISTRATION_NO
        defaultSupplierShouldNotBeFound("registrationNo.contains=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByRegistrationNoNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where registrationNo does not contain DEFAULT_REGISTRATION_NO
        defaultSupplierShouldNotBeFound("registrationNo.doesNotContain=" + DEFAULT_REGISTRATION_NO);

        // Get all the supplierList where registrationNo does not contain UPDATED_REGISTRATION_NO
        defaultSupplierShouldBeFound("registrationNo.doesNotContain=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 equals to DEFAULT_ADDRESS_1
        defaultSupplierShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the supplierList where address1 equals to UPDATED_ADDRESS_1
        defaultSupplierShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 not equals to DEFAULT_ADDRESS_1
        defaultSupplierShouldNotBeFound("address1.notEquals=" + DEFAULT_ADDRESS_1);

        // Get all the supplierList where address1 not equals to UPDATED_ADDRESS_1
        defaultSupplierShouldBeFound("address1.notEquals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultSupplierShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the supplierList where address1 equals to UPDATED_ADDRESS_1
        defaultSupplierShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 is not null
        defaultSupplierShouldBeFound("address1.specified=true");

        // Get all the supplierList where address1 is null
        defaultSupplierShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 contains DEFAULT_ADDRESS_1
        defaultSupplierShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the supplierList where address1 contains UPDATED_ADDRESS_1
        defaultSupplierShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address1 does not contain DEFAULT_ADDRESS_1
        defaultSupplierShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the supplierList where address1 does not contain UPDATED_ADDRESS_1
        defaultSupplierShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 equals to DEFAULT_ADDRESS_2
        defaultSupplierShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the supplierList where address2 equals to UPDATED_ADDRESS_2
        defaultSupplierShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 not equals to DEFAULT_ADDRESS_2
        defaultSupplierShouldNotBeFound("address2.notEquals=" + DEFAULT_ADDRESS_2);

        // Get all the supplierList where address2 not equals to UPDATED_ADDRESS_2
        defaultSupplierShouldBeFound("address2.notEquals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultSupplierShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the supplierList where address2 equals to UPDATED_ADDRESS_2
        defaultSupplierShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 is not null
        defaultSupplierShouldBeFound("address2.specified=true");

        // Get all the supplierList where address2 is null
        defaultSupplierShouldNotBeFound("address2.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 contains DEFAULT_ADDRESS_2
        defaultSupplierShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the supplierList where address2 contains UPDATED_ADDRESS_2
        defaultSupplierShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllSuppliersByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where address2 does not contain DEFAULT_ADDRESS_2
        defaultSupplierShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the supplierList where address2 does not contain UPDATED_ADDRESS_2
        defaultSupplierShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area equals to DEFAULT_AREA
        defaultSupplierShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the supplierList where area equals to UPDATED_AREA
        defaultSupplierShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area not equals to DEFAULT_AREA
        defaultSupplierShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the supplierList where area not equals to UPDATED_AREA
        defaultSupplierShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area in DEFAULT_AREA or UPDATED_AREA
        defaultSupplierShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the supplierList where area equals to UPDATED_AREA
        defaultSupplierShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area is not null
        defaultSupplierShouldBeFound("area.specified=true");

        // Get all the supplierList where area is null
        defaultSupplierShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area contains DEFAULT_AREA
        defaultSupplierShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the supplierList where area contains UPDATED_AREA
        defaultSupplierShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSuppliersByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where area does not contain DEFAULT_AREA
        defaultSupplierShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the supplierList where area does not contain UPDATED_AREA
        defaultSupplierShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode equals to DEFAULT_PIN_CODE
        defaultSupplierShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the supplierList where pinCode equals to UPDATED_PIN_CODE
        defaultSupplierShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode not equals to DEFAULT_PIN_CODE
        defaultSupplierShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the supplierList where pinCode not equals to UPDATED_PIN_CODE
        defaultSupplierShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultSupplierShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the supplierList where pinCode equals to UPDATED_PIN_CODE
        defaultSupplierShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode is not null
        defaultSupplierShouldBeFound("pinCode.specified=true");

        // Get all the supplierList where pinCode is null
        defaultSupplierShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode contains DEFAULT_PIN_CODE
        defaultSupplierShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the supplierList where pinCode contains UPDATED_PIN_CODE
        defaultSupplierShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where pinCode does not contain DEFAULT_PIN_CODE
        defaultSupplierShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the supplierList where pinCode does not contain UPDATED_PIN_CODE
        defaultSupplierShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllSuppliersByStatusIndIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where statusInd equals to DEFAULT_STATUS_IND
        defaultSupplierShouldBeFound("statusInd.equals=" + DEFAULT_STATUS_IND);

        // Get all the supplierList where statusInd equals to UPDATED_STATUS_IND
        defaultSupplierShouldNotBeFound("statusInd.equals=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllSuppliersByStatusIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where statusInd not equals to DEFAULT_STATUS_IND
        defaultSupplierShouldNotBeFound("statusInd.notEquals=" + DEFAULT_STATUS_IND);

        // Get all the supplierList where statusInd not equals to UPDATED_STATUS_IND
        defaultSupplierShouldBeFound("statusInd.notEquals=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllSuppliersByStatusIndIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where statusInd in DEFAULT_STATUS_IND or UPDATED_STATUS_IND
        defaultSupplierShouldBeFound("statusInd.in=" + DEFAULT_STATUS_IND + "," + UPDATED_STATUS_IND);

        // Get all the supplierList where statusInd equals to UPDATED_STATUS_IND
        defaultSupplierShouldNotBeFound("statusInd.in=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllSuppliersByStatusIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where statusInd is not null
        defaultSupplierShouldBeFound("statusInd.specified=true");

        // Get all the supplierList where statusInd is null
        defaultSupplierShouldNotBeFound("statusInd.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSupplierShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the supplierList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSupplierShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultSupplierShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the supplierList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultSupplierShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSupplierShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the supplierList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSupplierShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModified is not null
        defaultSupplierShouldBeFound("lastModified.specified=true");

        // Get all the supplierList where lastModified is null
        defaultSupplierShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSupplierShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the supplierList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultSupplierShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the supplierList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the supplierList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy is not null
        defaultSupplierShouldBeFound("lastModifiedBy.specified=true");

        // Get all the supplierList where lastModifiedBy is null
        defaultSupplierShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSupplierShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the supplierList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSuppliersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        // Get all the supplierList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSupplierShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the supplierList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSupplierShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSuppliersByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
        State state;
        if (TestUtil.findAll(em, State.class).isEmpty()) {
            state = StateResourceIT.createEntity(em);
            em.persist(state);
            em.flush();
        } else {
            state = TestUtil.findAll(em, State.class).get(0);
        }
        em.persist(state);
        em.flush();
        supplier.setState(state);
        supplierRepository.saveAndFlush(supplier);
        Long stateId = state.getId();

        // Get all the supplierList where state equals to stateId
        defaultSupplierShouldBeFound("stateId.equals=" + stateId);

        // Get all the supplierList where state equals to (stateId + 1)
        defaultSupplierShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            district = DistrictResourceIT.createEntity(em);
            em.persist(district);
            em.flush();
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        supplier.setDistrict(district);
        supplierRepository.saveAndFlush(supplier);
        Long districtId = district.getId();

        // Get all the supplierList where district equals to districtId
        defaultSupplierShouldBeFound("districtId.equals=" + districtId);

        // Get all the supplierList where district equals to (districtId + 1)
        defaultSupplierShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByTalukaIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
        Taluka taluka;
        if (TestUtil.findAll(em, Taluka.class).isEmpty()) {
            taluka = TalukaResourceIT.createEntity(em);
            em.persist(taluka);
            em.flush();
        } else {
            taluka = TestUtil.findAll(em, Taluka.class).get(0);
        }
        em.persist(taluka);
        em.flush();
        supplier.setTaluka(taluka);
        supplierRepository.saveAndFlush(supplier);
        Long talukaId = taluka.getId();

        // Get all the supplierList where taluka equals to talukaId
        defaultSupplierShouldBeFound("talukaId.equals=" + talukaId);

        // Get all the supplierList where taluka equals to (talukaId + 1)
        defaultSupplierShouldNotBeFound("talukaId.equals=" + (talukaId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            city = CityResourceIT.createEntity(em);
            em.persist(city);
            em.flush();
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        supplier.setCity(city);
        supplierRepository.saveAndFlush(supplier);
        Long cityId = city.getId();

        // Get all the supplierList where city equals to cityId
        defaultSupplierShouldBeFound("cityId.equals=" + cityId);

        // Get all the supplierList where city equals to (cityId + 1)
        defaultSupplierShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByInventoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
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
        supplier.setInventoryType(inventoryType);
        supplierRepository.saveAndFlush(supplier);
        Long inventoryTypeId = inventoryType.getId();

        // Get all the supplierList where inventoryType equals to inventoryTypeId
        defaultSupplierShouldBeFound("inventoryTypeId.equals=" + inventoryTypeId);

        // Get all the supplierList where inventoryType equals to (inventoryTypeId + 1)
        defaultSupplierShouldNotBeFound("inventoryTypeId.equals=" + (inventoryTypeId + 1));
    }

    @Test
    @Transactional
    void getAllSuppliersByHospitalIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);
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
        supplier.addHospital(hospital);
        supplierRepository.saveAndFlush(supplier);
        Long hospitalId = hospital.getId();

        // Get all the supplierList where hospital equals to hospitalId
        defaultSupplierShouldBeFound("hospitalId.equals=" + hospitalId);

        // Get all the supplierList where hospital equals to (hospitalId + 1)
        defaultSupplierShouldNotBeFound("hospitalId.equals=" + (hospitalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierShouldBeFound(String filter) throws Exception {
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].supplierType").value(hasItem(DEFAULT_SUPPLIER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].registrationNo").value(hasItem(DEFAULT_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].statusInd").value(hasItem(DEFAULT_STATUS_IND.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierShouldNotBeFound(String filter) throws Exception {
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSupplier() throws Exception {
        // Get the supplier
        restSupplierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier
        Supplier updatedSupplier = supplierRepository.findById(supplier.getId()).get();
        // Disconnect from session so that the updates on updatedSupplier are not directly saved in db
        em.detach(updatedSupplier);
        updatedSupplier
            .name(UPDATED_NAME)
            .supplierType(UPDATED_SUPPLIER_TYPE)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        SupplierDTO supplierDTO = supplierMapper.toDto(updatedSupplier);

        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplier.getSupplierType()).isEqualTo(UPDATED_SUPPLIER_TYPE);
        assertThat(testSupplier.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testSupplier.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSupplier.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSupplier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupplier.getRegistrationNo()).isEqualTo(UPDATED_REGISTRATION_NO);
        assertThat(testSupplier.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testSupplier.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testSupplier.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testSupplier.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testSupplier.getStatusInd()).isEqualTo(UPDATED_STATUS_IND);
        assertThat(testSupplier.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSupplier.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .supplierType(UPDATED_SUPPLIER_TYPE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .pinCode(UPDATED_PIN_CODE);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplier.getSupplierType()).isEqualTo(UPDATED_SUPPLIER_TYPE);
        assertThat(testSupplier.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testSupplier.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSupplier.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSupplier.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupplier.getRegistrationNo()).isEqualTo(UPDATED_REGISTRATION_NO);
        assertThat(testSupplier.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testSupplier.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testSupplier.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testSupplier.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testSupplier.getStatusInd()).isEqualTo(DEFAULT_STATUS_IND);
        assertThat(testSupplier.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSupplier.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateSupplierWithPatch() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();

        // Update the supplier using partial update
        Supplier partialUpdatedSupplier = new Supplier();
        partialUpdatedSupplier.setId(supplier.getId());

        partialUpdatedSupplier
            .name(UPDATED_NAME)
            .supplierType(UPDATED_SUPPLIER_TYPE)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .email(UPDATED_EMAIL)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplier.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplier))
            )
            .andExpect(status().isOk());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
        Supplier testSupplier = supplierList.get(supplierList.size() - 1);
        assertThat(testSupplier.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplier.getSupplierType()).isEqualTo(UPDATED_SUPPLIER_TYPE);
        assertThat(testSupplier.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testSupplier.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testSupplier.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testSupplier.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupplier.getRegistrationNo()).isEqualTo(UPDATED_REGISTRATION_NO);
        assertThat(testSupplier.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testSupplier.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testSupplier.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testSupplier.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testSupplier.getStatusInd()).isEqualTo(UPDATED_STATUS_IND);
        assertThat(testSupplier.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSupplier.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplier() throws Exception {
        int databaseSizeBeforeUpdate = supplierRepository.findAll().size();
        supplier.setId(count.incrementAndGet());

        // Create the Supplier
        SupplierDTO supplierDTO = supplierMapper.toDto(supplier);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(supplierDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Supplier in the database
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplier() throws Exception {
        // Initialize the database
        supplierRepository.saveAndFlush(supplier);

        int databaseSizeBeforeDelete = supplierRepository.findAll().size();

        // Delete the supplier
        restSupplierMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplier.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supplier> supplierList = supplierRepository.findAll();
        assertThat(supplierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
