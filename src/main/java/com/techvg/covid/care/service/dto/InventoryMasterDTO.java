package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.InventoryMaster} entity.
 */
public class InventoryMasterDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Double volume;

    @NotNull
    private String unit;

    private Double calulateVolume;

    private String dimensions;

    private String subTypeInd;

    private Boolean deleted;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getCalulateVolume() {
        return calulateVolume;
    }

    public void setCalulateVolume(Double calulateVolume) {
        this.calulateVolume = calulateVolume;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getSubTypeInd() {
        return subTypeInd;
    }

    public void setSubTypeInd(String subTypeInd) {
        this.subTypeInd = subTypeInd;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        if (!(o instanceof InventoryMasterDTO)) {
            return false;
        }

        InventoryMasterDTO inventoryMasterDTO = (InventoryMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryMasterDTO{" +
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
            ", inventoryType=" + getInventoryType() +
            "}";
    }
}
