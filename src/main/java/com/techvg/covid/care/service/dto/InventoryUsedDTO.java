package com.techvg.covid.care.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.covid.care.domain.InventoryUsed} entity.
 */
public class InventoryUsedDTO implements Serializable {

    private Long id;

    private Long stock;

    private Long capcity;

    private String comment;

    @NotNull
    private Instant lastModified;

    @NotNull
    private String lastModifiedBy;

    private InventoryDTO inventory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getCapcity() {
        return capcity;
    }

    public void setCapcity(Long capcity) {
        this.capcity = capcity;
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
        if (!(o instanceof InventoryUsedDTO)) {
            return false;
        }

        InventoryUsedDTO inventoryUsedDTO = (InventoryUsedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventoryUsedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryUsedDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", capcity=" + getCapcity() +
            ", comment='" + getComment() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", inventory=" + getInventory() +
            "}";
    }
}
