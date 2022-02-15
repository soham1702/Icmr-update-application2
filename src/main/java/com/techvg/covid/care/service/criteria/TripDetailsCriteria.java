package com.techvg.covid.care.service.criteria;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;
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
 * Criteria class for the {@link com.techvg.covid.care.domain.TripDetails} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.TripDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trip-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TripDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TransactionStatus
     */
    public static class TransactionStatusFilter extends Filter<TransactionStatus> {

        public TransactionStatusFilter() {}

        public TransactionStatusFilter(TransactionStatusFilter filter) {
            super(filter);
        }

        @Override
        public TransactionStatusFilter copy() {
            return new TransactionStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter stockSent;

    private LongFilter stockRec;

    private StringFilter comment;

    private StringFilter receiverComment;

    private TransactionStatusFilter status;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter supplierId;

    private LongFilter hospitalId;

    private LongFilter transactionsId;

    private LongFilter tripId;

    private Boolean distinct;

    public TripDetailsCriteria() {}

    public TripDetailsCriteria(TripDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.stockSent = other.stockSent == null ? null : other.stockSent.copy();
        this.stockRec = other.stockRec == null ? null : other.stockRec.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.receiverComment = other.receiverComment == null ? null : other.receiverComment.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.transactionsId = other.transactionsId == null ? null : other.transactionsId.copy();
        this.tripId = other.tripId == null ? null : other.tripId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TripDetailsCriteria copy() {
        return new TripDetailsCriteria(this);
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

    public LongFilter getStockSent() {
        return stockSent;
    }

    public LongFilter stockSent() {
        if (stockSent == null) {
            stockSent = new LongFilter();
        }
        return stockSent;
    }

    public void setStockSent(LongFilter stockSent) {
        this.stockSent = stockSent;
    }

    public LongFilter getStockRec() {
        return stockRec;
    }

    public LongFilter stockRec() {
        if (stockRec == null) {
            stockRec = new LongFilter();
        }
        return stockRec;
    }

    public void setStockRec(LongFilter stockRec) {
        this.stockRec = stockRec;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public StringFilter getReceiverComment() {
        return receiverComment;
    }

    public StringFilter receiverComment() {
        if (receiverComment == null) {
            receiverComment = new StringFilter();
        }
        return receiverComment;
    }

    public void setReceiverComment(StringFilter receiverComment) {
        this.receiverComment = receiverComment;
    }

    public TransactionStatusFilter getStatus() {
        return status;
    }

    public TransactionStatusFilter status() {
        if (status == null) {
            status = new TransactionStatusFilter();
        }
        return status;
    }

    public void setStatus(TransactionStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
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

    public LongFilter getTransactionsId() {
        return transactionsId;
    }

    public LongFilter transactionsId() {
        if (transactionsId == null) {
            transactionsId = new LongFilter();
        }
        return transactionsId;
    }

    public void setTransactionsId(LongFilter transactionsId) {
        this.transactionsId = transactionsId;
    }

    public LongFilter getTripId() {
        return tripId;
    }

    public LongFilter tripId() {
        if (tripId == null) {
            tripId = new LongFilter();
        }
        return tripId;
    }

    public void setTripId(LongFilter tripId) {
        this.tripId = tripId;
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
        final TripDetailsCriteria that = (TripDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(stockSent, that.stockSent) &&
            Objects.equals(stockRec, that.stockRec) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(receiverComment, that.receiverComment) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(transactionsId, that.transactionsId) &&
            Objects.equals(tripId, that.tripId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            stockSent,
            stockRec,
            comment,
            receiverComment,
            status,
            createdDate,
            createdBy,
            lastModified,
            lastModifiedBy,
            supplierId,
            hospitalId,
            transactionsId,
            tripId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (stockSent != null ? "stockSent=" + stockSent + ", " : "") +
            (stockRec != null ? "stockRec=" + stockRec + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (receiverComment != null ? "receiverComment=" + receiverComment + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            (transactionsId != null ? "transactionsId=" + transactionsId + ", " : "") +
            (tripId != null ? "tripId=" + tripId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
