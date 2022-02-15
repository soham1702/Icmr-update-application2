package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.ICMRDailyCount} entity.
 */
public class ICMRDailyCountDTO implements Serializable {

    private Long id;

    private Long totalSamplesTested;

    private Long totalNegative;

    private Long totalPositive;

    private Long currentPreviousMonthTest;

    private Long districtId;

    private String remarks;

    private Instant editedOn;

    private Instant updatedDate;

    private String freeField1;

    private String freeField2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSamplesTested() {
        return totalSamplesTested;
    }

    public void setTotalSamplesTested(Long totalSamplesTested) {
        this.totalSamplesTested = totalSamplesTested;
    }

    public Long getTotalNegative() {
        return totalNegative;
    }

    public void setTotalNegative(Long totalNegative) {
        this.totalNegative = totalNegative;
    }

    public Long getTotalPositive() {
        return totalPositive;
    }

    public void setTotalPositive(Long totalPositive) {
        this.totalPositive = totalPositive;
    }

    public Long getCurrentPreviousMonthTest() {
        return currentPreviousMonthTest;
    }

    public void setCurrentPreviousMonthTest(Long currentPreviousMonthTest) {
        this.currentPreviousMonthTest = currentPreviousMonthTest;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(Instant editedOn) {
        this.editedOn = editedOn;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ICMRDailyCountDTO)) {
            return false;
        }

        ICMRDailyCountDTO iCMRDailyCountDTO = (ICMRDailyCountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, iCMRDailyCountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ICMRDailyCountDTO{" +
            "id=" + getId() +
            ", totalSamplesTested=" + getTotalSamplesTested() +
            ", totalNegative=" + getTotalNegative() +
            ", totalPositive=" + getTotalPositive() +
            ", currentPreviousMonthTest=" + getCurrentPreviousMonthTest() +
            ", districtId=" + getDistrictId() +
            ", remarks='" + getRemarks() + "'" +
            ", editedOn='" + getEditedOn() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            "}";
    }
}
