package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SecurityUser.
 */
@Entity
@Table(name = "security_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "designation")
    private String designation;

    @NotNull
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @NotNull
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @Column(name = "lang_key")
    private String langKey;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "reset_key")
    private String resetKey;

    @Column(name = "reset_date")
    private Instant resetDate;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_expiry_time")
    private Instant otpExpiryTime;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToMany
    @JoinTable(
        name = "rel_security_user__security_permission",
        joinColumns = @JoinColumn(name = "security_user_id"),
        inverseJoinColumns = @JoinColumn(name = "security_permission_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityRoles", "securityUsers" }, allowSetters = true)
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_security_user__security_role",
        joinColumns = @JoinColumn(name = "security_user_id"),
        inverseJoinColumns = @JoinColumn(name = "security_role_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityPermissions", "securityUsers" }, allowSetters = true)
    private Set<SecurityRole> securityRoles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public SecurityUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public SecurityUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return this.designation;
    }

    public SecurityUser designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLogin() {
        return this.login;
    }

    public SecurityUser login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public SecurityUser passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return this.email;
    }

    public SecurityUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public SecurityUser imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActivated() {
        return this.activated;
    }

    public SecurityUser activated(Boolean activated) {
        this.setActivated(activated);
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return this.langKey;
    }

    public SecurityUser langKey(String langKey) {
        this.setLangKey(langKey);
        return this;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return this.activationKey;
    }

    public SecurityUser activationKey(String activationKey) {
        this.setActivationKey(activationKey);
        return this;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return this.resetKey;
    }

    public SecurityUser resetKey(String resetKey) {
        this.setResetKey(resetKey);
        return this;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return this.resetDate;
    }

    public SecurityUser resetDate(Instant resetDate) {
        this.setResetDate(resetDate);
        return this;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public SecurityUser mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getOneTimePassword() {
        return this.oneTimePassword;
    }

    public SecurityUser oneTimePassword(String oneTimePassword) {
        this.setOneTimePassword(oneTimePassword);
        return this;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public Instant getOtpExpiryTime() {
        return this.otpExpiryTime;
    }

    public SecurityUser otpExpiryTime(Instant otpExpiryTime) {
        this.setOtpExpiryTime(otpExpiryTime);
        return this;
    }

    public void setOtpExpiryTime(Instant otpExpiryTime) {
        this.otpExpiryTime = otpExpiryTime;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SecurityUser lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SecurityUser lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<SecurityPermission> getSecurityPermissions() {
        return this.securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermission> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public SecurityUser securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.setSecurityPermissions(securityPermissions);
        return this;
    }

    public SecurityUser addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.getSecurityUsers().remove(this);
        return this;
    }

    public Set<SecurityRole> getSecurityRoles() {
        return this.securityRoles;
    }

    public void setSecurityRoles(Set<SecurityRole> securityRoles) {
        this.securityRoles = securityRoles;
    }

    public SecurityUser securityRoles(Set<SecurityRole> securityRoles) {
        this.setSecurityRoles(securityRoles);
        return this;
    }

    public SecurityUser addSecurityRole(SecurityRole securityRole) {
        this.securityRoles.add(securityRole);
        securityRole.getSecurityUsers().add(this);
        return this;
    }

    public SecurityUser removeSecurityRole(SecurityRole securityRole) {
        this.securityRoles.remove(securityRole);
        securityRole.getSecurityUsers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityUser)) {
            return false;
        }
        return id != null && id.equals(((SecurityUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityUser{" +
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
            "}";
    }
}
