package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.DeliveryStatus;
import com.mullya.app.domain.enumeration.PaymentStatus;
import com.mullya.app.domain.enumeration.RequirementStatus;
import com.mullya.app.domain.enumeration.StockCategory;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requirement.
 */
@Entity
@Table(name = "requirement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requirement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private StockCategory category;

    @Column(name = "variant")
    private String variant;

    @Column(name = "sub_variant")
    private String subVariant;

    @Column(name = "min_price")
    private Float minPrice;

    @Column(name = "max_price")
    private Float maxPrice;

    @Column(name = "accepted_price")
    private Float acceptedPrice;

    @Column(name = "cod_amount")
    private Float codAmount;

    @Column(name = "quantity_kg")
    private Float quantityKg;

    @Column(name = "needed_by")
    private String neededBy;

    @Column(name = "payment_date")
    private String paymentDate;

    @Column(name = "accepted_delivery_date")
    private String acceptedDeliveryDate;

    @Column(name = "description")
    private String description;

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
    private RequirementStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actor", "requirements", "orderRequirements" }, allowSetters = true)
    private Address buyerAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = { "actor", "requirements", "orderRequirements" }, allowSetters = true)
    private Address farmerAddress;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "acceptedRequirements", "assignedRequirements", "oTPS", "addresses" },
        allowSetters = true
    )
    private Actor buyerActor;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "acceptedRequirements", "assignedRequirements", "oTPS", "addresses" },
        allowSetters = true
    )
    private Actor acceptedAgentActor;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "acceptedRequirements", "assignedRequirements", "oTPS", "addresses" },
        allowSetters = true
    )
    private Actor farmerActor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Requirement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockCategory getCategory() {
        return this.category;
    }

    public Requirement category(StockCategory category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(StockCategory category) {
        this.category = category;
    }

    public String getVariant() {
        return this.variant;
    }

    public Requirement variant(String variant) {
        this.setVariant(variant);
        return this;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getSubVariant() {
        return this.subVariant;
    }

    public Requirement subVariant(String subVariant) {
        this.setSubVariant(subVariant);
        return this;
    }

    public void setSubVariant(String subVariant) {
        this.subVariant = subVariant;
    }

    public Float getMinPrice() {
        return this.minPrice;
    }

    public Requirement minPrice(Float minPrice) {
        this.setMinPrice(minPrice);
        return this;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return this.maxPrice;
    }

    public Requirement maxPrice(Float maxPrice) {
        this.setMaxPrice(maxPrice);
        return this;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Float getAcceptedPrice() {
        return this.acceptedPrice;
    }

    public Requirement acceptedPrice(Float acceptedPrice) {
        this.setAcceptedPrice(acceptedPrice);
        return this;
    }

    public void setAcceptedPrice(Float acceptedPrice) {
        this.acceptedPrice = acceptedPrice;
    }

    public Float getCodAmount() {
        return this.codAmount;
    }

    public Requirement codAmount(Float codAmount) {
        this.setCodAmount(codAmount);
        return this;
    }

    public void setCodAmount(Float codAmount) {
        this.codAmount = codAmount;
    }

    public Float getQuantityKg() {
        return this.quantityKg;
    }

    public Requirement quantityKg(Float quantityKg) {
        this.setQuantityKg(quantityKg);
        return this;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public String getNeededBy() {
        return this.neededBy;
    }

    public Requirement neededBy(String neededBy) {
        this.setNeededBy(neededBy);
        return this;
    }

    public void setNeededBy(String neededBy) {
        this.neededBy = neededBy;
    }

    public String getPaymentDate() {
        return this.paymentDate;
    }

    public Requirement paymentDate(String paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getAcceptedDeliveryDate() {
        return this.acceptedDeliveryDate;
    }

    public Requirement acceptedDeliveryDate(String acceptedDeliveryDate) {
        this.setAcceptedDeliveryDate(acceptedDeliveryDate);
        return this;
    }

    public void setAcceptedDeliveryDate(String acceptedDeliveryDate) {
        this.acceptedDeliveryDate = acceptedDeliveryDate;
    }

    public String getDescription() {
        return this.description;
    }

    public Requirement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return this.createdOn;
    }

    public Requirement createdOn(LocalDate createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Requirement createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getUpdatedOn() {
        return this.updatedOn;
    }

    public Requirement updatedOn(LocalDate updatedOn) {
        this.setUpdatedOn(updatedOn);
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Requirement updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public RequirementStatus getStatus() {
        return this.status;
    }

    public Requirement status(RequirementStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequirementStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public Requirement paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public DeliveryStatus getDeliveryStatus() {
        return this.deliveryStatus;
    }

    public Requirement deliveryStatus(DeliveryStatus deliveryStatus) {
        this.setDeliveryStatus(deliveryStatus);
        return this;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Address getBuyerAddress() {
        return this.buyerAddress;
    }

    public void setBuyerAddress(Address address) {
        this.buyerAddress = address;
    }

    public Requirement buyerAddress(Address address) {
        this.setBuyerAddress(address);
        return this;
    }

    public Address getFarmerAddress() {
        return this.farmerAddress;
    }

    public void setFarmerAddress(Address address) {
        this.farmerAddress = address;
    }

    public Requirement farmerAddress(Address address) {
        this.setFarmerAddress(address);
        return this;
    }

    public Actor getBuyerActor() {
        return this.buyerActor;
    }

    public void setBuyerActor(Actor actor) {
        this.buyerActor = actor;
    }

    public Requirement buyerActor(Actor actor) {
        this.setBuyerActor(actor);
        return this;
    }

    public Actor getAcceptedAgentActor() {
        return this.acceptedAgentActor;
    }

    public void setAcceptedAgentActor(Actor actor) {
        this.acceptedAgentActor = actor;
    }

    public Requirement acceptedAgentActor(Actor actor) {
        this.setAcceptedAgentActor(actor);
        return this;
    }

    public Actor getFarmerActor() {
        return this.farmerActor;
    }

    public void setFarmerActor(Actor actor) {
        this.farmerActor = actor;
    }

    public Requirement farmerActor(Actor actor) {
        this.setFarmerActor(actor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requirement)) {
            return false;
        }
        return id != null && id.equals(((Requirement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Requirement{" +
            "id=" + getId() +
            ", category='" + getCategory() + "'" +
            ", variant='" + getVariant() + "'" +
            ", subVariant='" + getSubVariant() + "'" +
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", acceptedPrice=" + getAcceptedPrice() +
            ", codAmount=" + getCodAmount() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", acceptedDeliveryDate='" + getAcceptedDeliveryDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", deliveryStatus='" + getDeliveryStatus() + "'" +
            "}";
    }
}
