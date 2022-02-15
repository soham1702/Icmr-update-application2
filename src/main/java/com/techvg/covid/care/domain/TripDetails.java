package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TripDetails.
 */
@Entity
@Table(name = "trip_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TripDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "stock_sent", nullable = false)
    private Long stockSent;

    @Column(name = "stock_rec")
    private Long stockRec;

    @Column(name = "comment")
    private String comment;

    @Column(name = "receiver_comment")
    private String receiverComment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "state", "district", "taluka", "city", "municipalCorp", "hospitalType", "suppliers" },
        allowSetters = true
    )
    private Hospital hospital;

    @ManyToOne
    @JsonIgnoreProperties(value = { "supplier", "inventory" }, allowSetters = true)
    private Transactions transactions;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tripDetails", "supplier" }, allowSetters = true)
    private Trip trip;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TripDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockSent() {
        return this.stockSent;
    }

    public TripDetails stockSent(Long stockSent) {
        this.setStockSent(stockSent);
        return this;
    }

    public void setStockSent(Long stockSent) {
        this.stockSent = stockSent;
    }

    public Long getStockRec() {
        return this.stockRec;
    }

    public TripDetails stockRec(Long stockRec) {
        this.setStockRec(stockRec);
        return this;
    }

    public void setStockRec(Long stockRec) {
        this.stockRec = stockRec;
    }

    public String getComment() {
        return this.comment;
    }

    public TripDetails comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReceiverComment() {
        return this.receiverComment;
    }

    public TripDetails receiverComment(String receiverComment) {
        this.setReceiverComment(receiverComment);
        return this;
    }

    public void setReceiverComment(String receiverComment) {
        this.receiverComment = receiverComment;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public TripDetails status(TransactionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public TripDetails createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public TripDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public TripDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public TripDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public TripDetails supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public TripDetails hospital(Hospital hospital) {
        this.setHospital(hospital);
        return this;
    }

    public Transactions getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Transactions transactions) {
        this.transactions = transactions;
    }

    public TripDetails transactions(Transactions transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public Trip getTrip() {
        return this.trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public TripDetails trip(Trip trip) {
        this.setTrip(trip);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TripDetails)) {
            return false;
        }
        return id != null && id.equals(((TripDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetails{" +
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
            "}";
    }
}
