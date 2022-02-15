package com.techvg.covid.care.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.covid.care.IntegrationTest;
import com.techvg.covid.care.domain.District;
import com.techvg.covid.care.domain.PatientInfo;
import com.techvg.covid.care.domain.State;
import com.techvg.covid.care.repository.PatientInfoRepository;
import com.techvg.covid.care.service.criteria.PatientInfoCriteria;
import com.techvg.covid.care.service.dto.PatientInfoDTO;
import com.techvg.covid.care.service.mapper.PatientInfoMapper;
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
 * Integration tests for the {@link PatientInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientInfoResourceIT {

    private static final String DEFAULT_ICMR_ID = "AAAAAAAAAA";
    private static final String UPDATED_ICMR_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SRF_ID = "AAAAAAAAAA";
    private static final String UPDATED_SRF_ID = "BBBBBBBBBB";

    private static final String DEFAULT_LAB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_AGE_IN = "AAAAAAAAAA";
    private static final String UPDATED_AGE_IN = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLAGE_TOWN = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_TOWN = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final String DEFAULT_PATIENT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_PATIENT_CATEGORY = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_SAMPLE_COLLECTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_SAMPLE_COLLECTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_SAMPLE_RECEIVED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_SAMPLE_RECEIVED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SAMPLE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SAMPLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_SAMPLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_UNDERLYING_MEDICAL_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_UNDERLYING_MEDICAL_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITALIZED = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITALIZED = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_HOSPITALIZATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOSPITALIZATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_HOSPITAL_STATE = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_HOSPITAL_DISTRICT = "AAAAAAAAAA";
    private static final String UPDATED_HOSPITAL_DISTRICT = "BBBBBBBBBB";

    private static final String DEFAULT_SYMPTOMS_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SYMPTOMS_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_SYMPTOMS = "AAAAAAAAAA";
    private static final String UPDATED_SYMPTOMS = "BBBBBBBBBB";

    private static final String DEFAULT_TESTING_KIT_USED = "AAAAAAAAAA";
    private static final String UPDATED_TESTING_KIT_USED = "BBBBBBBBBB";

    private static final String DEFAULT_E_GENE_N_GENE = "AAAAAAAAAA";
    private static final String UPDATED_E_GENE_N_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_CT_VALUE_OF_E_GENE_N_GENE = "AAAAAAAAAA";
    private static final String UPDATED_CT_VALUE_OF_E_GENE_N_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_RD_RP_S_GENE = "AAAAAAAAAA";
    private static final String UPDATED_RD_RP_S_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_CT_VALUE_OF_RD_RP_S_GENE = "AAAAAAAAAA";
    private static final String UPDATED_CT_VALUE_OF_RD_RP_S_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE = "AAAAAAAAAA";
    private static final String UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE = "AAAAAAAAAA";
    private static final String UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE = "BBBBBBBBBB";

    private static final String DEFAULT_REPEAT_SAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_REPEAT_SAMPLE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_SAMPLE_TESTED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_SAMPLE_TESTED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENTRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENTRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CONFIRMATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONFIRMATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FINAL_RESULT_SAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_RESULT_SAMPLE = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_EDITED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EDITED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CCMS_PULL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CCMS_PULL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/patient-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientInfoRepository patientInfoRepository;

    @Autowired
    private PatientInfoMapper patientInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientInfoMockMvc;

    private PatientInfo patientInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientInfo createEntity(EntityManager em) {
        PatientInfo patientInfo = new PatientInfo()
            .icmrId(DEFAULT_ICMR_ID)
            .srfId(DEFAULT_SRF_ID)
            .labName(DEFAULT_LAB_NAME)
            .patientID(DEFAULT_PATIENT_ID)
            .patientName(DEFAULT_PATIENT_NAME)
            .age(DEFAULT_AGE)
            .ageIn(DEFAULT_AGE_IN)
            .gender(DEFAULT_GENDER)
            .nationality(DEFAULT_NATIONALITY)
            .address(DEFAULT_ADDRESS)
            .villageTown(DEFAULT_VILLAGE_TOWN)
            .pincode(DEFAULT_PINCODE)
            .patientCategory(DEFAULT_PATIENT_CATEGORY)
            .dateOfSampleCollection(DEFAULT_DATE_OF_SAMPLE_COLLECTION)
            .dateOfSampleReceived(DEFAULT_DATE_OF_SAMPLE_RECEIVED)
            .sampleType(DEFAULT_SAMPLE_TYPE)
            .sampleId(DEFAULT_SAMPLE_ID)
            .underlyingMedicalCondition(DEFAULT_UNDERLYING_MEDICAL_CONDITION)
            .hospitalized(DEFAULT_HOSPITALIZED)
            .hospitalName(DEFAULT_HOSPITAL_NAME)
            .hospitalizationDate(DEFAULT_HOSPITALIZATION_DATE)
            .hospitalState(DEFAULT_HOSPITAL_STATE)
            .hospitalDistrict(DEFAULT_HOSPITAL_DISTRICT)
            .symptomsStatus(DEFAULT_SYMPTOMS_STATUS)
            .symptoms(DEFAULT_SYMPTOMS)
            .testingKitUsed(DEFAULT_TESTING_KIT_USED)
            .eGeneNGene(DEFAULT_E_GENE_N_GENE)
            .ctValueOfEGeneNGene(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE)
            .rdRpSGene(DEFAULT_RD_RP_S_GENE)
            .ctValueOfRdRpSGene(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE)
            .oRF1aORF1bNN2Gene(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE)
            .ctValueOfORF1aORF1bNN2Gene(DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)
            .repeatSample(DEFAULT_REPEAT_SAMPLE)
            .dateOfSampleTested(DEFAULT_DATE_OF_SAMPLE_TESTED)
            .entryDate(DEFAULT_ENTRY_DATE)
            .confirmationDate(DEFAULT_CONFIRMATION_DATE)
            .finalResultSample(DEFAULT_FINAL_RESULT_SAMPLE)
            .remarks(DEFAULT_REMARKS)
            .editedOn(DEFAULT_EDITED_ON)
            .ccmsPullDate(DEFAULT_CCMS_PULL_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return patientInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientInfo createUpdatedEntity(EntityManager em) {
        PatientInfo patientInfo = new PatientInfo()
            .icmrId(UPDATED_ICMR_ID)
            .srfId(UPDATED_SRF_ID)
            .labName(UPDATED_LAB_NAME)
            .patientID(UPDATED_PATIENT_ID)
            .patientName(UPDATED_PATIENT_NAME)
            .age(UPDATED_AGE)
            .ageIn(UPDATED_AGE_IN)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .address(UPDATED_ADDRESS)
            .villageTown(UPDATED_VILLAGE_TOWN)
            .pincode(UPDATED_PINCODE)
            .patientCategory(UPDATED_PATIENT_CATEGORY)
            .dateOfSampleCollection(UPDATED_DATE_OF_SAMPLE_COLLECTION)
            .dateOfSampleReceived(UPDATED_DATE_OF_SAMPLE_RECEIVED)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .sampleId(UPDATED_SAMPLE_ID)
            .underlyingMedicalCondition(UPDATED_UNDERLYING_MEDICAL_CONDITION)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .hospitalState(UPDATED_HOSPITAL_STATE)
            .hospitalDistrict(UPDATED_HOSPITAL_DISTRICT)
            .symptomsStatus(UPDATED_SYMPTOMS_STATUS)
            .symptoms(UPDATED_SYMPTOMS)
            .testingKitUsed(UPDATED_TESTING_KIT_USED)
            .eGeneNGene(UPDATED_E_GENE_N_GENE)
            .ctValueOfEGeneNGene(UPDATED_CT_VALUE_OF_E_GENE_N_GENE)
            .rdRpSGene(UPDATED_RD_RP_S_GENE)
            .ctValueOfRdRpSGene(UPDATED_CT_VALUE_OF_RD_RP_S_GENE)
            .oRF1aORF1bNN2Gene(UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE)
            .ctValueOfORF1aORF1bNN2Gene(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)
            .repeatSample(UPDATED_REPEAT_SAMPLE)
            .dateOfSampleTested(UPDATED_DATE_OF_SAMPLE_TESTED)
            .entryDate(UPDATED_ENTRY_DATE)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .finalResultSample(UPDATED_FINAL_RESULT_SAMPLE)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .ccmsPullDate(UPDATED_CCMS_PULL_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return patientInfo;
    }

    @BeforeEach
    public void initTest() {
        patientInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPatientInfo() throws Exception {
        int databaseSizeBeforeCreate = patientInfoRepository.findAll().size();
        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);
        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getIcmrId()).isEqualTo(DEFAULT_ICMR_ID);
        assertThat(testPatientInfo.getSrfId()).isEqualTo(DEFAULT_SRF_ID);
        assertThat(testPatientInfo.getLabName()).isEqualTo(DEFAULT_LAB_NAME);
        assertThat(testPatientInfo.getPatientID()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testPatientInfo.getPatientName()).isEqualTo(DEFAULT_PATIENT_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatientInfo.getAgeIn()).isEqualTo(DEFAULT_AGE_IN);
        assertThat(testPatientInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatientInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPatientInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatientInfo.getVillageTown()).isEqualTo(DEFAULT_VILLAGE_TOWN);
        assertThat(testPatientInfo.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testPatientInfo.getPatientCategory()).isEqualTo(DEFAULT_PATIENT_CATEGORY);
        assertThat(testPatientInfo.getDateOfSampleCollection()).isEqualTo(DEFAULT_DATE_OF_SAMPLE_COLLECTION);
        assertThat(testPatientInfo.getDateOfSampleReceived()).isEqualTo(DEFAULT_DATE_OF_SAMPLE_RECEIVED);
        assertThat(testPatientInfo.getSampleType()).isEqualTo(DEFAULT_SAMPLE_TYPE);
        assertThat(testPatientInfo.getSampleId()).isEqualTo(DEFAULT_SAMPLE_ID);
        assertThat(testPatientInfo.getUnderlyingMedicalCondition()).isEqualTo(DEFAULT_UNDERLYING_MEDICAL_CONDITION);
        assertThat(testPatientInfo.getHospitalized()).isEqualTo(DEFAULT_HOSPITALIZED);
        assertThat(testPatientInfo.getHospitalName()).isEqualTo(DEFAULT_HOSPITAL_NAME);
        assertThat(testPatientInfo.getHospitalizationDate()).isEqualTo(DEFAULT_HOSPITALIZATION_DATE);
        assertThat(testPatientInfo.getHospitalState()).isEqualTo(DEFAULT_HOSPITAL_STATE);
        assertThat(testPatientInfo.getHospitalDistrict()).isEqualTo(DEFAULT_HOSPITAL_DISTRICT);
        assertThat(testPatientInfo.getSymptomsStatus()).isEqualTo(DEFAULT_SYMPTOMS_STATUS);
        assertThat(testPatientInfo.getSymptoms()).isEqualTo(DEFAULT_SYMPTOMS);
        assertThat(testPatientInfo.getTestingKitUsed()).isEqualTo(DEFAULT_TESTING_KIT_USED);
        assertThat(testPatientInfo.geteGeneNGene()).isEqualTo(DEFAULT_E_GENE_N_GENE);
        assertThat(testPatientInfo.getCtValueOfEGeneNGene()).isEqualTo(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);
        assertThat(testPatientInfo.getRdRpSGene()).isEqualTo(DEFAULT_RD_RP_S_GENE);
        assertThat(testPatientInfo.getCtValueOfRdRpSGene()).isEqualTo(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);
        assertThat(testPatientInfo.getoRF1aORF1bNN2Gene()).isEqualTo(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getCtValueOfORF1aORF1bNN2Gene()).isEqualTo(DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getRepeatSample()).isEqualTo(DEFAULT_REPEAT_SAMPLE);
        assertThat(testPatientInfo.getDateOfSampleTested()).isEqualTo(DEFAULT_DATE_OF_SAMPLE_TESTED);
        assertThat(testPatientInfo.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testPatientInfo.getConfirmationDate()).isEqualTo(DEFAULT_CONFIRMATION_DATE);
        assertThat(testPatientInfo.getFinalResultSample()).isEqualTo(DEFAULT_FINAL_RESULT_SAMPLE);
        assertThat(testPatientInfo.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testPatientInfo.getEditedOn()).isEqualTo(DEFAULT_EDITED_ON);
        assertThat(testPatientInfo.getCcmsPullDate()).isEqualTo(DEFAULT_CCMS_PULL_DATE);
        assertThat(testPatientInfo.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPatientInfo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createPatientInfoWithExistingId() throws Exception {
        // Create the PatientInfo with an existing ID
        patientInfo.setId(1L);
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        int databaseSizeBeforeCreate = patientInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIcmrIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientInfoRepository.findAll().size();
        // set the field null
        patientInfo.setIcmrId(null);

        // Create the PatientInfo, which fails.
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCcmsPullDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientInfoRepository.findAll().size();
        // set the field null
        patientInfo.setCcmsPullDate(null);

        // Create the PatientInfo, which fails.
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientInfoRepository.findAll().size();
        // set the field null
        patientInfo.setLastModified(null);

        // Create the PatientInfo, which fails.
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientInfoRepository.findAll().size();
        // set the field null
        patientInfo.setLastModifiedBy(null);

        // Create the PatientInfo, which fails.
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        restPatientInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPatientInfos() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].icmrId").value(hasItem(DEFAULT_ICMR_ID)))
            .andExpect(jsonPath("$.[*].srfId").value(hasItem(DEFAULT_SRF_ID)))
            .andExpect(jsonPath("$.[*].labName").value(hasItem(DEFAULT_LAB_NAME)))
            .andExpect(jsonPath("$.[*].patientID").value(hasItem(DEFAULT_PATIENT_ID)))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].ageIn").value(hasItem(DEFAULT_AGE_IN)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].villageTown").value(hasItem(DEFAULT_VILLAGE_TOWN)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].patientCategory").value(hasItem(DEFAULT_PATIENT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dateOfSampleCollection").value(hasItem(DEFAULT_DATE_OF_SAMPLE_COLLECTION.toString())))
            .andExpect(jsonPath("$.[*].dateOfSampleReceived").value(hasItem(DEFAULT_DATE_OF_SAMPLE_RECEIVED.toString())))
            .andExpect(jsonPath("$.[*].sampleType").value(hasItem(DEFAULT_SAMPLE_TYPE)))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID)))
            .andExpect(jsonPath("$.[*].underlyingMedicalCondition").value(hasItem(DEFAULT_UNDERLYING_MEDICAL_CONDITION)))
            .andExpect(jsonPath("$.[*].hospitalized").value(hasItem(DEFAULT_HOSPITALIZED)))
            .andExpect(jsonPath("$.[*].hospitalName").value(hasItem(DEFAULT_HOSPITAL_NAME)))
            .andExpect(jsonPath("$.[*].hospitalizationDate").value(hasItem(DEFAULT_HOSPITALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].hospitalState").value(hasItem(DEFAULT_HOSPITAL_STATE)))
            .andExpect(jsonPath("$.[*].hospitalDistrict").value(hasItem(DEFAULT_HOSPITAL_DISTRICT)))
            .andExpect(jsonPath("$.[*].symptomsStatus").value(hasItem(DEFAULT_SYMPTOMS_STATUS)))
            .andExpect(jsonPath("$.[*].symptoms").value(hasItem(DEFAULT_SYMPTOMS)))
            .andExpect(jsonPath("$.[*].testingKitUsed").value(hasItem(DEFAULT_TESTING_KIT_USED)))
            .andExpect(jsonPath("$.[*].eGeneNGene").value(hasItem(DEFAULT_E_GENE_N_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfEGeneNGene").value(hasItem(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE)))
            .andExpect(jsonPath("$.[*].rdRpSGene").value(hasItem(DEFAULT_RD_RP_S_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfRdRpSGene").value(hasItem(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE)))
            .andExpect(jsonPath("$.[*].oRF1aORF1bNN2Gene").value(hasItem(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfORF1aORF1bNN2Gene").value(hasItem(DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)))
            .andExpect(jsonPath("$.[*].repeatSample").value(hasItem(DEFAULT_REPEAT_SAMPLE)))
            .andExpect(jsonPath("$.[*].dateOfSampleTested").value(hasItem(DEFAULT_DATE_OF_SAMPLE_TESTED.toString())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].finalResultSample").value(hasItem(DEFAULT_FINAL_RESULT_SAMPLE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].editedOn").value(hasItem(DEFAULT_EDITED_ON.toString())))
            .andExpect(jsonPath("$.[*].ccmsPullDate").value(hasItem(DEFAULT_CCMS_PULL_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getPatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get the patientInfo
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, patientInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientInfo.getId().intValue()))
            .andExpect(jsonPath("$.icmrId").value(DEFAULT_ICMR_ID))
            .andExpect(jsonPath("$.srfId").value(DEFAULT_SRF_ID))
            .andExpect(jsonPath("$.labName").value(DEFAULT_LAB_NAME))
            .andExpect(jsonPath("$.patientID").value(DEFAULT_PATIENT_ID))
            .andExpect(jsonPath("$.patientName").value(DEFAULT_PATIENT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.ageIn").value(DEFAULT_AGE_IN))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.villageTown").value(DEFAULT_VILLAGE_TOWN))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.patientCategory").value(DEFAULT_PATIENT_CATEGORY))
            .andExpect(jsonPath("$.dateOfSampleCollection").value(DEFAULT_DATE_OF_SAMPLE_COLLECTION.toString()))
            .andExpect(jsonPath("$.dateOfSampleReceived").value(DEFAULT_DATE_OF_SAMPLE_RECEIVED.toString()))
            .andExpect(jsonPath("$.sampleType").value(DEFAULT_SAMPLE_TYPE))
            .andExpect(jsonPath("$.sampleId").value(DEFAULT_SAMPLE_ID))
            .andExpect(jsonPath("$.underlyingMedicalCondition").value(DEFAULT_UNDERLYING_MEDICAL_CONDITION))
            .andExpect(jsonPath("$.hospitalized").value(DEFAULT_HOSPITALIZED))
            .andExpect(jsonPath("$.hospitalName").value(DEFAULT_HOSPITAL_NAME))
            .andExpect(jsonPath("$.hospitalizationDate").value(DEFAULT_HOSPITALIZATION_DATE.toString()))
            .andExpect(jsonPath("$.hospitalState").value(DEFAULT_HOSPITAL_STATE))
            .andExpect(jsonPath("$.hospitalDistrict").value(DEFAULT_HOSPITAL_DISTRICT))
            .andExpect(jsonPath("$.symptomsStatus").value(DEFAULT_SYMPTOMS_STATUS))
            .andExpect(jsonPath("$.symptoms").value(DEFAULT_SYMPTOMS))
            .andExpect(jsonPath("$.testingKitUsed").value(DEFAULT_TESTING_KIT_USED))
            .andExpect(jsonPath("$.eGeneNGene").value(DEFAULT_E_GENE_N_GENE))
            .andExpect(jsonPath("$.ctValueOfEGeneNGene").value(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE))
            .andExpect(jsonPath("$.rdRpSGene").value(DEFAULT_RD_RP_S_GENE))
            .andExpect(jsonPath("$.ctValueOfRdRpSGene").value(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE))
            .andExpect(jsonPath("$.oRF1aORF1bNN2Gene").value(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE))
            .andExpect(jsonPath("$.ctValueOfORF1aORF1bNN2Gene").value(DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE))
            .andExpect(jsonPath("$.repeatSample").value(DEFAULT_REPEAT_SAMPLE))
            .andExpect(jsonPath("$.dateOfSampleTested").value(DEFAULT_DATE_OF_SAMPLE_TESTED.toString()))
            .andExpect(jsonPath("$.entryDate").value(DEFAULT_ENTRY_DATE.toString()))
            .andExpect(jsonPath("$.confirmationDate").value(DEFAULT_CONFIRMATION_DATE.toString()))
            .andExpect(jsonPath("$.finalResultSample").value(DEFAULT_FINAL_RESULT_SAMPLE))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.editedOn").value(DEFAULT_EDITED_ON.toString()))
            .andExpect(jsonPath("$.ccmsPullDate").value(DEFAULT_CCMS_PULL_DATE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getPatientInfosByIdFiltering() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        Long id = patientInfo.getId();

        defaultPatientInfoShouldBeFound("id.equals=" + id);
        defaultPatientInfoShouldNotBeFound("id.notEquals=" + id);

        defaultPatientInfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientInfoShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientInfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientInfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId equals to DEFAULT_ICMR_ID
        defaultPatientInfoShouldBeFound("icmrId.equals=" + DEFAULT_ICMR_ID);

        // Get all the patientInfoList where icmrId equals to UPDATED_ICMR_ID
        defaultPatientInfoShouldNotBeFound("icmrId.equals=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId not equals to DEFAULT_ICMR_ID
        defaultPatientInfoShouldNotBeFound("icmrId.notEquals=" + DEFAULT_ICMR_ID);

        // Get all the patientInfoList where icmrId not equals to UPDATED_ICMR_ID
        defaultPatientInfoShouldBeFound("icmrId.notEquals=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId in DEFAULT_ICMR_ID or UPDATED_ICMR_ID
        defaultPatientInfoShouldBeFound("icmrId.in=" + DEFAULT_ICMR_ID + "," + UPDATED_ICMR_ID);

        // Get all the patientInfoList where icmrId equals to UPDATED_ICMR_ID
        defaultPatientInfoShouldNotBeFound("icmrId.in=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId is not null
        defaultPatientInfoShouldBeFound("icmrId.specified=true");

        // Get all the patientInfoList where icmrId is null
        defaultPatientInfoShouldNotBeFound("icmrId.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId contains DEFAULT_ICMR_ID
        defaultPatientInfoShouldBeFound("icmrId.contains=" + DEFAULT_ICMR_ID);

        // Get all the patientInfoList where icmrId contains UPDATED_ICMR_ID
        defaultPatientInfoShouldNotBeFound("icmrId.contains=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByIcmrIdNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where icmrId does not contain DEFAULT_ICMR_ID
        defaultPatientInfoShouldNotBeFound("icmrId.doesNotContain=" + DEFAULT_ICMR_ID);

        // Get all the patientInfoList where icmrId does not contain UPDATED_ICMR_ID
        defaultPatientInfoShouldBeFound("icmrId.doesNotContain=" + UPDATED_ICMR_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId equals to DEFAULT_SRF_ID
        defaultPatientInfoShouldBeFound("srfId.equals=" + DEFAULT_SRF_ID);

        // Get all the patientInfoList where srfId equals to UPDATED_SRF_ID
        defaultPatientInfoShouldNotBeFound("srfId.equals=" + UPDATED_SRF_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId not equals to DEFAULT_SRF_ID
        defaultPatientInfoShouldNotBeFound("srfId.notEquals=" + DEFAULT_SRF_ID);

        // Get all the patientInfoList where srfId not equals to UPDATED_SRF_ID
        defaultPatientInfoShouldBeFound("srfId.notEquals=" + UPDATED_SRF_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId in DEFAULT_SRF_ID or UPDATED_SRF_ID
        defaultPatientInfoShouldBeFound("srfId.in=" + DEFAULT_SRF_ID + "," + UPDATED_SRF_ID);

        // Get all the patientInfoList where srfId equals to UPDATED_SRF_ID
        defaultPatientInfoShouldNotBeFound("srfId.in=" + UPDATED_SRF_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId is not null
        defaultPatientInfoShouldBeFound("srfId.specified=true");

        // Get all the patientInfoList where srfId is null
        defaultPatientInfoShouldNotBeFound("srfId.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId contains DEFAULT_SRF_ID
        defaultPatientInfoShouldBeFound("srfId.contains=" + DEFAULT_SRF_ID);

        // Get all the patientInfoList where srfId contains UPDATED_SRF_ID
        defaultPatientInfoShouldNotBeFound("srfId.contains=" + UPDATED_SRF_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySrfIdNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where srfId does not contain DEFAULT_SRF_ID
        defaultPatientInfoShouldNotBeFound("srfId.doesNotContain=" + DEFAULT_SRF_ID);

        // Get all the patientInfoList where srfId does not contain UPDATED_SRF_ID
        defaultPatientInfoShouldBeFound("srfId.doesNotContain=" + UPDATED_SRF_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName equals to DEFAULT_LAB_NAME
        defaultPatientInfoShouldBeFound("labName.equals=" + DEFAULT_LAB_NAME);

        // Get all the patientInfoList where labName equals to UPDATED_LAB_NAME
        defaultPatientInfoShouldNotBeFound("labName.equals=" + UPDATED_LAB_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName not equals to DEFAULT_LAB_NAME
        defaultPatientInfoShouldNotBeFound("labName.notEquals=" + DEFAULT_LAB_NAME);

        // Get all the patientInfoList where labName not equals to UPDATED_LAB_NAME
        defaultPatientInfoShouldBeFound("labName.notEquals=" + UPDATED_LAB_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName in DEFAULT_LAB_NAME or UPDATED_LAB_NAME
        defaultPatientInfoShouldBeFound("labName.in=" + DEFAULT_LAB_NAME + "," + UPDATED_LAB_NAME);

        // Get all the patientInfoList where labName equals to UPDATED_LAB_NAME
        defaultPatientInfoShouldNotBeFound("labName.in=" + UPDATED_LAB_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName is not null
        defaultPatientInfoShouldBeFound("labName.specified=true");

        // Get all the patientInfoList where labName is null
        defaultPatientInfoShouldNotBeFound("labName.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName contains DEFAULT_LAB_NAME
        defaultPatientInfoShouldBeFound("labName.contains=" + DEFAULT_LAB_NAME);

        // Get all the patientInfoList where labName contains UPDATED_LAB_NAME
        defaultPatientInfoShouldNotBeFound("labName.contains=" + UPDATED_LAB_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLabNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where labName does not contain DEFAULT_LAB_NAME
        defaultPatientInfoShouldNotBeFound("labName.doesNotContain=" + DEFAULT_LAB_NAME);

        // Get all the patientInfoList where labName does not contain UPDATED_LAB_NAME
        defaultPatientInfoShouldBeFound("labName.doesNotContain=" + UPDATED_LAB_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID equals to DEFAULT_PATIENT_ID
        defaultPatientInfoShouldBeFound("patientID.equals=" + DEFAULT_PATIENT_ID);

        // Get all the patientInfoList where patientID equals to UPDATED_PATIENT_ID
        defaultPatientInfoShouldNotBeFound("patientID.equals=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID not equals to DEFAULT_PATIENT_ID
        defaultPatientInfoShouldNotBeFound("patientID.notEquals=" + DEFAULT_PATIENT_ID);

        // Get all the patientInfoList where patientID not equals to UPDATED_PATIENT_ID
        defaultPatientInfoShouldBeFound("patientID.notEquals=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID in DEFAULT_PATIENT_ID or UPDATED_PATIENT_ID
        defaultPatientInfoShouldBeFound("patientID.in=" + DEFAULT_PATIENT_ID + "," + UPDATED_PATIENT_ID);

        // Get all the patientInfoList where patientID equals to UPDATED_PATIENT_ID
        defaultPatientInfoShouldNotBeFound("patientID.in=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID is not null
        defaultPatientInfoShouldBeFound("patientID.specified=true");

        // Get all the patientInfoList where patientID is null
        defaultPatientInfoShouldNotBeFound("patientID.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID contains DEFAULT_PATIENT_ID
        defaultPatientInfoShouldBeFound("patientID.contains=" + DEFAULT_PATIENT_ID);

        // Get all the patientInfoList where patientID contains UPDATED_PATIENT_ID
        defaultPatientInfoShouldNotBeFound("patientID.contains=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientIDNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientID does not contain DEFAULT_PATIENT_ID
        defaultPatientInfoShouldNotBeFound("patientID.doesNotContain=" + DEFAULT_PATIENT_ID);

        // Get all the patientInfoList where patientID does not contain UPDATED_PATIENT_ID
        defaultPatientInfoShouldBeFound("patientID.doesNotContain=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName equals to DEFAULT_PATIENT_NAME
        defaultPatientInfoShouldBeFound("patientName.equals=" + DEFAULT_PATIENT_NAME);

        // Get all the patientInfoList where patientName equals to UPDATED_PATIENT_NAME
        defaultPatientInfoShouldNotBeFound("patientName.equals=" + UPDATED_PATIENT_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName not equals to DEFAULT_PATIENT_NAME
        defaultPatientInfoShouldNotBeFound("patientName.notEquals=" + DEFAULT_PATIENT_NAME);

        // Get all the patientInfoList where patientName not equals to UPDATED_PATIENT_NAME
        defaultPatientInfoShouldBeFound("patientName.notEquals=" + UPDATED_PATIENT_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName in DEFAULT_PATIENT_NAME or UPDATED_PATIENT_NAME
        defaultPatientInfoShouldBeFound("patientName.in=" + DEFAULT_PATIENT_NAME + "," + UPDATED_PATIENT_NAME);

        // Get all the patientInfoList where patientName equals to UPDATED_PATIENT_NAME
        defaultPatientInfoShouldNotBeFound("patientName.in=" + UPDATED_PATIENT_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName is not null
        defaultPatientInfoShouldBeFound("patientName.specified=true");

        // Get all the patientInfoList where patientName is null
        defaultPatientInfoShouldNotBeFound("patientName.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName contains DEFAULT_PATIENT_NAME
        defaultPatientInfoShouldBeFound("patientName.contains=" + DEFAULT_PATIENT_NAME);

        // Get all the patientInfoList where patientName contains UPDATED_PATIENT_NAME
        defaultPatientInfoShouldNotBeFound("patientName.contains=" + UPDATED_PATIENT_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientName does not contain DEFAULT_PATIENT_NAME
        defaultPatientInfoShouldNotBeFound("patientName.doesNotContain=" + DEFAULT_PATIENT_NAME);

        // Get all the patientInfoList where patientName does not contain UPDATED_PATIENT_NAME
        defaultPatientInfoShouldBeFound("patientName.doesNotContain=" + UPDATED_PATIENT_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age equals to DEFAULT_AGE
        defaultPatientInfoShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the patientInfoList where age equals to UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age not equals to DEFAULT_AGE
        defaultPatientInfoShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the patientInfoList where age not equals to UPDATED_AGE
        defaultPatientInfoShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age in DEFAULT_AGE or UPDATED_AGE
        defaultPatientInfoShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the patientInfoList where age equals to UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age is not null
        defaultPatientInfoShouldBeFound("age.specified=true");

        // Get all the patientInfoList where age is null
        defaultPatientInfoShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age contains DEFAULT_AGE
        defaultPatientInfoShouldBeFound("age.contains=" + DEFAULT_AGE);

        // Get all the patientInfoList where age contains UPDATED_AGE
        defaultPatientInfoShouldNotBeFound("age.contains=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where age does not contain DEFAULT_AGE
        defaultPatientInfoShouldNotBeFound("age.doesNotContain=" + DEFAULT_AGE);

        // Get all the patientInfoList where age does not contain UPDATED_AGE
        defaultPatientInfoShouldBeFound("age.doesNotContain=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn equals to DEFAULT_AGE_IN
        defaultPatientInfoShouldBeFound("ageIn.equals=" + DEFAULT_AGE_IN);

        // Get all the patientInfoList where ageIn equals to UPDATED_AGE_IN
        defaultPatientInfoShouldNotBeFound("ageIn.equals=" + UPDATED_AGE_IN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn not equals to DEFAULT_AGE_IN
        defaultPatientInfoShouldNotBeFound("ageIn.notEquals=" + DEFAULT_AGE_IN);

        // Get all the patientInfoList where ageIn not equals to UPDATED_AGE_IN
        defaultPatientInfoShouldBeFound("ageIn.notEquals=" + UPDATED_AGE_IN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn in DEFAULT_AGE_IN or UPDATED_AGE_IN
        defaultPatientInfoShouldBeFound("ageIn.in=" + DEFAULT_AGE_IN + "," + UPDATED_AGE_IN);

        // Get all the patientInfoList where ageIn equals to UPDATED_AGE_IN
        defaultPatientInfoShouldNotBeFound("ageIn.in=" + UPDATED_AGE_IN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn is not null
        defaultPatientInfoShouldBeFound("ageIn.specified=true");

        // Get all the patientInfoList where ageIn is null
        defaultPatientInfoShouldNotBeFound("ageIn.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn contains DEFAULT_AGE_IN
        defaultPatientInfoShouldBeFound("ageIn.contains=" + DEFAULT_AGE_IN);

        // Get all the patientInfoList where ageIn contains UPDATED_AGE_IN
        defaultPatientInfoShouldNotBeFound("ageIn.contains=" + UPDATED_AGE_IN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAgeInNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ageIn does not contain DEFAULT_AGE_IN
        defaultPatientInfoShouldNotBeFound("ageIn.doesNotContain=" + DEFAULT_AGE_IN);

        // Get all the patientInfoList where ageIn does not contain UPDATED_AGE_IN
        defaultPatientInfoShouldBeFound("ageIn.doesNotContain=" + UPDATED_AGE_IN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender equals to DEFAULT_GENDER
        defaultPatientInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender equals to UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender not equals to DEFAULT_GENDER
        defaultPatientInfoShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender not equals to UPDATED_GENDER
        defaultPatientInfoShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPatientInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the patientInfoList where gender equals to UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender is not null
        defaultPatientInfoShouldBeFound("gender.specified=true");

        // Get all the patientInfoList where gender is null
        defaultPatientInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender contains DEFAULT_GENDER
        defaultPatientInfoShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender contains UPDATED_GENDER
        defaultPatientInfoShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where gender does not contain DEFAULT_GENDER
        defaultPatientInfoShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the patientInfoList where gender does not contain UPDATED_GENDER
        defaultPatientInfoShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality equals to DEFAULT_NATIONALITY
        defaultPatientInfoShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the patientInfoList where nationality equals to UPDATED_NATIONALITY
        defaultPatientInfoShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality not equals to DEFAULT_NATIONALITY
        defaultPatientInfoShouldNotBeFound("nationality.notEquals=" + DEFAULT_NATIONALITY);

        // Get all the patientInfoList where nationality not equals to UPDATED_NATIONALITY
        defaultPatientInfoShouldBeFound("nationality.notEquals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultPatientInfoShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the patientInfoList where nationality equals to UPDATED_NATIONALITY
        defaultPatientInfoShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality is not null
        defaultPatientInfoShouldBeFound("nationality.specified=true");

        // Get all the patientInfoList where nationality is null
        defaultPatientInfoShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality contains DEFAULT_NATIONALITY
        defaultPatientInfoShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the patientInfoList where nationality contains UPDATED_NATIONALITY
        defaultPatientInfoShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where nationality does not contain DEFAULT_NATIONALITY
        defaultPatientInfoShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the patientInfoList where nationality does not contain UPDATED_NATIONALITY
        defaultPatientInfoShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address equals to DEFAULT_ADDRESS
        defaultPatientInfoShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the patientInfoList where address equals to UPDATED_ADDRESS
        defaultPatientInfoShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address not equals to DEFAULT_ADDRESS
        defaultPatientInfoShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the patientInfoList where address not equals to UPDATED_ADDRESS
        defaultPatientInfoShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultPatientInfoShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the patientInfoList where address equals to UPDATED_ADDRESS
        defaultPatientInfoShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address is not null
        defaultPatientInfoShouldBeFound("address.specified=true");

        // Get all the patientInfoList where address is null
        defaultPatientInfoShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address contains DEFAULT_ADDRESS
        defaultPatientInfoShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the patientInfoList where address contains UPDATED_ADDRESS
        defaultPatientInfoShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where address does not contain DEFAULT_ADDRESS
        defaultPatientInfoShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the patientInfoList where address does not contain UPDATED_ADDRESS
        defaultPatientInfoShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown equals to DEFAULT_VILLAGE_TOWN
        defaultPatientInfoShouldBeFound("villageTown.equals=" + DEFAULT_VILLAGE_TOWN);

        // Get all the patientInfoList where villageTown equals to UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldNotBeFound("villageTown.equals=" + UPDATED_VILLAGE_TOWN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown not equals to DEFAULT_VILLAGE_TOWN
        defaultPatientInfoShouldNotBeFound("villageTown.notEquals=" + DEFAULT_VILLAGE_TOWN);

        // Get all the patientInfoList where villageTown not equals to UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldBeFound("villageTown.notEquals=" + UPDATED_VILLAGE_TOWN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown in DEFAULT_VILLAGE_TOWN or UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldBeFound("villageTown.in=" + DEFAULT_VILLAGE_TOWN + "," + UPDATED_VILLAGE_TOWN);

        // Get all the patientInfoList where villageTown equals to UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldNotBeFound("villageTown.in=" + UPDATED_VILLAGE_TOWN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown is not null
        defaultPatientInfoShouldBeFound("villageTown.specified=true");

        // Get all the patientInfoList where villageTown is null
        defaultPatientInfoShouldNotBeFound("villageTown.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown contains DEFAULT_VILLAGE_TOWN
        defaultPatientInfoShouldBeFound("villageTown.contains=" + DEFAULT_VILLAGE_TOWN);

        // Get all the patientInfoList where villageTown contains UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldNotBeFound("villageTown.contains=" + UPDATED_VILLAGE_TOWN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByVillageTownNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where villageTown does not contain DEFAULT_VILLAGE_TOWN
        defaultPatientInfoShouldNotBeFound("villageTown.doesNotContain=" + DEFAULT_VILLAGE_TOWN);

        // Get all the patientInfoList where villageTown does not contain UPDATED_VILLAGE_TOWN
        defaultPatientInfoShouldBeFound("villageTown.doesNotContain=" + UPDATED_VILLAGE_TOWN);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode equals to DEFAULT_PINCODE
        defaultPatientInfoShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the patientInfoList where pincode equals to UPDATED_PINCODE
        defaultPatientInfoShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode not equals to DEFAULT_PINCODE
        defaultPatientInfoShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the patientInfoList where pincode not equals to UPDATED_PINCODE
        defaultPatientInfoShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultPatientInfoShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the patientInfoList where pincode equals to UPDATED_PINCODE
        defaultPatientInfoShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode is not null
        defaultPatientInfoShouldBeFound("pincode.specified=true");

        // Get all the patientInfoList where pincode is null
        defaultPatientInfoShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode contains DEFAULT_PINCODE
        defaultPatientInfoShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the patientInfoList where pincode contains UPDATED_PINCODE
        defaultPatientInfoShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where pincode does not contain DEFAULT_PINCODE
        defaultPatientInfoShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the patientInfoList where pincode does not contain UPDATED_PINCODE
        defaultPatientInfoShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory equals to DEFAULT_PATIENT_CATEGORY
        defaultPatientInfoShouldBeFound("patientCategory.equals=" + DEFAULT_PATIENT_CATEGORY);

        // Get all the patientInfoList where patientCategory equals to UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldNotBeFound("patientCategory.equals=" + UPDATED_PATIENT_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory not equals to DEFAULT_PATIENT_CATEGORY
        defaultPatientInfoShouldNotBeFound("patientCategory.notEquals=" + DEFAULT_PATIENT_CATEGORY);

        // Get all the patientInfoList where patientCategory not equals to UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldBeFound("patientCategory.notEquals=" + UPDATED_PATIENT_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory in DEFAULT_PATIENT_CATEGORY or UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldBeFound("patientCategory.in=" + DEFAULT_PATIENT_CATEGORY + "," + UPDATED_PATIENT_CATEGORY);

        // Get all the patientInfoList where patientCategory equals to UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldNotBeFound("patientCategory.in=" + UPDATED_PATIENT_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory is not null
        defaultPatientInfoShouldBeFound("patientCategory.specified=true");

        // Get all the patientInfoList where patientCategory is null
        defaultPatientInfoShouldNotBeFound("patientCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory contains DEFAULT_PATIENT_CATEGORY
        defaultPatientInfoShouldBeFound("patientCategory.contains=" + DEFAULT_PATIENT_CATEGORY);

        // Get all the patientInfoList where patientCategory contains UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldNotBeFound("patientCategory.contains=" + UPDATED_PATIENT_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByPatientCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where patientCategory does not contain DEFAULT_PATIENT_CATEGORY
        defaultPatientInfoShouldNotBeFound("patientCategory.doesNotContain=" + DEFAULT_PATIENT_CATEGORY);

        // Get all the patientInfoList where patientCategory does not contain UPDATED_PATIENT_CATEGORY
        defaultPatientInfoShouldBeFound("patientCategory.doesNotContain=" + UPDATED_PATIENT_CATEGORY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleCollectionIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleCollection equals to DEFAULT_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldBeFound("dateOfSampleCollection.equals=" + DEFAULT_DATE_OF_SAMPLE_COLLECTION);

        // Get all the patientInfoList where dateOfSampleCollection equals to UPDATED_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldNotBeFound("dateOfSampleCollection.equals=" + UPDATED_DATE_OF_SAMPLE_COLLECTION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleCollectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleCollection not equals to DEFAULT_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldNotBeFound("dateOfSampleCollection.notEquals=" + DEFAULT_DATE_OF_SAMPLE_COLLECTION);

        // Get all the patientInfoList where dateOfSampleCollection not equals to UPDATED_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldBeFound("dateOfSampleCollection.notEquals=" + UPDATED_DATE_OF_SAMPLE_COLLECTION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleCollectionIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleCollection in DEFAULT_DATE_OF_SAMPLE_COLLECTION or UPDATED_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldBeFound(
            "dateOfSampleCollection.in=" + DEFAULT_DATE_OF_SAMPLE_COLLECTION + "," + UPDATED_DATE_OF_SAMPLE_COLLECTION
        );

        // Get all the patientInfoList where dateOfSampleCollection equals to UPDATED_DATE_OF_SAMPLE_COLLECTION
        defaultPatientInfoShouldNotBeFound("dateOfSampleCollection.in=" + UPDATED_DATE_OF_SAMPLE_COLLECTION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleCollectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleCollection is not null
        defaultPatientInfoShouldBeFound("dateOfSampleCollection.specified=true");

        // Get all the patientInfoList where dateOfSampleCollection is null
        defaultPatientInfoShouldNotBeFound("dateOfSampleCollection.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleReceived equals to DEFAULT_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldBeFound("dateOfSampleReceived.equals=" + DEFAULT_DATE_OF_SAMPLE_RECEIVED);

        // Get all the patientInfoList where dateOfSampleReceived equals to UPDATED_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldNotBeFound("dateOfSampleReceived.equals=" + UPDATED_DATE_OF_SAMPLE_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleReceived not equals to DEFAULT_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldNotBeFound("dateOfSampleReceived.notEquals=" + DEFAULT_DATE_OF_SAMPLE_RECEIVED);

        // Get all the patientInfoList where dateOfSampleReceived not equals to UPDATED_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldBeFound("dateOfSampleReceived.notEquals=" + UPDATED_DATE_OF_SAMPLE_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleReceived in DEFAULT_DATE_OF_SAMPLE_RECEIVED or UPDATED_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldBeFound(
            "dateOfSampleReceived.in=" + DEFAULT_DATE_OF_SAMPLE_RECEIVED + "," + UPDATED_DATE_OF_SAMPLE_RECEIVED
        );

        // Get all the patientInfoList where dateOfSampleReceived equals to UPDATED_DATE_OF_SAMPLE_RECEIVED
        defaultPatientInfoShouldNotBeFound("dateOfSampleReceived.in=" + UPDATED_DATE_OF_SAMPLE_RECEIVED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleReceived is not null
        defaultPatientInfoShouldBeFound("dateOfSampleReceived.specified=true");

        // Get all the patientInfoList where dateOfSampleReceived is null
        defaultPatientInfoShouldNotBeFound("dateOfSampleReceived.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType equals to DEFAULT_SAMPLE_TYPE
        defaultPatientInfoShouldBeFound("sampleType.equals=" + DEFAULT_SAMPLE_TYPE);

        // Get all the patientInfoList where sampleType equals to UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldNotBeFound("sampleType.equals=" + UPDATED_SAMPLE_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType not equals to DEFAULT_SAMPLE_TYPE
        defaultPatientInfoShouldNotBeFound("sampleType.notEquals=" + DEFAULT_SAMPLE_TYPE);

        // Get all the patientInfoList where sampleType not equals to UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldBeFound("sampleType.notEquals=" + UPDATED_SAMPLE_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType in DEFAULT_SAMPLE_TYPE or UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldBeFound("sampleType.in=" + DEFAULT_SAMPLE_TYPE + "," + UPDATED_SAMPLE_TYPE);

        // Get all the patientInfoList where sampleType equals to UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldNotBeFound("sampleType.in=" + UPDATED_SAMPLE_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType is not null
        defaultPatientInfoShouldBeFound("sampleType.specified=true");

        // Get all the patientInfoList where sampleType is null
        defaultPatientInfoShouldNotBeFound("sampleType.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType contains DEFAULT_SAMPLE_TYPE
        defaultPatientInfoShouldBeFound("sampleType.contains=" + DEFAULT_SAMPLE_TYPE);

        // Get all the patientInfoList where sampleType contains UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldNotBeFound("sampleType.contains=" + UPDATED_SAMPLE_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleTypeNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleType does not contain DEFAULT_SAMPLE_TYPE
        defaultPatientInfoShouldNotBeFound("sampleType.doesNotContain=" + DEFAULT_SAMPLE_TYPE);

        // Get all the patientInfoList where sampleType does not contain UPDATED_SAMPLE_TYPE
        defaultPatientInfoShouldBeFound("sampleType.doesNotContain=" + UPDATED_SAMPLE_TYPE);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId equals to DEFAULT_SAMPLE_ID
        defaultPatientInfoShouldBeFound("sampleId.equals=" + DEFAULT_SAMPLE_ID);

        // Get all the patientInfoList where sampleId equals to UPDATED_SAMPLE_ID
        defaultPatientInfoShouldNotBeFound("sampleId.equals=" + UPDATED_SAMPLE_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId not equals to DEFAULT_SAMPLE_ID
        defaultPatientInfoShouldNotBeFound("sampleId.notEquals=" + DEFAULT_SAMPLE_ID);

        // Get all the patientInfoList where sampleId not equals to UPDATED_SAMPLE_ID
        defaultPatientInfoShouldBeFound("sampleId.notEquals=" + UPDATED_SAMPLE_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId in DEFAULT_SAMPLE_ID or UPDATED_SAMPLE_ID
        defaultPatientInfoShouldBeFound("sampleId.in=" + DEFAULT_SAMPLE_ID + "," + UPDATED_SAMPLE_ID);

        // Get all the patientInfoList where sampleId equals to UPDATED_SAMPLE_ID
        defaultPatientInfoShouldNotBeFound("sampleId.in=" + UPDATED_SAMPLE_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId is not null
        defaultPatientInfoShouldBeFound("sampleId.specified=true");

        // Get all the patientInfoList where sampleId is null
        defaultPatientInfoShouldNotBeFound("sampleId.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId contains DEFAULT_SAMPLE_ID
        defaultPatientInfoShouldBeFound("sampleId.contains=" + DEFAULT_SAMPLE_ID);

        // Get all the patientInfoList where sampleId contains UPDATED_SAMPLE_ID
        defaultPatientInfoShouldNotBeFound("sampleId.contains=" + UPDATED_SAMPLE_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySampleIdNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where sampleId does not contain DEFAULT_SAMPLE_ID
        defaultPatientInfoShouldNotBeFound("sampleId.doesNotContain=" + DEFAULT_SAMPLE_ID);

        // Get all the patientInfoList where sampleId does not contain UPDATED_SAMPLE_ID
        defaultPatientInfoShouldBeFound("sampleId.doesNotContain=" + UPDATED_SAMPLE_ID);
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition equals to DEFAULT_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldBeFound("underlyingMedicalCondition.equals=" + DEFAULT_UNDERLYING_MEDICAL_CONDITION);

        // Get all the patientInfoList where underlyingMedicalCondition equals to UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.equals=" + UPDATED_UNDERLYING_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition not equals to DEFAULT_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.notEquals=" + DEFAULT_UNDERLYING_MEDICAL_CONDITION);

        // Get all the patientInfoList where underlyingMedicalCondition not equals to UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldBeFound("underlyingMedicalCondition.notEquals=" + UPDATED_UNDERLYING_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition in DEFAULT_UNDERLYING_MEDICAL_CONDITION or UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldBeFound(
            "underlyingMedicalCondition.in=" + DEFAULT_UNDERLYING_MEDICAL_CONDITION + "," + UPDATED_UNDERLYING_MEDICAL_CONDITION
        );

        // Get all the patientInfoList where underlyingMedicalCondition equals to UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.in=" + UPDATED_UNDERLYING_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition is not null
        defaultPatientInfoShouldBeFound("underlyingMedicalCondition.specified=true");

        // Get all the patientInfoList where underlyingMedicalCondition is null
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition contains DEFAULT_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldBeFound("underlyingMedicalCondition.contains=" + DEFAULT_UNDERLYING_MEDICAL_CONDITION);

        // Get all the patientInfoList where underlyingMedicalCondition contains UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.contains=" + UPDATED_UNDERLYING_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByUnderlyingMedicalConditionNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where underlyingMedicalCondition does not contain DEFAULT_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldNotBeFound("underlyingMedicalCondition.doesNotContain=" + DEFAULT_UNDERLYING_MEDICAL_CONDITION);

        // Get all the patientInfoList where underlyingMedicalCondition does not contain UPDATED_UNDERLYING_MEDICAL_CONDITION
        defaultPatientInfoShouldBeFound("underlyingMedicalCondition.doesNotContain=" + UPDATED_UNDERLYING_MEDICAL_CONDITION);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized equals to DEFAULT_HOSPITALIZED
        defaultPatientInfoShouldBeFound("hospitalized.equals=" + DEFAULT_HOSPITALIZED);

        // Get all the patientInfoList where hospitalized equals to UPDATED_HOSPITALIZED
        defaultPatientInfoShouldNotBeFound("hospitalized.equals=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized not equals to DEFAULT_HOSPITALIZED
        defaultPatientInfoShouldNotBeFound("hospitalized.notEquals=" + DEFAULT_HOSPITALIZED);

        // Get all the patientInfoList where hospitalized not equals to UPDATED_HOSPITALIZED
        defaultPatientInfoShouldBeFound("hospitalized.notEquals=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized in DEFAULT_HOSPITALIZED or UPDATED_HOSPITALIZED
        defaultPatientInfoShouldBeFound("hospitalized.in=" + DEFAULT_HOSPITALIZED + "," + UPDATED_HOSPITALIZED);

        // Get all the patientInfoList where hospitalized equals to UPDATED_HOSPITALIZED
        defaultPatientInfoShouldNotBeFound("hospitalized.in=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized is not null
        defaultPatientInfoShouldBeFound("hospitalized.specified=true");

        // Get all the patientInfoList where hospitalized is null
        defaultPatientInfoShouldNotBeFound("hospitalized.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized contains DEFAULT_HOSPITALIZED
        defaultPatientInfoShouldBeFound("hospitalized.contains=" + DEFAULT_HOSPITALIZED);

        // Get all the patientInfoList where hospitalized contains UPDATED_HOSPITALIZED
        defaultPatientInfoShouldNotBeFound("hospitalized.contains=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizedNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalized does not contain DEFAULT_HOSPITALIZED
        defaultPatientInfoShouldNotBeFound("hospitalized.doesNotContain=" + DEFAULT_HOSPITALIZED);

        // Get all the patientInfoList where hospitalized does not contain UPDATED_HOSPITALIZED
        defaultPatientInfoShouldBeFound("hospitalized.doesNotContain=" + UPDATED_HOSPITALIZED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName equals to DEFAULT_HOSPITAL_NAME
        defaultPatientInfoShouldBeFound("hospitalName.equals=" + DEFAULT_HOSPITAL_NAME);

        // Get all the patientInfoList where hospitalName equals to UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldNotBeFound("hospitalName.equals=" + UPDATED_HOSPITAL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName not equals to DEFAULT_HOSPITAL_NAME
        defaultPatientInfoShouldNotBeFound("hospitalName.notEquals=" + DEFAULT_HOSPITAL_NAME);

        // Get all the patientInfoList where hospitalName not equals to UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldBeFound("hospitalName.notEquals=" + UPDATED_HOSPITAL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName in DEFAULT_HOSPITAL_NAME or UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldBeFound("hospitalName.in=" + DEFAULT_HOSPITAL_NAME + "," + UPDATED_HOSPITAL_NAME);

        // Get all the patientInfoList where hospitalName equals to UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldNotBeFound("hospitalName.in=" + UPDATED_HOSPITAL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName is not null
        defaultPatientInfoShouldBeFound("hospitalName.specified=true");

        // Get all the patientInfoList where hospitalName is null
        defaultPatientInfoShouldNotBeFound("hospitalName.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName contains DEFAULT_HOSPITAL_NAME
        defaultPatientInfoShouldBeFound("hospitalName.contains=" + DEFAULT_HOSPITAL_NAME);

        // Get all the patientInfoList where hospitalName contains UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldNotBeFound("hospitalName.contains=" + UPDATED_HOSPITAL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalName does not contain DEFAULT_HOSPITAL_NAME
        defaultPatientInfoShouldNotBeFound("hospitalName.doesNotContain=" + DEFAULT_HOSPITAL_NAME);

        // Get all the patientInfoList where hospitalName does not contain UPDATED_HOSPITAL_NAME
        defaultPatientInfoShouldBeFound("hospitalName.doesNotContain=" + UPDATED_HOSPITAL_NAME);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalizationDate equals to DEFAULT_HOSPITALIZATION_DATE
        defaultPatientInfoShouldBeFound("hospitalizationDate.equals=" + DEFAULT_HOSPITALIZATION_DATE);

        // Get all the patientInfoList where hospitalizationDate equals to UPDATED_HOSPITALIZATION_DATE
        defaultPatientInfoShouldNotBeFound("hospitalizationDate.equals=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalizationDate not equals to DEFAULT_HOSPITALIZATION_DATE
        defaultPatientInfoShouldNotBeFound("hospitalizationDate.notEquals=" + DEFAULT_HOSPITALIZATION_DATE);

        // Get all the patientInfoList where hospitalizationDate not equals to UPDATED_HOSPITALIZATION_DATE
        defaultPatientInfoShouldBeFound("hospitalizationDate.notEquals=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalizationDate in DEFAULT_HOSPITALIZATION_DATE or UPDATED_HOSPITALIZATION_DATE
        defaultPatientInfoShouldBeFound("hospitalizationDate.in=" + DEFAULT_HOSPITALIZATION_DATE + "," + UPDATED_HOSPITALIZATION_DATE);

        // Get all the patientInfoList where hospitalizationDate equals to UPDATED_HOSPITALIZATION_DATE
        defaultPatientInfoShouldNotBeFound("hospitalizationDate.in=" + UPDATED_HOSPITALIZATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalizationDate is not null
        defaultPatientInfoShouldBeFound("hospitalizationDate.specified=true");

        // Get all the patientInfoList where hospitalizationDate is null
        defaultPatientInfoShouldNotBeFound("hospitalizationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState equals to DEFAULT_HOSPITAL_STATE
        defaultPatientInfoShouldBeFound("hospitalState.equals=" + DEFAULT_HOSPITAL_STATE);

        // Get all the patientInfoList where hospitalState equals to UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldNotBeFound("hospitalState.equals=" + UPDATED_HOSPITAL_STATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState not equals to DEFAULT_HOSPITAL_STATE
        defaultPatientInfoShouldNotBeFound("hospitalState.notEquals=" + DEFAULT_HOSPITAL_STATE);

        // Get all the patientInfoList where hospitalState not equals to UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldBeFound("hospitalState.notEquals=" + UPDATED_HOSPITAL_STATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState in DEFAULT_HOSPITAL_STATE or UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldBeFound("hospitalState.in=" + DEFAULT_HOSPITAL_STATE + "," + UPDATED_HOSPITAL_STATE);

        // Get all the patientInfoList where hospitalState equals to UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldNotBeFound("hospitalState.in=" + UPDATED_HOSPITAL_STATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState is not null
        defaultPatientInfoShouldBeFound("hospitalState.specified=true");

        // Get all the patientInfoList where hospitalState is null
        defaultPatientInfoShouldNotBeFound("hospitalState.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState contains DEFAULT_HOSPITAL_STATE
        defaultPatientInfoShouldBeFound("hospitalState.contains=" + DEFAULT_HOSPITAL_STATE);

        // Get all the patientInfoList where hospitalState contains UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldNotBeFound("hospitalState.contains=" + UPDATED_HOSPITAL_STATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalStateNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalState does not contain DEFAULT_HOSPITAL_STATE
        defaultPatientInfoShouldNotBeFound("hospitalState.doesNotContain=" + DEFAULT_HOSPITAL_STATE);

        // Get all the patientInfoList where hospitalState does not contain UPDATED_HOSPITAL_STATE
        defaultPatientInfoShouldBeFound("hospitalState.doesNotContain=" + UPDATED_HOSPITAL_STATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict equals to DEFAULT_HOSPITAL_DISTRICT
        defaultPatientInfoShouldBeFound("hospitalDistrict.equals=" + DEFAULT_HOSPITAL_DISTRICT);

        // Get all the patientInfoList where hospitalDistrict equals to UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.equals=" + UPDATED_HOSPITAL_DISTRICT);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict not equals to DEFAULT_HOSPITAL_DISTRICT
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.notEquals=" + DEFAULT_HOSPITAL_DISTRICT);

        // Get all the patientInfoList where hospitalDistrict not equals to UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldBeFound("hospitalDistrict.notEquals=" + UPDATED_HOSPITAL_DISTRICT);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict in DEFAULT_HOSPITAL_DISTRICT or UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldBeFound("hospitalDistrict.in=" + DEFAULT_HOSPITAL_DISTRICT + "," + UPDATED_HOSPITAL_DISTRICT);

        // Get all the patientInfoList where hospitalDistrict equals to UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.in=" + UPDATED_HOSPITAL_DISTRICT);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict is not null
        defaultPatientInfoShouldBeFound("hospitalDistrict.specified=true");

        // Get all the patientInfoList where hospitalDistrict is null
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict contains DEFAULT_HOSPITAL_DISTRICT
        defaultPatientInfoShouldBeFound("hospitalDistrict.contains=" + DEFAULT_HOSPITAL_DISTRICT);

        // Get all the patientInfoList where hospitalDistrict contains UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.contains=" + UPDATED_HOSPITAL_DISTRICT);
    }

    @Test
    @Transactional
    void getAllPatientInfosByHospitalDistrictNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where hospitalDistrict does not contain DEFAULT_HOSPITAL_DISTRICT
        defaultPatientInfoShouldNotBeFound("hospitalDistrict.doesNotContain=" + DEFAULT_HOSPITAL_DISTRICT);

        // Get all the patientInfoList where hospitalDistrict does not contain UPDATED_HOSPITAL_DISTRICT
        defaultPatientInfoShouldBeFound("hospitalDistrict.doesNotContain=" + UPDATED_HOSPITAL_DISTRICT);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus equals to DEFAULT_SYMPTOMS_STATUS
        defaultPatientInfoShouldBeFound("symptomsStatus.equals=" + DEFAULT_SYMPTOMS_STATUS);

        // Get all the patientInfoList where symptomsStatus equals to UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldNotBeFound("symptomsStatus.equals=" + UPDATED_SYMPTOMS_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus not equals to DEFAULT_SYMPTOMS_STATUS
        defaultPatientInfoShouldNotBeFound("symptomsStatus.notEquals=" + DEFAULT_SYMPTOMS_STATUS);

        // Get all the patientInfoList where symptomsStatus not equals to UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldBeFound("symptomsStatus.notEquals=" + UPDATED_SYMPTOMS_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus in DEFAULT_SYMPTOMS_STATUS or UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldBeFound("symptomsStatus.in=" + DEFAULT_SYMPTOMS_STATUS + "," + UPDATED_SYMPTOMS_STATUS);

        // Get all the patientInfoList where symptomsStatus equals to UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldNotBeFound("symptomsStatus.in=" + UPDATED_SYMPTOMS_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus is not null
        defaultPatientInfoShouldBeFound("symptomsStatus.specified=true");

        // Get all the patientInfoList where symptomsStatus is null
        defaultPatientInfoShouldNotBeFound("symptomsStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus contains DEFAULT_SYMPTOMS_STATUS
        defaultPatientInfoShouldBeFound("symptomsStatus.contains=" + DEFAULT_SYMPTOMS_STATUS);

        // Get all the patientInfoList where symptomsStatus contains UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldNotBeFound("symptomsStatus.contains=" + UPDATED_SYMPTOMS_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsStatusNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptomsStatus does not contain DEFAULT_SYMPTOMS_STATUS
        defaultPatientInfoShouldNotBeFound("symptomsStatus.doesNotContain=" + DEFAULT_SYMPTOMS_STATUS);

        // Get all the patientInfoList where symptomsStatus does not contain UPDATED_SYMPTOMS_STATUS
        defaultPatientInfoShouldBeFound("symptomsStatus.doesNotContain=" + UPDATED_SYMPTOMS_STATUS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms equals to DEFAULT_SYMPTOMS
        defaultPatientInfoShouldBeFound("symptoms.equals=" + DEFAULT_SYMPTOMS);

        // Get all the patientInfoList where symptoms equals to UPDATED_SYMPTOMS
        defaultPatientInfoShouldNotBeFound("symptoms.equals=" + UPDATED_SYMPTOMS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms not equals to DEFAULT_SYMPTOMS
        defaultPatientInfoShouldNotBeFound("symptoms.notEquals=" + DEFAULT_SYMPTOMS);

        // Get all the patientInfoList where symptoms not equals to UPDATED_SYMPTOMS
        defaultPatientInfoShouldBeFound("symptoms.notEquals=" + UPDATED_SYMPTOMS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms in DEFAULT_SYMPTOMS or UPDATED_SYMPTOMS
        defaultPatientInfoShouldBeFound("symptoms.in=" + DEFAULT_SYMPTOMS + "," + UPDATED_SYMPTOMS);

        // Get all the patientInfoList where symptoms equals to UPDATED_SYMPTOMS
        defaultPatientInfoShouldNotBeFound("symptoms.in=" + UPDATED_SYMPTOMS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms is not null
        defaultPatientInfoShouldBeFound("symptoms.specified=true");

        // Get all the patientInfoList where symptoms is null
        defaultPatientInfoShouldNotBeFound("symptoms.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms contains DEFAULT_SYMPTOMS
        defaultPatientInfoShouldBeFound("symptoms.contains=" + DEFAULT_SYMPTOMS);

        // Get all the patientInfoList where symptoms contains UPDATED_SYMPTOMS
        defaultPatientInfoShouldNotBeFound("symptoms.contains=" + UPDATED_SYMPTOMS);
    }

    @Test
    @Transactional
    void getAllPatientInfosBySymptomsNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where symptoms does not contain DEFAULT_SYMPTOMS
        defaultPatientInfoShouldNotBeFound("symptoms.doesNotContain=" + DEFAULT_SYMPTOMS);

        // Get all the patientInfoList where symptoms does not contain UPDATED_SYMPTOMS
        defaultPatientInfoShouldBeFound("symptoms.doesNotContain=" + UPDATED_SYMPTOMS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed equals to DEFAULT_TESTING_KIT_USED
        defaultPatientInfoShouldBeFound("testingKitUsed.equals=" + DEFAULT_TESTING_KIT_USED);

        // Get all the patientInfoList where testingKitUsed equals to UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldNotBeFound("testingKitUsed.equals=" + UPDATED_TESTING_KIT_USED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed not equals to DEFAULT_TESTING_KIT_USED
        defaultPatientInfoShouldNotBeFound("testingKitUsed.notEquals=" + DEFAULT_TESTING_KIT_USED);

        // Get all the patientInfoList where testingKitUsed not equals to UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldBeFound("testingKitUsed.notEquals=" + UPDATED_TESTING_KIT_USED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed in DEFAULT_TESTING_KIT_USED or UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldBeFound("testingKitUsed.in=" + DEFAULT_TESTING_KIT_USED + "," + UPDATED_TESTING_KIT_USED);

        // Get all the patientInfoList where testingKitUsed equals to UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldNotBeFound("testingKitUsed.in=" + UPDATED_TESTING_KIT_USED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed is not null
        defaultPatientInfoShouldBeFound("testingKitUsed.specified=true");

        // Get all the patientInfoList where testingKitUsed is null
        defaultPatientInfoShouldNotBeFound("testingKitUsed.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed contains DEFAULT_TESTING_KIT_USED
        defaultPatientInfoShouldBeFound("testingKitUsed.contains=" + DEFAULT_TESTING_KIT_USED);

        // Get all the patientInfoList where testingKitUsed contains UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldNotBeFound("testingKitUsed.contains=" + UPDATED_TESTING_KIT_USED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByTestingKitUsedNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where testingKitUsed does not contain DEFAULT_TESTING_KIT_USED
        defaultPatientInfoShouldNotBeFound("testingKitUsed.doesNotContain=" + DEFAULT_TESTING_KIT_USED);

        // Get all the patientInfoList where testingKitUsed does not contain UPDATED_TESTING_KIT_USED
        defaultPatientInfoShouldBeFound("testingKitUsed.doesNotContain=" + UPDATED_TESTING_KIT_USED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene equals to DEFAULT_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("eGeneNGene.equals=" + DEFAULT_E_GENE_N_GENE);

        // Get all the patientInfoList where eGeneNGene equals to UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("eGeneNGene.equals=" + UPDATED_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene not equals to DEFAULT_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("eGeneNGene.notEquals=" + DEFAULT_E_GENE_N_GENE);

        // Get all the patientInfoList where eGeneNGene not equals to UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("eGeneNGene.notEquals=" + UPDATED_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene in DEFAULT_E_GENE_N_GENE or UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("eGeneNGene.in=" + DEFAULT_E_GENE_N_GENE + "," + UPDATED_E_GENE_N_GENE);

        // Get all the patientInfoList where eGeneNGene equals to UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("eGeneNGene.in=" + UPDATED_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene is not null
        defaultPatientInfoShouldBeFound("eGeneNGene.specified=true");

        // Get all the patientInfoList where eGeneNGene is null
        defaultPatientInfoShouldNotBeFound("eGeneNGene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene contains DEFAULT_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("eGeneNGene.contains=" + DEFAULT_E_GENE_N_GENE);

        // Get all the patientInfoList where eGeneNGene contains UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("eGeneNGene.contains=" + UPDATED_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByeGeneNGeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where eGeneNGene does not contain DEFAULT_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("eGeneNGene.doesNotContain=" + DEFAULT_E_GENE_N_GENE);

        // Get all the patientInfoList where eGeneNGene does not contain UPDATED_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("eGeneNGene.doesNotContain=" + UPDATED_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene equals to DEFAULT_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("ctValueOfEGeneNGene.equals=" + DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);

        // Get all the patientInfoList where ctValueOfEGeneNGene equals to UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.equals=" + UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene not equals to DEFAULT_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.notEquals=" + DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);

        // Get all the patientInfoList where ctValueOfEGeneNGene not equals to UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("ctValueOfEGeneNGene.notEquals=" + UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene in DEFAULT_CT_VALUE_OF_E_GENE_N_GENE or UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound(
            "ctValueOfEGeneNGene.in=" + DEFAULT_CT_VALUE_OF_E_GENE_N_GENE + "," + UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        );

        // Get all the patientInfoList where ctValueOfEGeneNGene equals to UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.in=" + UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene is not null
        defaultPatientInfoShouldBeFound("ctValueOfEGeneNGene.specified=true");

        // Get all the patientInfoList where ctValueOfEGeneNGene is null
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene contains DEFAULT_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("ctValueOfEGeneNGene.contains=" + DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);

        // Get all the patientInfoList where ctValueOfEGeneNGene contains UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.contains=" + UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfEGeneNGeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfEGeneNGene does not contain DEFAULT_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfEGeneNGene.doesNotContain=" + DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);

        // Get all the patientInfoList where ctValueOfEGeneNGene does not contain UPDATED_CT_VALUE_OF_E_GENE_N_GENE
        defaultPatientInfoShouldBeFound("ctValueOfEGeneNGene.doesNotContain=" + UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene equals to DEFAULT_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("rdRpSGene.equals=" + DEFAULT_RD_RP_S_GENE);

        // Get all the patientInfoList where rdRpSGene equals to UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("rdRpSGene.equals=" + UPDATED_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene not equals to DEFAULT_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("rdRpSGene.notEquals=" + DEFAULT_RD_RP_S_GENE);

        // Get all the patientInfoList where rdRpSGene not equals to UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("rdRpSGene.notEquals=" + UPDATED_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene in DEFAULT_RD_RP_S_GENE or UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("rdRpSGene.in=" + DEFAULT_RD_RP_S_GENE + "," + UPDATED_RD_RP_S_GENE);

        // Get all the patientInfoList where rdRpSGene equals to UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("rdRpSGene.in=" + UPDATED_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene is not null
        defaultPatientInfoShouldBeFound("rdRpSGene.specified=true");

        // Get all the patientInfoList where rdRpSGene is null
        defaultPatientInfoShouldNotBeFound("rdRpSGene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene contains DEFAULT_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("rdRpSGene.contains=" + DEFAULT_RD_RP_S_GENE);

        // Get all the patientInfoList where rdRpSGene contains UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("rdRpSGene.contains=" + UPDATED_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRdRpSGeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where rdRpSGene does not contain DEFAULT_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("rdRpSGene.doesNotContain=" + DEFAULT_RD_RP_S_GENE);

        // Get all the patientInfoList where rdRpSGene does not contain UPDATED_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("rdRpSGene.doesNotContain=" + UPDATED_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene equals to DEFAULT_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("ctValueOfRdRpSGene.equals=" + DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);

        // Get all the patientInfoList where ctValueOfRdRpSGene equals to UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.equals=" + UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene not equals to DEFAULT_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.notEquals=" + DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);

        // Get all the patientInfoList where ctValueOfRdRpSGene not equals to UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("ctValueOfRdRpSGene.notEquals=" + UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene in DEFAULT_CT_VALUE_OF_RD_RP_S_GENE or UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound(
            "ctValueOfRdRpSGene.in=" + DEFAULT_CT_VALUE_OF_RD_RP_S_GENE + "," + UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        );

        // Get all the patientInfoList where ctValueOfRdRpSGene equals to UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.in=" + UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene is not null
        defaultPatientInfoShouldBeFound("ctValueOfRdRpSGene.specified=true");

        // Get all the patientInfoList where ctValueOfRdRpSGene is null
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene contains DEFAULT_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("ctValueOfRdRpSGene.contains=" + DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);

        // Get all the patientInfoList where ctValueOfRdRpSGene contains UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.contains=" + UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfRdRpSGeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfRdRpSGene does not contain DEFAULT_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfRdRpSGene.doesNotContain=" + DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);

        // Get all the patientInfoList where ctValueOfRdRpSGene does not contain UPDATED_CT_VALUE_OF_RD_RP_S_GENE
        defaultPatientInfoShouldBeFound("ctValueOfRdRpSGene.doesNotContain=" + UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene equals to DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("oRF1aORF1bNN2Gene.equals=" + DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene equals to UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.equals=" + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene not equals to DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.notEquals=" + DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene not equals to UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("oRF1aORF1bNN2Gene.notEquals=" + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene in DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE or UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound(
            "oRF1aORF1bNN2Gene.in=" + DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE + "," + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        );

        // Get all the patientInfoList where oRF1aORF1bNN2Gene equals to UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.in=" + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene is not null
        defaultPatientInfoShouldBeFound("oRF1aORF1bNN2Gene.specified=true");

        // Get all the patientInfoList where oRF1aORF1bNN2Gene is null
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene contains DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("oRF1aORF1bNN2Gene.contains=" + DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene contains UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.contains=" + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByoRF1aORF1bNN2GeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene does not contain DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("oRF1aORF1bNN2Gene.doesNotContain=" + DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where oRF1aORF1bNN2Gene does not contain UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("oRF1aORF1bNN2Gene.doesNotContain=" + UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene equals to DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("ctValueOfORF1aORF1bNN2Gene.equals=" + DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene equals to UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.equals=" + UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene not equals to DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.notEquals=" + DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene not equals to UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("ctValueOfORF1aORF1bNN2Gene.notEquals=" + UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene in DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE or UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound(
            "ctValueOfORF1aORF1bNN2Gene.in=" +
            DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE +
            "," +
            UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        );

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene equals to UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.in=" + UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene is not null
        defaultPatientInfoShouldBeFound("ctValueOfORF1aORF1bNN2Gene.specified=true");

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene is null
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene contains DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("ctValueOfORF1aORF1bNN2Gene.contains=" + DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene contains UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.contains=" + UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCtValueOfORF1aORF1bNN2GeneNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene does not contain DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldNotBeFound("ctValueOfORF1aORF1bNN2Gene.doesNotContain=" + DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);

        // Get all the patientInfoList where ctValueOfORF1aORF1bNN2Gene does not contain UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE
        defaultPatientInfoShouldBeFound("ctValueOfORF1aORF1bNN2Gene.doesNotContain=" + UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample equals to DEFAULT_REPEAT_SAMPLE
        defaultPatientInfoShouldBeFound("repeatSample.equals=" + DEFAULT_REPEAT_SAMPLE);

        // Get all the patientInfoList where repeatSample equals to UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldNotBeFound("repeatSample.equals=" + UPDATED_REPEAT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample not equals to DEFAULT_REPEAT_SAMPLE
        defaultPatientInfoShouldNotBeFound("repeatSample.notEquals=" + DEFAULT_REPEAT_SAMPLE);

        // Get all the patientInfoList where repeatSample not equals to UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldBeFound("repeatSample.notEquals=" + UPDATED_REPEAT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample in DEFAULT_REPEAT_SAMPLE or UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldBeFound("repeatSample.in=" + DEFAULT_REPEAT_SAMPLE + "," + UPDATED_REPEAT_SAMPLE);

        // Get all the patientInfoList where repeatSample equals to UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldNotBeFound("repeatSample.in=" + UPDATED_REPEAT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample is not null
        defaultPatientInfoShouldBeFound("repeatSample.specified=true");

        // Get all the patientInfoList where repeatSample is null
        defaultPatientInfoShouldNotBeFound("repeatSample.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample contains DEFAULT_REPEAT_SAMPLE
        defaultPatientInfoShouldBeFound("repeatSample.contains=" + DEFAULT_REPEAT_SAMPLE);

        // Get all the patientInfoList where repeatSample contains UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldNotBeFound("repeatSample.contains=" + UPDATED_REPEAT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRepeatSampleNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where repeatSample does not contain DEFAULT_REPEAT_SAMPLE
        defaultPatientInfoShouldNotBeFound("repeatSample.doesNotContain=" + DEFAULT_REPEAT_SAMPLE);

        // Get all the patientInfoList where repeatSample does not contain UPDATED_REPEAT_SAMPLE
        defaultPatientInfoShouldBeFound("repeatSample.doesNotContain=" + UPDATED_REPEAT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleTestedIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleTested equals to DEFAULT_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldBeFound("dateOfSampleTested.equals=" + DEFAULT_DATE_OF_SAMPLE_TESTED);

        // Get all the patientInfoList where dateOfSampleTested equals to UPDATED_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldNotBeFound("dateOfSampleTested.equals=" + UPDATED_DATE_OF_SAMPLE_TESTED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleTestedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleTested not equals to DEFAULT_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldNotBeFound("dateOfSampleTested.notEquals=" + DEFAULT_DATE_OF_SAMPLE_TESTED);

        // Get all the patientInfoList where dateOfSampleTested not equals to UPDATED_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldBeFound("dateOfSampleTested.notEquals=" + UPDATED_DATE_OF_SAMPLE_TESTED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleTestedIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleTested in DEFAULT_DATE_OF_SAMPLE_TESTED or UPDATED_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldBeFound("dateOfSampleTested.in=" + DEFAULT_DATE_OF_SAMPLE_TESTED + "," + UPDATED_DATE_OF_SAMPLE_TESTED);

        // Get all the patientInfoList where dateOfSampleTested equals to UPDATED_DATE_OF_SAMPLE_TESTED
        defaultPatientInfoShouldNotBeFound("dateOfSampleTested.in=" + UPDATED_DATE_OF_SAMPLE_TESTED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByDateOfSampleTestedIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where dateOfSampleTested is not null
        defaultPatientInfoShouldBeFound("dateOfSampleTested.specified=true");

        // Get all the patientInfoList where dateOfSampleTested is null
        defaultPatientInfoShouldNotBeFound("dateOfSampleTested.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByEntryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where entryDate equals to DEFAULT_ENTRY_DATE
        defaultPatientInfoShouldBeFound("entryDate.equals=" + DEFAULT_ENTRY_DATE);

        // Get all the patientInfoList where entryDate equals to UPDATED_ENTRY_DATE
        defaultPatientInfoShouldNotBeFound("entryDate.equals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEntryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where entryDate not equals to DEFAULT_ENTRY_DATE
        defaultPatientInfoShouldNotBeFound("entryDate.notEquals=" + DEFAULT_ENTRY_DATE);

        // Get all the patientInfoList where entryDate not equals to UPDATED_ENTRY_DATE
        defaultPatientInfoShouldBeFound("entryDate.notEquals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEntryDateIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where entryDate in DEFAULT_ENTRY_DATE or UPDATED_ENTRY_DATE
        defaultPatientInfoShouldBeFound("entryDate.in=" + DEFAULT_ENTRY_DATE + "," + UPDATED_ENTRY_DATE);

        // Get all the patientInfoList where entryDate equals to UPDATED_ENTRY_DATE
        defaultPatientInfoShouldNotBeFound("entryDate.in=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEntryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where entryDate is not null
        defaultPatientInfoShouldBeFound("entryDate.specified=true");

        // Get all the patientInfoList where entryDate is null
        defaultPatientInfoShouldNotBeFound("entryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByConfirmationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where confirmationDate equals to DEFAULT_CONFIRMATION_DATE
        defaultPatientInfoShouldBeFound("confirmationDate.equals=" + DEFAULT_CONFIRMATION_DATE);

        // Get all the patientInfoList where confirmationDate equals to UPDATED_CONFIRMATION_DATE
        defaultPatientInfoShouldNotBeFound("confirmationDate.equals=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByConfirmationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where confirmationDate not equals to DEFAULT_CONFIRMATION_DATE
        defaultPatientInfoShouldNotBeFound("confirmationDate.notEquals=" + DEFAULT_CONFIRMATION_DATE);

        // Get all the patientInfoList where confirmationDate not equals to UPDATED_CONFIRMATION_DATE
        defaultPatientInfoShouldBeFound("confirmationDate.notEquals=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByConfirmationDateIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where confirmationDate in DEFAULT_CONFIRMATION_DATE or UPDATED_CONFIRMATION_DATE
        defaultPatientInfoShouldBeFound("confirmationDate.in=" + DEFAULT_CONFIRMATION_DATE + "," + UPDATED_CONFIRMATION_DATE);

        // Get all the patientInfoList where confirmationDate equals to UPDATED_CONFIRMATION_DATE
        defaultPatientInfoShouldNotBeFound("confirmationDate.in=" + UPDATED_CONFIRMATION_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByConfirmationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where confirmationDate is not null
        defaultPatientInfoShouldBeFound("confirmationDate.specified=true");

        // Get all the patientInfoList where confirmationDate is null
        defaultPatientInfoShouldNotBeFound("confirmationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample equals to DEFAULT_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldBeFound("finalResultSample.equals=" + DEFAULT_FINAL_RESULT_SAMPLE);

        // Get all the patientInfoList where finalResultSample equals to UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldNotBeFound("finalResultSample.equals=" + UPDATED_FINAL_RESULT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample not equals to DEFAULT_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldNotBeFound("finalResultSample.notEquals=" + DEFAULT_FINAL_RESULT_SAMPLE);

        // Get all the patientInfoList where finalResultSample not equals to UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldBeFound("finalResultSample.notEquals=" + UPDATED_FINAL_RESULT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample in DEFAULT_FINAL_RESULT_SAMPLE or UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldBeFound("finalResultSample.in=" + DEFAULT_FINAL_RESULT_SAMPLE + "," + UPDATED_FINAL_RESULT_SAMPLE);

        // Get all the patientInfoList where finalResultSample equals to UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldNotBeFound("finalResultSample.in=" + UPDATED_FINAL_RESULT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample is not null
        defaultPatientInfoShouldBeFound("finalResultSample.specified=true");

        // Get all the patientInfoList where finalResultSample is null
        defaultPatientInfoShouldNotBeFound("finalResultSample.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample contains DEFAULT_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldBeFound("finalResultSample.contains=" + DEFAULT_FINAL_RESULT_SAMPLE);

        // Get all the patientInfoList where finalResultSample contains UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldNotBeFound("finalResultSample.contains=" + UPDATED_FINAL_RESULT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByFinalResultSampleNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where finalResultSample does not contain DEFAULT_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldNotBeFound("finalResultSample.doesNotContain=" + DEFAULT_FINAL_RESULT_SAMPLE);

        // Get all the patientInfoList where finalResultSample does not contain UPDATED_FINAL_RESULT_SAMPLE
        defaultPatientInfoShouldBeFound("finalResultSample.doesNotContain=" + UPDATED_FINAL_RESULT_SAMPLE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks equals to DEFAULT_REMARKS
        defaultPatientInfoShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the patientInfoList where remarks equals to UPDATED_REMARKS
        defaultPatientInfoShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks not equals to DEFAULT_REMARKS
        defaultPatientInfoShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the patientInfoList where remarks not equals to UPDATED_REMARKS
        defaultPatientInfoShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultPatientInfoShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the patientInfoList where remarks equals to UPDATED_REMARKS
        defaultPatientInfoShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks is not null
        defaultPatientInfoShouldBeFound("remarks.specified=true");

        // Get all the patientInfoList where remarks is null
        defaultPatientInfoShouldNotBeFound("remarks.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks contains DEFAULT_REMARKS
        defaultPatientInfoShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the patientInfoList where remarks contains UPDATED_REMARKS
        defaultPatientInfoShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where remarks does not contain DEFAULT_REMARKS
        defaultPatientInfoShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the patientInfoList where remarks does not contain UPDATED_REMARKS
        defaultPatientInfoShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEditedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where editedOn equals to DEFAULT_EDITED_ON
        defaultPatientInfoShouldBeFound("editedOn.equals=" + DEFAULT_EDITED_ON);

        // Get all the patientInfoList where editedOn equals to UPDATED_EDITED_ON
        defaultPatientInfoShouldNotBeFound("editedOn.equals=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEditedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where editedOn not equals to DEFAULT_EDITED_ON
        defaultPatientInfoShouldNotBeFound("editedOn.notEquals=" + DEFAULT_EDITED_ON);

        // Get all the patientInfoList where editedOn not equals to UPDATED_EDITED_ON
        defaultPatientInfoShouldBeFound("editedOn.notEquals=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEditedOnIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where editedOn in DEFAULT_EDITED_ON or UPDATED_EDITED_ON
        defaultPatientInfoShouldBeFound("editedOn.in=" + DEFAULT_EDITED_ON + "," + UPDATED_EDITED_ON);

        // Get all the patientInfoList where editedOn equals to UPDATED_EDITED_ON
        defaultPatientInfoShouldNotBeFound("editedOn.in=" + UPDATED_EDITED_ON);
    }

    @Test
    @Transactional
    void getAllPatientInfosByEditedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where editedOn is not null
        defaultPatientInfoShouldBeFound("editedOn.specified=true");

        // Get all the patientInfoList where editedOn is null
        defaultPatientInfoShouldNotBeFound("editedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByCcmsPullDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ccmsPullDate equals to DEFAULT_CCMS_PULL_DATE
        defaultPatientInfoShouldBeFound("ccmsPullDate.equals=" + DEFAULT_CCMS_PULL_DATE);

        // Get all the patientInfoList where ccmsPullDate equals to UPDATED_CCMS_PULL_DATE
        defaultPatientInfoShouldNotBeFound("ccmsPullDate.equals=" + UPDATED_CCMS_PULL_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCcmsPullDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ccmsPullDate not equals to DEFAULT_CCMS_PULL_DATE
        defaultPatientInfoShouldNotBeFound("ccmsPullDate.notEquals=" + DEFAULT_CCMS_PULL_DATE);

        // Get all the patientInfoList where ccmsPullDate not equals to UPDATED_CCMS_PULL_DATE
        defaultPatientInfoShouldBeFound("ccmsPullDate.notEquals=" + UPDATED_CCMS_PULL_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCcmsPullDateIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ccmsPullDate in DEFAULT_CCMS_PULL_DATE or UPDATED_CCMS_PULL_DATE
        defaultPatientInfoShouldBeFound("ccmsPullDate.in=" + DEFAULT_CCMS_PULL_DATE + "," + UPDATED_CCMS_PULL_DATE);

        // Get all the patientInfoList where ccmsPullDate equals to UPDATED_CCMS_PULL_DATE
        defaultPatientInfoShouldNotBeFound("ccmsPullDate.in=" + UPDATED_CCMS_PULL_DATE);
    }

    @Test
    @Transactional
    void getAllPatientInfosByCcmsPullDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where ccmsPullDate is not null
        defaultPatientInfoShouldBeFound("ccmsPullDate.specified=true");

        // Get all the patientInfoList where ccmsPullDate is null
        defaultPatientInfoShouldNotBeFound("ccmsPullDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPatientInfoShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the patientInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPatientInfoShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPatientInfoShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the patientInfoList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPatientInfoShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPatientInfoShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the patientInfoList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPatientInfoShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModified is not null
        defaultPatientInfoShouldBeFound("lastModified.specified=true");

        // Get all the patientInfoList where lastModified is null
        defaultPatientInfoShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPatientInfoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the patientInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the patientInfoList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the patientInfoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy is not null
        defaultPatientInfoShouldBeFound("lastModifiedBy.specified=true");

        // Get all the patientInfoList where lastModifiedBy is null
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPatientInfoShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the patientInfoList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        // Get all the patientInfoList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPatientInfoShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the patientInfoList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPatientInfoShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPatientInfosByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);
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
        patientInfo.setState(state);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long stateId = state.getId();

        // Get all the patientInfoList where state equals to stateId
        defaultPatientInfoShouldBeFound("stateId.equals=" + stateId);

        // Get all the patientInfoList where state equals to (stateId + 1)
        defaultPatientInfoShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllPatientInfosByDistrictIsEqualToSomething() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);
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
        patientInfo.setDistrict(district);
        patientInfoRepository.saveAndFlush(patientInfo);
        Long districtId = district.getId();

        // Get all the patientInfoList where district equals to districtId
        defaultPatientInfoShouldBeFound("districtId.equals=" + districtId);

        // Get all the patientInfoList where district equals to (districtId + 1)
        defaultPatientInfoShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientInfoShouldBeFound(String filter) throws Exception {
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].icmrId").value(hasItem(DEFAULT_ICMR_ID)))
            .andExpect(jsonPath("$.[*].srfId").value(hasItem(DEFAULT_SRF_ID)))
            .andExpect(jsonPath("$.[*].labName").value(hasItem(DEFAULT_LAB_NAME)))
            .andExpect(jsonPath("$.[*].patientID").value(hasItem(DEFAULT_PATIENT_ID)))
            .andExpect(jsonPath("$.[*].patientName").value(hasItem(DEFAULT_PATIENT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].ageIn").value(hasItem(DEFAULT_AGE_IN)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].villageTown").value(hasItem(DEFAULT_VILLAGE_TOWN)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].patientCategory").value(hasItem(DEFAULT_PATIENT_CATEGORY)))
            .andExpect(jsonPath("$.[*].dateOfSampleCollection").value(hasItem(DEFAULT_DATE_OF_SAMPLE_COLLECTION.toString())))
            .andExpect(jsonPath("$.[*].dateOfSampleReceived").value(hasItem(DEFAULT_DATE_OF_SAMPLE_RECEIVED.toString())))
            .andExpect(jsonPath("$.[*].sampleType").value(hasItem(DEFAULT_SAMPLE_TYPE)))
            .andExpect(jsonPath("$.[*].sampleId").value(hasItem(DEFAULT_SAMPLE_ID)))
            .andExpect(jsonPath("$.[*].underlyingMedicalCondition").value(hasItem(DEFAULT_UNDERLYING_MEDICAL_CONDITION)))
            .andExpect(jsonPath("$.[*].hospitalized").value(hasItem(DEFAULT_HOSPITALIZED)))
            .andExpect(jsonPath("$.[*].hospitalName").value(hasItem(DEFAULT_HOSPITAL_NAME)))
            .andExpect(jsonPath("$.[*].hospitalizationDate").value(hasItem(DEFAULT_HOSPITALIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].hospitalState").value(hasItem(DEFAULT_HOSPITAL_STATE)))
            .andExpect(jsonPath("$.[*].hospitalDistrict").value(hasItem(DEFAULT_HOSPITAL_DISTRICT)))
            .andExpect(jsonPath("$.[*].symptomsStatus").value(hasItem(DEFAULT_SYMPTOMS_STATUS)))
            .andExpect(jsonPath("$.[*].symptoms").value(hasItem(DEFAULT_SYMPTOMS)))
            .andExpect(jsonPath("$.[*].testingKitUsed").value(hasItem(DEFAULT_TESTING_KIT_USED)))
            .andExpect(jsonPath("$.[*].eGeneNGene").value(hasItem(DEFAULT_E_GENE_N_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfEGeneNGene").value(hasItem(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE)))
            .andExpect(jsonPath("$.[*].rdRpSGene").value(hasItem(DEFAULT_RD_RP_S_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfRdRpSGene").value(hasItem(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE)))
            .andExpect(jsonPath("$.[*].oRF1aORF1bNN2Gene").value(hasItem(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE)))
            .andExpect(jsonPath("$.[*].ctValueOfORF1aORF1bNN2Gene").value(hasItem(DEFAULT_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)))
            .andExpect(jsonPath("$.[*].repeatSample").value(hasItem(DEFAULT_REPEAT_SAMPLE)))
            .andExpect(jsonPath("$.[*].dateOfSampleTested").value(hasItem(DEFAULT_DATE_OF_SAMPLE_TESTED.toString())))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(DEFAULT_ENTRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmationDate").value(hasItem(DEFAULT_CONFIRMATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].finalResultSample").value(hasItem(DEFAULT_FINAL_RESULT_SAMPLE)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].editedOn").value(hasItem(DEFAULT_EDITED_ON.toString())))
            .andExpect(jsonPath("$.[*].ccmsPullDate").value(hasItem(DEFAULT_CCMS_PULL_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientInfoShouldNotBeFound(String filter) throws Exception {
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPatientInfo() throws Exception {
        // Get the patientInfo
        restPatientInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo
        PatientInfo updatedPatientInfo = patientInfoRepository.findById(patientInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPatientInfo are not directly saved in db
        em.detach(updatedPatientInfo);
        updatedPatientInfo
            .icmrId(UPDATED_ICMR_ID)
            .srfId(UPDATED_SRF_ID)
            .labName(UPDATED_LAB_NAME)
            .patientID(UPDATED_PATIENT_ID)
            .patientName(UPDATED_PATIENT_NAME)
            .age(UPDATED_AGE)
            .ageIn(UPDATED_AGE_IN)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .address(UPDATED_ADDRESS)
            .villageTown(UPDATED_VILLAGE_TOWN)
            .pincode(UPDATED_PINCODE)
            .patientCategory(UPDATED_PATIENT_CATEGORY)
            .dateOfSampleCollection(UPDATED_DATE_OF_SAMPLE_COLLECTION)
            .dateOfSampleReceived(UPDATED_DATE_OF_SAMPLE_RECEIVED)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .sampleId(UPDATED_SAMPLE_ID)
            .underlyingMedicalCondition(UPDATED_UNDERLYING_MEDICAL_CONDITION)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .hospitalState(UPDATED_HOSPITAL_STATE)
            .hospitalDistrict(UPDATED_HOSPITAL_DISTRICT)
            .symptomsStatus(UPDATED_SYMPTOMS_STATUS)
            .symptoms(UPDATED_SYMPTOMS)
            .testingKitUsed(UPDATED_TESTING_KIT_USED)
            .eGeneNGene(UPDATED_E_GENE_N_GENE)
            .ctValueOfEGeneNGene(UPDATED_CT_VALUE_OF_E_GENE_N_GENE)
            .rdRpSGene(UPDATED_RD_RP_S_GENE)
            .ctValueOfRdRpSGene(UPDATED_CT_VALUE_OF_RD_RP_S_GENE)
            .oRF1aORF1bNN2Gene(UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE)
            .ctValueOfORF1aORF1bNN2Gene(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)
            .repeatSample(UPDATED_REPEAT_SAMPLE)
            .dateOfSampleTested(UPDATED_DATE_OF_SAMPLE_TESTED)
            .entryDate(UPDATED_ENTRY_DATE)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .finalResultSample(UPDATED_FINAL_RESULT_SAMPLE)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .ccmsPullDate(UPDATED_CCMS_PULL_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(updatedPatientInfo);

        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getIcmrId()).isEqualTo(UPDATED_ICMR_ID);
        assertThat(testPatientInfo.getSrfId()).isEqualTo(UPDATED_SRF_ID);
        assertThat(testPatientInfo.getLabName()).isEqualTo(UPDATED_LAB_NAME);
        assertThat(testPatientInfo.getPatientID()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testPatientInfo.getPatientName()).isEqualTo(UPDATED_PATIENT_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatientInfo.getAgeIn()).isEqualTo(UPDATED_AGE_IN);
        assertThat(testPatientInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatientInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPatientInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatientInfo.getVillageTown()).isEqualTo(UPDATED_VILLAGE_TOWN);
        assertThat(testPatientInfo.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testPatientInfo.getPatientCategory()).isEqualTo(UPDATED_PATIENT_CATEGORY);
        assertThat(testPatientInfo.getDateOfSampleCollection()).isEqualTo(UPDATED_DATE_OF_SAMPLE_COLLECTION);
        assertThat(testPatientInfo.getDateOfSampleReceived()).isEqualTo(UPDATED_DATE_OF_SAMPLE_RECEIVED);
        assertThat(testPatientInfo.getSampleType()).isEqualTo(UPDATED_SAMPLE_TYPE);
        assertThat(testPatientInfo.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testPatientInfo.getUnderlyingMedicalCondition()).isEqualTo(UPDATED_UNDERLYING_MEDICAL_CONDITION);
        assertThat(testPatientInfo.getHospitalized()).isEqualTo(UPDATED_HOSPITALIZED);
        assertThat(testPatientInfo.getHospitalName()).isEqualTo(UPDATED_HOSPITAL_NAME);
        assertThat(testPatientInfo.getHospitalizationDate()).isEqualTo(UPDATED_HOSPITALIZATION_DATE);
        assertThat(testPatientInfo.getHospitalState()).isEqualTo(UPDATED_HOSPITAL_STATE);
        assertThat(testPatientInfo.getHospitalDistrict()).isEqualTo(UPDATED_HOSPITAL_DISTRICT);
        assertThat(testPatientInfo.getSymptomsStatus()).isEqualTo(UPDATED_SYMPTOMS_STATUS);
        assertThat(testPatientInfo.getSymptoms()).isEqualTo(UPDATED_SYMPTOMS);
        assertThat(testPatientInfo.getTestingKitUsed()).isEqualTo(UPDATED_TESTING_KIT_USED);
        assertThat(testPatientInfo.geteGeneNGene()).isEqualTo(UPDATED_E_GENE_N_GENE);
        assertThat(testPatientInfo.getCtValueOfEGeneNGene()).isEqualTo(UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
        assertThat(testPatientInfo.getRdRpSGene()).isEqualTo(UPDATED_RD_RP_S_GENE);
        assertThat(testPatientInfo.getCtValueOfRdRpSGene()).isEqualTo(UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
        assertThat(testPatientInfo.getoRF1aORF1bNN2Gene()).isEqualTo(UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getCtValueOfORF1aORF1bNN2Gene()).isEqualTo(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getRepeatSample()).isEqualTo(UPDATED_REPEAT_SAMPLE);
        assertThat(testPatientInfo.getDateOfSampleTested()).isEqualTo(UPDATED_DATE_OF_SAMPLE_TESTED);
        assertThat(testPatientInfo.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testPatientInfo.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testPatientInfo.getFinalResultSample()).isEqualTo(UPDATED_FINAL_RESULT_SAMPLE);
        assertThat(testPatientInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPatientInfo.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testPatientInfo.getCcmsPullDate()).isEqualTo(UPDATED_CCMS_PULL_DATE);
        assertThat(testPatientInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPatientInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientInfoWithPatch() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo using partial update
        PatientInfo partialUpdatedPatientInfo = new PatientInfo();
        partialUpdatedPatientInfo.setId(patientInfo.getId());

        partialUpdatedPatientInfo
            .patientID(UPDATED_PATIENT_ID)
            .ageIn(UPDATED_AGE_IN)
            .dateOfSampleCollection(UPDATED_DATE_OF_SAMPLE_COLLECTION)
            .sampleId(UPDATED_SAMPLE_ID)
            .underlyingMedicalCondition(UPDATED_UNDERLYING_MEDICAL_CONDITION)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .rdRpSGene(UPDATED_RD_RP_S_GENE)
            .ctValueOfORF1aORF1bNN2Gene(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getIcmrId()).isEqualTo(DEFAULT_ICMR_ID);
        assertThat(testPatientInfo.getSrfId()).isEqualTo(DEFAULT_SRF_ID);
        assertThat(testPatientInfo.getLabName()).isEqualTo(DEFAULT_LAB_NAME);
        assertThat(testPatientInfo.getPatientID()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testPatientInfo.getPatientName()).isEqualTo(DEFAULT_PATIENT_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatientInfo.getAgeIn()).isEqualTo(UPDATED_AGE_IN);
        assertThat(testPatientInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatientInfo.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testPatientInfo.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatientInfo.getVillageTown()).isEqualTo(DEFAULT_VILLAGE_TOWN);
        assertThat(testPatientInfo.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testPatientInfo.getPatientCategory()).isEqualTo(DEFAULT_PATIENT_CATEGORY);
        assertThat(testPatientInfo.getDateOfSampleCollection()).isEqualTo(UPDATED_DATE_OF_SAMPLE_COLLECTION);
        assertThat(testPatientInfo.getDateOfSampleReceived()).isEqualTo(DEFAULT_DATE_OF_SAMPLE_RECEIVED);
        assertThat(testPatientInfo.getSampleType()).isEqualTo(DEFAULT_SAMPLE_TYPE);
        assertThat(testPatientInfo.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testPatientInfo.getUnderlyingMedicalCondition()).isEqualTo(UPDATED_UNDERLYING_MEDICAL_CONDITION);
        assertThat(testPatientInfo.getHospitalized()).isEqualTo(DEFAULT_HOSPITALIZED);
        assertThat(testPatientInfo.getHospitalName()).isEqualTo(DEFAULT_HOSPITAL_NAME);
        assertThat(testPatientInfo.getHospitalizationDate()).isEqualTo(UPDATED_HOSPITALIZATION_DATE);
        assertThat(testPatientInfo.getHospitalState()).isEqualTo(DEFAULT_HOSPITAL_STATE);
        assertThat(testPatientInfo.getHospitalDistrict()).isEqualTo(DEFAULT_HOSPITAL_DISTRICT);
        assertThat(testPatientInfo.getSymptomsStatus()).isEqualTo(DEFAULT_SYMPTOMS_STATUS);
        assertThat(testPatientInfo.getSymptoms()).isEqualTo(DEFAULT_SYMPTOMS);
        assertThat(testPatientInfo.getTestingKitUsed()).isEqualTo(DEFAULT_TESTING_KIT_USED);
        assertThat(testPatientInfo.geteGeneNGene()).isEqualTo(DEFAULT_E_GENE_N_GENE);
        assertThat(testPatientInfo.getCtValueOfEGeneNGene()).isEqualTo(DEFAULT_CT_VALUE_OF_E_GENE_N_GENE);
        assertThat(testPatientInfo.getRdRpSGene()).isEqualTo(UPDATED_RD_RP_S_GENE);
        assertThat(testPatientInfo.getCtValueOfRdRpSGene()).isEqualTo(DEFAULT_CT_VALUE_OF_RD_RP_S_GENE);
        assertThat(testPatientInfo.getoRF1aORF1bNN2Gene()).isEqualTo(DEFAULT_O_RF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getCtValueOfORF1aORF1bNN2Gene()).isEqualTo(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getRepeatSample()).isEqualTo(DEFAULT_REPEAT_SAMPLE);
        assertThat(testPatientInfo.getDateOfSampleTested()).isEqualTo(DEFAULT_DATE_OF_SAMPLE_TESTED);
        assertThat(testPatientInfo.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testPatientInfo.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testPatientInfo.getFinalResultSample()).isEqualTo(DEFAULT_FINAL_RESULT_SAMPLE);
        assertThat(testPatientInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPatientInfo.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testPatientInfo.getCcmsPullDate()).isEqualTo(DEFAULT_CCMS_PULL_DATE);
        assertThat(testPatientInfo.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPatientInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdatePatientInfoWithPatch() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();

        // Update the patientInfo using partial update
        PatientInfo partialUpdatedPatientInfo = new PatientInfo();
        partialUpdatedPatientInfo.setId(patientInfo.getId());

        partialUpdatedPatientInfo
            .icmrId(UPDATED_ICMR_ID)
            .srfId(UPDATED_SRF_ID)
            .labName(UPDATED_LAB_NAME)
            .patientID(UPDATED_PATIENT_ID)
            .patientName(UPDATED_PATIENT_NAME)
            .age(UPDATED_AGE)
            .ageIn(UPDATED_AGE_IN)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .address(UPDATED_ADDRESS)
            .villageTown(UPDATED_VILLAGE_TOWN)
            .pincode(UPDATED_PINCODE)
            .patientCategory(UPDATED_PATIENT_CATEGORY)
            .dateOfSampleCollection(UPDATED_DATE_OF_SAMPLE_COLLECTION)
            .dateOfSampleReceived(UPDATED_DATE_OF_SAMPLE_RECEIVED)
            .sampleType(UPDATED_SAMPLE_TYPE)
            .sampleId(UPDATED_SAMPLE_ID)
            .underlyingMedicalCondition(UPDATED_UNDERLYING_MEDICAL_CONDITION)
            .hospitalized(UPDATED_HOSPITALIZED)
            .hospitalName(UPDATED_HOSPITAL_NAME)
            .hospitalizationDate(UPDATED_HOSPITALIZATION_DATE)
            .hospitalState(UPDATED_HOSPITAL_STATE)
            .hospitalDistrict(UPDATED_HOSPITAL_DISTRICT)
            .symptomsStatus(UPDATED_SYMPTOMS_STATUS)
            .symptoms(UPDATED_SYMPTOMS)
            .testingKitUsed(UPDATED_TESTING_KIT_USED)
            .eGeneNGene(UPDATED_E_GENE_N_GENE)
            .ctValueOfEGeneNGene(UPDATED_CT_VALUE_OF_E_GENE_N_GENE)
            .rdRpSGene(UPDATED_RD_RP_S_GENE)
            .ctValueOfRdRpSGene(UPDATED_CT_VALUE_OF_RD_RP_S_GENE)
            .oRF1aORF1bNN2Gene(UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE)
            .ctValueOfORF1aORF1bNN2Gene(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE)
            .repeatSample(UPDATED_REPEAT_SAMPLE)
            .dateOfSampleTested(UPDATED_DATE_OF_SAMPLE_TESTED)
            .entryDate(UPDATED_ENTRY_DATE)
            .confirmationDate(UPDATED_CONFIRMATION_DATE)
            .finalResultSample(UPDATED_FINAL_RESULT_SAMPLE)
            .remarks(UPDATED_REMARKS)
            .editedOn(UPDATED_EDITED_ON)
            .ccmsPullDate(UPDATED_CCMS_PULL_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatientInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatientInfo))
            )
            .andExpect(status().isOk());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
        PatientInfo testPatientInfo = patientInfoList.get(patientInfoList.size() - 1);
        assertThat(testPatientInfo.getIcmrId()).isEqualTo(UPDATED_ICMR_ID);
        assertThat(testPatientInfo.getSrfId()).isEqualTo(UPDATED_SRF_ID);
        assertThat(testPatientInfo.getLabName()).isEqualTo(UPDATED_LAB_NAME);
        assertThat(testPatientInfo.getPatientID()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testPatientInfo.getPatientName()).isEqualTo(UPDATED_PATIENT_NAME);
        assertThat(testPatientInfo.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatientInfo.getAgeIn()).isEqualTo(UPDATED_AGE_IN);
        assertThat(testPatientInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatientInfo.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testPatientInfo.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatientInfo.getVillageTown()).isEqualTo(UPDATED_VILLAGE_TOWN);
        assertThat(testPatientInfo.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testPatientInfo.getPatientCategory()).isEqualTo(UPDATED_PATIENT_CATEGORY);
        assertThat(testPatientInfo.getDateOfSampleCollection()).isEqualTo(UPDATED_DATE_OF_SAMPLE_COLLECTION);
        assertThat(testPatientInfo.getDateOfSampleReceived()).isEqualTo(UPDATED_DATE_OF_SAMPLE_RECEIVED);
        assertThat(testPatientInfo.getSampleType()).isEqualTo(UPDATED_SAMPLE_TYPE);
        assertThat(testPatientInfo.getSampleId()).isEqualTo(UPDATED_SAMPLE_ID);
        assertThat(testPatientInfo.getUnderlyingMedicalCondition()).isEqualTo(UPDATED_UNDERLYING_MEDICAL_CONDITION);
        assertThat(testPatientInfo.getHospitalized()).isEqualTo(UPDATED_HOSPITALIZED);
        assertThat(testPatientInfo.getHospitalName()).isEqualTo(UPDATED_HOSPITAL_NAME);
        assertThat(testPatientInfo.getHospitalizationDate()).isEqualTo(UPDATED_HOSPITALIZATION_DATE);
        assertThat(testPatientInfo.getHospitalState()).isEqualTo(UPDATED_HOSPITAL_STATE);
        assertThat(testPatientInfo.getHospitalDistrict()).isEqualTo(UPDATED_HOSPITAL_DISTRICT);
        assertThat(testPatientInfo.getSymptomsStatus()).isEqualTo(UPDATED_SYMPTOMS_STATUS);
        assertThat(testPatientInfo.getSymptoms()).isEqualTo(UPDATED_SYMPTOMS);
        assertThat(testPatientInfo.getTestingKitUsed()).isEqualTo(UPDATED_TESTING_KIT_USED);
        assertThat(testPatientInfo.geteGeneNGene()).isEqualTo(UPDATED_E_GENE_N_GENE);
        assertThat(testPatientInfo.getCtValueOfEGeneNGene()).isEqualTo(UPDATED_CT_VALUE_OF_E_GENE_N_GENE);
        assertThat(testPatientInfo.getRdRpSGene()).isEqualTo(UPDATED_RD_RP_S_GENE);
        assertThat(testPatientInfo.getCtValueOfRdRpSGene()).isEqualTo(UPDATED_CT_VALUE_OF_RD_RP_S_GENE);
        assertThat(testPatientInfo.getoRF1aORF1bNN2Gene()).isEqualTo(UPDATED_O_RF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getCtValueOfORF1aORF1bNN2Gene()).isEqualTo(UPDATED_CT_VALUE_OF_ORF_1_A_ORF_1_B_NN_2_GENE);
        assertThat(testPatientInfo.getRepeatSample()).isEqualTo(UPDATED_REPEAT_SAMPLE);
        assertThat(testPatientInfo.getDateOfSampleTested()).isEqualTo(UPDATED_DATE_OF_SAMPLE_TESTED);
        assertThat(testPatientInfo.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testPatientInfo.getConfirmationDate()).isEqualTo(UPDATED_CONFIRMATION_DATE);
        assertThat(testPatientInfo.getFinalResultSample()).isEqualTo(UPDATED_FINAL_RESULT_SAMPLE);
        assertThat(testPatientInfo.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testPatientInfo.getEditedOn()).isEqualTo(UPDATED_EDITED_ON);
        assertThat(testPatientInfo.getCcmsPullDate()).isEqualTo(UPDATED_CCMS_PULL_DATE);
        assertThat(testPatientInfo.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPatientInfo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatientInfo() throws Exception {
        int databaseSizeBeforeUpdate = patientInfoRepository.findAll().size();
        patientInfo.setId(count.incrementAndGet());

        // Create the PatientInfo
        PatientInfoDTO patientInfoDTO = patientInfoMapper.toDto(patientInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patientInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PatientInfo in the database
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatientInfo() throws Exception {
        // Initialize the database
        patientInfoRepository.saveAndFlush(patientInfo);

        int databaseSizeBeforeDelete = patientInfoRepository.findAll().size();

        // Delete the patientInfo
        restPatientInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, patientInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientInfo> patientInfoList = patientInfoRepository.findAll();
        assertThat(patientInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
