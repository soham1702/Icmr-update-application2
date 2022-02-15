package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Hospital} entity.
 */
public class HospitalDTO implements Serializable {

    private Long id;

    @NotNull
    private HospitalCategory category;

    @NotNull
    private HospitalSubCategory subCategory;

    private String contactNo;

    private String latitude;

    private String longitude;

    private Integer docCount;

    private String email;

    @NotNull
    private String name;

    private String registrationNo;

    private String address1;

    private String address2;

    private String area;

    @NotNull
    private String pinCode;

    private Long hospitalId;

    private String odasFacilityId;

    private String referenceNumber;

    private StatusInd statusInd;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private StateDTO state;

    private DistrictDTO district;

    private TalukaDTO taluka;

    private CityDTO city;

    private MunicipalCorpDTO municipalCorp;

    private HospitalTypeDTO hospitalType;

    private Set<SupplierDTO> suppliers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HospitalCategory getCategory() {
        return category;
    }

    public void setCategory(HospitalCategory category) {
        this.category = category;
    }

    public HospitalSubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(HospitalSubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getDocCount() {
        return docCount;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getOdasFacilityId() {
        return odasFacilityId;
    }

    public void setOdasFacilityId(String odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusInd getStatusInd() {
        return statusInd;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
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

    public TalukaDTO getTaluka() {
        return taluka;
    }

    public void setTaluka(TalukaDTO taluka) {
        this.taluka = taluka;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public MunicipalCorpDTO getMunicipalCorp() {
        return municipalCorp;
    }

    public void setMunicipalCorp(MunicipalCorpDTO municipalCorp) {
        this.municipalCorp = municipalCorp;
    }

    public HospitalTypeDTO getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(HospitalTypeDTO hospitalType) {
        this.hospitalType = hospitalType;
    }

    public Set<SupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Set<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalDTO)) {
            return false;
        }

        HospitalDTO hospitalDTO = (HospitalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hospitalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalDTO{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", subCategory='" + getSubCategory() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", docCount=" + getDocCount() +
            ", email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", area='" + getArea() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", hospitalId=" + getHospitalId() +
            ", odasFacilityId='" + getOdasFacilityId() + "'" +
            ", referenceNumber='" + getReferenceNumber() + "'" +
            ", statusInd='" + getStatusInd() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", state=" + getState() +
            ", district=" + getDistrict() +
            ", taluka=" + getTaluka() +
            ", city=" + getCity() +
            ", municipalCorp=" + getMunicipalCorp() +
            ", hospitalType=" + getHospitalType() +
            ", suppliers=" + getSuppliers() +
            "}";
    }
}
