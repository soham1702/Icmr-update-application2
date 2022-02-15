package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.AuditSystem} entity.
 */
public class AuditSystemDTO implements Serializable {

    private Long id;

    @NotNull
    private String auditorName;

    private Long defectCount;

    private Long defectFixCount;

    @NotNull
    private Instant inspectionDate;

    private String remark;

    private String status;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private AuditTypeDTO auditType;

    private HospitalDTO hospital;

    private SupplierDTO supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Long getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(Long defectCount) {
        this.defectCount = defectCount;
    }

    public Long getDefectFixCount() {
        return defectFixCount;
    }

    public void setDefectFixCount(Long defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public Instant getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Instant inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public AuditTypeDTO getAuditType() {
        return auditType;
    }

    public void setAuditType(AuditTypeDTO auditType) {
        this.auditType = auditType;
    }

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditSystemDTO)) {
            return false;
        }

        AuditSystemDTO auditSystemDTO = (AuditSystemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditSystemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystemDTO{" +
            "id=" + getId() +
            ", auditorName='" + getAuditorName() + "'" +
            ", defectCount=" + getDefectCount() +
            ", defectFixCount=" + getDefectFixCount() +
            ", inspectionDate='" + getInspectionDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", auditType=" + getAuditType() +
            ", hospital=" + getHospital() +
            ", supplier=" + getSupplier() +
            "}";
    }
}
