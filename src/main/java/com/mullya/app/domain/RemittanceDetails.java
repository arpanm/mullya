package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.PGType;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.PaymentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RemittanceDetails.
 */
@Entity
@Table(name = "remittance_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RemittanceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "online_pg_type")
    private PGType onlinePgType;

    @Column(name = "pg_txn_id")
    private String pgTxnId;

    @Column(name = "pg_status")
    private String pgStatus;

    @Column(name = "offline_txn_id")
    private String offlineTxnId;

    @Column(name = "offline_txn_details")
    private String offlineTxnDetails;

    @Column(name = "offline_txn_given_by")
    private String offlineTxnGivenBy;

    @Column(name = "offline_txn_clearing_status")
    private String offlineTxnClearingStatus;

    @Column(name = "remittance_date")
    private String remittanceDate;

    @Column(name = "remittance_init_time")
    private LocalDate remittanceInitTime;

    @Column(name = "remittance_update_time")
    private LocalDate remittanceUpdateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private Actor farmer;

    @ManyToMany(mappedBy = "remittances")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RemittanceDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentType getPaymentType() {
        return this.paymentType;
    }

    public RemittanceDetails paymentType(PaymentType paymentType) {
        this.setPaymentType(paymentType);
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public PGType getOnlinePgType() {
        return this.onlinePgType;
    }

    public RemittanceDetails onlinePgType(PGType onlinePgType) {
        this.setOnlinePgType(onlinePgType);
        return this;
    }

    public void setOnlinePgType(PGType onlinePgType) {
        this.onlinePgType = onlinePgType;
    }

    public String getPgTxnId() {
        return this.pgTxnId;
    }

    public RemittanceDetails pgTxnId(String pgTxnId) {
        this.setPgTxnId(pgTxnId);
        return this;
    }

    public void setPgTxnId(String pgTxnId) {
        this.pgTxnId = pgTxnId;
    }

    public String getPgStatus() {
        return this.pgStatus;
    }

    public RemittanceDetails pgStatus(String pgStatus) {
        this.setPgStatus(pgStatus);
        return this;
    }

    public void setPgStatus(String pgStatus) {
        this.pgStatus = pgStatus;
    }

    public String getOfflineTxnId() {
        return this.offlineTxnId;
    }

    public RemittanceDetails offlineTxnId(String offlineTxnId) {
        this.setOfflineTxnId(offlineTxnId);
        return this;
    }

    public void setOfflineTxnId(String offlineTxnId) {
        this.offlineTxnId = offlineTxnId;
    }

    public String getOfflineTxnDetails() {
        return this.offlineTxnDetails;
    }

    public RemittanceDetails offlineTxnDetails(String offlineTxnDetails) {
        this.setOfflineTxnDetails(offlineTxnDetails);
        return this;
    }

    public void setOfflineTxnDetails(String offlineTxnDetails) {
        this.offlineTxnDetails = offlineTxnDetails;
    }

    public String getOfflineTxnGivenBy() {
        return this.offlineTxnGivenBy;
    }

    public RemittanceDetails offlineTxnGivenBy(String offlineTxnGivenBy) {
        this.setOfflineTxnGivenBy(offlineTxnGivenBy);
        return this;
    }

    public void setOfflineTxnGivenBy(String offlineTxnGivenBy) {
        this.offlineTxnGivenBy = offlineTxnGivenBy;
    }

    public String getOfflineTxnClearingStatus() {
        return this.offlineTxnClearingStatus;
    }

    public RemittanceDetails offlineTxnClearingStatus(String offlineTxnClearingStatus) {
        this.setOfflineTxnClearingStatus(offlineTxnClearingStatus);
        return this;
    }

    public void setOfflineTxnClearingStatus(String offlineTxnClearingStatus) {
        this.offlineTxnClearingStatus = offlineTxnClearingStatus;
    }

    public String getRemittanceDate() {
        return this.remittanceDate;
    }

    public RemittanceDetails remittanceDate(String remittanceDate) {
        this.setRemittanceDate(remittanceDate);
        return this;
    }

    public void setRemittanceDate(String remittanceDate) {
        this.remittanceDate = remittanceDate;
    }

    public LocalDate getRemittanceInitTime() {
        return this.remittanceInitTime;
    }

    public RemittanceDetails remittanceInitTime(LocalDate remittanceInitTime) {
        this.setRemittanceInitTime(remittanceInitTime);
        return this;
    }

    public void setRemittanceInitTime(LocalDate remittanceInitTime) {
        this.remittanceInitTime = remittanceInitTime;
    }

    public LocalDate getRemittanceUpdateTime() {
        return this.remittanceUpdateTime;
    }

    public RemittanceDetails remittanceUpdateTime(LocalDate remittanceUpdateTime) {
        this.setRemittanceUpdateTime(remittanceUpdateTime);
        return this;
    }

    public void setRemittanceUpdateTime(LocalDate remittanceUpdateTime) {
        this.remittanceUpdateTime = remittanceUpdateTime;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public RemittanceDetails paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public RemittanceDetails createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RemittanceDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public RemittanceDetails updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public RemittanceDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Actor getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Actor actor) {
        this.farmer = actor;
    }

    public RemittanceDetails farmer(Actor actor) {
        this.setFarmer(actor);
        return this;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.removeRemittance(this));
        }
        if (orders != null) {
            orders.forEach(i -> i.addRemittance(this));
        }
        this.orders = orders;
    }

    public RemittanceDetails orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public RemittanceDetails addOrder(Order order) {
        this.orders.add(order);
        order.getRemittances().add(this);
        return this;
    }

    public RemittanceDetails removeOrder(Order order) {
        this.orders.remove(order);
        order.getRemittances().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemittanceDetails)) {
            return false;
        }
        return id != null && id.equals(((RemittanceDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RemittanceDetails{" +
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
            "}";
    }
}
