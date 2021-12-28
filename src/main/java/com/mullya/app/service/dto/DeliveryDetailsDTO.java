package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.DeliveryType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * A DTO for the {@link com.mullya.app.domain.DeliveryDetails} entity.
 */
public class DeliveryDetailsDTO implements Serializable {

    private Long id;

    private DeliveryType deliveryType;

    private String pickupDate;

    private String deliveryDate;

    private String truckDetails;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    private Long deliveryAgentPhone;

    private LocalDate pickupTime;

    private LocalDate deliveryTime;

    private Instant createdOn;

    private String createdBy;

    private Instant updatedOn;

    private String updatedBy;

    private DeliveryStatus deliveryStatus;

    private AddressDTO fromAddress;

    private AddressDTO toAddress;

    private OrderDTO order;

    private CancellationDetailsDTO cancellation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTruckDetails() {
        return truckDetails;
    }

    public void setTruckDetails(String truckDetails) {
        this.truckDetails = truckDetails;
    }

    public Long getDeliveryAgentPhone() {
        return deliveryAgentPhone;
    }

    public void setDeliveryAgentPhone(Long deliveryAgentPhone) {
        this.deliveryAgentPhone = deliveryAgentPhone;
    }

    public LocalDate getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalDate pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
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

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public AddressDTO getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(AddressDTO fromAddress) {
        this.fromAddress = fromAddress;
    }

    public AddressDTO getToAddress() {
        return toAddress;
    }

    public void setToAddress(AddressDTO toAddress) {
        this.toAddress = toAddress;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public CancellationDetailsDTO getCancellation() {
        return cancellation;
    }

    public void setCancellation(CancellationDetailsDTO cancellation) {
        this.cancellation = cancellation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryDetailsDTO)) {
            return false;
        }

        DeliveryDetailsDTO deliveryDetailsDTO = (DeliveryDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, deliveryDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryDetailsDTO{" +
            "id=" + getId() +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", pickupDate='" + getPickupDate() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", truckDetails='" + getTruckDetails() + "'" +
            ", deliveryAgentPhone=" + getDeliveryAgentPhone() +
            ", pickupTime='" + getPickupTime() + "'" +
            ", deliveryTime='" + getDeliveryTime() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            ", fromAddress=" + getFromAddress() +
            ", toAddress=" + getToAddress() +
            ", order=" + getOrder() +
            ", cancellation=" + getCancellation() +
            "}";
    }
}
