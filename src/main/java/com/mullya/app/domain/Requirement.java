package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.RequirementStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Requirement.
 */
@Entity
@Table(name = "requirement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Requirement extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "min_price")
    private Float minPrice;

    @Column(name = "max_price")
    private Float maxPrice;

    @Column(name = "quantity_kg")
    private Float quantityKg;

    @Column(name = "needed_by")
    private String neededBy;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequirementStatus status = RequirementStatus.New;

    @OneToMany(mappedBy = "requirement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "hub", "user" }, allowSetters = true)
    private Address buyerAddress;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private User buyerUser;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "catalogues",
            "categoryStocks",
            "variantStocks",
            "subVariantStocks",
            "categoryRequirements",
            "variantRequirements",
            "subVariantRequirements",
            "parent",
        },
        allowSetters = true
    )
    private Catalogue category;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "catalogues",
            "categoryStocks",
            "variantStocks",
            "subVariantStocks",
            "categoryRequirements",
            "variantRequirements",
            "subVariantRequirements",
            "parent",
        },
        allowSetters = true
    )
    private Catalogue variant;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "catalogues",
            "categoryStocks",
            "variantStocks",
            "subVariantStocks",
            "categoryRequirements",
            "variantRequirements",
            "subVariantRequirements",
            "parent",
        },
        allowSetters = true
    )
    private Catalogue subVariant;

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

    public RequirementStatus getStatus() {
        return this.status;
    }

    public Requirement status(RequirementStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequirementStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setRequirement(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setRequirement(this));
        }
        this.orders = orders;
    }

    public Requirement orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Requirement addOrder(Order order) {
        this.orders.add(order);
        order.setRequirement(this);
        return this;
    }

    public Requirement removeOrder(Order order) {
        this.orders.remove(order);
        order.setRequirement(null);
        return this;
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

    public User getBuyerUser() {
        return this.buyerUser;
    }

    public void setBuyerUser(User user) {
        this.buyerUser = user;
    }

    public Requirement buyerUser(User user) {
        this.setBuyerUser(user);
        return this;
    }

    public Catalogue getCategory() {
        return this.category;
    }

    public void setCategory(Catalogue catalogue) {
        this.category = catalogue;
    }

    public Requirement category(Catalogue catalogue) {
        this.setCategory(catalogue);
        return this;
    }

    public Catalogue getVariant() {
        return this.variant;
    }

    public void setVariant(Catalogue catalogue) {
        this.variant = catalogue;
    }

    public Requirement variant(Catalogue catalogue) {
        this.setVariant(catalogue);
        return this;
    }

    public Catalogue getSubVariant() {
        return this.subVariant;
    }

    public void setSubVariant(Catalogue catalogue) {
        this.subVariant = catalogue;
    }

    public Requirement subVariant(Catalogue catalogue) {
        this.setSubVariant(catalogue);
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
            ", minPrice=" + getMinPrice() +
            ", maxPrice=" + getMaxPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", neededBy='" + getNeededBy() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
