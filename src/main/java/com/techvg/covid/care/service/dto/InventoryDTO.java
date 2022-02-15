package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Inventory} entity.
 */
public class InventoryDTO implements Serializable {

    private Long id;

    @NotNull
    private Long stock;

    private Long capcity;

    private Long installedCapcity;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private InventoryMasterDTO inventoryMaster;

    private SupplierDTO supplier;

    private HospitalDTO hospital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCapcity() {
        return capcity;
    }

    public void setCapcity(Long capcity) {
        this.capcity = capcity;
    }

    public Long getInstalledCapcity() {
        return installedCapcity;
    }

    public void setInstalledCapcity(Long installedCapcity) {
        this.installedCapcity = installedCapcity;
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

    public InventoryMasterDTO getInventoryMaster() {
        return inventoryMaster;
    }

    public void setInventoryMaster(InventoryMasterDTO inventoryMaster) {
        this.inventoryMaster = inventoryMaster;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryDTO)) {
            return false;
        }

        InventoryDTO inventoryDTO = (InventoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", installedCapcity=" + getInstalledCapcity() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", inventoryMaster=" + getInventoryMaster() +
            ", supplier=" + getSupplier() +
            ", hospital=" + getHospital() +
            "}";
    }
}
