package com.techvg.covid.care.service.dto;

import com.techvg.covid.care.domain.enumeration.TransactionStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.Transactions} entity.
 */
public class TransactionsDTO implements Serializable {

    private Long id;

    @NotNull
    private Long stockReq;

    private Long stockProvided;

    @NotNull
    private TransactionStatus status;

    private String comment;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private SupplierDTO supplier;

    private InventoryDTO inventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStockReq() {
        return stockReq;
    }

    public void setStockReq(Long stockReq) {
        this.stockReq = stockReq;
    }

    public Long getStockProvided() {
        return stockProvided;
    }

    public void setStockProvided(Long stockProvided) {
        this.stockProvided = stockProvided;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public InventoryDTO getInventory() {
        return inventory;
    }

    public void setInventory(InventoryDTO inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionsDTO)) {
            return false;
        }

        TransactionsDTO transactionsDTO = (TransactionsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionsDTO{" +
            "id=" + getId() +
            ", stockReq=" + getStockReq() +
            ", stockProvided=" + getStockProvided() +
            ", status='" + getStatus() + "'" +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", supplier=" + getSupplier() +
            ", inventory=" + getInventory() +
            "}";
    }
}
