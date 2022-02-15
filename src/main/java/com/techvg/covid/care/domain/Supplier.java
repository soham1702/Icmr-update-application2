package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.domain.enumeration.StatusInd;
import com.techvg.covid.care.domain.enumeration.SupplierType;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "supplier_type", nullable = false)
    private SupplierType supplierType;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "email")
    private String email;

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
    private InventoryType inventoryType;

    @ManyToMany(mappedBy = "suppliers")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "state", "district", "taluka", "city", "municipalCorp", "hospitalType", "suppliers" },
        allowSetters = true
    )
    private Set<Hospital> hospitals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Supplier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Supplier name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierType getSupplierType() {
        return this.supplierType;
    }

    public Supplier supplierType(SupplierType supplierType) {
        this.setSupplierType(supplierType);
        return this;
    }

    public void setSupplierType(SupplierType supplierType) {
        this.supplierType = supplierType;
    }

    public String getContactNo() {
        return this.contactNo;
    }

    public Supplier contactNo(String contactNo) {
        this.setContactNo(contactNo);
        return this;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Supplier latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Supplier longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEmail() {
        return this.email;
    }

    public Supplier email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNo() {
        return this.registrationNo;
    }

    public Supplier registrationNo(String registrationNo) {
        this.setRegistrationNo(registrationNo);
        return this;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Supplier address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Supplier address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getArea() {
        return this.area;
    }

    public Supplier area(String area) {
        this.setArea(area);
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPinCode() {
        return this.pinCode;
    }

    public Supplier pinCode(String pinCode) {
        this.setPinCode(pinCode);
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public StatusInd getStatusInd() {
        return this.statusInd;
    }

    public Supplier statusInd(StatusInd statusInd) {
        this.setStatusInd(statusInd);
        return this;
    }

    public void setStatusInd(StatusInd statusInd) {
        this.statusInd = statusInd;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Supplier lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Supplier lastModifiedBy(String lastModifiedBy) {
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

    public Supplier state(State state) {
        this.setState(state);
        return this;
    }

    public District getDistrict() {
        return this.district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public Supplier district(District district) {
        this.setDistrict(district);
        return this;
    }

    public Taluka getTaluka() {
        return this.taluka;
    }

    public void setTaluka(Taluka taluka) {
        this.taluka = taluka;
    }

    public Supplier taluka(Taluka taluka) {
        this.setTaluka(taluka);
        return this;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Supplier city(City city) {
        this.setCity(city);
        return this;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Supplier inventoryType(InventoryType inventoryType) {
        this.setInventoryType(inventoryType);
        return this;
    }

    public Set<Hospital> getHospitals() {
        return this.hospitals;
    }

    public void setHospitals(Set<Hospital> hospitals) {
        if (this.hospitals != null) {
            this.hospitals.forEach(i -> i.removeSupplier(this));
        }
        if (hospitals != null) {
            hospitals.forEach(i -> i.addSupplier(this));
        }
        this.hospitals = hospitals;
    }

    public Supplier hospitals(Set<Hospital> hospitals) {
        this.setHospitals(hospitals);
        return this;
    }

    public Supplier addHospital(Hospital hospital) {
        this.hospitals.add(hospital);
        hospital.getSuppliers().add(this);
        return this;
    }

    public Supplier removeHospital(Hospital hospital) {
        this.hospitals.remove(hospital);
        hospital.getSuppliers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Supplier)) {
            return false;
        }
        return id != null && id.equals(((Supplier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Supplier{" +
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
            "}";
    }
}
