package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.OtpStatus;
import com.mullya.app.domain.enumeration.OtpType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
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
    private Integer otpVal;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Integer phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private OtpType type;

    @Column(name = "expiry_time")
    private LocalDate expiryTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OtpStatus status;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "otp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "otp" }, allowSetters = true)
    private Set<OTPAttempt> oTPAttempts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private Actor actor;

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

    public Integer getOtpVal() {
        return this.otpVal;
    }

    public OTP otpVal(Integer otpVal) {
        this.setOtpVal(otpVal);
        return this;
    }

    public void setOtpVal(Integer otpVal) {
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

    public Integer getPhone() {
        return this.phone;
    }

    public OTP phone(Integer phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Integer phone) {
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

    public LocalDate getExpiryTime() {
        return this.expiryTime;
    }

    public OTP expiryTime(LocalDate expiryTime) {
        this.setExpiryTime(expiryTime);
        return this;
    }

    public void setExpiryTime(LocalDate expiryTime) {
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
        this.status = status;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public OTP createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
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

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public OTP updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
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

    public Actor getActor() {
        return this.actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public OTP actor(Actor actor) {
        this.setActor(actor);
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
