package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.RequirementStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Requirement} entity.
 */
public class RequirementDTO implements Serializable {

    private Long id;

    private Float minPrice;

    private Float maxPrice;

    private Float quantityKg;

    private String neededBy;

    private String description;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    private RequirementStatus status;

    private AddressDTO buyerAddress;

    private UserDTO buyerUser;

    private CatalogueDTO category;

    private CatalogueDTO variant;

    private CatalogueDTO subVariant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Float getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public String getNeededBy() {
        return neededBy;
    }

    public void setNeededBy(String neededBy) {
        this.neededBy = neededBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RequirementStatus getStatus() {
        return status;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
    }

    public AddressDTO getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(AddressDTO buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public UserDTO getBuyerUser() {
        return buyerUser;
    }

    public void setBuyerUser(UserDTO buyerUser) {
        this.buyerUser = buyerUser;
    }

    public CatalogueDTO getCategory() {
        return category;
    }

    public void setCategory(CatalogueDTO category) {
        this.category = category;
    }

    public CatalogueDTO getVariant() {
        return variant;
    }

    public void setVariant(CatalogueDTO variant) {
        this.variant = variant;
    }

    public CatalogueDTO getSubVariant() {
        return subVariant;
    }

    public void setSubVariant(CatalogueDTO subVariant) {
        this.subVariant = subVariant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequirementDTO)) {
            return false;
        }

        RequirementDTO requirementDTO = (RequirementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requirementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequirementDTO{" +
            "id=" + getId() +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", buyerAddress=" + getBuyerAddress() +
            ", buyerUser=" + getBuyerUser() +
            ", category=" + getCategory() +
            ", variant=" + getVariant() +
            ", subVariant=" + getSubVariant() +
            "}";
    }
}
