package com.techvg.covid.care.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ICMRDailyCount.
 */
@Entity
@Table(name = "icmr_daily_count")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ICMRDailyCount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "total_samples_tested")
    private Long totalSamplesTested;

    @Column(name = "total_negative")
    private Long totalNegative;

    @Column(name = "total_positive")
    private Long totalPositive;

    @Column(name = "current_previous_month_test")
    private Long currentPreviousMonthTest;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "edited_on")
    private Instant editedOn;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ICMRDailyCount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSamplesTested() {
        return this.totalSamplesTested;
    }

    public ICMRDailyCount totalSamplesTested(Long totalSamplesTested) {
        this.setTotalSamplesTested(totalSamplesTested);
        return this;
    }

    public void setTotalSamplesTested(Long totalSamplesTested) {
        this.totalSamplesTested = totalSamplesTested;
    }

    public Long getTotalNegative() {
        return this.totalNegative;
    }

    public ICMRDailyCount totalNegative(Long totalNegative) {
        this.setTotalNegative(totalNegative);
        return this;
    }

    public void setTotalNegative(Long totalNegative) {
        this.totalNegative = totalNegative;
    }

    public Long getTotalPositive() {
        return this.totalPositive;
    }

    public ICMRDailyCount totalPositive(Long totalPositive) {
        this.setTotalPositive(totalPositive);
        return this;
    }

    public void setTotalPositive(Long totalPositive) {
        this.totalPositive = totalPositive;
    }

    public Long getCurrentPreviousMonthTest() {
        return this.currentPreviousMonthTest;
    }

    public ICMRDailyCount currentPreviousMonthTest(Long currentPreviousMonthTest) {
        this.setCurrentPreviousMonthTest(currentPreviousMonthTest);
        return this;
    }

    public void setCurrentPreviousMonthTest(Long currentPreviousMonthTest) {
        this.currentPreviousMonthTest = currentPreviousMonthTest;
    }

    public Long getDistrictId() {
        return this.districtId;
    }

    public ICMRDailyCount districtId(Long districtId) {
        this.setDistrictId(districtId);
        return this;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public ICMRDailyCount remarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getEditedOn() {
        return this.editedOn;
    }

    public ICMRDailyCount editedOn(Instant editedOn) {
        this.setEditedOn(editedOn);
        return this;
    }

    public void setEditedOn(Instant editedOn) {
        this.editedOn = editedOn;
    }

    public Instant getUpdatedDate() {
        return this.updatedDate;
    }

    public ICMRDailyCount updatedDate(Instant updatedDate) {
        this.setUpdatedDate(updatedDate);
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public ICMRDailyCount freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public ICMRDailyCount freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ICMRDailyCount)) {
            return false;
        }
        return id != null && id.equals(((ICMRDailyCount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ICMRDailyCount{" +
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
