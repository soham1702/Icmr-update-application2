package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Trip} entity.
 */
public class TripDTO implements Serializable {

    private Long id;

    @NotNull
    private String trackingNo;

    @NotNull
    private Long mobaId;

    @NotNull
    private String numberPlate;

    @NotNull
    private Long stock;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private Instant createdDate;

    @NotNull
    private String createdBy;

    private Instant lastModified;

    private String comment;

    private String lastModifiedBy;

    private SupplierDTO supplier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getMobaId() {
        return mobaId;
    }

    public void setMobaId(Long mobaId) {
        this.mobaId = mobaId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
        if (!(o instanceof TripDTO)) {
            return false;
        }

        TripDTO tripDTO = (TripDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tripDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDTO{" +
            "id=" + getId() +
            ", trackingNo='" + getTrackingNo() + "'" +
            ", mobaId=" + getMobaId() +
            ", numberPlate='" + getNumberPlate() + "'" +
            ", stock=" + getStock() +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", comment='" + getComment() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", supplier=" + getSupplier() +
            "}";
    }
}
