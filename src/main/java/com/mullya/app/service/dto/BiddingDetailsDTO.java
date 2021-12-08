package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.BiddingStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.BiddingDetails} entity.
 */
public class BiddingDetailsDTO implements Serializable {

    private Long id;

    private String startDate;

    private String endDate;

    private BiddingStatus biddingStatus;

    private Float minPrice;

    private Float maxPrice;

    private Float minQuantityKg;

    private Float maxQuantityKg;

    private Boolean isActive;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private StockDTO stock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public BiddingStatus getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(BiddingStatus biddingStatus) {
        this.biddingStatus = biddingStatus;
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

    public Float getMinQuantityKg() {
        return minQuantityKg;
    }

    public void setMinQuantityKg(Float minQuantityKg) {
        this.minQuantityKg = minQuantityKg;
    }

    public Float getMaxQuantityKg() {
        return maxQuantityKg;
    }

    public void setMaxQuantityKg(Float maxQuantityKg) {
        this.maxQuantityKg = maxQuantityKg;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public StockDTO getStock() {
        return stock;
    }

    public void setStock(StockDTO stock) {
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BiddingDetailsDTO)) {
            return false;
        }

        BiddingDetailsDTO biddingDetailsDTO = (BiddingDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, biddingDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BiddingDetailsDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", biddingStatus='" + getBiddingStatus() + "'" +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", minQuantityKg=" + getMinQuantityKg() +
            ", maxQuantityKg=" + getMaxQuantityKg() +
            ", isActive='" + getIsActive() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", stock=" + getStock() +
            "}";
    }
}
