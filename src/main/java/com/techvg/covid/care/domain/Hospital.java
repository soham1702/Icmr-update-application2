package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Hospital.
 */
@Entity
@Table(name = "hospital")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private HospitalCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sub_category", nullable = false)
    private HospitalSubCategory subCategory;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "doc_count")
    private Integer docCount;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "area")
    private String area;

    @NotNull
    @Column(name = "pin_code", nullable = false)
    private String pinCode;

    @Column(name = "hospital_id")
    private Long hospitalId;

    @Column(name = "odas_facility_id")
    private String odasFacilityId;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_ind")
    private StatusInd statusInd;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private Taluka taluka;

    @ManyToOne
    @JsonIgnoreProperties(value = { "taluka" }, allowSetters = true)
    private City city;

    @ManyToOne
    @JsonIgnoreProperties(value = { "district" }, allowSetters = true)
    private MunicipalCorp municipalCorp;

    @ManyToOne
    private HospitalType hospitalType;

    @ManyToMany
    @JoinTable(
        name = "rel_hospital__supplier",
        joinColumns = @JoinColumn(name = "hospital_id"),
        inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Set<Supplier> suppliers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Hospital id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HospitalCategory getCategory() {
        return this.category;
    }

    public Hospital category(HospitalCategory category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(HospitalCategory category) {
        this.category = category;
    }

    public HospitalSubCategory getSubCategory() {
        return this.subCategory;
    }

    public Hospital subCategory(HospitalSubCategory subCategory) {
        this.setSubCategory(subCategory);
        return this;
    }

    public void setSubCategory(HospitalSubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public Hospital contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Hospital latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Hospital longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getDocCount() {
        return this.docCount;
    }

    public Hospital docCount(Integer docCount) {
        this.setDocCount(docCount);
        return this;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }

    public String getEmail() {
        return this.email;
    }

    public Hospital email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public Hospital name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public Hospital registrationNo(String registrationNo) {
        this.setRegistrationNo(registrationNo);
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Hospital address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Hospital address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return this.area;
    }

    public Hospital area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public Hospital pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getHospitalId() {
        return this.hospitalId;
    }

    public Hospital hospitalId(Long hospitalId) {
        this.setHospitalId(hospitalId);
        return this;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getOdasFacilityId() {
        return this.odasFacilityId;
    }

    public Hospital odasFacilityId(String odasFacilityId) {
        this.setOdasFacilityId(odasFacilityId);
        return this;
    }

    public void setOdasFacilityId(String odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
    }

    public String getReferenceNumber() {
        return this.referenceNumber;
    }

    public Hospital referenceNumber(String referenceNumber) {
        this.setReferenceNumber(referenceNumber);
        return this;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusInd getStatusInd() {
        return this.statusInd;
    }

    public Hospital statusInd(StatusInd statusInd) {
        this.setStatusInd(statusInd);
        return this;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Hospital lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Hospital lastModifiedBy(String lastModifiedBy) {
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

    public Hospital state(State state) {
        this.setState(state);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Hospital district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Taluka getTaluka() {
        return this.taluka;
    }

    public void setTaluka(Taluka taluka) {
        this.taluka = taluka;
    }

    public Hospital taluka(Taluka taluka) {
        this.setTaluka(taluka);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Hospital city(City city) {
        this.setCity(city);
        return this;
    }

    public MunicipalCorp getMunicipalCorp() {
        return this.municipalCorp;
    }

    public void setMunicipalCorp(MunicipalCorp municipalCorp) {
        this.municipalCorp = municipalCorp;
    }

    public Hospital municipalCorp(MunicipalCorp municipalCorp) {
        this.setMunicipalCorp(municipalCorp);
        return this;
    }

    public HospitalType getHospitalType() {
        return this.hospitalType;
    }

    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }

    public Hospital hospitalType(HospitalType hospitalType) {
        this.setHospitalType(hospitalType);
        return this;
    }

    public Set<Supplier> getSuppliers() {
        return this.suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Hospital suppliers(Set<Supplier> suppliers) {
        this.setSuppliers(suppliers);
        return this;
    }

    public Hospital addSupplier(Supplier supplier) {
        this.suppliers.add(supplier);
        supplier.getHospitals().add(this);
        return this;
    }

    public Hospital removeSupplier(Supplier supplier) {
        this.suppliers.remove(supplier);
        supplier.getHospitals().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hospital)) {
            return false;
        }
        return id != null && id.equals(((Hospital) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Hospital{" +
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
            "}";
    }
}
