package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.PGType;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.PaymentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.RemittanceDetails} entity.
 */
public class RemittanceDetailsDTO implements Serializable {

    private Long id;

    private PaymentType paymentType;

    private PGType onlinePgType;

    private String pgTxnId;

    private String pgStatus;

    private String offlineTxnId;

    private String offlineTxnDetails;

    private String offlineTxnGivenBy;

    private String offlineTxnClearingStatus;

    private String remittanceDate;

    private LocalDate remittanceInitTime;

    private LocalDate remittanceUpdateTime;

    private PaymentStatus paymentStatus;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private UserDTO farmer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOfflineTxnGivenBy() {
        return offlineTxnGivenBy;
    }

    public void setOfflineTxnGivenBy(String offlineTxnGivenBy) {
        this.offlineTxnGivenBy = offlineTxnGivenBy;
    }

    public String getOfflineTxnClearingStatus() {
        return offlineTxnClearingStatus;
    }

    public void setOfflineTxnClearingStatus(String offlineTxnClearingStatus) {
        this.offlineTxnClearingStatus = offlineTxnClearingStatus;
    }

    public String getRemittanceDate() {
        return remittanceDate;
    }

    public void setRemittanceDate(String remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public LocalDate getRemittanceInitTime() {
        return remittanceInitTime;
    }

    public void setRemittanceInitTime(LocalDate remittanceInitTime) {
        this.remittanceInitTime = remittanceInitTime;
    }

    public LocalDate getRemittanceUpdateTime() {
        return remittanceUpdateTime;
    }

    public void setRemittanceUpdateTime(LocalDate remittanceUpdateTime) {
        this.remittanceUpdateTime = remittanceUpdateTime;
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

    public UserDTO getFarmer() {
        return farmer;
    }

    public void setFarmer(UserDTO farmer) {
        this.farmer = farmer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemittanceDetailsDTO)) {
            return false;
        }

        RemittanceDetailsDTO remittanceDetailsDTO = (RemittanceDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remittanceDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemittanceDetailsDTO{" +
            "id=" + getId() +
            ", paymentType='" + getPaymentType() + "'" +
            ", onlinePgType='" + getOnlinePgType() + "'" +
            ", pgTxnId='" + getPgTxnId() + "'" +
            ", pgStatus='" + getPgStatus() + "'" +
            ", offlineTxnId='" + getOfflineTxnId() + "'" +
            ", offlineTxnDetails='" + getOfflineTxnDetails() + "'" +
            ", offlineTxnGivenBy='" + getOfflineTxnGivenBy() + "'" +
            ", offlineTxnClearingStatus='" + getOfflineTxnClearingStatus() + "'" +
            ", remittanceDate='" + getRemittanceDate() + "'" +
            ", remittanceInitTime='" + getRemittanceInitTime() + "'" +
            ", remittanceUpdateTime='" + getRemittanceUpdateTime() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", farmer=" + getFarmer() +
            "}";
    }
}
