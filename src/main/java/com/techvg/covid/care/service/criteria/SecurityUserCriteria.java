package com.techvg.covid.care.service.criteria;

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
 * Criteria class for the {@link com.techvg.covid.care.domain.SecurityUser} entity. This class is used
 * in {@link com.techvg.covid.care.web.rest.SecurityUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /security-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SecurityUserCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter firstName;

    private StringFilter lastName;

    private StringFilter designation;

    private StringFilter login;

    private StringFilter passwordHash;

    private StringFilter email;

    private StringFilter imageUrl;

    private BooleanFilter activated;

    private StringFilter langKey;

    private StringFilter activationKey;

    private StringFilter resetKey;

    private InstantFilter resetDate;

    private StringFilter mobileNo;

    private StringFilter oneTimePassword;

    private InstantFilter otpExpiryTime;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityPermissionId;

    private LongFilter securityRoleId;

    private Boolean distinct;

    public SecurityUserCriteria() {}

    public SecurityUserCriteria(SecurityUserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.passwordHash = other.passwordHash == null ? null : other.passwordHash.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy();
        this.activated = other.activated == null ? null : other.activated.copy();
        this.langKey = other.langKey == null ? null : other.langKey.copy();
        this.activationKey = other.activationKey == null ? null : other.activationKey.copy();
        this.resetKey = other.resetKey == null ? null : other.resetKey.copy();
        this.resetDate = other.resetDate == null ? null : other.resetDate.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.oneTimePassword = other.oneTimePassword == null ? null : other.oneTimePassword.copy();
        this.otpExpiryTime = other.otpExpiryTime == null ? null : other.otpExpiryTime.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityPermissionId = other.securityPermissionId == null ? null : other.securityPermissionId.copy();
        this.securityRoleId = other.securityRoleId == null ? null : other.securityRoleId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SecurityUserCriteria copy() {
        return new SecurityUserCriteria(this);
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

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public StringFilter getLogin() {
        return login;
    }

    public StringFilter login() {
        if (login == null) {
            login = new StringFilter();
        }
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public StringFilter passwordHash() {
        if (passwordHash == null) {
            passwordHash = new StringFilter();
        }
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
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

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public StringFilter imageUrl() {
        if (imageUrl == null) {
            imageUrl = new StringFilter();
        }
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public BooleanFilter activated() {
        if (activated == null) {
            activated = new BooleanFilter();
        }
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLangKey() {
        return langKey;
    }

    public StringFilter langKey() {
        if (langKey == null) {
            langKey = new StringFilter();
        }
        return langKey;
    }

    public void setLangKey(StringFilter langKey) {
        this.langKey = langKey;
    }

    public StringFilter getActivationKey() {
        return activationKey;
    }

    public StringFilter activationKey() {
        if (activationKey == null) {
            activationKey = new StringFilter();
        }
        return activationKey;
    }

    public void setActivationKey(StringFilter activationKey) {
        this.activationKey = activationKey;
    }

    public StringFilter getResetKey() {
        return resetKey;
    }

    public StringFilter resetKey() {
        if (resetKey == null) {
            resetKey = new StringFilter();
        }
        return resetKey;
    }

    public void setResetKey(StringFilter resetKey) {
        this.resetKey = resetKey;
    }

    public InstantFilter getResetDate() {
        return resetDate;
    }

    public InstantFilter resetDate() {
        if (resetDate == null) {
            resetDate = new InstantFilter();
        }
        return resetDate;
    }

    public void setResetDate(InstantFilter resetDate) {
        this.resetDate = resetDate;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getOneTimePassword() {
        return oneTimePassword;
    }

    public StringFilter oneTimePassword() {
        if (oneTimePassword == null) {
            oneTimePassword = new StringFilter();
        }
        return oneTimePassword;
    }

    public void setOneTimePassword(StringFilter oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public InstantFilter getOtpExpiryTime() {
        return otpExpiryTime;
    }

    public InstantFilter otpExpiryTime() {
        if (otpExpiryTime == null) {
            otpExpiryTime = new InstantFilter();
        }
        return otpExpiryTime;
    }

    public void setOtpExpiryTime(InstantFilter otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
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

    public LongFilter getSecurityPermissionId() {
        return securityPermissionId;
    }

    public LongFilter securityPermissionId() {
        if (securityPermissionId == null) {
            securityPermissionId = new LongFilter();
        }
        return securityPermissionId;
    }

    public void setSecurityPermissionId(LongFilter securityPermissionId) {
        this.securityPermissionId = securityPermissionId;
    }

    public LongFilter getSecurityRoleId() {
        return securityRoleId;
    }

    public LongFilter securityRoleId() {
        if (securityRoleId == null) {
            securityRoleId = new LongFilter();
        }
        return securityRoleId;
    }

    public void setSecurityRoleId(LongFilter securityRoleId) {
        this.securityRoleId = securityRoleId;
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
        final SecurityUserCriteria that = (SecurityUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(login, that.login) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(activationKey, that.activationKey) &&
            Objects.equals(resetKey, that.resetKey) &&
            Objects.equals(resetDate, that.resetDate) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(oneTimePassword, that.oneTimePassword) &&
            Objects.equals(otpExpiryTime, that.otpExpiryTime) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityPermissionId, that.securityPermissionId) &&
            Objects.equals(securityRoleId, that.securityRoleId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstName,
            lastName,
            designation,
            login,
            passwordHash,
            email,
            imageUrl,
            activated,
            langKey,
            activationKey,
            resetKey,
            resetDate,
            mobileNo,
            oneTimePassword,
            otpExpiryTime,
            lastModified,
            lastModifiedBy,
            securityPermissionId,
            securityRoleId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityUserCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (login != null ? "login=" + login + ", " : "") +
            (passwordHash != null ? "passwordHash=" + passwordHash + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
            (activated != null ? "activated=" + activated + ", " : "") +
            (langKey != null ? "langKey=" + langKey + ", " : "") +
            (activationKey != null ? "activationKey=" + activationKey + ", " : "") +
            (resetKey != null ? "resetKey=" + resetKey + ", " : "") +
            (resetDate != null ? "resetDate=" + resetDate + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (oneTimePassword != null ? "oneTimePassword=" + oneTimePassword + ", " : "") +
            (otpExpiryTime != null ? "otpExpiryTime=" + otpExpiryTime + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityPermissionId != null ? "securityPermissionId=" + securityPermissionId + ", " : "") +
            (securityRoleId != null ? "securityRoleId=" + securityRoleId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
