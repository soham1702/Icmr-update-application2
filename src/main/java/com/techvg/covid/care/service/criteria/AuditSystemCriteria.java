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
 * Criteria class for the {@link com.techvg.covid.care.domain.AuditSystem} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.AuditSystemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /audit-systems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuditSystemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter auditorName;

    private LongFilter defectCount;

    private LongFilter defectFixCount;

    private InstantFilter inspectionDate;

    private StringFilter remark;

    private StringFilter status;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter auditTypeId;

    private LongFilter hospitalId;

    private LongFilter supplierId;

    private Boolean distinct;

    public AuditSystemCriteria() {}

    public AuditSystemCriteria(AuditSystemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.auditorName = other.auditorName == null ? null : other.auditorName.copy();
        this.defectCount = other.defectCount == null ? null : other.defectCount.copy();
        this.defectFixCount = other.defectFixCount == null ? null : other.defectFixCount.copy();
        this.inspectionDate = other.inspectionDate == null ? null : other.inspectionDate.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.auditTypeId = other.auditTypeId == null ? null : other.auditTypeId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AuditSystemCriteria copy() {
        return new AuditSystemCriteria(this);
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

    public StringFilter getAuditorName() {
        return auditorName;
    }

    public StringFilter auditorName() {
        if (auditorName == null) {
            auditorName = new StringFilter();
        }
        return auditorName;
    }

    public void setAuditorName(StringFilter auditorName) {
        this.auditorName = auditorName;
    }

    public LongFilter getDefectCount() {
        return defectCount;
    }

    public LongFilter defectCount() {
        if (defectCount == null) {
            defectCount = new LongFilter();
        }
        return defectCount;
    }

    public void setDefectCount(LongFilter defectCount) {
        this.defectCount = defectCount;
    }

    public LongFilter getDefectFixCount() {
        return defectFixCount;
    }

    public LongFilter defectFixCount() {
        if (defectFixCount == null) {
            defectFixCount = new LongFilter();
        }
        return defectFixCount;
    }

    public void setDefectFixCount(LongFilter defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public InstantFilter getInspectionDate() {
        return inspectionDate;
    }

    public InstantFilter inspectionDate() {
        if (inspectionDate == null) {
            inspectionDate = new InstantFilter();
        }
        return inspectionDate;
    }

    public void setInspectionDate(InstantFilter inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public StringFilter getStatus() {
        return status;
    }

    public StringFilter status() {
        if (status == null) {
            status = new StringFilter();
        }
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public LongFilter getAuditTypeId() {
        return auditTypeId;
    }

    public LongFilter auditTypeId() {
        if (auditTypeId == null) {
            auditTypeId = new LongFilter();
        }
        return auditTypeId;
    }

    public void setAuditTypeId(LongFilter auditTypeId) {
        this.auditTypeId = auditTypeId;
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
        final AuditSystemCriteria that = (AuditSystemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(auditorName, that.auditorName) &&
            Objects.equals(defectCount, that.defectCount) &&
            Objects.equals(defectFixCount, that.defectFixCount) &&
            Objects.equals(inspectionDate, that.inspectionDate) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(status, that.status) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(auditTypeId, that.auditTypeId) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            auditorName,
            defectCount,
            defectFixCount,
            inspectionDate,
            remark,
            status,
            lastModified,
            lastModifiedBy,
            auditTypeId,
            hospitalId,
            supplierId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (auditorName != null ? "auditorName=" + auditorName + ", " : "") +
            (defectCount != null ? "defectCount=" + defectCount + ", " : "") +
            (defectFixCount != null ? "defectFixCount=" + defectFixCount + ", " : "") +
            (inspectionDate != null ? "inspectionDate=" + inspectionDate + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (auditTypeId != null ? "auditTypeId=" + auditTypeId + ", " : "") +
            (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
