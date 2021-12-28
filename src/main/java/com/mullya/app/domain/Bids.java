package com.mullya.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mullya.app.domain.enumeration.BidStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bids.
 */
@Entity
@Table(name = "bids")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bids extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bid_price")
    private Float bidPrice;

    @Column(name = "quantity_kg")
    private Float quantityKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "bid_status")
    private BidStatus bidStatus;

    @OneToMany(mappedBy = "bid")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentDetails", "remittances", "requirement", "bid", "assignedAgent", "stock" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "hub", "user" }, allowSetters = true)
    private Address buyerAddress;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bids", "stock" }, allowSetters = true)
    private BiddingDetails biddingDetails;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "requirements", "oTPS", "addresses", "stocks", "bids", "orders", "remittanceDetails" },
        allowSetters = true
    )
    private User buyer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bids id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getBidPrice() {
        return this.bidPrice;
    }

    public Bids bidPrice(Float bidPrice) {
        this.setBidPrice(bidPrice);
        return this;
    }

    public void setBidPrice(Float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public Float getQuantityKg() {
        return this.quantityKg;
    }

    public Bids quantityKg(Float quantityKg) {
        this.setQuantityKg(quantityKg);
        return this;
    }

    public void setQuantityKg(Float quantityKg) {
        this.quantityKg = quantityKg;
    }

    public BidStatus getBidStatus() {
        return this.bidStatus;
    }

    public Bids bidStatus(BidStatus bidStatus) {
        this.setBidStatus(bidStatus);
        return this;
    }

    public void setBidStatus(BidStatus bidStatus) {
        this.bidStatus = bidStatus;
    }

    public Set<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(Set<Order> orders) {
        if (this.orders != null) {
            this.orders.forEach(i -> i.setBid(null));
        }
        if (orders != null) {
            orders.forEach(i -> i.setBid(this));
        }
        this.orders = orders;
    }

    public Bids orders(Set<Order> orders) {
        this.setOrders(orders);
        return this;
    }

    public Bids addOrder(Order order) {
        this.orders.add(order);
        order.setBid(this);
        return this;
    }

    public Bids removeOrder(Order order) {
        this.orders.remove(order);
        order.setBid(null);
        return this;
    }

    public Address getBuyerAddress() {
        return this.buyerAddress;
    }

    public void setBuyerAddress(Address address) {
        this.buyerAddress = address;
    }

    public Bids buyerAddress(Address address) {
        this.setBuyerAddress(address);
        return this;
    }

    public BiddingDetails getBiddingDetails() {
        return this.biddingDetails;
    }

    public void setBiddingDetails(BiddingDetails biddingDetails) {
        this.biddingDetails = biddingDetails;
    }

    public Bids biddingDetails(BiddingDetails biddingDetails) {
        this.setBiddingDetails(biddingDetails);
        return this;
    }

    public User getBuyer() {
        return this.buyer;
    }

    public void setBuyer(User user) {
        this.buyer = user;
    }

    public Bids buyer(User user) {
        this.setBuyer(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bids)) {
            return false;
        }
        return id != null && id.equals(((Bids) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bids{" +
            "id=" + getId() +
            ", bidPrice=" + getBidPrice() +
            ", quantityKg=" + getQuantityKg() +
            ", bidStatus='" + getBidStatus() + "'" +
            "}";
    }
}
