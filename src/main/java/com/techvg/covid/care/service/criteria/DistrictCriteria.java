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
 * Criteria class for the {@link com.techvg.covid.care.domain.District} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.DistrictResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /districts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistrictCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private BooleanFilter deleted;

    private LongFilter lgdCode;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter stateId;

    private LongFilter divisionId;

    private Boolean distinct;

    public DistrictCriteria() {}

    public DistrictCriteria(DistrictCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.deleted = other.deleted == null ? null : other.deleted.copy();
        this.lgdCode = other.lgdCode == null ? null : other.lgdCode.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.divisionId = other.divisionId == null ? null : other.divisionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DistrictCriteria copy() {
        return new DistrictCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public BooleanFilter getDeleted() {
        return deleted;
    }

    public BooleanFilter deleted() {
        if (deleted == null) {
            deleted = new BooleanFilter();
        }
        return deleted;
    }

    public void setDeleted(BooleanFilter deleted) {
        this.deleted = deleted;
    }

    public LongFilter getLgdCode() {
        return lgdCode;
    }

    public LongFilter lgdCode() {
        if (lgdCode == null) {
            lgdCode = new LongFilter();
        }
        return lgdCode;
    }

    public void setLgdCode(LongFilter lgdCode) {
        this.lgdCode = lgdCode;
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

    public LongFilter getStateId() {
        return stateId;
    }

    public LongFilter stateId() {
        if (stateId == null) {
            stateId = new LongFilter();
        }
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getDivisionId() {
        return divisionId;
    }

    public LongFilter divisionId() {
        if (divisionId == null) {
            divisionId = new LongFilter();
        }
        return divisionId;
    }

    public void setDivisionId(LongFilter divisionId) {
        this.divisionId = divisionId;
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
        final DistrictCriteria that = (DistrictCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(deleted, that.deleted) &&
            Objects.equals(lgdCode, that.lgdCode) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(divisionId, that.divisionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deleted, lgdCode, lastModified, lastModifiedBy, stateId, divisionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (deleted != null ? "deleted=" + deleted + ", " : "") +
            (lgdCode != null ? "lgdCode=" + lgdCode + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (stateId != null ? "stateId=" + stateId + ", " : "") +
            (divisionId != null ? "divisionId=" + divisionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
