package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.PGType;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.PaymentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.PaymentDetails} entity.
 */
public class PaymentDetailsDTO implements Serializable {

    private Long id;

    private Float amount;

    private PaymentType paymentType;

    private PGType onlinePgType;

    private String pgTxnId;

    private String pgStatus;

    private String offlineTxnId;

    private String offlineTxnDetails;

    private String offlineTxnCollectedBy;

    private String offlineTxnClearingStatus;

    private String paymentDate;

    private LocalDate paymentInitTime;

    private LocalDate paymentUpdateTime;

    private PaymentStatus paymentStatus;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private OrderDTO order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PGType getOnlinePgType() {
        return onlinePgType;
    }

    public void setOnlinePgType(PGType onlinePgType) {
        this.onlinePgType = onlinePgType;
    }

    public String getPgTxnId() {
        return pgTxnId;
    }

    public void setPgTxnId(String pgTxnId) {
        this.pgTxnId = pgTxnId;
    }

    public String getPgStatus() {
        return pgStatus;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getOfflineTxnId() {
        return offlineTxnId;
    }

    public void setOfflineTxnId(String offlineTxnId) {
        this.offlineTxnId = offlineTxnId;
    }

    public String getOfflineTxnDetails() {
        return offlineTxnDetails;
    }

    public void setOfflineTxnDetails(String offlineTxnDetails) {
        this.offlineTxnDetails = offlineTxnDetails;
    }

    public String getOfflineTxnCollectedBy() {
        return offlineTxnCollectedBy;
    }

    public void setOfflineTxnCollectedBy(String offlineTxnCollectedBy) {
        this.offlineTxnCollectedBy = offlineTxnCollectedBy;
    }

    public String getOfflineTxnClearingStatus() {
        return offlineTxnClearingStatus;
    }

    public void setOfflineTxnClearingStatus(String offlineTxnClearingStatus) {
        this.offlineTxnClearingStatus = offlineTxnClearingStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDate getPaymentInitTime() {
        return paymentInitTime;
    }

    public void setPaymentInitTime(LocalDate paymentInitTime) {
        this.paymentInitTime = paymentInitTime;
    }

    public LocalDate getPaymentUpdateTime() {
        return paymentUpdateTime;
    }

    public void setPaymentUpdateTime(LocalDate paymentUpdateTime) {
        this.paymentUpdateTime = paymentUpdateTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDetailsDTO)) {
            return false;
        }

        PaymentDetailsDTO paymentDetailsDTO = (PaymentDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDetailsDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", paymentType='" + getPaymentType() + "'" +
            ", onlinePgType='" + getOnlinePgType() + "'" +
            ", pgTxnId='" + getPgTxnId() + "'" +
            ", pgStatus='" + getPgStatus() + "'" +
            ", offlineTxnId='" + getOfflineTxnId() + "'" +
            ", offlineTxnDetails='" + getOfflineTxnDetails() + "'" +
            ", offlineTxnCollectedBy='" + getOfflineTxnCollectedBy() + "'" +
            ", offlineTxnClearingStatus='" + getOfflineTxnClearingStatus() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", paymentInitTime='" + getPaymentInitTime() + "'" +
            ", paymentUpdateTime='" + getPaymentUpdateTime() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
