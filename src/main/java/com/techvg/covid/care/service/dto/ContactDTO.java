package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Contact} entity.
 */
public class ContactDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String contactNo;

    private String email;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private ContactTypeDTO contactType;

    private HospitalDTO hospital;

    private SupplierDTO supplier;

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

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public ContactTypeDTO getContactType() {
        return contactType;
    }

    public void setContactType(ContactTypeDTO contactType) {
        this.contactType = contactType;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactDTO)) {
            return false;
        }

        ContactDTO contactDTO = (ContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, contactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", contactNo='" + getContactNo() + "'" +
            ", email='" + getEmail() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", contactType=" + getContactType() +
            ", hospital=" + getHospital() +
            ", supplier=" + getSupplier() +
            "}";
    }
}
