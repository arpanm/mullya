package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.RequirementStatus;
import com.mullya.app.domain.enumeration.StockCategory;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.Requirement} entity.
 */
public class RequirementDTO implements Serializable {

    private Long id;

    private StockCategory category;

    private String variant;

    private String subVariant;

    private Float minPrice;

    private Float maxPrice;

    private Float acceptedPrice;

    private Float codAmount;

    private Float quantityKg;

    private String neededBy;

    private String paymentDate;

    private String acceptedDeliveryDate;

    private String description;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private RequirementStatus status;

    private PaymentStatus paymentStatus;

    private DeliveryStatus deliveryStatus;

    private AddressDTO buyerAddress;

    private AddressDTO farmerAddress;

    private ActorDTO buyerActor;

    private ActorDTO acceptedAgentActor;

    private ActorDTO farmerActor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockCategory getCategory() {
        return category;
    }

    public void setCategory(StockCategory category) {
        this.category = category;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSubVariant() {
        return subVariant;
    }

    public void setSubVariant(String subVariant) {
        this.subVariant = subVariant;
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

    public Float getAcceptedPrice() {
        return acceptedPrice;
    }

    public void setAcceptedPrice(Float acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }

    public Float getCodAmount() {
        return codAmount;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAcceptedDeliveryDate() {
        return acceptedDeliveryDate;
    }

    public void setAcceptedDeliveryDate(String acceptedDeliveryDate) {
        this.acceptedDeliveryDate = acceptedDeliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RequirementStatus getStatus() {
        return status;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public AddressDTO getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(AddressDTO buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public AddressDTO getFarmerAddress() {
        return farmerAddress;
    }

    public void setFarmerAddress(AddressDTO farmerAddress) {
        this.farmerAddress = farmerAddress;
    }

    public ActorDTO getBuyerActor() {
        return buyerActor;
    }

    public void setBuyerActor(ActorDTO buyerActor) {
        this.buyerActor = buyerActor;
    }

    public ActorDTO getAcceptedAgentActor() {
        return acceptedAgentActor;
    }

    public void setAcceptedAgentActor(ActorDTO acceptedAgentActor) {
        this.acceptedAgentActor = acceptedAgentActor;
    }

    public ActorDTO getFarmerActor() {
        return farmerActor;
    }

    public void setFarmerActor(ActorDTO farmerActor) {
        this.farmerActor = farmerActor;
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
            ", category='" + getCategory() + "'" +
            ", variant='" + getVariant() + "'" +
            ", subVariant='" + getSubVariant() + "'" +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", acceptedPrice=" + getAcceptedPrice() +
            ", codAmount=" + getCodAmount() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", acceptedDeliveryDate='" + getAcceptedDeliveryDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", buyerAddress=" + getBuyerAddress() +
            ", farmerAddress=" + getFarmerAddress() +
            ", buyerActor=" + getBuyerActor() +
            ", acceptedAgentActor=" + getAcceptedAgentActor() +
            ", farmerActor=" + getFarmerActor() +
            "}";
    }
}
