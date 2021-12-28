package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A OTPAttempt.
 */
@Entity
@Table(name = "otp_attempt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OTPAttempt extends AbstractAuditingEntity implements Serializable {

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
    private Integer phone;

    @Column(name = "ip")
    private String ip;

    @Column(name = "coookie")
    private String coookie;

    @ManyToOne
    @JsonIgnoreProperties(value = { "oTPAttempts", "user" }, allowSetters = true)
    private OTP otp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OTPAttempt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtpVal() {
        return this.otpVal;
    }

    public OTPAttempt otpVal(String otpVal) {
        this.setOtpVal(otpVal);
        return this;
    }

    public void setOtpVal(String otpVal) {
        this.otpVal = otpVal;
    }

    public String getEmail() {
        return this.email;
    }

    public OTPAttempt email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhone() {
        return this.phone;
    }

    public OTPAttempt phone(Integer phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getIp() {
        return this.ip;
    }

    public OTPAttempt ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCoookie() {
        return this.coookie;
    }

    public OTPAttempt coookie(String coookie) {
        this.setCoookie(coookie);
        return this;
    }

    public void setCoookie(String coookie) {
        this.coookie = coookie;
    }

    public OTP getOtp() {
        return this.otp;
    }

    public void setOtp(OTP oTP) {
        this.otp = oTP;
    }

    public OTPAttempt otp(OTP oTP) {
        this.setOtp(oTP);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OTPAttempt)) {
            return false;
        }
        return id != null && id.equals(((OTPAttempt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OTPAttempt{" +
            "id=" + getId() +
            ", otpVal=" + getOtpVal() +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", ip='" + getIp() + "'" +
            ", coookie='" + getCoookie() + "'" +
            "}";
    }
}
