package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PatientInfo.
 */
@Entity
@Table(name = "patient_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PatientInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "icmr_id", nullable = false, unique = true)
    private String icmrId;

    @Column(name = "srf_id")
    private String srfId;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "patient_id")
    private String patientID;

    @Column(name = "patient_name")
    private String patientName;

    @Column(name = "age")
    private String age;

    @Column(name = "age_in")
    private String ageIn;

    @Column(name = "gender")
    private String gender;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "address")
    private String address;

    @Column(name = "village_town")
    private String villageTown;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "patient_category")
    private String patientCategory;

    @Column(name = "date_of_sample_collection")
    private Instant dateOfSampleCollection;

    @Column(name = "date_of_sample_received")
    private Instant dateOfSampleReceived;

    @Column(name = "sample_type")
    private String sampleType;

    @Column(name = "sample_id")
    private String sampleId;

    @Column(name = "underlying_medical_condition")
    private String underlyingMedicalCondition;

    @Column(name = "hospitalized")
    private String hospitalized;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "hospitalization_date")
    private Instant hospitalizationDate;

    @Column(name = "hospital_state")
    private String hospitalState;

    @Column(name = "hospital_district")
    private String hospitalDistrict;

    @Column(name = "symptoms_status")
    private String symptomsStatus;

    @Column(name = "symptoms")
    private String symptoms;

    @Column(name = "testing_kit_used")
    private String testingKitUsed;

    @Column(name = "e_gene_n_gene")
    private String eGeneNGene;

    @Column(name = "ct_value_of_e_gene_n_gene")
    private String ctValueOfEGeneNGene;

    @Column(name = "rd_rp_s_gene")
    private String rdRpSGene;

    @Column(name = "ct_value_of_rd_rp_s_gene")
    private String ctValueOfRdRpSGene;

    @Column(name = "o_rf_1_a_orf_1_b_nn_2_gene")
    private String oRF1aORF1bNN2Gene;

    @Column(name = "ct_value_of_orf_1_a_orf_1_b_nn_2_gene")
    private String ctValueOfORF1aORF1bNN2Gene;

    @Column(name = "repeat_sample")
    private String repeatSample;

    @Column(name = "date_of_sample_tested")
    private Instant dateOfSampleTested;

    @Column(name = "entry_date")
    private Instant entryDate;

    @Column(name = "confirmation_date")
    private Instant confirmationDate;

    @Column(name = "final_result_sample")
    private String finalResultSample;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "edited_on")
    private Instant editedOn;

    @NotNull
    @Column(name = "ccms_pull_date", nullable = false)
    private Instant ccmsPullDate;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    private State state;

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "division" }, allowSetters = true)
    private District district;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PatientInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return this.icmrId;
    }

    public PatientInfo icmrId(String icmrId) {
        this.setIcmrId(icmrId);
        return this;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getSrfId() {
        return this.srfId;
    }

    public PatientInfo srfId(String srfId) {
        this.setSrfId(srfId);
        return this;
    }

    public void setSrfId(String srfId) {
        this.srfId = srfId;
    }

    public String getLabName() {
        return this.labName;
    }

    public PatientInfo labName(String labName) {
        this.setLabName(labName);
        return this;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getPatientID() {
        return this.patientID;
    }

    public PatientInfo patientID(String patientID) {
        this.setPatientID(patientID);
        return this;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public PatientInfo patientName(String patientName) {
        this.setPatientName(patientName);
        return this;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAge() {
        return this.age;
    }

    public PatientInfo age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAgeIn() {
        return this.ageIn;
    }

    public PatientInfo ageIn(String ageIn) {
        this.setAgeIn(ageIn);
        return this;
    }

    public void setAgeIn(String ageIn) {
        this.ageIn = ageIn;
    }

    public String getGender() {
        return this.gender;
    }

    public PatientInfo gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return this.nationality;
    }

    public PatientInfo nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return this.address;
    }

    public PatientInfo address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillageTown() {
        return this.villageTown;
    }

    public PatientInfo villageTown(String villageTown) {
        this.setVillageTown(villageTown);
        return this;
    }

    public void setVillageTown(String villageTown) {
        this.villageTown = villageTown;
    }

    public String getPincode() {
        return this.pincode;
    }

    public PatientInfo pincode(String pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPatientCategory() {
        return this.patientCategory;
    }

    public PatientInfo patientCategory(String patientCategory) {
        this.setPatientCategory(patientCategory);
        return this;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public Instant getDateOfSampleCollection() {
        return this.dateOfSampleCollection;
    }

    public PatientInfo dateOfSampleCollection(Instant dateOfSampleCollection) {
        this.setDateOfSampleCollection(dateOfSampleCollection);
        return this;
    }

    public void setDateOfSampleCollection(Instant dateOfSampleCollection) {
        this.dateOfSampleCollection = dateOfSampleCollection;
    }

    public Instant getDateOfSampleReceived() {
        return this.dateOfSampleReceived;
    }

    public PatientInfo dateOfSampleReceived(Instant dateOfSampleReceived) {
        this.setDateOfSampleReceived(dateOfSampleReceived);
        return this;
    }

    public void setDateOfSampleReceived(Instant dateOfSampleReceived) {
        this.dateOfSampleReceived = dateOfSampleReceived;
    }

    public String getSampleType() {
        return this.sampleType;
    }

    public PatientInfo sampleType(String sampleType) {
        this.setSampleType(sampleType);
        return this;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleId() {
        return this.sampleId;
    }

    public PatientInfo sampleId(String sampleId) {
        this.setSampleId(sampleId);
        return this;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getUnderlyingMedicalCondition() {
        return this.underlyingMedicalCondition;
    }

    public PatientInfo underlyingMedicalCondition(String underlyingMedicalCondition) {
        this.setUnderlyingMedicalCondition(underlyingMedicalCondition);
        return this;
    }

    public void setUnderlyingMedicalCondition(String underlyingMedicalCondition) {
        this.underlyingMedicalCondition = underlyingMedicalCondition;
    }

    public String getHospitalized() {
        return this.hospitalized;
    }

    public PatientInfo hospitalized(String hospitalized) {
        this.setHospitalized(hospitalized);
        return this;
    }

    public void setHospitalized(String hospitalized) {
        this.hospitalized = hospitalized;
    }

    public String getHospitalName() {
        return this.hospitalName;
    }

    public PatientInfo hospitalName(String hospitalName) {
        this.setHospitalName(hospitalName);
        return this;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Instant getHospitalizationDate() {
        return this.hospitalizationDate;
    }

    public PatientInfo hospitalizationDate(Instant hospitalizationDate) {
        this.setHospitalizationDate(hospitalizationDate);
        return this;
    }

    public void setHospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public String getHospitalState() {
        return this.hospitalState;
    }

    public PatientInfo hospitalState(String hospitalState) {
        this.setHospitalState(hospitalState);
        return this;
    }

    public void setHospitalState(String hospitalState) {
        this.hospitalState = hospitalState;
    }

    public String getHospitalDistrict() {
        return this.hospitalDistrict;
    }

    public PatientInfo hospitalDistrict(String hospitalDistrict) {
        this.setHospitalDistrict(hospitalDistrict);
        return this;
    }

    public void setHospitalDistrict(String hospitalDistrict) {
        this.hospitalDistrict = hospitalDistrict;
    }

    public String getSymptomsStatus() {
        return this.symptomsStatus;
    }

    public PatientInfo symptomsStatus(String symptomsStatus) {
        this.setSymptomsStatus(symptomsStatus);
        return this;
    }

    public void setSymptomsStatus(String symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
    }

    public String getSymptoms() {
        return this.symptoms;
    }

    public PatientInfo symptoms(String symptoms) {
        this.setSymptoms(symptoms);
        return this;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTestingKitUsed() {
        return this.testingKitUsed;
    }

    public PatientInfo testingKitUsed(String testingKitUsed) {
        this.setTestingKitUsed(testingKitUsed);
        return this;
    }

    public void setTestingKitUsed(String testingKitUsed) {
        this.testingKitUsed = testingKitUsed;
    }

    public String geteGeneNGene() {
        return this.eGeneNGene;
    }

    public PatientInfo eGeneNGene(String eGeneNGene) {
        this.seteGeneNGene(eGeneNGene);
        return this;
    }

    public void seteGeneNGene(String eGeneNGene) {
        this.eGeneNGene = eGeneNGene;
    }

    public String getCtValueOfEGeneNGene() {
        return this.ctValueOfEGeneNGene;
    }

    public PatientInfo ctValueOfEGeneNGene(String ctValueOfEGeneNGene) {
        this.setCtValueOfEGeneNGene(ctValueOfEGeneNGene);
        return this;
    }

    public void setCtValueOfEGeneNGene(String ctValueOfEGeneNGene) {
        this.ctValueOfEGeneNGene = ctValueOfEGeneNGene;
    }

    public String getRdRpSGene() {
        return this.rdRpSGene;
    }

    public PatientInfo rdRpSGene(String rdRpSGene) {
        this.setRdRpSGene(rdRpSGene);
        return this;
    }

    public void setRdRpSGene(String rdRpSGene) {
        this.rdRpSGene = rdRpSGene;
    }

    public String getCtValueOfRdRpSGene() {
        return this.ctValueOfRdRpSGene;
    }

    public PatientInfo ctValueOfRdRpSGene(String ctValueOfRdRpSGene) {
        this.setCtValueOfRdRpSGene(ctValueOfRdRpSGene);
        return this;
    }

    public void setCtValueOfRdRpSGene(String ctValueOfRdRpSGene) {
        this.ctValueOfRdRpSGene = ctValueOfRdRpSGene;
    }

    public String getoRF1aORF1bNN2Gene() {
        return this.oRF1aORF1bNN2Gene;
    }

    public PatientInfo oRF1aORF1bNN2Gene(String oRF1aORF1bNN2Gene) {
        this.setoRF1aORF1bNN2Gene(oRF1aORF1bNN2Gene);
        return this;
    }

    public void setoRF1aORF1bNN2Gene(String oRF1aORF1bNN2Gene) {
        this.oRF1aORF1bNN2Gene = oRF1aORF1bNN2Gene;
    }

    public String getCtValueOfORF1aORF1bNN2Gene() {
        return this.ctValueOfORF1aORF1bNN2Gene;
    }

    public PatientInfo ctValueOfORF1aORF1bNN2Gene(String ctValueOfORF1aORF1bNN2Gene) {
        this.setCtValueOfORF1aORF1bNN2Gene(ctValueOfORF1aORF1bNN2Gene);
        return this;
    }

    public void setCtValueOfORF1aORF1bNN2Gene(String ctValueOfORF1aORF1bNN2Gene) {
        this.ctValueOfORF1aORF1bNN2Gene = ctValueOfORF1aORF1bNN2Gene;
    }

    public String getRepeatSample() {
        return this.repeatSample;
    }

    public PatientInfo repeatSample(String repeatSample) {
        this.setRepeatSample(repeatSample);
        return this;
    }

    public void setRepeatSample(String repeatSample) {
        this.repeatSample = repeatSample;
    }

    public Instant getDateOfSampleTested() {
        return this.dateOfSampleTested;
    }

    public PatientInfo dateOfSampleTested(Instant dateOfSampleTested) {
        this.setDateOfSampleTested(dateOfSampleTested);
        return this;
    }

    public void setDateOfSampleTested(Instant dateOfSampleTested) {
        this.dateOfSampleTested = dateOfSampleTested;
    }

    public Instant getEntryDate() {
        return this.entryDate;
    }

    public PatientInfo entryDate(Instant entryDate) {
        this.setEntryDate(entryDate);
        return this;
    }

    public void setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
    }

    public Instant getConfirmationDate() {
        return this.confirmationDate;
    }

    public PatientInfo confirmationDate(Instant confirmationDate) {
        this.setConfirmationDate(confirmationDate);
        return this;
    }

    public void setConfirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getFinalResultSample() {
        return this.finalResultSample;
    }

    public PatientInfo finalResultSample(String finalResultSample) {
        this.setFinalResultSample(finalResultSample);
        return this;
    }

    public void setFinalResultSample(String finalResultSample) {
        this.finalResultSample = finalResultSample;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public PatientInfo remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getEditedOn() {
        return this.editedOn;
    }

    public PatientInfo editedOn(Instant editedOn) {
        this.setEditedOn(editedOn);
        return this;
    }

    public void setEditedOn(Instant editedOn) {
        this.editedOn = editedOn;
    }

    public Instant getCcmsPullDate() {
        return this.ccmsPullDate;
    }

    public PatientInfo ccmsPullDate(Instant ccmsPullDate) {
        this.setCcmsPullDate(ccmsPullDate);
        return this;
    }

    public void setCcmsPullDate(Instant ccmsPullDate) {
        this.ccmsPullDate = ccmsPullDate;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public PatientInfo lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PatientInfo lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public PatientInfo state(State state) {
        this.setState(state);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public PatientInfo district(District district) {
        this.setDistrict(district);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInfo)) {
            return false;
        }
        return id != null && id.equals(((PatientInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfo{" +
            "id=" + getId() +
            ", icmrId='" + getIcmrId() + "'" +
            ", srfId='" + getSrfId() + "'" +
            ", labName='" + getLabName() + "'" +
            ", patientID='" + getPatientID() + "'" +
            ", patientName='" + getPatientName() + "'" +
            ", age='" + getAge() + "'" +
            ", ageIn='" + getAgeIn() + "'" +
            ", gender='" + getGender() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", address='" + getAddress() + "'" +
            ", villageTown='" + getVillageTown() + "'" +
            ", pincode='" + getPincode() + "'" +
            ", patientCategory='" + getPatientCategory() + "'" +
            ", dateOfSampleCollection='" + getDateOfSampleCollection() + "'" +
            ", dateOfSampleReceived='" + getDateOfSampleReceived() + "'" +
            ", sampleType='" + getSampleType() + "'" +
            ", sampleId='" + getSampleId() + "'" +
            ", underlyingMedicalCondition='" + getUnderlyingMedicalCondition() + "'" +
            ", hospitalized='" + getHospitalized() + "'" +
            ", hospitalName='" + getHospitalName() + "'" +
            ", hospitalizationDate='" + getHospitalizationDate() + "'" +
            ", hospitalState='" + getHospitalState() + "'" +
            ", hospitalDistrict='" + getHospitalDistrict() + "'" +
            ", symptomsStatus='" + getSymptomsStatus() + "'" +
            ", symptoms='" + getSymptoms() + "'" +
            ", testingKitUsed='" + getTestingKitUsed() + "'" +
            ", eGeneNGene='" + geteGeneNGene() + "'" +
            ", ctValueOfEGeneNGene='" + getCtValueOfEGeneNGene() + "'" +
            ", rdRpSGene='" + getRdRpSGene() + "'" +
            ", ctValueOfRdRpSGene='" + getCtValueOfRdRpSGene() + "'" +
            ", oRF1aORF1bNN2Gene='" + getoRF1aORF1bNN2Gene() + "'" +
            ", ctValueOfORF1aORF1bNN2Gene='" + getCtValueOfORF1aORF1bNN2Gene() + "'" +
            ", repeatSample='" + getRepeatSample() + "'" +
            ", dateOfSampleTested='" + getDateOfSampleTested() + "'" +
            ", entryDate='" + getEntryDate() + "'" +
            ", confirmationDate='" + getConfirmationDate() + "'" +
            ", finalResultSample='" + getFinalResultSample() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", editedOn='" + getEditedOn() + "'" +
            ", ccmsPullDate='" + getCcmsPullDate() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
