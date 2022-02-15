package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.HospitalType} entity.
 */
public class HospitalTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String desciption;

    private Boolean deleted;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

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

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HospitalTypeDTO)) {
            return false;
        }

        HospitalTypeDTO hospitalTypeDTO = (HospitalTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hospitalTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HospitalTypeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desciption='" + getDesciption() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
