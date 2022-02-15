package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.StatusInd;
import com.techvg.covid.care.domain.enumeration.SupplierType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Supplier} entity.
 */
public class SupplierDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private SupplierType supplierType;

    private String contactNo;

    private String latitude;

    private String longitude;

    private String email;

    private String registrationNo;

    private String address1;

    private String address2;

    private String area;

    @NotNull
    private String pinCode;

    private StatusInd statusInd;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private StateDTO state;

    private DistrictDTO district;

    private TalukaDTO taluka;

    private CityDTO city;

    private InventoryTypeDTO inventoryType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierType getSupplierType() {
        return supplierType;
    }

    public void setSupplierType(SupplierType supplierType) {
        this.supplierType = supplierType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public InventoryTypeDTO getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryTypeDTO inventoryType) {
        this.inventoryType = inventoryType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierDTO)) {
            return false;
        }

        SupplierDTO supplierDTO = (SupplierDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, supplierDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", supplierType='" + getSupplierType() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", email='" + getEmail() + "'" +
            ", registrationNo='" + getRegistrationNo() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", area='" + getArea() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", statusInd='" + getStatusInd() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", state=" + getState() +
            ", district=" + getDistrict() +
            ", taluka=" + getTaluka() +
            ", city=" + getCity() +
            ", inventoryType=" + getInventoryType() +
            "}";
    }
}
