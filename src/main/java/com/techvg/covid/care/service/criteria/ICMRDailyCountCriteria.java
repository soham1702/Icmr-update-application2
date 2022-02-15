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
 * Criteria class for the {@link com.techvg.covid.care.domain.ICMRDailyCount} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.ICMRDailyCountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /icmr-daily-counts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ICMRDailyCountCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter totalSamplesTested;

    private LongFilter totalNegative;

    private LongFilter totalPositive;

    private LongFilter currentPreviousMonthTest;

    private LongFilter districtId;

    private StringFilter remarks;

    private InstantFilter editedOn;

    private InstantFilter updatedDate;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private Boolean distinct;

    public ICMRDailyCountCriteria() {}

    public ICMRDailyCountCriteria(ICMRDailyCountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.totalSamplesTested = other.totalSamplesTested == null ? null : other.totalSamplesTested.copy();
        this.totalNegative = other.totalNegative == null ? null : other.totalNegative.copy();
        this.totalPositive = other.totalPositive == null ? null : other.totalPositive.copy();
        this.currentPreviousMonthTest = other.currentPreviousMonthTest == null ? null : other.currentPreviousMonthTest.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.editedOn = other.editedOn == null ? null : other.editedOn.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ICMRDailyCountCriteria copy() {
        return new ICMRDailyCountCriteria(this);
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

    public LongFilter getTotalSamplesTested() {
        return totalSamplesTested;
    }

    public LongFilter totalSamplesTested() {
        if (totalSamplesTested == null) {
            totalSamplesTested = new LongFilter();
        }
        return totalSamplesTested;
    }

    public void setTotalSamplesTested(LongFilter totalSamplesTested) {
        this.totalSamplesTested = totalSamplesTested;
    }

    public LongFilter getTotalNegative() {
        return totalNegative;
    }

    public LongFilter totalNegative() {
        if (totalNegative == null) {
            totalNegative = new LongFilter();
        }
        return totalNegative;
    }

    public void setTotalNegative(LongFilter totalNegative) {
        this.totalNegative = totalNegative;
    }

    public LongFilter getTotalPositive() {
        return totalPositive;
    }

    public LongFilter totalPositive() {
        if (totalPositive == null) {
            totalPositive = new LongFilter();
        }
        return totalPositive;
    }

    public void setTotalPositive(LongFilter totalPositive) {
        this.totalPositive = totalPositive;
    }

    public LongFilter getCurrentPreviousMonthTest() {
        return currentPreviousMonthTest;
    }

    public LongFilter currentPreviousMonthTest() {
        if (currentPreviousMonthTest == null) {
            currentPreviousMonthTest = new LongFilter();
        }
        return currentPreviousMonthTest;
    }

    public void setCurrentPreviousMonthTest(LongFilter currentPreviousMonthTest) {
        this.currentPreviousMonthTest = currentPreviousMonthTest;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public StringFilter remarks() {
        if (remarks == null) {
            remarks = new StringFilter();
        }
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public InstantFilter getEditedOn() {
        return editedOn;
    }

    public InstantFilter editedOn() {
        if (editedOn == null) {
            editedOn = new InstantFilter();
        }
        return editedOn;
    }

    public void setEditedOn(InstantFilter editedOn) {
        this.editedOn = editedOn;
    }

    public InstantFilter getUpdatedDate() {
        return updatedDate;
    }

    public InstantFilter updatedDate() {
        if (updatedDate == null) {
            updatedDate = new InstantFilter();
        }
        return updatedDate;
    }

    public void setUpdatedDate(InstantFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
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
        final ICMRDailyCountCriteria that = (ICMRDailyCountCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(totalSamplesTested, that.totalSamplesTested) &&
            Objects.equals(totalNegative, that.totalNegative) &&
            Objects.equals(totalPositive, that.totalPositive) &&
            Objects.equals(currentPreviousMonthTest, that.currentPreviousMonthTest) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(editedOn, that.editedOn) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            totalSamplesTested,
            totalNegative,
            totalPositive,
            currentPreviousMonthTest,
            districtId,
            remarks,
            editedOn,
            updatedDate,
            freeField1,
            freeField2,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ICMRDailyCountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (totalSamplesTested != null ? "totalSamplesTested=" + totalSamplesTested + ", " : "") +
            (totalNegative != null ? "totalNegative=" + totalNegative + ", " : "") +
            (totalPositive != null ? "totalPositive=" + totalPositive + ", " : "") +
            (currentPreviousMonthTest != null ? "currentPreviousMonthTest=" + currentPreviousMonthTest + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (remarks != null ? "remarks=" + remarks + ", " : "") +
            (editedOn != null ? "editedOn=" + editedOn + ", " : "") +
            (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
