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
 * Criteria class for the {@link com.techvg.covid.care.domain.Trip} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.TripResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /trips?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TripCriteria implements Serializable, Criteria {

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

    private StringFilter trackingNo;

    private LongFilter mobaId;

    private StringFilter numberPlate;

    private LongFilter stock;

    private TransactionStatusFilter status;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModified;

    private StringFilter comment;

    private StringFilter lastModifiedBy;

    private LongFilter tripDetailsId;

    private LongFilter supplierId;

    private Boolean distinct;

    public TripCriteria() {}

    public TripCriteria(TripCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.trackingNo = other.trackingNo == null ? null : other.trackingNo.copy();
        this.mobaId = other.mobaId == null ? null : other.mobaId.copy();
        this.numberPlate = other.numberPlate == null ? null : other.numberPlate.copy();
        this.stock = other.stock == null ? null : other.stock.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.tripDetailsId = other.tripDetailsId == null ? null : other.tripDetailsId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TripCriteria copy() {
        return new TripCriteria(this);
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

    public StringFilter getTrackingNo() {
        return trackingNo;
    }

    public StringFilter trackingNo() {
        if (trackingNo == null) {
            trackingNo = new StringFilter();
        }
        return trackingNo;
    }

    public void setTrackingNo(StringFilter trackingNo) {
        this.trackingNo = trackingNo;
    }

    public LongFilter getMobaId() {
        return mobaId;
    }

    public LongFilter mobaId() {
        if (mobaId == null) {
            mobaId = new LongFilter();
        }
        return mobaId;
    }

    public void setMobaId(LongFilter mobaId) {
        this.mobaId = mobaId;
    }

    public StringFilter getNumberPlate() {
        return numberPlate;
    }

    public StringFilter numberPlate() {
        if (numberPlate == null) {
            numberPlate = new StringFilter();
        }
        return numberPlate;
    }

    public void setNumberPlate(StringFilter numberPlate) {
        this.numberPlate = numberPlate;
    }

    public LongFilter getStock() {
        return stock;
    }

    public LongFilter stock() {
        if (stock == null) {
            stock = new LongFilter();
        }
        return stock;
    }

    public void setStock(LongFilter stock) {
        this.stock = stock;
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

    public LongFilter getTripDetailsId() {
        return tripDetailsId;
    }

    public LongFilter tripDetailsId() {
        if (tripDetailsId == null) {
            tripDetailsId = new LongFilter();
        }
        return tripDetailsId;
    }

    public void setTripDetailsId(LongFilter tripDetailsId) {
        this.tripDetailsId = tripDetailsId;
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
        final TripCriteria that = (TripCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(trackingNo, that.trackingNo) &&
            Objects.equals(mobaId, that.mobaId) &&
            Objects.equals(numberPlate, that.numberPlate) &&
            Objects.equals(stock, that.stock) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(tripDetailsId, that.tripDetailsId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            trackingNo,
            mobaId,
            numberPlate,
            stock,
            status,
            createdDate,
            createdBy,
            lastModified,
            comment,
            lastModifiedBy,
            tripDetailsId,
            supplierId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TripCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (trackingNo != null ? "trackingNo=" + trackingNo + ", " : "") +
            (mobaId != null ? "mobaId=" + mobaId + ", " : "") +
            (numberPlate != null ? "numberPlate=" + numberPlate + ", " : "") +
            (stock != null ? "stock=" + stock + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (tripDetailsId != null ? "tripDetailsId=" + tripDetailsId + ", " : "") +
            (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
