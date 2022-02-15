package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.City;
import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.domain.Hospital;
import com.techvg.covid.care.domain.HospitalType;
import com.techvg.covid.care.domain.MunicipalCorp;
import com.techvg.covid.care.domain.State;
import com.techvg.covid.care.domain.Supplier;
import com.techvg.covid.care.domain.Taluka;
import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import com.techvg.covid.care.repository.HospitalRepository;
import com.techvg.covid.care.service.HospitalService;
import com.techvg.covid.care.service.criteria.HospitalCriteria;
import com.techvg.covid.care.service.dto.HospitalDTO;
import com.techvg.covid.care.service.mapper.HospitalMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HospitalResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class HospitalResourceIT {

    private static final HospitalCategory DEFAULT_CATEGORY = HospitalCategory.CENTRAL;
    private static final HospitalCategory UPDATED_CATEGORY = HospitalCategory.GOVT;

    private static final HospitalSubCategory DEFAULT_SUB_CATEGORY = HospitalSubCategory.FREE;
    private static final HospitalSubCategory UPDATED_SUB_CATEGORY = HospitalSubCategory.MPJAY;

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DOC_COUNT = 1;
    private static final Integer UPDATED_DOC_COUNT = 2;
    private static final Integer SMALLER_DOC_COUNT = 1 - 1;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final Long DEFAULT_HOSPITAL_ID = 1L;
    private static final Long UPDATED_HOSPITAL_ID = 2L;
    private static final Long SMALLER_HOSPITAL_ID = 1L - 1L;

    private static final String DEFAULT_ODAS_FACILITY_ID = "AAAAAAAAAA";
    private static final String UPDATED_ODAS_FACILITY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    private static final StatusInd DEFAULT_STATUS_IND = StatusInd.A;
    private static final StatusInd UPDATED_STATUS_IND = StatusInd.I;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/hospitals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HospitalRepository hospitalRepository;

    @Mock
    private HospitalRepository hospitalRepositoryMock;

    @Autowired
    private HospitalMapper hospitalMapper;

    @Mock
    private HospitalService hospitalServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHospitalMockMvc;

    private Hospital hospital;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .category(DEFAULT_CATEGORY)
            .subCategory(DEFAULT_SUB_CATEGORY)
            .contactNo(DEFAULT_CONTACT_NO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .docCount(DEFAULT_DOC_COUNT)
            .email(DEFAULT_EMAIL)
            .name(DEFAULT_NAME)
            .registrationNo(DEFAULT_REGISTRATION_NO)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .area(DEFAULT_AREA)
            .pinCode(DEFAULT_PIN_CODE)
            .hospitalId(DEFAULT_HOSPITAL_ID)
            .odasFacilityId(DEFAULT_ODAS_FACILITY_ID)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER)
            .statusInd(DEFAULT_STATUS_IND)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return hospital;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Hospital createUpdatedEntity(EntityManager em) {
        Hospital hospital = new Hospital()
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .docCount(UPDATED_DOC_COUNT)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .odasFacilityId(UPDATED_ODAS_FACILITY_ID)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return hospital;
    }

    @BeforeEach
    public void initTest() {
        hospital = createEntity(em);
    }

    @Test
    @Transactional
    void createHospital() throws Exception {
        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();
        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);
        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isCreated());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate + 1);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testHospital.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testHospital.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testHospital.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testHospital.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testHospital.getDocCount()).isEqualTo(DEFAULT_DOC_COUNT);
        assertThat(testHospital.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testHospital.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHospital.getRegistrationNo()).isEqualTo(DEFAULT_REGISTRATION_NO);
        assertThat(testHospital.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testHospital.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testHospital.getArea()).isEqualTo(DEFAULT_AREA);
        assertThat(testHospital.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testHospital.getHospitalId()).isEqualTo(DEFAULT_HOSPITAL_ID);
        assertThat(testHospital.getOdasFacilityId()).isEqualTo(DEFAULT_ODAS_FACILITY_ID);
        assertThat(testHospital.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
        assertThat(testHospital.getStatusInd()).isEqualTo(DEFAULT_STATUS_IND);
        assertThat(testHospital.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createHospitalWithExistingId() throws Exception {
        // Create the Hospital with an existing ID
        hospital.setId(1L);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        int databaseSizeBeforeCreate = hospitalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setCategory(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSubCategoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setSubCategory(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setName(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPinCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setPinCode(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setLastModified(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = hospitalRepository.findAll().size();
        // set the field null
        hospital.setLastModifiedBy(null);

        // Create the Hospital, which fails.
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        restHospitalMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isBadRequest());

        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHospitals() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].docCount").value(hasItem(DEFAULT_DOC_COUNT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].registrationNo").value(hasItem(DEFAULT_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].hospitalId").value(hasItem(DEFAULT_HOSPITAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].odasFacilityId").value(hasItem(DEFAULT_ODAS_FACILITY_ID)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].statusInd").value(hasItem(DEFAULT_STATUS_IND.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHospitalsWithEagerRelationshipsIsEnabled() throws Exception {
        when(hospitalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHospitalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hospitalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllHospitalsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(hospitalServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restHospitalMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(hospitalServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get the hospital
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL_ID, hospital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hospital.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.subCategory").value(DEFAULT_SUB_CATEGORY.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.docCount").value(DEFAULT_DOC_COUNT))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.registrationNo").value(DEFAULT_REGISTRATION_NO))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE))
            .andExpect(jsonPath("$.hospitalId").value(DEFAULT_HOSPITAL_ID.intValue()))
            .andExpect(jsonPath("$.odasFacilityId").value(DEFAULT_ODAS_FACILITY_ID))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER))
            .andExpect(jsonPath("$.statusInd").value(DEFAULT_STATUS_IND.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getHospitalsByIdFiltering() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        Long id = hospital.getId();

        defaultHospitalShouldBeFound("id.equals=" + id);
        defaultHospitalShouldNotBeFound("id.notEquals=" + id);

        defaultHospitalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultHospitalShouldNotBeFound("id.greaterThan=" + id);

        defaultHospitalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultHospitalShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllHospitalsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where category equals to DEFAULT_CATEGORY
        defaultHospitalShouldBeFound("category.equals=" + DEFAULT_CATEGORY);

        // Get all the hospitalList where category equals to UPDATED_CATEGORY
        defaultHospitalShouldNotBeFound("category.equals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsByCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where category not equals to DEFAULT_CATEGORY
        defaultHospitalShouldNotBeFound("category.notEquals=" + DEFAULT_CATEGORY);

        // Get all the hospitalList where category not equals to UPDATED_CATEGORY
        defaultHospitalShouldBeFound("category.notEquals=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsByCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where category in DEFAULT_CATEGORY or UPDATED_CATEGORY
        defaultHospitalShouldBeFound("category.in=" + DEFAULT_CATEGORY + "," + UPDATED_CATEGORY);

        // Get all the hospitalList where category equals to UPDATED_CATEGORY
        defaultHospitalShouldNotBeFound("category.in=" + UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsByCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where category is not null
        defaultHospitalShouldBeFound("category.specified=true");

        // Get all the hospitalList where category is null
        defaultHospitalShouldNotBeFound("category.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsBySubCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where subCategory equals to DEFAULT_SUB_CATEGORY
        defaultHospitalShouldBeFound("subCategory.equals=" + DEFAULT_SUB_CATEGORY);

        // Get all the hospitalList where subCategory equals to UPDATED_SUB_CATEGORY
        defaultHospitalShouldNotBeFound("subCategory.equals=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsBySubCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where subCategory not equals to DEFAULT_SUB_CATEGORY
        defaultHospitalShouldNotBeFound("subCategory.notEquals=" + DEFAULT_SUB_CATEGORY);

        // Get all the hospitalList where subCategory not equals to UPDATED_SUB_CATEGORY
        defaultHospitalShouldBeFound("subCategory.notEquals=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsBySubCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where subCategory in DEFAULT_SUB_CATEGORY or UPDATED_SUB_CATEGORY
        defaultHospitalShouldBeFound("subCategory.in=" + DEFAULT_SUB_CATEGORY + "," + UPDATED_SUB_CATEGORY);

        // Get all the hospitalList where subCategory equals to UPDATED_SUB_CATEGORY
        defaultHospitalShouldNotBeFound("subCategory.in=" + UPDATED_SUB_CATEGORY);
    }

    @Test
    @Transactional
    void getAllHospitalsBySubCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where subCategory is not null
        defaultHospitalShouldBeFound("subCategory.specified=true");

        // Get all the hospitalList where subCategory is null
        defaultHospitalShouldNotBeFound("subCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo equals to DEFAULT_CONTACT_NO
        defaultHospitalShouldBeFound("contactNo.equals=" + DEFAULT_CONTACT_NO);

        // Get all the hospitalList where contactNo equals to UPDATED_CONTACT_NO
        defaultHospitalShouldNotBeFound("contactNo.equals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo not equals to DEFAULT_CONTACT_NO
        defaultHospitalShouldNotBeFound("contactNo.notEquals=" + DEFAULT_CONTACT_NO);

        // Get all the hospitalList where contactNo not equals to UPDATED_CONTACT_NO
        defaultHospitalShouldBeFound("contactNo.notEquals=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo in DEFAULT_CONTACT_NO or UPDATED_CONTACT_NO
        defaultHospitalShouldBeFound("contactNo.in=" + DEFAULT_CONTACT_NO + "," + UPDATED_CONTACT_NO);

        // Get all the hospitalList where contactNo equals to UPDATED_CONTACT_NO
        defaultHospitalShouldNotBeFound("contactNo.in=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo is not null
        defaultHospitalShouldBeFound("contactNo.specified=true");

        // Get all the hospitalList where contactNo is null
        defaultHospitalShouldNotBeFound("contactNo.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo contains DEFAULT_CONTACT_NO
        defaultHospitalShouldBeFound("contactNo.contains=" + DEFAULT_CONTACT_NO);

        // Get all the hospitalList where contactNo contains UPDATED_CONTACT_NO
        defaultHospitalShouldNotBeFound("contactNo.contains=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByContactNoNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where contactNo does not contain DEFAULT_CONTACT_NO
        defaultHospitalShouldNotBeFound("contactNo.doesNotContain=" + DEFAULT_CONTACT_NO);

        // Get all the hospitalList where contactNo does not contain UPDATED_CONTACT_NO
        defaultHospitalShouldBeFound("contactNo.doesNotContain=" + UPDATED_CONTACT_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude equals to DEFAULT_LATITUDE
        defaultHospitalShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the hospitalList where latitude equals to UPDATED_LATITUDE
        defaultHospitalShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude not equals to DEFAULT_LATITUDE
        defaultHospitalShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the hospitalList where latitude not equals to UPDATED_LATITUDE
        defaultHospitalShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultHospitalShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the hospitalList where latitude equals to UPDATED_LATITUDE
        defaultHospitalShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude is not null
        defaultHospitalShouldBeFound("latitude.specified=true");

        // Get all the hospitalList where latitude is null
        defaultHospitalShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude contains DEFAULT_LATITUDE
        defaultHospitalShouldBeFound("latitude.contains=" + DEFAULT_LATITUDE);

        // Get all the hospitalList where latitude contains UPDATED_LATITUDE
        defaultHospitalShouldNotBeFound("latitude.contains=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLatitudeNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where latitude does not contain DEFAULT_LATITUDE
        defaultHospitalShouldNotBeFound("latitude.doesNotContain=" + DEFAULT_LATITUDE);

        // Get all the hospitalList where latitude does not contain UPDATED_LATITUDE
        defaultHospitalShouldBeFound("latitude.doesNotContain=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude equals to DEFAULT_LONGITUDE
        defaultHospitalShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the hospitalList where longitude equals to UPDATED_LONGITUDE
        defaultHospitalShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude not equals to DEFAULT_LONGITUDE
        defaultHospitalShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the hospitalList where longitude not equals to UPDATED_LONGITUDE
        defaultHospitalShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultHospitalShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the hospitalList where longitude equals to UPDATED_LONGITUDE
        defaultHospitalShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude is not null
        defaultHospitalShouldBeFound("longitude.specified=true");

        // Get all the hospitalList where longitude is null
        defaultHospitalShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude contains DEFAULT_LONGITUDE
        defaultHospitalShouldBeFound("longitude.contains=" + DEFAULT_LONGITUDE);

        // Get all the hospitalList where longitude contains UPDATED_LONGITUDE
        defaultHospitalShouldNotBeFound("longitude.contains=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByLongitudeNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where longitude does not contain DEFAULT_LONGITUDE
        defaultHospitalShouldNotBeFound("longitude.doesNotContain=" + DEFAULT_LONGITUDE);

        // Get all the hospitalList where longitude does not contain UPDATED_LONGITUDE
        defaultHospitalShouldBeFound("longitude.doesNotContain=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount equals to DEFAULT_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.equals=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount equals to UPDATED_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.equals=" + UPDATED_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount not equals to DEFAULT_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.notEquals=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount not equals to UPDATED_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.notEquals=" + UPDATED_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount in DEFAULT_DOC_COUNT or UPDATED_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.in=" + DEFAULT_DOC_COUNT + "," + UPDATED_DOC_COUNT);

        // Get all the hospitalList where docCount equals to UPDATED_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.in=" + UPDATED_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount is not null
        defaultHospitalShouldBeFound("docCount.specified=true");

        // Get all the hospitalList where docCount is null
        defaultHospitalShouldNotBeFound("docCount.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount is greater than or equal to DEFAULT_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.greaterThanOrEqual=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount is greater than or equal to UPDATED_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.greaterThanOrEqual=" + UPDATED_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount is less than or equal to DEFAULT_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.lessThanOrEqual=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount is less than or equal to SMALLER_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.lessThanOrEqual=" + SMALLER_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsLessThanSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount is less than DEFAULT_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.lessThan=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount is less than UPDATED_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.lessThan=" + UPDATED_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByDocCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where docCount is greater than DEFAULT_DOC_COUNT
        defaultHospitalShouldNotBeFound("docCount.greaterThan=" + DEFAULT_DOC_COUNT);

        // Get all the hospitalList where docCount is greater than SMALLER_DOC_COUNT
        defaultHospitalShouldBeFound("docCount.greaterThan=" + SMALLER_DOC_COUNT);
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email equals to DEFAULT_EMAIL
        defaultHospitalShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the hospitalList where email equals to UPDATED_EMAIL
        defaultHospitalShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email not equals to DEFAULT_EMAIL
        defaultHospitalShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the hospitalList where email not equals to UPDATED_EMAIL
        defaultHospitalShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultHospitalShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the hospitalList where email equals to UPDATED_EMAIL
        defaultHospitalShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email is not null
        defaultHospitalShouldBeFound("email.specified=true");

        // Get all the hospitalList where email is null
        defaultHospitalShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email contains DEFAULT_EMAIL
        defaultHospitalShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the hospitalList where email contains UPDATED_EMAIL
        defaultHospitalShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllHospitalsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where email does not contain DEFAULT_EMAIL
        defaultHospitalShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the hospitalList where email does not contain UPDATED_EMAIL
        defaultHospitalShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllHospitalsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name equals to DEFAULT_NAME
        defaultHospitalShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the hospitalList where name equals to UPDATED_NAME
        defaultHospitalShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllHospitalsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name not equals to DEFAULT_NAME
        defaultHospitalShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the hospitalList where name not equals to UPDATED_NAME
        defaultHospitalShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllHospitalsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name in DEFAULT_NAME or UPDATED_NAME
        defaultHospitalShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the hospitalList where name equals to UPDATED_NAME
        defaultHospitalShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllHospitalsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name is not null
        defaultHospitalShouldBeFound("name.specified=true");

        // Get all the hospitalList where name is null
        defaultHospitalShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByNameContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name contains DEFAULT_NAME
        defaultHospitalShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the hospitalList where name contains UPDATED_NAME
        defaultHospitalShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllHospitalsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where name does not contain DEFAULT_NAME
        defaultHospitalShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the hospitalList where name does not contain UPDATED_NAME
        defaultHospitalShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo equals to DEFAULT_REGISTRATION_NO
        defaultHospitalShouldBeFound("registrationNo.equals=" + DEFAULT_REGISTRATION_NO);

        // Get all the hospitalList where registrationNo equals to UPDATED_REGISTRATION_NO
        defaultHospitalShouldNotBeFound("registrationNo.equals=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo not equals to DEFAULT_REGISTRATION_NO
        defaultHospitalShouldNotBeFound("registrationNo.notEquals=" + DEFAULT_REGISTRATION_NO);

        // Get all the hospitalList where registrationNo not equals to UPDATED_REGISTRATION_NO
        defaultHospitalShouldBeFound("registrationNo.notEquals=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo in DEFAULT_REGISTRATION_NO or UPDATED_REGISTRATION_NO
        defaultHospitalShouldBeFound("registrationNo.in=" + DEFAULT_REGISTRATION_NO + "," + UPDATED_REGISTRATION_NO);

        // Get all the hospitalList where registrationNo equals to UPDATED_REGISTRATION_NO
        defaultHospitalShouldNotBeFound("registrationNo.in=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo is not null
        defaultHospitalShouldBeFound("registrationNo.specified=true");

        // Get all the hospitalList where registrationNo is null
        defaultHospitalShouldNotBeFound("registrationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo contains DEFAULT_REGISTRATION_NO
        defaultHospitalShouldBeFound("registrationNo.contains=" + DEFAULT_REGISTRATION_NO);

        // Get all the hospitalList where registrationNo contains UPDATED_REGISTRATION_NO
        defaultHospitalShouldNotBeFound("registrationNo.contains=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByRegistrationNoNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where registrationNo does not contain DEFAULT_REGISTRATION_NO
        defaultHospitalShouldNotBeFound("registrationNo.doesNotContain=" + DEFAULT_REGISTRATION_NO);

        // Get all the hospitalList where registrationNo does not contain UPDATED_REGISTRATION_NO
        defaultHospitalShouldBeFound("registrationNo.doesNotContain=" + UPDATED_REGISTRATION_NO);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 equals to DEFAULT_ADDRESS_1
        defaultHospitalShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the hospitalList where address1 equals to UPDATED_ADDRESS_1
        defaultHospitalShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 not equals to DEFAULT_ADDRESS_1
        defaultHospitalShouldNotBeFound("address1.notEquals=" + DEFAULT_ADDRESS_1);

        // Get all the hospitalList where address1 not equals to UPDATED_ADDRESS_1
        defaultHospitalShouldBeFound("address1.notEquals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultHospitalShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the hospitalList where address1 equals to UPDATED_ADDRESS_1
        defaultHospitalShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 is not null
        defaultHospitalShouldBeFound("address1.specified=true");

        // Get all the hospitalList where address1 is null
        defaultHospitalShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 contains DEFAULT_ADDRESS_1
        defaultHospitalShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the hospitalList where address1 contains UPDATED_ADDRESS_1
        defaultHospitalShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address1 does not contain DEFAULT_ADDRESS_1
        defaultHospitalShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the hospitalList where address1 does not contain UPDATED_ADDRESS_1
        defaultHospitalShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 equals to DEFAULT_ADDRESS_2
        defaultHospitalShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the hospitalList where address2 equals to UPDATED_ADDRESS_2
        defaultHospitalShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 not equals to DEFAULT_ADDRESS_2
        defaultHospitalShouldNotBeFound("address2.notEquals=" + DEFAULT_ADDRESS_2);

        // Get all the hospitalList where address2 not equals to UPDATED_ADDRESS_2
        defaultHospitalShouldBeFound("address2.notEquals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultHospitalShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the hospitalList where address2 equals to UPDATED_ADDRESS_2
        defaultHospitalShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 is not null
        defaultHospitalShouldBeFound("address2.specified=true");

        // Get all the hospitalList where address2 is null
        defaultHospitalShouldNotBeFound("address2.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 contains DEFAULT_ADDRESS_2
        defaultHospitalShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the hospitalList where address2 contains UPDATED_ADDRESS_2
        defaultHospitalShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllHospitalsByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where address2 does not contain DEFAULT_ADDRESS_2
        defaultHospitalShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the hospitalList where address2 does not contain UPDATED_ADDRESS_2
        defaultHospitalShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area equals to DEFAULT_AREA
        defaultHospitalShouldBeFound("area.equals=" + DEFAULT_AREA);

        // Get all the hospitalList where area equals to UPDATED_AREA
        defaultHospitalShouldNotBeFound("area.equals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area not equals to DEFAULT_AREA
        defaultHospitalShouldNotBeFound("area.notEquals=" + DEFAULT_AREA);

        // Get all the hospitalList where area not equals to UPDATED_AREA
        defaultHospitalShouldBeFound("area.notEquals=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area in DEFAULT_AREA or UPDATED_AREA
        defaultHospitalShouldBeFound("area.in=" + DEFAULT_AREA + "," + UPDATED_AREA);

        // Get all the hospitalList where area equals to UPDATED_AREA
        defaultHospitalShouldNotBeFound("area.in=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area is not null
        defaultHospitalShouldBeFound("area.specified=true");

        // Get all the hospitalList where area is null
        defaultHospitalShouldNotBeFound("area.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area contains DEFAULT_AREA
        defaultHospitalShouldBeFound("area.contains=" + DEFAULT_AREA);

        // Get all the hospitalList where area contains UPDATED_AREA
        defaultHospitalShouldNotBeFound("area.contains=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllHospitalsByAreaNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where area does not contain DEFAULT_AREA
        defaultHospitalShouldNotBeFound("area.doesNotContain=" + DEFAULT_AREA);

        // Get all the hospitalList where area does not contain UPDATED_AREA
        defaultHospitalShouldBeFound("area.doesNotContain=" + UPDATED_AREA);
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode equals to DEFAULT_PIN_CODE
        defaultHospitalShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the hospitalList where pinCode equals to UPDATED_PIN_CODE
        defaultHospitalShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode not equals to DEFAULT_PIN_CODE
        defaultHospitalShouldNotBeFound("pinCode.notEquals=" + DEFAULT_PIN_CODE);

        // Get all the hospitalList where pinCode not equals to UPDATED_PIN_CODE
        defaultHospitalShouldBeFound("pinCode.notEquals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultHospitalShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the hospitalList where pinCode equals to UPDATED_PIN_CODE
        defaultHospitalShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode is not null
        defaultHospitalShouldBeFound("pinCode.specified=true");

        // Get all the hospitalList where pinCode is null
        defaultHospitalShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode contains DEFAULT_PIN_CODE
        defaultHospitalShouldBeFound("pinCode.contains=" + DEFAULT_PIN_CODE);

        // Get all the hospitalList where pinCode contains UPDATED_PIN_CODE
        defaultHospitalShouldNotBeFound("pinCode.contains=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllHospitalsByPinCodeNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where pinCode does not contain DEFAULT_PIN_CODE
        defaultHospitalShouldNotBeFound("pinCode.doesNotContain=" + DEFAULT_PIN_CODE);

        // Get all the hospitalList where pinCode does not contain UPDATED_PIN_CODE
        defaultHospitalShouldBeFound("pinCode.doesNotContain=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId equals to DEFAULT_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.equals=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId equals to UPDATED_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.equals=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId not equals to DEFAULT_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.notEquals=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId not equals to UPDATED_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.notEquals=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId in DEFAULT_HOSPITAL_ID or UPDATED_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.in=" + DEFAULT_HOSPITAL_ID + "," + UPDATED_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId equals to UPDATED_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.in=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId is not null
        defaultHospitalShouldBeFound("hospitalId.specified=true");

        // Get all the hospitalList where hospitalId is null
        defaultHospitalShouldNotBeFound("hospitalId.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId is greater than or equal to DEFAULT_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.greaterThanOrEqual=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId is greater than or equal to UPDATED_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.greaterThanOrEqual=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId is less than or equal to DEFAULT_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.lessThanOrEqual=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId is less than or equal to SMALLER_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.lessThanOrEqual=" + SMALLER_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsLessThanSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId is less than DEFAULT_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.lessThan=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId is less than UPDATED_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.lessThan=" + UPDATED_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where hospitalId is greater than DEFAULT_HOSPITAL_ID
        defaultHospitalShouldNotBeFound("hospitalId.greaterThan=" + DEFAULT_HOSPITAL_ID);

        // Get all the hospitalList where hospitalId is greater than SMALLER_HOSPITAL_ID
        defaultHospitalShouldBeFound("hospitalId.greaterThan=" + SMALLER_HOSPITAL_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId equals to DEFAULT_ODAS_FACILITY_ID
        defaultHospitalShouldBeFound("odasFacilityId.equals=" + DEFAULT_ODAS_FACILITY_ID);

        // Get all the hospitalList where odasFacilityId equals to UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldNotBeFound("odasFacilityId.equals=" + UPDATED_ODAS_FACILITY_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId not equals to DEFAULT_ODAS_FACILITY_ID
        defaultHospitalShouldNotBeFound("odasFacilityId.notEquals=" + DEFAULT_ODAS_FACILITY_ID);

        // Get all the hospitalList where odasFacilityId not equals to UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldBeFound("odasFacilityId.notEquals=" + UPDATED_ODAS_FACILITY_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId in DEFAULT_ODAS_FACILITY_ID or UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldBeFound("odasFacilityId.in=" + DEFAULT_ODAS_FACILITY_ID + "," + UPDATED_ODAS_FACILITY_ID);

        // Get all the hospitalList where odasFacilityId equals to UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldNotBeFound("odasFacilityId.in=" + UPDATED_ODAS_FACILITY_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId is not null
        defaultHospitalShouldBeFound("odasFacilityId.specified=true");

        // Get all the hospitalList where odasFacilityId is null
        defaultHospitalShouldNotBeFound("odasFacilityId.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId contains DEFAULT_ODAS_FACILITY_ID
        defaultHospitalShouldBeFound("odasFacilityId.contains=" + DEFAULT_ODAS_FACILITY_ID);

        // Get all the hospitalList where odasFacilityId contains UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldNotBeFound("odasFacilityId.contains=" + UPDATED_ODAS_FACILITY_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByOdasFacilityIdNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where odasFacilityId does not contain DEFAULT_ODAS_FACILITY_ID
        defaultHospitalShouldNotBeFound("odasFacilityId.doesNotContain=" + DEFAULT_ODAS_FACILITY_ID);

        // Get all the hospitalList where odasFacilityId does not contain UPDATED_ODAS_FACILITY_ID
        defaultHospitalShouldBeFound("odasFacilityId.doesNotContain=" + UPDATED_ODAS_FACILITY_ID);
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber equals to DEFAULT_REFERENCE_NUMBER
        defaultHospitalShouldBeFound("referenceNumber.equals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the hospitalList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldNotBeFound("referenceNumber.equals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber not equals to DEFAULT_REFERENCE_NUMBER
        defaultHospitalShouldNotBeFound("referenceNumber.notEquals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the hospitalList where referenceNumber not equals to UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldBeFound("referenceNumber.notEquals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber in DEFAULT_REFERENCE_NUMBER or UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldBeFound("referenceNumber.in=" + DEFAULT_REFERENCE_NUMBER + "," + UPDATED_REFERENCE_NUMBER);

        // Get all the hospitalList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldNotBeFound("referenceNumber.in=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber is not null
        defaultHospitalShouldBeFound("referenceNumber.specified=true");

        // Get all the hospitalList where referenceNumber is null
        defaultHospitalShouldNotBeFound("referenceNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber contains DEFAULT_REFERENCE_NUMBER
        defaultHospitalShouldBeFound("referenceNumber.contains=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the hospitalList where referenceNumber contains UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldNotBeFound("referenceNumber.contains=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllHospitalsByReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where referenceNumber does not contain DEFAULT_REFERENCE_NUMBER
        defaultHospitalShouldNotBeFound("referenceNumber.doesNotContain=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the hospitalList where referenceNumber does not contain UPDATED_REFERENCE_NUMBER
        defaultHospitalShouldBeFound("referenceNumber.doesNotContain=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    void getAllHospitalsByStatusIndIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where statusInd equals to DEFAULT_STATUS_IND
        defaultHospitalShouldBeFound("statusInd.equals=" + DEFAULT_STATUS_IND);

        // Get all the hospitalList where statusInd equals to UPDATED_STATUS_IND
        defaultHospitalShouldNotBeFound("statusInd.equals=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllHospitalsByStatusIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where statusInd not equals to DEFAULT_STATUS_IND
        defaultHospitalShouldNotBeFound("statusInd.notEquals=" + DEFAULT_STATUS_IND);

        // Get all the hospitalList where statusInd not equals to UPDATED_STATUS_IND
        defaultHospitalShouldBeFound("statusInd.notEquals=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllHospitalsByStatusIndIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where statusInd in DEFAULT_STATUS_IND or UPDATED_STATUS_IND
        defaultHospitalShouldBeFound("statusInd.in=" + DEFAULT_STATUS_IND + "," + UPDATED_STATUS_IND);

        // Get all the hospitalList where statusInd equals to UPDATED_STATUS_IND
        defaultHospitalShouldNotBeFound("statusInd.in=" + UPDATED_STATUS_IND);
    }

    @Test
    @Transactional
    void getAllHospitalsByStatusIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where statusInd is not null
        defaultHospitalShouldBeFound("statusInd.specified=true");

        // Get all the hospitalList where statusInd is null
        defaultHospitalShouldNotBeFound("statusInd.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultHospitalShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hospitalList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHospitalShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultHospitalShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the hospitalList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultHospitalShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultHospitalShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the hospitalList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultHospitalShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModified is not null
        defaultHospitalShouldBeFound("lastModified.specified=true");

        // Get all the hospitalList where lastModified is null
        defaultHospitalShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultHospitalShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultHospitalShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the hospitalList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy is not null
        defaultHospitalShouldBeFound("lastModifiedBy.specified=true");

        // Get all the hospitalList where lastModifiedBy is null
        defaultHospitalShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultHospitalShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHospitalsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        // Get all the hospitalList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultHospitalShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the hospitalList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultHospitalShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllHospitalsByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
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
        hospital.setState(state);
        hospitalRepository.saveAndFlush(hospital);
        Long stateId = state.getId();

        // Get all the hospitalList where state equals to stateId
        defaultHospitalShouldBeFound("stateId.equals=" + stateId);

        // Get all the hospitalList where state equals to (stateId + 1)
        defaultHospitalShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
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
        hospital.setDistrict(district);
        hospitalRepository.saveAndFlush(hospital);
        Long districtId = district.getId();

        // Get all the hospitalList where district equals to districtId
        defaultHospitalShouldBeFound("districtId.equals=" + districtId);

        // Get all the hospitalList where district equals to (districtId + 1)
        defaultHospitalShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsByTalukaIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
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
        hospital.setTaluka(taluka);
        hospitalRepository.saveAndFlush(hospital);
        Long talukaId = taluka.getId();

        // Get all the hospitalList where taluka equals to talukaId
        defaultHospitalShouldBeFound("talukaId.equals=" + talukaId);

        // Get all the hospitalList where taluka equals to (talukaId + 1)
        defaultHospitalShouldNotBeFound("talukaId.equals=" + (talukaId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
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
        hospital.setCity(city);
        hospitalRepository.saveAndFlush(hospital);
        Long cityId = city.getId();

        // Get all the hospitalList where city equals to cityId
        defaultHospitalShouldBeFound("cityId.equals=" + cityId);

        // Get all the hospitalList where city equals to (cityId + 1)
        defaultHospitalShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsByMunicipalCorpIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        MunicipalCorp municipalCorp;
        if (TestUtil.findAll(em, MunicipalCorp.class).isEmpty()) {
            municipalCorp = MunicipalCorpResourceIT.createEntity(em);
            em.persist(municipalCorp);
            em.flush();
        } else {
            municipalCorp = TestUtil.findAll(em, MunicipalCorp.class).get(0);
        }
        em.persist(municipalCorp);
        em.flush();
        hospital.setMunicipalCorp(municipalCorp);
        hospitalRepository.saveAndFlush(hospital);
        Long municipalCorpId = municipalCorp.getId();

        // Get all the hospitalList where municipalCorp equals to municipalCorpId
        defaultHospitalShouldBeFound("municipalCorpId.equals=" + municipalCorpId);

        // Get all the hospitalList where municipalCorp equals to (municipalCorpId + 1)
        defaultHospitalShouldNotBeFound("municipalCorpId.equals=" + (municipalCorpId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsByHospitalTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
        HospitalType hospitalType;
        if (TestUtil.findAll(em, HospitalType.class).isEmpty()) {
            hospitalType = HospitalTypeResourceIT.createEntity(em);
            em.persist(hospitalType);
            em.flush();
        } else {
            hospitalType = TestUtil.findAll(em, HospitalType.class).get(0);
        }
        em.persist(hospitalType);
        em.flush();
        hospital.setHospitalType(hospitalType);
        hospitalRepository.saveAndFlush(hospital);
        Long hospitalTypeId = hospitalType.getId();

        // Get all the hospitalList where hospitalType equals to hospitalTypeId
        defaultHospitalShouldBeFound("hospitalTypeId.equals=" + hospitalTypeId);

        // Get all the hospitalList where hospitalType equals to (hospitalTypeId + 1)
        defaultHospitalShouldNotBeFound("hospitalTypeId.equals=" + (hospitalTypeId + 1));
    }

    @Test
    @Transactional
    void getAllHospitalsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);
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
        hospital.addSupplier(supplier);
        hospitalRepository.saveAndFlush(hospital);
        Long supplierId = supplier.getId();

        // Get all the hospitalList where supplier equals to supplierId
        defaultHospitalShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the hospitalList where supplier equals to (supplierId + 1)
        defaultHospitalShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultHospitalShouldBeFound(String filter) throws Exception {
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hospital.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].subCategory").value(hasItem(DEFAULT_SUB_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].docCount").value(hasItem(DEFAULT_DOC_COUNT)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].registrationNo").value(hasItem(DEFAULT_REGISTRATION_NO)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA)))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].hospitalId").value(hasItem(DEFAULT_HOSPITAL_ID.intValue())))
            .andExpect(jsonPath("$.[*].odasFacilityId").value(hasItem(DEFAULT_ODAS_FACILITY_ID)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)))
            .andExpect(jsonPath("$.[*].statusInd").value(hasItem(DEFAULT_STATUS_IND.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultHospitalShouldNotBeFound(String filter) throws Exception {
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restHospitalMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingHospital() throws Exception {
        // Get the hospital
        restHospitalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital
        Hospital updatedHospital = hospitalRepository.findById(hospital.getId()).get();
        // Disconnect from session so that the updates on updatedHospital are not directly saved in db
        em.detach(updatedHospital);
        updatedHospital
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .docCount(UPDATED_DOC_COUNT)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .odasFacilityId(UPDATED_ODAS_FACILITY_ID)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        HospitalDTO hospitalDTO = hospitalMapper.toDto(updatedHospital);

        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testHospital.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testHospital.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testHospital.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testHospital.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHospital.getDocCount()).isEqualTo(UPDATED_DOC_COUNT);
        assertThat(testHospital.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHospital.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHospital.getRegistrationNo()).isEqualTo(UPDATED_REGISTRATION_NO);
        assertThat(testHospital.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testHospital.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testHospital.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testHospital.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testHospital.getHospitalId()).isEqualTo(UPDATED_HOSPITAL_ID);
        assertThat(testHospital.getOdasFacilityId()).isEqualTo(UPDATED_ODAS_FACILITY_ID);
        assertThat(testHospital.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testHospital.getStatusInd()).isEqualTo(UPDATED_STATUS_IND);
        assertThat(testHospital.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hospitalDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHospitalWithPatch() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital using partial update
        Hospital partialUpdatedHospital = new Hospital();
        partialUpdatedHospital.setId(hospital.getId());

        partialUpdatedHospital
            .longitude(UPDATED_LONGITUDE)
            .docCount(UPDATED_DOC_COUNT)
            .email(UPDATED_EMAIL)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .lastModified(UPDATED_LAST_MODIFIED);

        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospital.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHospital))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testHospital.getSubCategory()).isEqualTo(DEFAULT_SUB_CATEGORY);
        assertThat(testHospital.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testHospital.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testHospital.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHospital.getDocCount()).isEqualTo(UPDATED_DOC_COUNT);
        assertThat(testHospital.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHospital.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHospital.getRegistrationNo()).isEqualTo(DEFAULT_REGISTRATION_NO);
        assertThat(testHospital.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testHospital.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testHospital.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testHospital.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testHospital.getHospitalId()).isEqualTo(UPDATED_HOSPITAL_ID);
        assertThat(testHospital.getOdasFacilityId()).isEqualTo(DEFAULT_ODAS_FACILITY_ID);
        assertThat(testHospital.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testHospital.getStatusInd()).isEqualTo(DEFAULT_STATUS_IND);
        assertThat(testHospital.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateHospitalWithPatch() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();

        // Update the hospital using partial update
        Hospital partialUpdatedHospital = new Hospital();
        partialUpdatedHospital.setId(hospital.getId());

        partialUpdatedHospital
            .category(UPDATED_CATEGORY)
            .subCategory(UPDATED_SUB_CATEGORY)
            .contactNo(UPDATED_CONTACT_NO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .docCount(UPDATED_DOC_COUNT)
            .email(UPDATED_EMAIL)
            .name(UPDATED_NAME)
            .registrationNo(UPDATED_REGISTRATION_NO)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .area(UPDATED_AREA)
            .pinCode(UPDATED_PIN_CODE)
            .hospitalId(UPDATED_HOSPITAL_ID)
            .odasFacilityId(UPDATED_ODAS_FACILITY_ID)
            .referenceNumber(UPDATED_REFERENCE_NUMBER)
            .statusInd(UPDATED_STATUS_IND)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHospital.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHospital))
            )
            .andExpect(status().isOk());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
        Hospital testHospital = hospitalList.get(hospitalList.size() - 1);
        assertThat(testHospital.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testHospital.getSubCategory()).isEqualTo(UPDATED_SUB_CATEGORY);
        assertThat(testHospital.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testHospital.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testHospital.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testHospital.getDocCount()).isEqualTo(UPDATED_DOC_COUNT);
        assertThat(testHospital.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testHospital.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHospital.getRegistrationNo()).isEqualTo(UPDATED_REGISTRATION_NO);
        assertThat(testHospital.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testHospital.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testHospital.getArea()).isEqualTo(UPDATED_AREA);
        assertThat(testHospital.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testHospital.getHospitalId()).isEqualTo(UPDATED_HOSPITAL_ID);
        assertThat(testHospital.getOdasFacilityId()).isEqualTo(UPDATED_ODAS_FACILITY_ID);
        assertThat(testHospital.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
        assertThat(testHospital.getStatusInd()).isEqualTo(UPDATED_STATUS_IND);
        assertThat(testHospital.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testHospital.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hospitalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHospital() throws Exception {
        int databaseSizeBeforeUpdate = hospitalRepository.findAll().size();
        hospital.setId(count.incrementAndGet());

        // Create the Hospital
        HospitalDTO hospitalDTO = hospitalMapper.toDto(hospital);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHospitalMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hospitalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Hospital in the database
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHospital() throws Exception {
        // Initialize the database
        hospitalRepository.saveAndFlush(hospital);

        int databaseSizeBeforeDelete = hospitalRepository.findAll().size();

        // Delete the hospital
        restHospitalMockMvc
            .perform(delete(ENTITY_API_URL_ID, hospital.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Hospital> hospitalList = hospitalRepository.findAll();
        assertThat(hospitalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
