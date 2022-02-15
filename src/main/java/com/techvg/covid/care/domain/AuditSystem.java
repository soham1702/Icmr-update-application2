package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AuditSystem.
 */
@Entity
@Table(name = "audit_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AuditSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "auditor_name", nullable = false)
    private String auditorName;

    @Column(name = "defect_count")
    private Long defectCount;

    @Column(name = "defect_fix_count")
    private Long defectFixCount;

    @NotNull
    @Column(name = "inspection_date", nullable = false)
    private Instant inspectionDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    private AuditType auditType;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "state", "district", "taluka", "city", "municipalCorp", "hospitalType", "suppliers" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Supplier supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AuditSystem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuditorName() {
        return this.auditorName;
    }

    public AuditSystem auditorName(String auditorName) {
        this.setAuditorName(auditorName);
        return this;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public Long getDefectCount() {
        return this.defectCount;
    }

    public AuditSystem defectCount(Long defectCount) {
        this.setDefectCount(defectCount);
        return this;
    }

    public void setDefectCount(Long defectCount) {
        this.defectCount = defectCount;
    }

    public Long getDefectFixCount() {
        return this.defectFixCount;
    }

    public AuditSystem defectFixCount(Long defectFixCount) {
        this.setDefectFixCount(defectFixCount);
        return this;
    }

    public void setDefectFixCount(Long defectFixCount) {
        this.defectFixCount = defectFixCount;
    }

    public Instant getInspectionDate() {
        return this.inspectionDate;
    }

    public AuditSystem inspectionDate(Instant inspectionDate) {
        this.setInspectionDate(inspectionDate);
        return this;
    }

    public void setInspectionDate(Instant inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public AuditSystem remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return this.status;
    }

    public AuditSystem status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public AuditSystem lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public AuditSystem lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public AuditType getAuditType() {
        return this.auditType;
    }

    public void setAuditType(AuditType auditType) {
        this.auditType = auditType;
    }

    public AuditSystem auditType(AuditType auditType) {
        this.setAuditType(auditType);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public AuditSystem hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public AuditSystem supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditSystem)) {
            return false;
        }
        return id != null && id.equals(((AuditSystem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditSystem{" +
            "id=" + getId() +
            ", auditorName='" + getAuditorName() + "'" +
            ", defectCount=" + getDefectCount() +
            ", defectFixCount=" + getDefectFixCount() +
            ", inspectionDate='" + getInspectionDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", status='" + getStatus() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
