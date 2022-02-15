package com.techvg.covid.care.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "stock_req", nullable = false)
    private Long stockReq;

    @Column(name = "stock_provided")
    private Long stockProvided;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    @NotNull
    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "state", "district", "taluka", "city", "inventoryType", "hospitals" }, allowSetters = true)
    private Supplier supplier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inventoryMaster", "supplier", "hospital" }, allowSetters = true)
    private Inventory inventory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transactions id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockReq() {
        return this.stockReq;
    }

    public Transactions stockReq(Long stockReq) {
        this.setStockReq(stockReq);
        return this;
    }

    public void setStockReq(Long stockReq) {
        this.stockReq = stockReq;
    }

    public Long getStockProvided() {
        return this.stockProvided;
    }

    public Transactions stockProvided(Long stockProvided) {
        this.setStockProvided(stockProvided);
        return this;
    }

    public void setStockProvided(Long stockProvided) {
        this.stockProvided = stockProvided;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }

    public Transactions status(TransactionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getComment() {
        return this.comment;
    }

    public Transactions comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Transactions lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Transactions lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Transactions supplier(Supplier supplier) {
        this.setSupplier(supplier);
        return this;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Transactions inventory(Inventory inventory) {
        this.setInventory(inventory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transactions)) {
            return false;
        }
        return id != null && id.equals(((Transactions) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transactions{" +
            "id=" + getId() +
            ", stockReq=" + getStockReq() +
            ", stockProvided=" + getStockProvided() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
