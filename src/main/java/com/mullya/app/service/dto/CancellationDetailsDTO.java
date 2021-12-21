package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.CancelationType;
import com.mullya.app.domain.enumeration.CancellationStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.CancellationDetails} entity.
 */
public class CancellationDetailsDTO implements Serializable {

    private Long id;

    private CancelationType cancelationType;

    private String cancellationReason;

    private String cancellationDate;

    private LocalDate cancellationTime;

    private String refundId;

    private CancellationStatus cancellationStatus;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private OrderDTO order;

    private UserDTO approver;

    private UserDTO initiator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CancelationType getCancelationType() {
        return cancelationType;
    }

    public void setCancelationType(CancelationType cancelationType) {
        this.cancelationType = cancelationType;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public LocalDate getCancellationTime() {
        return cancellationTime;
    }

    public void setCancellationTime(LocalDate cancellationTime) {
        this.cancellationTime = cancellationTime;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public CancellationStatus getCancellationStatus() {
        return cancellationStatus;
    }

    public void setCancellationStatus(CancellationStatus cancellationStatus) {
        this.cancellationStatus = cancellationStatus;
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

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public UserDTO getApprover() {
        return approver;
    }

    public void setApprover(UserDTO approver) {
        this.approver = approver;
    }

    public UserDTO getInitiator() {
        return initiator;
    }

    public void setInitiator(UserDTO initiator) {
        this.initiator = initiator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CancellationDetailsDTO)) {
            return false;
        }

        CancellationDetailsDTO cancellationDetailsDTO = (CancellationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cancellationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CancellationDetailsDTO{" +
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
            ", order=" + getOrder() +
            ", approver=" + getApprover() +
            ", initiator=" + getInitiator() +
            "}";
    }
}
