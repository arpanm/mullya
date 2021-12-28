package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.StockStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Stock} entity.
 */
public class StockDTO implements Serializable {

    private Long id;

    private Float minPrice;

    private Float maxPrice;

    private Float quantityKg;

    private String expiry;

    private String avialableFrom;

    private String description;

    private StockStatus stockStatus;

    private boolean isOpenForBidding;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    private AddressDTO farmerAddress;

    private UserDTO farmer;

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

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getAvialableFrom() {
        return avialableFrom;
    }

    public void setAvialableFrom(String avialableFrom) {
        this.avialableFrom = avialableFrom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StockStatus getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(StockStatus stockStatus) {
        this.stockStatus = stockStatus;
    }

    public Boolean getIsOpenForBidding() {
        return isOpenForBidding;
    }

    public void setIsOpenForBidding(Boolean isOpenForBidding) {
        this.isOpenForBidding = isOpenForBidding;
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

    public AddressDTO getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(AddressDTO farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public UserDTO getFarmer() {
        return farmer;
    }

    public void setFarmer(UserDTO farmer) {
        this.farmer = farmer;
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
        if (!(o instanceof StockDTO)) {
            return false;
        }

        StockDTO stockDTO = (StockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, stockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StockDTO{" +
            "id=" + getId() +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", expiry='" + getExpiry() + "'" +
            ", avialableFrom='" + getAvialableFrom() + "'" +
            ", description='" + getDescription() + "'" +
            ", stockStatus='" + getStockStatus() + "'" +
            ", isOpenForBidding='" + getIsOpenForBidding() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", farmerAddress=" + getFarmerAddress() +
            ", farmer=" + getFarmer() +
            ", category=" + getCategory() +
            ", variant=" + getVariant() +
            ", subVariant=" + getSubVariant() +
            "}";
    }
}
