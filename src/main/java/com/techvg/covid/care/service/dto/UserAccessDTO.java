package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.AccessLevel;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.UserAccess} entity.
 */
public class UserAccessDTO implements Serializable {

    private Long id;

    private AccessLevel level;

    private Long accessId;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
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

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccessDTO)) {
            return false;
        }

        UserAccessDTO userAccessDTO = (UserAccessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAccessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccessDTO{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", accessId=" + getAccessId() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
