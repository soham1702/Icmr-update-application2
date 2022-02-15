package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.District} entity.
 */
public class DistrictDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean deleted;

    private Long lgdCode;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private StateDTO state;

    private DivisionDTO division;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getLgdCode() {
        return lgdCode;
    }

    public void setLgdCode(Long lgdCode) {
        this.lgdCode = lgdCode;
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

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public DivisionDTO getDivision() {
        return division;
    }

    public void setDivision(DivisionDTO division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DistrictDTO)) {
            return false;
        }

        DistrictDTO districtDTO = (DistrictDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, districtDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistrictDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", lgdCode=" + getLgdCode() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", state=" + getState() +
            ", division=" + getDivision() +
            "}";
    }
}
