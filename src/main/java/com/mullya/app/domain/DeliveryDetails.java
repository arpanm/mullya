package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.DeliveryType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DeliveryDetails.
 */
@Entity
@Table(name = "delivery_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DeliveryDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_type")
    private DeliveryType deliveryType;

    @Column(name = "pickup_date")
    private String pickupDate;

    @Column(name = "delivery_date")
    private String deliveryDate;

    @Column(name = "truck_details")
    private String truckDetails;

    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "delivery_agent_phone")
    private Long deliveryAgentPhone;

    @Column(name = "pickup_time")
    private LocalDate pickupTime;

    @Column(name = "delivery_time")
    private LocalDate deliveryTime;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hub", "user" }, allowSetters = true)
    private Address fromAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = { "hub", "user" }, allowSetters = true)
    private Address toAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    private Order order;

    @ManyToOne
    @JsonIgnoreProperties(value = { "order", "approver", "initiator", "deliveryDetails" }, allowSetters = true)
    private CancellationDetails cancellation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DeliveryDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeliveryType getDeliveryType() {
        return this.deliveryType;
    }

    public DeliveryDetails deliveryType(DeliveryType deliveryType) {
        this.setDeliveryType(deliveryType);
        return this;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPickupDate() {
        return this.pickupDate;
    }

    public DeliveryDetails pickupDate(String pickupDate) {
        this.setPickupDate(pickupDate);
        return this;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDeliveryDate() {
        return this.deliveryDate;
    }

    public DeliveryDetails deliveryDate(String deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTruckDetails() {
        return this.truckDetails;
    }

    public DeliveryDetails truckDetails(String truckDetails) {
        this.setTruckDetails(truckDetails);
        return this;
    }

    public void setTruckDetails(String truckDetails) {
        this.truckDetails = truckDetails;
    }

    public Long getDeliveryAgentPhone() {
        return this.deliveryAgentPhone;
    }

    public DeliveryDetails deliveryAgentPhone(Long deliveryAgentPhone) {
        this.setDeliveryAgentPhone(deliveryAgentPhone);
        return this;
    }

    public void setDeliveryAgentPhone(Long deliveryAgentPhone) {
        this.deliveryAgentPhone = deliveryAgentPhone;
    }

    public LocalDate getPickupTime() {
        return this.pickupTime;
    }

    public DeliveryDetails pickupTime(LocalDate pickupTime) {
        this.setPickupTime(pickupTime);
        return this;
    }

    public void setPickupTime(LocalDate pickupTime) {
        this.pickupTime = pickupTime;
    }

    public LocalDate getDeliveryTime() {
        return this.deliveryTime;
    }

    public DeliveryDetails deliveryTime(LocalDate deliveryTime) {
        this.setDeliveryTime(deliveryTime);
        return this;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public DeliveryDetails createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DeliveryDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public DeliveryDetails updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DeliveryDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public DeliveryStatus getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public DeliveryDetails deliveryStatus(DeliveryStatus deliveryStatus) {
        this.setDeliveryStatus(deliveryStatus);
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Address getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(Address address) {
        this.fromAddress = address;
    }

    public DeliveryDetails fromAddress(Address address) {
        this.setFromAddress(address);
        return this;
    }

    public Address getToAddress() {
        return this.toAddress;
    }

    public void setToAddress(Address address) {
        this.toAddress = address;
    }

    public DeliveryDetails toAddress(Address address) {
        this.setToAddress(address);
        return this;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryDetails order(Order order) {
        this.setOrder(order);
        return this;
    }

    public CancellationDetails getCancellation() {
        return this.cancellation;
    }

    public void setCancellation(CancellationDetails cancellationDetails) {
        this.cancellation = cancellationDetails;
    }

    public DeliveryDetails cancellation(CancellationDetails cancellationDetails) {
        this.setCancellation(cancellationDetails);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeliveryDetails)) {
            return false;
        }
        return id != null && id.equals(((DeliveryDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeliveryDetails{" +
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
            "}";
    }
}
