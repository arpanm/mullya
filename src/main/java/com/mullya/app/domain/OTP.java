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

    @Column(name = "otp")
    private Integer otp;

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

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "otp")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "otp" }, allowSetters = true)
    private Set<OTPAttempt> oTPAttempts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "requirements", "oTPS" }, allowSetters = true)
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

    public Integer getOtp() {
        return this.otp;
    }

    public OTP otp(Integer otp) {
        this.setOtp(otp);
        return this;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
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

    public LocalDate getCreatedAt() {
        return this.createdAt;
    }

    public OTP createdAt(LocalDate createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
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

    public LocalDate getUpdatedAt() {
        return this.updatedAt;
    }

    public OTP updatedAt(LocalDate updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
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
            ", otp=" + getOtp() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", type='" + getType() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
