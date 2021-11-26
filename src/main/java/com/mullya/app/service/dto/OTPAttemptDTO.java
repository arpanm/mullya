package com.mullya.app.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mullya.app.domain.OTPAttempt} entity.
 */
public class OTPAttemptDTO implements Serializable {

    private Long id;

    private Integer otpVal;

    private String email;

    private Integer phone;

    private String ip;

    private String coookie;

    private String createdBy;

    private LocalDate createdAt;

    private OTPDTO otp;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCoookie() {
        return coookie;
    }

    public void setCoookie(String coookie) {
        this.coookie = coookie;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public OTPDTO getOtp() {
        return otp;
    }

    public void setOtp(OTPDTO otp) {
        this.otp = otp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPAttemptDTO)) {
            return false;
        }

        OTPAttemptDTO oTPAttemptDTO = (OTPAttemptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, oTPAttemptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPAttemptDTO{" +
            "id=" + getId() +
            ", otpVal=" + getOtpVal() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", ip='" + getIp() + "'" +
            ", coookie='" + getCoookie() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", otp=" + getOtp() +
            "}";
    }
}
