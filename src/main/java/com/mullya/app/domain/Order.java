package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.OrderStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accepted_price")
    private Float acceptedPrice;

    @Column(name = "cod_amount")
    private Float codAmount;

    @Column(name = "quantity_kg")
    private Float quantityKg;

    @Column(name = "needed_by")
    private String neededBy;

    @Column(name = "accepted_delivery_date")
    private String acceptedDeliveryDate;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @OneToMany(mappedBy = "order")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "order" }, allowSetters = true)
    private Set<PaymentDetails> paymentDetails = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_jhi_order__remittance",
        joinColumns = @JoinColumn(name = "jhi_order_id"),
        inverseJoinColumns = @JoinColumn(name = "remittance_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "orders" }, allowSetters = true)
    private Set<RemittanceDetails> remittances = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "buyerActor", "category", "variant", "subVariant" }, allowSetters = true)
    private Requirement requirement;

    @ManyToOne
    @JsonIgnoreProperties(value = { "orders", "buyerAddress", "biddingDetails", "buyer" }, allowSetters = true)
    private Bids bid;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private Actor assignedAgent;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "biddingDetails", "orders", "farmerAddress", "farmer", "category", "variant", "subVariant" },
        allowSetters = true
    )
    private Stock stock;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAcceptedPrice() {
        return this.acceptedPrice;
    }

    public Order acceptedPrice(Float acceptedPrice) {
        this.setAcceptedPrice(acceptedPrice);
        return this;
    }

    public void setAcceptedPrice(Float acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }

    public Float getCodAmount() {
        return this.codAmount;
    }

    public Order codAmount(Float codAmount) {
        this.setCodAmount(codAmount);
        return this;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
    }

    public Float getQuantityKg() {
        return this.quantityKg;
    }

    public Order quantityKg(Float quantityKg) {
        this.setQuantityKg(quantityKg);
        return this;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public String getNeededBy() {
        return this.neededBy;
    }

    public Order neededBy(String neededBy) {
        this.setNeededBy(neededBy);
        return this;
    }

    public void setNeededBy(String neededBy) {
        this.neededBy = neededBy;
    }

    public String getAcceptedDeliveryDate() {
        return this.acceptedDeliveryDate;
    }

    public Order acceptedDeliveryDate(String acceptedDeliveryDate) {
        this.setAcceptedDeliveryDate(acceptedDeliveryDate);
        return this;
    }

    public void setAcceptedDeliveryDate(String acceptedDeliveryDate) {
        this.acceptedDeliveryDate = acceptedDeliveryDate;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Order createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Order createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Order updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Order updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Order status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<PaymentDetails> getPaymentDetails() {
        return this.paymentDetails;
    }

    public void setPaymentDetails(Set<PaymentDetails> paymentDetails) {
        if (this.paymentDetails != null) {
            this.paymentDetails.forEach(i -> i.setOrder(null));
        }
        if (paymentDetails != null) {
            paymentDetails.forEach(i -> i.setOrder(this));
        }
        this.paymentDetails = paymentDetails;
    }

    public Order paymentDetails(Set<PaymentDetails> paymentDetails) {
        this.setPaymentDetails(paymentDetails);
        return this;
    }

    public Order addPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails.add(paymentDetails);
        paymentDetails.setOrder(this);
        return this;
    }

    public Order removePaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails.remove(paymentDetails);
        paymentDetails.setOrder(null);
        return this;
    }

    public Set<RemittanceDetails> getRemittances() {
        return this.remittances;
    }

    public void setRemittances(Set<RemittanceDetails> remittanceDetails) {
        this.remittances = remittanceDetails;
    }

    public Order remittances(Set<RemittanceDetails> remittanceDetails) {
        this.setRemittances(remittanceDetails);
        return this;
    }

    public Order addRemittance(RemittanceDetails remittanceDetails) {
        this.remittances.add(remittanceDetails);
        remittanceDetails.getOrders().add(this);
        return this;
    }

    public Order removeRemittance(RemittanceDetails remittanceDetails) {
        this.remittances.remove(remittanceDetails);
        remittanceDetails.getOrders().remove(this);
        return this;
    }

    public Requirement getRequirement() {
        return this.requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public Order requirement(Requirement requirement) {
        this.setRequirement(requirement);
        return this;
    }

    public Bids getBid() {
        return this.bid;
    }

    public void setBid(Bids bids) {
        this.bid = bids;
    }

    public Order bid(Bids bids) {
        this.setBid(bids);
        return this;
    }

    public Actor getAssignedAgent() {
        return this.assignedAgent;
    }

    public void setAssignedAgent(Actor actor) {
        this.assignedAgent = actor;
    }

    public Order assignedAgent(Actor actor) {
        this.setAssignedAgent(actor);
        return this;
    }

    public Stock getStock() {
        return this.stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Order stock(Stock stock) {
        this.setStock(stock);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", acceptedPrice=" + getAcceptedPrice() +
            ", codAmount=" + getCodAmount() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", acceptedDeliveryDate='" + getAcceptedDeliveryDate() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
