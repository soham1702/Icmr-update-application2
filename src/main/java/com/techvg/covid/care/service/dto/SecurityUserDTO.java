package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.SecurityUser} entity.
 */
public class SecurityUserDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String designation;

    @NotNull
    private String login;

    @NotNull
    private String passwordHash;

    private String email;

    private String imageUrl;

    @NotNull
    private Boolean activated;

    private String langKey;

    private String activationKey;

    private String resetKey;

    private Instant resetDate;

    private String mobileNo;

    private String oneTimePassword;

    private Instant otpExpiryTime;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private Set<SecurityPermissionDTO> securityPermissions = new HashSet<>();

    private Set<SecurityRoleDTO> securityRoles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public Instant getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(Instant otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
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

    public Set<SecurityPermissionDTO> getSecurityPermissions() {
        return securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermissionDTO> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public Set<SecurityRoleDTO> getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(Set<SecurityRoleDTO> securityRoles) {
        this.securityRoles = securityRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityUserDTO)) {
            return false;
        }

        SecurityUserDTO securityUserDTO = (SecurityUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, securityUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityUserDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", login='" + getLogin() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", email='" + getEmail() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", activated='" + getActivated() + "'" +
            ", langKey='" + getLangKey() + "'" +
            ", activationKey='" + getActivationKey() + "'" +
            ", resetKey='" + getResetKey() + "'" +
            ", resetDate='" + getResetDate() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", oneTimePassword='" + getOneTimePassword() + "'" +
            ", otpExpiryTime='" + getOtpExpiryTime() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", securityPermissions=" + getSecurityPermissions() +
            ", securityRoles=" + getSecurityRoles() +
            "}";
    }
}
