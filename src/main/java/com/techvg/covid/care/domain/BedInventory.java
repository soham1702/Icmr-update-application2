package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BedInventory.
 */
@Entity
@Table(name = "bed_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BedInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "bed_count", nullable = false)
    private Long bedCount;

    @NotNull
    @Column(name = "occupied", nullable = false)
    private Long occupied;

    @Column(name = "on_cylinder")
    private Long onCylinder;

    @Column(name = "on_lmo")
    private Long onLMO;

    @Column(name = "on_concentrators")
    private Long onConcentrators;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    private BedType bedType;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "state", "district", "taluka", "city", "municipalCorp", "hospitalType", "suppliers" },
        allowSetters = true
    )
    private Hospital hospital;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BedInventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBedCount() {
        return this.bedCount;
    }

    public BedInventory bedCount(Long bedCount) {
        this.setBedCount(bedCount);
        return this;
    }

    public void setBedCount(Long bedCount) {
        this.bedCount = bedCount;
    }

    public Long getOccupied() {
        return this.occupied;
    }

    public BedInventory occupied(Long occupied) {
        this.setOccupied(occupied);
        return this;
    }

    public void setOccupied(Long occupied) {
        this.occupied = occupied;
    }

    public Long getOnCylinder() {
        return this.onCylinder;
    }

    public BedInventory onCylinder(Long onCylinder) {
        this.setOnCylinder(onCylinder);
        return this;
    }

    public void setOnCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
    }

    public Long getOnLMO() {
        return this.onLMO;
    }

    public BedInventory onLMO(Long onLMO) {
        this.setOnLMO(onLMO);
        return this;
    }

    public void setOnLMO(Long onLMO) {
        this.onLMO = onLMO;
    }

    public Long getOnConcentrators() {
        return this.onConcentrators;
    }

    public BedInventory onConcentrators(Long onConcentrators) {
        this.setOnConcentrators(onConcentrators);
        return this;
    }

    public void setOnConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public BedInventory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public BedInventory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public BedType getBedType() {
        return this.bedType;
    }

    public void setBedType(BedType bedType) {
        this.bedType = bedType;
    }

    public BedInventory bedType(BedType bedType) {
        this.setBedType(bedType);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public BedInventory hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BedInventory)) {
            return false;
        }
        return id != null && id.equals(((BedInventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BedInventory{" +
            "id=" + getId() +
            ", bedCount=" + getBedCount() +
            ", occupied=" + getOccupied() +
            ", onCylinder=" + getOnCylinder() +
            ", onLMO=" + getOnLMO() +
            ", onConcentrators=" + getOnConcentrators() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
