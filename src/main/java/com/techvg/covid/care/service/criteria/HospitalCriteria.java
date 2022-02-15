package com.techvg.covid.care.service.criteria;

import com.techvg.covid.care.domain.enumeration.HospitalCategory;
import com.techvg.covid.care.domain.enumeration.HospitalSubCategory;
import com.techvg.covid.care.domain.enumeration.StatusInd;
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
 * Criteria class for the {@link com.techvg.covid.care.domain.Hospital} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.HospitalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /hospitals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HospitalCriteria implements Serializable, Criteria {

    /**
     * Class for filtering HospitalCategory
     */
    public static class HospitalCategoryFilter extends Filter<HospitalCategory> {

        public HospitalCategoryFilter() {}

        public HospitalCategoryFilter(HospitalCategoryFilter filter) {
            super(filter);
        }

        @Override
        public HospitalCategoryFilter copy() {
            return new HospitalCategoryFilter(this);
        }
    }

    /**
     * Class for filtering HospitalSubCategory
     */
    public static class HospitalSubCategoryFilter extends Filter<HospitalSubCategory> {

        public HospitalSubCategoryFilter() {}

        public HospitalSubCategoryFilter(HospitalSubCategoryFilter filter) {
            super(filter);
        }

        @Override
        public HospitalSubCategoryFilter copy() {
            return new HospitalSubCategoryFilter(this);
        }
    }

    /**
     * Class for filtering StatusInd
     */
    public static class StatusIndFilter extends Filter<StatusInd> {

        public StatusIndFilter() {}

        public StatusIndFilter(StatusIndFilter filter) {
            super(filter);
        }

        @Override
        public StatusIndFilter copy() {
            return new StatusIndFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private HospitalCategoryFilter category;

    private HospitalSubCategoryFilter subCategory;

    private StringFilter contactNo;

    private StringFilter latitude;

    private StringFilter longitude;

    private IntegerFilter docCount;

    private StringFilter email;

    private StringFilter name;

    private StringFilter registrationNo;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter area;

    private StringFilter pinCode;

    private LongFilter hospitalId;

    private StringFilter odasFacilityId;

    private StringFilter referenceNumber;

    private StatusIndFilter statusInd;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter stateId;

    private LongFilter districtId;

    private LongFilter talukaId;

    private LongFilter cityId;

    private LongFilter municipalCorpId;

    private LongFilter hospitalTypeId;

    private LongFilter supplierId;

    private Boolean distinct;

    public HospitalCriteria() {}

    public HospitalCriteria(HospitalCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.subCategory = other.subCategory == null ? null : other.subCategory.copy();
        this.contactNo = other.contactNo == null ? null : other.contactNo.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.docCount = other.docCount == null ? null : other.docCount.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.registrationNo = other.registrationNo == null ? null : other.registrationNo.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.area = other.area == null ? null : other.area.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.hospitalId = other.hospitalId == null ? null : other.hospitalId.copy();
        this.odasFacilityId = other.odasFacilityId == null ? null : other.odasFacilityId.copy();
        this.referenceNumber = other.referenceNumber == null ? null : other.referenceNumber.copy();
        this.statusInd = other.statusInd == null ? null : other.statusInd.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.stateId = other.stateId == null ? null : other.stateId.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.talukaId = other.talukaId == null ? null : other.talukaId.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.municipalCorpId = other.municipalCorpId == null ? null : other.municipalCorpId.copy();
        this.hospitalTypeId = other.hospitalTypeId == null ? null : other.hospitalTypeId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public HospitalCriteria copy() {
        return new HospitalCriteria(this);
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

    public HospitalCategoryFilter getCategory() {
        return category;
    }

    public HospitalCategoryFilter category() {
        if (category == null) {
            category = new HospitalCategoryFilter();
        }
        return category;
    }

    public void setCategory(HospitalCategoryFilter category) {
        this.category = category;
    }

    public HospitalSubCategoryFilter getSubCategory() {
        return subCategory;
    }

    public HospitalSubCategoryFilter subCategory() {
        if (subCategory == null) {
            subCategory = new HospitalSubCategoryFilter();
        }
        return subCategory;
    }

    public void setSubCategory(HospitalSubCategoryFilter subCategory) {
        this.subCategory = subCategory;
    }

    public StringFilter getContactNo() {
        return contactNo;
    }

    public StringFilter contactNo() {
        if (contactNo == null) {
            contactNo = new StringFilter();
        }
        return contactNo;
    }

    public void setContactNo(StringFilter contactNo) {
        this.contactNo = contactNo;
    }

    public StringFilter getLatitude() {
        return latitude;
    }

    public StringFilter latitude() {
        if (latitude == null) {
            latitude = new StringFilter();
        }
        return latitude;
    }

    public void setLatitude(StringFilter latitude) {
        this.latitude = latitude;
    }

    public StringFilter getLongitude() {
        return longitude;
    }

    public StringFilter longitude() {
        if (longitude == null) {
            longitude = new StringFilter();
        }
        return longitude;
    }

    public void setLongitude(StringFilter longitude) {
        this.longitude = longitude;
    }

    public IntegerFilter getDocCount() {
        return docCount;
    }

    public IntegerFilter docCount() {
        if (docCount == null) {
            docCount = new IntegerFilter();
        }
        return docCount;
    }

    public void setDocCount(IntegerFilter docCount) {
        this.docCount = docCount;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getRegistrationNo() {
        return registrationNo;
    }

    public StringFilter registrationNo() {
        if (registrationNo == null) {
            registrationNo = new StringFilter();
        }
        return registrationNo;
    }

    public void setRegistrationNo(StringFilter registrationNo) {
        this.registrationNo = registrationNo;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public StringFilter address1() {
        if (address1 == null) {
            address1 = new StringFilter();
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public StringFilter address2() {
        if (address2 == null) {
            address2 = new StringFilter();
        }
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getArea() {
        return area;
    }

    public StringFilter area() {
        if (area == null) {
            area = new StringFilter();
        }
        return area;
    }

    public void setArea(StringFilter area) {
        this.area = area;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public StringFilter pinCode() {
        if (pinCode == null) {
            pinCode = new StringFilter();
        }
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
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

    public StringFilter getOdasFacilityId() {
        return odasFacilityId;
    }

    public StringFilter odasFacilityId() {
        if (odasFacilityId == null) {
            odasFacilityId = new StringFilter();
        }
        return odasFacilityId;
    }

    public void setOdasFacilityId(StringFilter odasFacilityId) {
        this.odasFacilityId = odasFacilityId;
    }

    public StringFilter getReferenceNumber() {
        return referenceNumber;
    }

    public StringFilter referenceNumber() {
        if (referenceNumber == null) {
            referenceNumber = new StringFilter();
        }
        return referenceNumber;
    }

    public void setReferenceNumber(StringFilter referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public StatusIndFilter getStatusInd() {
        return statusInd;
    }

    public StatusIndFilter statusInd() {
        if (statusInd == null) {
            statusInd = new StatusIndFilter();
        }
        return statusInd;
    }

    public void setStatusInd(StatusIndFilter statusInd) {
        this.statusInd = statusInd;
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

    public LongFilter getStateId() {
        return stateId;
    }

    public LongFilter stateId() {
        if (stateId == null) {
            stateId = new LongFilter();
        }
        return stateId;
    }

    public void setStateId(LongFilter stateId) {
        this.stateId = stateId;
    }

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
    }

    public LongFilter getTalukaId() {
        return talukaId;
    }

    public LongFilter talukaId() {
        if (talukaId == null) {
            talukaId = new LongFilter();
        }
        return talukaId;
    }

    public void setTalukaId(LongFilter talukaId) {
        this.talukaId = talukaId;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getMunicipalCorpId() {
        return municipalCorpId;
    }

    public LongFilter municipalCorpId() {
        if (municipalCorpId == null) {
            municipalCorpId = new LongFilter();
        }
        return municipalCorpId;
    }

    public void setMunicipalCorpId(LongFilter municipalCorpId) {
        this.municipalCorpId = municipalCorpId;
    }

    public LongFilter getHospitalTypeId() {
        return hospitalTypeId;
    }

    public LongFilter hospitalTypeId() {
        if (hospitalTypeId == null) {
            hospitalTypeId = new LongFilter();
        }
        return hospitalTypeId;
    }

    public void setHospitalTypeId(LongFilter hospitalTypeId) {
        this.hospitalTypeId = hospitalTypeId;
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
        final HospitalCriteria that = (HospitalCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(category, that.category) &&
            Objects.equals(subCategory, that.subCategory) &&
            Objects.equals(contactNo, that.contactNo) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(docCount, that.docCount) &&
            Objects.equals(email, that.email) &&
            Objects.equals(name, that.name) &&
            Objects.equals(registrationNo, that.registrationNo) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(area, that.area) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(hospitalId, that.hospitalId) &&
            Objects.equals(odasFacilityId, that.odasFacilityId) &&
            Objects.equals(referenceNumber, that.referenceNumber) &&
            Objects.equals(statusInd, that.statusInd) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(stateId, that.stateId) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(talukaId, that.talukaId) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(municipalCorpId, that.municipalCorpId) &&
            Objects.equals(hospitalTypeId, that.hospitalTypeId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            category,
            subCategory,
            contactNo,
            latitude,
            longitude,
            docCount,
            email,
            name,
            registrationNo,
            address1,
            address2,
            area,
            pinCode,
            hospitalId,
            odasFacilityId,
            referenceNumber,
            statusInd,
            lastModified,
            lastModifiedBy,
            stateId,
            districtId,
            talukaId,
            cityId,
            municipalCorpId,
            hospitalTypeId,
            supplierId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (subCategory != null ? "subCategory=" + subCategory + ", " : "") +
            (contactNo != null ? "contactNo=" + contactNo + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (docCount != null ? "docCount=" + docCount + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (registrationNo != null ? "registrationNo=" + registrationNo + ", " : "") +
            (address1 != null ? "address1=" + address1 + ", " : "") +
            (address2 != null ? "address2=" + address2 + ", " : "") +
            (area != null ? "area=" + area + ", " : "") +
            (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
            (hospitalId != null ? "hospitalId=" + hospitalId + ", " : "") +
            (odasFacilityId != null ? "odasFacilityId=" + odasFacilityId + ", " : "") +
            (referenceNumber != null ? "referenceNumber=" + referenceNumber + ", " : "") +
            (statusInd != null ? "statusInd=" + statusInd + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (stateId != null ? "stateId=" + stateId + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (talukaId != null ? "talukaId=" + talukaId + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (municipalCorpId != null ? "municipalCorpId=" + municipalCorpId + ", " : "") +
            (hospitalTypeId != null ? "hospitalTypeId=" + hospitalTypeId + ", " : "") +
            (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
