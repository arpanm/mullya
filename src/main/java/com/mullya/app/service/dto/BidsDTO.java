package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.BidStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Bids} entity.
 */
public class BidsDTO implements Serializable {

    private Long id;

    private Float bidPrice;

    private Float quantityKg;

    private BidStatus bidStatus;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private AddressDTO buyerAddress;

    private BiddingDetailsDTO biddingDetails;

    private ActorDTO buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(Float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Float getQuantityKg() {
        return quantityKg;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public BidStatus getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(BidStatus bidStatus) {
        this.bidStatus = bidStatus;
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

    public AddressDTO getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(AddressDTO buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public BiddingDetailsDTO getBiddingDetails() {
        return biddingDetails;
    }

    public void setBiddingDetails(BiddingDetailsDTO biddingDetails) {
        this.biddingDetails = biddingDetails;
    }

    public ActorDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(ActorDTO buyer) {
        this.buyer = buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BidsDTO)) {
            return false;
        }

        BidsDTO bidsDTO = (BidsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bidsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BidsDTO{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", bidStatus='" + getBidStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", buyerAddress=" + getBuyerAddress() +
            ", biddingDetails=" + getBiddingDetails() +
            ", buyer=" + getBuyer() +
            "}";
    }
}
