package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "tracking_no", nullable = false)
    private String trackingNo;

    @NotNull
    @Column(name = "moba_id", nullable = false)
    private Long mobaId;

    @NotNull
    @Column(name = "number_plate", nullable = false)
    private String numberPlate;

    @NotNull
    @Column(name = "stock", nullable = false)
    private Long stock;

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

    @Column(name = "comment")
    private String comment;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "trip")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "supplier", "hospital", "transactions", "trip" }, allowSetters = true)
    private Set<TripDetails> tripDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Supplier supplier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trip id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrackingNo() {
        return this.trackingNo;
    }

    public Trip trackingNo(String trackingNo) {
        this.setTrackingNo(trackingNo);
        return this;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public Long getMobaId() {
        return this.mobaId;
    }

    public Trip mobaId(Long mobaId) {
        this.setMobaId(mobaId);
        return this;
    }

    public void setMobaId(Long mobaId) {
        this.mobaId = mobaId;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

    public Trip numberPlate(String numberPlate) {
        this.setNumberPlate(numberPlate);
        return this;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public Long getStock() {
        return this.stock;
    }

    public Trip stock(Long stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public Trip status(TransactionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Trip createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Trip createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Trip lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getComment() {
        return this.comment;
    }

    public Trip comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Trip lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<TripDetails> getTripDetails() {
        return this.tripDetails;
    }

    public void setTripDetails(Set<TripDetails> tripDetails) {
        if (this.tripDetails != null) {
            this.tripDetails.forEach(i -> i.setTrip(null));
        }
        if (tripDetails != null) {
            tripDetails.forEach(i -> i.setTrip(this));
        }
        this.tripDetails = tripDetails;
    }

    public Trip tripDetails(Set<TripDetails> tripDetails) {
        this.setTripDetails(tripDetails);
        return this;
    }

    public Trip addTripDetails(TripDetails tripDetails) {
        this.tripDetails.add(tripDetails);
        tripDetails.setTrip(this);
        return this;
    }

    public Trip removeTripDetails(TripDetails tripDetails) {
        this.tripDetails.remove(tripDetails);
        tripDetails.setTrip(null);
        return this;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Trip supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trip{" +
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
            "}";
    }
}
