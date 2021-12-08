package com.mullya.app.service.dto;

import com.mullya.app.domain.enumeration.OtpStatus;
import com.mullya.app.domain.enumeration.OtpType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.OTP} entity.
 */
public class OTPDTO implements Serializable {

    private Long id;

    private Integer otpVal;

    private String email;

    private Integer phone;

    private OtpType type;

    private LocalDate expiryTime;

    private OtpStatus status;

    private LocalDate createdOn;

    private String createdBy;

    private LocalDate updatedOn;

    private String updatedBy;

    private ActorDTO actor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOtpVal() {
        return otpVal;
    }

    public void setOtpVal(Integer otpVal) {
        this.otpVal = otpVal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public OtpType getType() {
        return type;
    }

    public void setType(OtpType type) {
        this.type = type;
    }

    public LocalDate getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDate expiryTime) {
        this.expiryTime = expiryTime;
    }

    public OtpStatus getStatus() {
        return status;
    }

    public void setStatus(OtpStatus status) {
        this.status = status;
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

    public ActorDTO getActor() {
        return actor;
    }

    public void setActor(ActorDTO actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPDTO)) {
            return false;
        }

        OTPDTO oTPDTO = (OTPDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oTPDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPDTO{" +
            "id=" + getId() +
            ", otpVal=" + getOtpVal() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", type='" + getType() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", actor=" + getActor() +
            "}";
    }
}
