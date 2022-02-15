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
 * Criteria class for the {@link com.techvg.covid.care.domain.InventoryUsed} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.InventoryUsedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventory-useds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InventoryUsedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stock;

    private LongFilter capcity;

    private StringFilter comment;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter inventoryId;

    private Boolean distinct;

    public InventoryUsedCriteria() {}

    public InventoryUsedCriteria(InventoryUsedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.capcity = other.capcity == null ? null : other.capcity.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.inventoryId = other.inventoryId == null ? null : other.inventoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InventoryUsedCriteria copy() {
        return new InventoryUsedCriteria(this);
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

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
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

    public LongFilter getInventoryId() {
        return inventoryId;
    }

    public LongFilter inventoryId() {
        if (inventoryId == null) {
            inventoryId = new LongFilter();
        }
        return inventoryId;
    }

    public void setInventoryId(LongFilter inventoryId) {
        this.inventoryId = inventoryId;
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
        final InventoryUsedCriteria that = (InventoryUsedCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(capcity, that.capcity) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(inventoryId, that.inventoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stock, capcity, comment, lastModified, lastModifiedBy, inventoryId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (capcity != null ? "capcity=" + capcity + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (inventoryId != null ? "inventoryId=" + inventoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
