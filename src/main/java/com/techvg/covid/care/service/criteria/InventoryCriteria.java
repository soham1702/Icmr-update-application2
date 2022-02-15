package com.techvg.covid.care.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.techvg.covid.care.domain.Inventory} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.InventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stock;

    private LongFilter capcity;

    private LongFilter installedCapcity;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter inventoryMasterId;

    private LongFilter supplierId;

    private LongFilter hospitalId;

    private Boolean distinct;

    public InventoryCriteria() {}

    public InventoryCriteria(InventoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.capcity = other.capcity == null ? null : other.capcity.copy();
        this.installedCapcity = other.installedCapcity == null ? null : other.installedCapcity.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.inventoryMasterId = other.inventoryMasterId == null ? null : other.inventoryMasterId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InventoryCriteria copy() {
        return new InventoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getStock() {
        return stock;
    }

    public LongFilter stock() {
        if (stock == null) {
            stock = new LongFilter();
        }
        return stock;
    }

    public void setStock(LongFilter stock) {
        this.stock = stock;
    }

    public LongFilter getCapcity() {
        return capcity;
    }

    public LongFilter capcity() {
        if (capcity == null) {
            capcity = new LongFilter();
        }
        return capcity;
    }

    public void setCapcity(LongFilter capcity) {
        this.capcity = capcity;
    }

    public LongFilter getInstalledCapcity() {
        return installedCapcity;
    }

    public LongFilter installedCapcity() {
        if (installedCapcity == null) {
            installedCapcity = new LongFilter();
        }
        return installedCapcity;
    }

    public void setInstalledCapcity(LongFilter installedCapcity) {
        this.installedCapcity = installedCapcity;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getInventoryMasterId() {
        return inventoryMasterId;
    }

    public LongFilter inventoryMasterId() {
        if (inventoryMasterId == null) {
            inventoryMasterId = new LongFilter();
        }
        return inventoryMasterId;
    }

    public void setInventoryMasterId(LongFilter inventoryMasterId) {
        this.inventoryMasterId = inventoryMasterId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public LongFilter supplierId() {
        if (supplierId == null) {
            supplierId = new LongFilter();
        }
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getHospitalId() {
        return hospitalId;
    }

    public LongFilter hospitalId() {
        if (hospitalId == null) {
            hospitalId = new LongFilter();
        }
        return hospitalId;
    }

    public void setHospitalId(LongFilter hospitalId) {
        this.hospitalId = hospitalId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final InventoryCriteria that = (InventoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(capcity, that.capcity) &&
            Objects.equals(installedCapcity, that.installedCapcity) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(inventoryMasterId, that.inventoryMasterId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            stock,
            capcity,
            installedCapcity,
            lastModified,
            lastModifiedBy,
            inventoryMasterId,
            supplierId,
            hospitalId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (capcity != null ? "capcity=" + capcity + ", " : "") +
            (installedCapcity != null ? "installedCapcity=" + installedCapcity + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (inventoryMasterId != null ? "inventoryMasterId=" + inventoryMasterId + ", " : "") +
            (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
