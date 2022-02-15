package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InventoryUsed.
 */
@Entity
@Table(name = "inventory_used")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InventoryUsed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stock")
    private Long stock;

    @Column(name = "capcity")
    private Long capcity;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventoryMaster", "supplier", "hospital" }, allowSetters = true)
    private Inventory inventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InventoryUsed id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return this.stock;
    }

    public InventoryUsed stock(Long stock) {
        this.setStock(stock);
        return this;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCapcity() {
        return this.capcity;
    }

    public InventoryUsed capcity(Long capcity) {
        this.setCapcity(capcity);
        return this;
    }

    public void setCapcity(Long capcity) {
        this.capcity = capcity;
    }

    public String getComment() {
        return this.comment;
    }

    public InventoryUsed comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public InventoryUsed lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public InventoryUsed lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public InventoryUsed inventory(Inventory inventory) {
        this.setInventory(inventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryUsed)) {
            return false;
        }
        return id != null && id.equals(((InventoryUsed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsed{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
