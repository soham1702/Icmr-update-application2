package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.PatientInfo} entity.
 */
public class PatientInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private String icmrId;

    private String srfId;

    private String labName;

    private String patientID;

    private String patientName;

    private String age;

    private String ageIn;

    private String gender;

    private String nationality;

    private String address;

    private String villageTown;

    private String pincode;

    private String patientCategory;

    private Instant dateOfSampleCollection;

    private Instant dateOfSampleReceived;

    private String sampleType;

    private String sampleId;

    private String underlyingMedicalCondition;

    private String hospitalized;

    private String hospitalName;

    private Instant hospitalizationDate;

    private String hospitalState;

    private String hospitalDistrict;

    private String symptomsStatus;

    private String symptoms;

    private String testingKitUsed;

    private String eGeneNGene;

    private String ctValueOfEGeneNGene;

    private String rdRpSGene;

    private String ctValueOfRdRpSGene;

    private String oRF1aORF1bNN2Gene;

    private String ctValueOfORF1aORF1bNN2Gene;

    private String repeatSample;

    private Instant dateOfSampleTested;

    private Instant entryDate;

    private Instant confirmationDate;

    private String finalResultSample;

    private String remarks;

    private Instant editedOn;

    @NotNull
    private Instant ccmsPullDate;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private StateDTO state;

    private DistrictDTO district;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcmrId() {
        return icmrId;
    }

    public void setIcmrId(String icmrId) {
        this.icmrId = icmrId;
    }

    public String getSrfId() {
        return srfId;
    }

    public void setSrfId(String srfId) {
        this.srfId = srfId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAgeIn() {
        return ageIn;
    }

    public void setAgeIn(String ageIn) {
        this.ageIn = ageIn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVillageTown() {
        return villageTown;
    }

    public void setVillageTown(String villageTown) {
        this.villageTown = villageTown;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPatientCategory() {
        return patientCategory;
    }

    public void setPatientCategory(String patientCategory) {
        this.patientCategory = patientCategory;
    }

    public Instant getDateOfSampleCollection() {
        return dateOfSampleCollection;
    }

    public void setDateOfSampleCollection(Instant dateOfSampleCollection) {
        this.dateOfSampleCollection = dateOfSampleCollection;
    }

    public Instant getDateOfSampleReceived() {
        return dateOfSampleReceived;
    }

    public void setDateOfSampleReceived(Instant dateOfSampleReceived) {
        this.dateOfSampleReceived = dateOfSampleReceived;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getUnderlyingMedicalCondition() {
        return underlyingMedicalCondition;
    }

    public void setUnderlyingMedicalCondition(String underlyingMedicalCondition) {
        this.underlyingMedicalCondition = underlyingMedicalCondition;
    }

    public String getHospitalized() {
        return hospitalized;
    }

    public void setHospitalized(String hospitalized) {
        this.hospitalized = hospitalized;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public Instant getHospitalizationDate() {
        return hospitalizationDate;
    }

    public void setHospitalizationDate(Instant hospitalizationDate) {
        this.hospitalizationDate = hospitalizationDate;
    }

    public String getHospitalState() {
        return hospitalState;
    }

    public void setHospitalState(String hospitalState) {
        this.hospitalState = hospitalState;
    }

    public String getHospitalDistrict() {
        return hospitalDistrict;
    }

    public void setHospitalDistrict(String hospitalDistrict) {
        this.hospitalDistrict = hospitalDistrict;
    }

    public String getSymptomsStatus() {
        return symptomsStatus;
    }

    public void setSymptomsStatus(String symptomsStatus) {
        this.symptomsStatus = symptomsStatus;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTestingKitUsed() {
        return testingKitUsed;
    }

    public void setTestingKitUsed(String testingKitUsed) {
        this.testingKitUsed = testingKitUsed;
    }

    public String geteGeneNGene() {
        return eGeneNGene;
    }

    public void seteGeneNGene(String eGeneNGene) {
        this.eGeneNGene = eGeneNGene;
    }

    public String getCtValueOfEGeneNGene() {
        return ctValueOfEGeneNGene;
    }

    public void setCtValueOfEGeneNGene(String ctValueOfEGeneNGene) {
        this.ctValueOfEGeneNGene = ctValueOfEGeneNGene;
    }

    public String getRdRpSGene() {
        return rdRpSGene;
    }

    public void setRdRpSGene(String rdRpSGene) {
        this.rdRpSGene = rdRpSGene;
    }

    public String getCtValueOfRdRpSGene() {
        return ctValueOfRdRpSGene;
    }

    public void setCtValueOfRdRpSGene(String ctValueOfRdRpSGene) {
        this.ctValueOfRdRpSGene = ctValueOfRdRpSGene;
    }

    public String getoRF1aORF1bNN2Gene() {
        return oRF1aORF1bNN2Gene;
    }

    public void setoRF1aORF1bNN2Gene(String oRF1aORF1bNN2Gene) {
        this.oRF1aORF1bNN2Gene = oRF1aORF1bNN2Gene;
    }

    public String getCtValueOfORF1aORF1bNN2Gene() {
        return ctValueOfORF1aORF1bNN2Gene;
    }

    public void setCtValueOfORF1aORF1bNN2Gene(String ctValueOfORF1aORF1bNN2Gene) {
        this.ctValueOfORF1aORF1bNN2Gene = ctValueOfORF1aORF1bNN2Gene;
    }

    public String getRepeatSample() {
        return repeatSample;
    }

    public void setRepeatSample(String repeatSample) {
        this.repeatSample = repeatSample;
    }

    public Instant getDateOfSampleTested() {
        return dateOfSampleTested;
    }

    public void setDateOfSampleTested(Instant dateOfSampleTested) {
        this.dateOfSampleTested = dateOfSampleTested;
    }

    public Instant getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Instant entryDate) {
        this.entryDate = entryDate;
    }

    public Instant getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(Instant confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getFinalResultSample() {
        return finalResultSample;
    }

    public void setFinalResultSample(String finalResultSample) {
        this.finalResultSample = finalResultSample;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Instant editedOn) {
        this.editedOn = editedOn;
    }

    public Instant getCcmsPullDate() {
        return ccmsPullDate;
    }

    public void setCcmsPullDate(Instant ccmsPullDate) {
        this.ccmsPullDate = ccmsPullDate;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public DistrictDTO getDistrict() {
        return district;
    }

    public void setDistrict(DistrictDTO district) {
        this.district = district;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PatientInfoDTO)) {
            return false;
        }

        PatientInfoDTO patientInfoDTO = (PatientInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, patientInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PatientInfoDTO{" +
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
            ", state=" + getState() +
            ", district=" + getDistrict() +
            "}";
    }
}
