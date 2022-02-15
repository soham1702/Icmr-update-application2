package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.TripDetails} entity.
 */
public class TripDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long stockSent;

    private Long stockRec;

    private String comment;

    private String receiverComment;

    @NotNull
    private TransactionStatus status;

    @NotNull
    private Instant createdDate;

    @NotNull
    private String createdBy;

    private Instant lastModified;

    private String lastModifiedBy;

    private SupplierDTO supplier;

    private HospitalDTO hospital;

    private TransactionsDTO transactions;

    private TripDTO trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockSent() {
        return stockSent;
    }

    public void setStockSent(Long stockSent) {
        this.stockSent = stockSent;
    }

    public Long getStockRec() {
        return stockRec;
    }

    public void setStockRec(Long stockRec) {
        this.stockRec = stockRec;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReceiverComment() {
        return receiverComment;
    }

    public void setReceiverComment(String receiverComment) {
        this.receiverComment = receiverComment;
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

    public HospitalDTO getHospital() {
        return hospital;
    }

    public void setHospital(HospitalDTO hospital) {
        this.hospital = hospital;
    }

    public TransactionsDTO getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionsDTO transactions) {
        this.transactions = transactions;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public void setTrip(TripDTO trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripDetailsDTO)) {
            return false;
        }

        TripDetailsDTO tripDetailsDTO = (TripDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tripDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetailsDTO{" +
            "id=" + getId() +
            ", stockSent=" + getStockSent() +
            ", stockRec=" + getStockRec() +
            ", comment='" + getComment() + "'" +
            ", receiverComment='" + getReceiverComment() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", supplier=" + getSupplier() +
            ", hospital=" + getHospital() +
            ", transactions=" + getTransactions() +
            ", trip=" + getTrip() +
            "}";
    }
}
