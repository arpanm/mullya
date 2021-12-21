package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.OtpStatus;
import com.mullya.app.domain.enumeration.OtpType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OTP.
 */
@Entity
@Table(name = "otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OTP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "otp_val")
    private String otpVal;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OtpType type;

    @NotNull
    @Column(name = "expiry_time")
    private LocalDateTime expiryTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OtpStatus status = OtpStatus.Init;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @NotNull
    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "otp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "otp" }, allowSetters = true)
    private Set<OTPAttempt> oTPAttempts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OTP id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpVal() {
        return this.otpVal;
    }

    public OTP otpVal(String otpVal) {
        this.setOtpVal(otpVal);
        return this;
    }

    public void setOtpVal(String otpVal) {
        this.otpVal = otpVal;
    }

    public String getEmail() {
        return this.email;
    }

    public OTP email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return this.phone;
    }

    public OTP phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public OtpType getType() {
        return this.type;
    }

    public OTP type(OtpType type) {
        this.setType(type);
        return this;
    }

    public void setType(OtpType type) {
        this.type = type;
    }

    public LocalDateTime getExpiryTime() {
        return this.expiryTime;
    }

    public OTP expiryTime(LocalDateTime expiryTime) {
        this.setExpiryTime(expiryTime);
        return this;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public OtpStatus getStatus() {
        return this.status;
    }

    public OTP status(OtpStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OtpStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    public OTP createdOn(LocalDateTime createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public OTP createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedOn() {
        return this.updatedOn;
    }

    public OTP updatedOn(LocalDateTime updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public OTP updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Boolean isActive() {
        return this.isActive;
    }

    public OTP active(Boolean isActive) {
        this.setActive(isActive);
        return this;
    }

    public void setActive(Boolean isActive) {
        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public Set<OTPAttempt> getOTPAttempts() {
        return this.oTPAttempts;
    }

    public void setOTPAttempts(Set<OTPAttempt> oTPAttempts) {
        if (this.oTPAttempts != null) {
            this.oTPAttempts.forEach(i -> i.setOtp(null));
        }
        if (oTPAttempts != null) {
            oTPAttempts.forEach(i -> i.setOtp(this));
        }
        this.oTPAttempts = oTPAttempts;
    }

    public OTP oTPAttempts(Set<OTPAttempt> oTPAttempts) {
        this.setOTPAttempts(oTPAttempts);
        return this;
    }

    public OTP addOTPAttempt(OTPAttempt oTPAttempt) {
        this.oTPAttempts.add(oTPAttempt);
        oTPAttempt.setOtp(this);
        return this;
    }

    public OTP removeOTPAttempt(OTPAttempt oTPAttempt) {
        this.oTPAttempts.remove(oTPAttempt);
        oTPAttempt.setOtp(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OTP user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTP)) {
            return false;
        }
        return id != null && id.equals(((OTP) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTP{" +
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
            "}";
    }
}
