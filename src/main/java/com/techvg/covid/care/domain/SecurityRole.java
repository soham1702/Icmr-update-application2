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
 * A SecurityRole.
 */
@Entity
@Table(name = "security_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecurityRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToMany
    @JoinTable(
        name = "rel_security_role__security_permission",
        joinColumns = @JoinColumn(name = "security_role_id"),
        inverseJoinColumns = @JoinColumn(name = "security_permission_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityRoles", "securityUsers" }, allowSetters = true)
    private Set<SecurityPermission> securityPermissions = new HashSet<>();

    @ManyToMany(mappedBy = "securityRoles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityPermissions", "securityRoles" }, allowSetters = true)
    private Set<SecurityUser> securityUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecurityRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SecurityRole name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public SecurityRole description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SecurityRole lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SecurityRole lastModifiedBy(String lastModifiedBy) {
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

    public SecurityRole securityPermissions(Set<SecurityPermission> securityPermissions) {
        this.setSecurityPermissions(securityPermissions);
        return this;
    }

    public SecurityRole addSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.add(securityPermission);
        securityPermission.getSecurityRoles().add(this);
        return this;
    }

    public SecurityRole removeSecurityPermission(SecurityPermission securityPermission) {
        this.securityPermissions.remove(securityPermission);
        securityPermission.getSecurityRoles().remove(this);
        return this;
    }

    public Set<SecurityUser> getSecurityUsers() {
        return this.securityUsers;
    }

    public void setSecurityUsers(Set<SecurityUser> securityUsers) {
        if (this.securityUsers != null) {
            this.securityUsers.forEach(i -> i.removeSecurityRole(this));
        }
        if (securityUsers != null) {
            securityUsers.forEach(i -> i.addSecurityRole(this));
        }
        this.securityUsers = securityUsers;
    }

    public SecurityRole securityUsers(Set<SecurityUser> securityUsers) {
        this.setSecurityUsers(securityUsers);
        return this;
    }

    public SecurityRole addSecurityUser(SecurityUser securityUser) {
        this.securityUsers.add(securityUser);
        securityUser.getSecurityRoles().add(this);
        return this;
    }

    public SecurityRole removeSecurityUser(SecurityUser securityUser) {
        this.securityUsers.remove(securityUser);
        securityUser.getSecurityRoles().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityRole)) {
            return false;
        }
        return id != null && id.equals(((SecurityRole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecurityRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
