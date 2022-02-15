package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.BedTransactions} entity.
 */
public class BedTransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long occupied;

    private Long onCylinder;

    private Long onLMO;

    private Long onConcentrators;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private BedTypeDTO bedType;

    private HospitalDTO hospital;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOccupied() {
        return occupied;
    }

    public void setOccupied(Long occupied) {
        this.occupied = occupied;
    }

    public Long getOnCylinder() {
        return onCylinder;
    }

    public void setOnCylinder(Long onCylinder) {
        this.onCylinder = onCylinder;
    }

    public Long getOnLMO() {
        return onLMO;
    }

    public void setOnLMO(Long onLMO) {
        this.onLMO = onLMO;
    }

    public Long getOnConcentrators() {
        return onConcentrators;
    }

    public void setOnConcentrators(Long onConcentrators) {
        this.onConcentrators = onConcentrators;
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

    public BedTypeDTO getBedType() {
        return bedType;
    }

    public void setBedType(BedTypeDTO bedType) {
        this.bedType = bedType;
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
        if (!(o instanceof BedTransactionsDTO)) {
            return false;
        }

        BedTransactionsDTO bedTransactionsDTO = (BedTransactionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bedTransactionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BedTransactionsDTO{" +
            "id=" + getId() +
            ", occupied=" + getOccupied() +
            ", onCylinder=" + getOnCylinder() +
            ", onLMO=" + getOnLMO() +
            ", onConcentrators=" + getOnConcentrators() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", bedType=" + getBedType() +
            ", hospital=" + getHospital() +
            "}";
    }
}
