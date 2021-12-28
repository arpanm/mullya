package com.mullya.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Catalogue} entity.
 */
public class CatalogueDTO implements Serializable {

    private Long id;

    private String name;

    private String stockImageUrl;

    private String landingUrl;

    private String description;

    private boolean isActive;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    private CatalogueDTO parent;

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

    public String getStockImageUrl() {
        return stockImageUrl;
    }

    public void setStockImageUrl(String stockImageUrl) {
        this.stockImageUrl = stockImageUrl;
    }

    public String getLandingUrl() {
        return landingUrl;
    }

    public void setLandingUrl(String landingUrl) {
        this.landingUrl = landingUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public CatalogueDTO getParent() {
        return parent;
    }

    public void setParent(CatalogueDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CatalogueDTO)) {
            return false;
        }

        CatalogueDTO catalogueDTO = (CatalogueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, catalogueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CatalogueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", stockImageUrl='" + getStockImageUrl() + "'" +
            ", landingUrl='" + getLandingUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
