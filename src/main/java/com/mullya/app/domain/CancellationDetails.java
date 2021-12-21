package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.CancelationType;
import com.mullya.app.domain.enumeration.CancellationStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CancellationDetails.
 */
@Entity
@Table(name = "cancellation_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CancellationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancelation_type")
    private CancelationType cancelationType;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "cancellation_date")
    private String cancellationDate;

    @Column(name = "cancellation_time")
    private LocalDate cancellationTime;

    @Column(name = "refund_id")
    private String refundId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cancellation_status")
    private CancellationStatus cancellationStatus;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Order order;

    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private User approver;

    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    @OneToOne
    @JoinColumn(unique = true)
    private User initiator;

    @OneToMany(mappedBy = "cancellation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fromAddress", "toAddress", "order", "cancellation" }, allowSetters = true)
    private Set<DeliveryDetails> deliveryDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CancellationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CancelationType getCancelationType() {
        return this.cancelationType;
    }

    public CancellationDetails cancelationType(CancelationType cancelationType) {
        this.setCancelationType(cancelationType);
        return this;
    }

    public void setCancelationType(CancelationType cancelationType) {
        this.cancelationType = cancelationType;
    }

    public String getCancellationReason() {
        return this.cancellationReason;
    }

    public CancellationDetails cancellationReason(String cancellationReason) {
        this.setCancellationReason(cancellationReason);
        return this;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCancellationDate() {
        return this.cancellationDate;
    }

    public CancellationDetails cancellationDate(String cancellationDate) {
        this.setCancellationDate(cancellationDate);
        return this;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public LocalDate getCancellationTime() {
        return this.cancellationTime;
    }

    public CancellationDetails cancellationTime(LocalDate cancellationTime) {
        this.setCancellationTime(cancellationTime);
        return this;
    }

    public void setCancellationTime(LocalDate cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public String getRefundId() {
        return this.refundId;
    }

    public CancellationDetails refundId(String refundId) {
        this.setRefundId(refundId);
        return this;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public CancellationStatus getCancellationStatus() {
        return this.cancellationStatus;
    }

    public CancellationDetails cancellationStatus(CancellationStatus cancellationStatus) {
        this.setCancellationStatus(cancellationStatus);
        return this;
    }

    public void setCancellationStatus(CancellationStatus cancellationStatus) {
        this.cancellationStatus = cancellationStatus;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public CancellationDetails createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public CancellationDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public CancellationDetails updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public CancellationDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Order getOrder() {
        return this.order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public CancellationDetails order(Order order) {
        this.setOrder(order);
        return this;
    }

    public User getApprover() {
        return this.approver;
    }

    public void setApprover(User user) {
        this.approver = user;
    }

    public CancellationDetails approver(User user) {
        this.setApprover(user);
        return this;
    }

    public User getInitiator() {
        return this.initiator;
    }

    public void setInitiator(User user) {
        this.initiator = user;
    }

    public CancellationDetails initiator(User user) {
        this.setInitiator(user);
        return this;
    }

    public Set<DeliveryDetails> getDeliveryDetails() {
        return this.deliveryDetails;
    }

    public void setDeliveryDetails(Set<DeliveryDetails> deliveryDetails) {
        if (this.deliveryDetails != null) {
            this.deliveryDetails.forEach(i -> i.setCancellation(null));
        }
        if (deliveryDetails != null) {
            deliveryDetails.forEach(i -> i.setCancellation(this));
        }
        this.deliveryDetails = deliveryDetails;
    }

    public CancellationDetails deliveryDetails(Set<DeliveryDetails> deliveryDetails) {
        this.setDeliveryDetails(deliveryDetails);
        return this;
    }

    public CancellationDetails addDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails.add(deliveryDetails);
        deliveryDetails.setCancellation(this);
        return this;
    }

    public CancellationDetails removeDeliveryDetails(DeliveryDetails deliveryDetails) {
        this.deliveryDetails.remove(deliveryDetails);
        deliveryDetails.setCancellation(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CancellationDetails)) {
            return false;
        }
        return id != null && id.equals(((CancellationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CancellationDetails{" +
            "id=" + getId() +
            ", cancelationType='" + getCancelationType() + "'" +
            ", cancellationReason='" + getCancellationReason() + "'" +
            ", cancellationDate='" + getCancellationDate() + "'" +
            ", cancellationTime='" + getCancellationTime() + "'" +
            ", refundId='" + getRefundId() + "'" +
            ", cancellationStatus='" + getCancellationStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
