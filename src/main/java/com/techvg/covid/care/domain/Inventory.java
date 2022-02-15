package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Long stock;

    @Column(name = "capcity")
    private Long capcity;

    @Column(name = "installed_capcity")
    private Long installedCapcity;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventoryType" }, allowSetters = true)
    private InventoryMaster inventoryMaster;

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Supplier supplier;

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

    public Inventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return this.stock;
    }

    public Inventory stock(Long stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCapcity() {
        return this.capcity;
    }

    public Inventory capcity(Long capcity) {
        this.setCapcity(capcity);
        return this;
    }

    public void setCapcity(Long capcity) {
        this.capcity = capcity;
    }

    public Long getInstalledCapcity() {
        return this.installedCapcity;
    }

    public Inventory installedCapcity(Long installedCapcity) {
        this.setInstalledCapcity(installedCapcity);
        return this;
    }

    public void setInstalledCapcity(Long installedCapcity) {
        this.installedCapcity = installedCapcity;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Inventory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Inventory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InventoryMaster getInventoryMaster() {
        return this.inventoryMaster;
    }

    public void setInventoryMaster(InventoryMaster inventoryMaster) {
        this.inventoryMaster = inventoryMaster;
    }

    public Inventory inventoryMaster(InventoryMaster inventoryMaster) {
        this.setInventoryMaster(inventoryMaster);
        return this;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Inventory supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Inventory hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", installedCapcity=" + getInstalledCapcity() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
