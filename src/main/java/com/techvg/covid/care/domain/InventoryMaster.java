package com.techvg.covid.care.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InventoryMaster.
 */
@Entity
@Table(name = "inventory_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "volume")
    private Double volume;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "calulate_volume")
    private Double calulateVolume;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "sub_type_ind")
    private String subTypeInd;

    @Column(name = "deleted")
    private Boolean deleted;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    private InventoryType inventoryType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InventoryMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public InventoryMaster name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public InventoryMaster description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVolume() {
        return this.volume;
    }

    public InventoryMaster volume(Double volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return this.unit;
    }

    public InventoryMaster unit(String unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCalulateVolume() {
        return this.calulateVolume;
    }

    public InventoryMaster calulateVolume(Double calulateVolume) {
        this.setCalulateVolume(calulateVolume);
        return this;
    }

    public void setCalulateVolume(Double calulateVolume) {
        this.calulateVolume = calulateVolume;
    }

    public String getDimensions() {
        return this.dimensions;
    }

    public InventoryMaster dimensions(String dimensions) {
        this.setDimensions(dimensions);
        return this;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSubTypeInd() {
        return this.subTypeInd;
    }

    public InventoryMaster subTypeInd(String subTypeInd) {
        this.setSubTypeInd(subTypeInd);
        return this;
    }

    public void setSubTypeInd(String subTypeInd) {
        this.subTypeInd = subTypeInd;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public InventoryMaster deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public InventoryMaster lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public InventoryMaster lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InventoryType getInventoryType() {
        return this.inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public InventoryMaster inventoryType(InventoryType inventoryType) {
        this.setInventoryType(inventoryType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryMaster)) {
            return false;
        }
        return id != null && id.equals(((InventoryMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryMaster{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", volume=" + getVolume() +
            ", unit='" + getUnit() + "'" +
            ", calulateVolume=" + getCalulateVolume() +
            ", dimensions='" + getDimensions() + "'" +
            ", subTypeInd='" + getSubTypeInd() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
